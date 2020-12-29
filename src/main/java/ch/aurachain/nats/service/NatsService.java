package ch.aurachain.nats.service;

import io.nats.client.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Service
public class NatsService {

    private Connection connection;

    @PostConstruct
    private void initConnection() throws IOException, InterruptedException {
        this.connection = Nats.connect("nats://localhost:4222");
    }

    public void publishing(String subject, String text) {
        connection.publish(subject, text.getBytes(StandardCharsets.UTF_8));
    }

    public String subscription(String subject) throws InterruptedException {
        Subscription sub = connection.subscribe(subject);
        Message msg = sub.nextMessage(Duration.ofMillis(0));

        if(msg != null){
            return new String(msg.getData(), StandardCharsets.UTF_8);
        }
        return null;
    }

    public void dispacher(String subject) {
        Dispatcher dispatcher = connection.createDispatcher((msg) -> {
        });
        Subscription subscribe = dispatcher.subscribe(subject, (msg) -> {
            String response = new String(msg.getData(), StandardCharsets.UTF_8);
            System.out.println("Message received: " + response);
        });
    }
}
