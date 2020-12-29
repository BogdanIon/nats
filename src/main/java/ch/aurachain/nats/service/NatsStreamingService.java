package ch.aurachain.nats.service;

import io.nats.streaming.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Service
public class NatsStreamingService {

    private StreamingConnection streamingConnection;

    private Subscription subscription;

    @PostConstruct
    private void initConnection() throws IOException, InterruptedException {
        io.nats.streaming.Options options = new io.nats.streaming.Options.Builder().clusterId("test-cluster").clientId("client-1").natsUrl("nats://localhost:4222").build();
        this.streamingConnection = new StreamingConnectionFactory(options).createConnection();
    }

    public void publishing(String subject, String text) throws InterruptedException, TimeoutException, IOException {
        streamingConnection.publish(subject, text.getBytes(StandardCharsets.UTF_8));
    }

    public void subscription(String subject) throws InterruptedException, IOException, TimeoutException {
        this.subscription = streamingConnection.subscribe(subject, new MessageHandler() {
            public void onMessage(Message m) {
                System.out.printf("Received a message: %s\n", new String(m.getData()));
            }
        }, new SubscriptionOptions.Builder().durableName("durable").build());
    }

    public void unsubscribe() throws IOException {
        subscription.unsubscribe();
    }
}
