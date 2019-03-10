package io.github.arpagaus.spring.remoting.kryo.example.server;

import io.github.arpagaus.spring.remoting.kryo.example.api.IMailService;

public class MailService implements IMailService {

    @Override
    public Message send(Message message) {
        return message;
    }
}
