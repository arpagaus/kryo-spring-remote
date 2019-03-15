package io.github.arpagaus.spring.remoting.kryo.example.api;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import io.github.arpagaus.spring.remoting.kryo.KryoProvider;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class KryoConfiguration {

    @Bean
    public KryoProvider kryoSupplier() {
        return KryoProvider.threadLocalProvider(this::createKryo);
    }

    private Kryo createKryo() {
        Kryo kryo = new Kryo();
        kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));

        // Spring Remoting
        kryo.register(Class.class);
        kryo.register(Class[].class);
        kryo.register(Object[].class);
        kryo.register(RemoteInvocation.class);
        kryo.register(RemoteInvocationResult.class);

        // Example Service
        kryo.register(IMailService.Message.class);
        kryo.register(LocalDate.class);
        kryo.register(LocalTime.class);
        kryo.register(LocalDateTime.class);

        return kryo;
    }
}
