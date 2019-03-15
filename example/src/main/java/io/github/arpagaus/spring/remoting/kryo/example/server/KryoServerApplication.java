package io.github.arpagaus.spring.remoting.kryo.example.server;

import io.github.arpagaus.spring.remoting.kryo.KryoProvider;
import io.github.arpagaus.spring.remoting.kryo.example.api.IMailService;
import io.github.arpagaus.spring.remoting.kryo.example.api.KryoConfiguration;
import io.github.arpagaus.spring.remoting.kryo.server.KryoServiceExporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.support.RemoteExporter;

@SpringBootApplication(scanBasePackageClasses = KryoConfiguration.class)
public class KryoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KryoServerApplication.class, args);
    }

    @Bean(name = "/mail")
    public RemoteExporter mailService(KryoProvider kryoProvider) {
        KryoServiceExporter exporter = new KryoServiceExporter(kryoProvider);
        exporter.setService(new MailService());
        exporter.setServiceInterface(IMailService.class);
        return exporter;
    }
}
