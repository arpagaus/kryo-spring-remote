package io.github.arpagaus.spring.remoting.kryo.example.client;

import io.github.arpagaus.spring.remoting.kryo.KryoProvider;
import io.github.arpagaus.spring.remoting.kryo.client.KryoHttpComponentsHttpInvokerRequestExecutor;
import io.github.arpagaus.spring.remoting.kryo.example.api.IMailService;
import io.github.arpagaus.spring.remoting.kryo.example.api.KryoConfiguration;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import java.time.LocalDateTime;

@SpringBootApplication(scanBasePackageClasses = KryoConfiguration.class)
public class KryoClientApplication {

    public static void main(String[] args) {
        IMailService remoteServiceProxy = new SpringApplicationBuilder()
                .sources(KryoClientApplication.class)
                .web(WebApplicationType.NONE)
                .build()
                .run(args)
                .getBean(IMailService.class);

        IMailService.Message request = new IMailService.Message("Subject", "Message", LocalDateTime.now());
        IMailService.Message response = remoteServiceProxy.send(request);
        System.out.println(response.equals(request));
    }

    @Bean
    public HttpInvokerProxyFactoryBean hessianInvoker(KryoProvider kryoProvider) {
        KryoHttpComponentsHttpInvokerRequestExecutor requestExecutor = new KryoHttpComponentsHttpInvokerRequestExecutor(kryoProvider);
        requestExecutor.setHttpClient(HttpClients.createSystem());

        HttpInvokerProxyFactoryBean remoteAccessor = new HttpInvokerProxyFactoryBean();
        remoteAccessor.setHttpInvokerRequestExecutor(requestExecutor);
        remoteAccessor.setServiceUrl("http://localhost:8080/mail");
        remoteAccessor.setServiceInterface(IMailService.class);
        return remoteAccessor;
    }
}
