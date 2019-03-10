package io.github.arpagaus.spring.remoting.kryo.example.api;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.TimeSerializers;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Supplier;

@Configuration
public class KryoConfiguration {

    @Bean
    public Supplier<Kryo> kryoSupplier() {
        return this::createKryo;
    }

    private Kryo createKryo() {
        Kryo kryo = new Kryo();

        kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));

        TimeSerializers.addDefaultSerializers(kryo);

        kryo.register(Object[].class);
        kryo.register(Class.class);
        kryo.register(Class[].class);

        kryo.register(RemoteInvocation.class);
        kryo.register(RemoteInvocationResult.class);

        kryo.register(LocalDate.class);
        kryo.register(LocalTime.class);
        kryo.register(LocalDateTime.class);

        kryo.register(IMailService.Message.class);

        return kryo;
    }
}
