package io.github.arpagaus.spring.remoting.kryo.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.github.arpagaus.spring.remoting.kryo.KryoProvider;
import io.github.arpagaus.spring.remoting.kryo.KryoSpringRemote;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

import java.io.InputStream;
import java.io.OutputStream;

public class KryoSimpleHttpInvokerRequestExecutor extends SimpleHttpInvokerRequestExecutor {

    private final KryoProvider kryoProvider;

    public KryoSimpleHttpInvokerRequestExecutor(KryoProvider kryoProvider) {
        this.kryoProvider = kryoProvider;
        setContentType(KryoSpringRemote.DEFAULT_MEDIA_TYPE.toString());
    }

    @Override
    protected void writeRemoteInvocation(RemoteInvocation invocation, OutputStream os) {
        Kryo kryo = kryoProvider.get();
        try (Output output = new Output(os)) {
            kryo.writeObject(output, invocation);
        }
    }

    @Override
    protected RemoteInvocationResult readRemoteInvocationResult(InputStream is, String codebaseUrl) {
        Kryo kryo = kryoProvider.get();
        try (Input input = new Input(is)) {
            return kryo.readObjectOrNull(input, RemoteInvocationResult.class);
        }
    }
}
