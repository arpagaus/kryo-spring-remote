package io.github.arpagaus.spring.remoting.kryo.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.github.arpagaus.spring.remoting.kryo.KryoProvider;
import io.github.arpagaus.spring.remoting.kryo.KryoSpringRemote;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationBasedExporter;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class KryoServiceExporter extends RemoteInvocationBasedExporter implements HttpRequestHandler, InitializingBean {

    private final MediaType mediaType;
    private final KryoProvider kryoProvider;

    private Object proxy;

    public KryoServiceExporter(KryoProvider kryoProvider) {
        this(KryoSpringRemote.DEFAULT_MEDIA_TYPE, kryoProvider);
    }

    public KryoServiceExporter(MediaType mediaType, KryoProvider kryoProvider) {
        this.mediaType = mediaType;
        this.kryoProvider = kryoProvider;
    }

    @Override
    public void afterPropertiesSet() {
        this.proxy = getProxyForService();
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Kryo kryo = kryoProvider.get();

        final RemoteInvocation remoteInvocation;
        try (Input input = new Input(request.getInputStream())) {
            remoteInvocation = kryo.readObjectOrNull(input, RemoteInvocation.class);
        }

        final RemoteInvocationResult remoteInvocationResult = invokeAndCreateResult(remoteInvocation, this.proxy);

        response.setContentType(mediaType.toString());
        try (Output output = new Output(response.getOutputStream())) {
            kryo.writeObject(output, remoteInvocationResult);
        }
    }
}
