package io.github.arpagaus.spring.remoting.kryo.example.server;

import com.esotericsoftware.kryo.Kryo;
import io.github.arpagaus.spring.remoting.kryo.example.api.IMailService;
import io.github.arpagaus.spring.remoting.kryo.example.api.KryoConfiguration;
import io.github.arpagaus.spring.remoting.kryo.server.KryoServiceExporter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

import java.util.function.Supplier;

@SpringBootApplication(scanBasePackageClasses = KryoConfiguration.class)
public class KryoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KryoServerApplication.class, args);
    }

    @Bean(name = "/mail")
    RemoteExporter mailService(Supplier<Kryo> kryoSupplier) {
        KryoServiceExporter exporter = new KryoServiceExporter(kryoSupplier);
        exporter.setService(new MailService());
        exporter.setServiceInterface(IMailService.class);
        return exporter;
    }
}
