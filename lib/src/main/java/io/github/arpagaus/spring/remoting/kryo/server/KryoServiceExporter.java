package io.github.arpagaus.spring.remoting.kryo.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
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
import java.util.function.Supplier;

public class KryoServiceExporter extends RemoteInvocationBasedExporter implements HttpRequestHandler, InitializingBean {

    private final MediaType mediaType;
    private final Supplier<Kryo> kryoSupplier;

    private Object proxy;

    public KryoServiceExporter(Supplier<Kryo> kryoSupplier) {
        this(kryoSupplier, KryoSpringRemote.DEFAULT_MEDIA_TYPE);
    }

    public KryoServiceExporter(Supplier<Kryo> kryoSupplier, MediaType mediaType) {
        this.kryoSupplier = kryoSupplier;
        this.mediaType = mediaType;
    }

    @Override
    public void afterPropertiesSet() {
        this.proxy = getProxyForService();
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Kryo kryo = kryoSupplier.get();

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
