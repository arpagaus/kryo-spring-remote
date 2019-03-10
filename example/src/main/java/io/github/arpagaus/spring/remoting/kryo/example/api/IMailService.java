package io.github.arpagaus.spring.remoting.kryo.example.api;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public interface IMailService {

    class Message {
        private final String subject;
        private final String message;
        private final LocalDateTime dateTime;

        public Message(String subject, String message, LocalDateTime dateTime) {
            this.subject = subject;
            this.message = message;
            this.dateTime = dateTime;
        }

        public String getSubject() {
            return subject;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Message message1 = (Message) o;
            return subject.equals(message1.subject) &&
                    message.equals(message1.message) &&
                    dateTime.equals(message1.dateTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(subject, message, dateTime);
        }
    }

    Message send(Message message);
}
