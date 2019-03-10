package io.github.arpagaus.spring.remoting.kryo.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.github.arpagaus.spring.remoting.kryo.KryoSpringRemote;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;

public class KryoHttpComponentsHttpInvokerRequestExecutor extends HttpComponentsHttpInvokerRequestExecutor {

    private final Supplier<Kryo> kryoSupplier;

    public KryoHttpComponentsHttpInvokerRequestExecutor(Supplier<Kryo> kryoSupplier) {
        this.kryoSupplier = kryoSupplier;
        setContentType(KryoSpringRemote.DEFAULT_MEDIA_TYPE.toString());
    }

    @Override
    protected void writeRemoteInvocation(RemoteInvocation invocation, OutputStream os) {
        Kryo kryo = kryoSupplier.get();
        try (Output output = new Output(os)) {
            kryo.writeObject(output, invocation);
        }
    }

    @Override
    protected RemoteInvocationResult readRemoteInvocationResult(InputStream is, String codebaseUrl) {
        Kryo kryo = kryoSupplier.get();
        try (Input input = new Input(is)) {
            return kryo.readObjectOrNull(input, RemoteInvocationResult.class);
        }
    }
}
