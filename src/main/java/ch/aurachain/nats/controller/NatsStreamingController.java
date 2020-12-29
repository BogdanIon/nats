package ch.aurachain.nats.controller;

import ch.aurachain.nats.service.NatsStreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/nats-streaming")
public class NatsStreamingController {

    @Autowired
    private NatsStreamingService natsStreamingService;

    @GetMapping("/publish/{subject}/{text}")
    public void publishing(@PathVariable String subject, @PathVariable String text) throws InterruptedException, IOException, TimeoutException {
        natsStreamingService.publishing(subject, text);
    }

    @GetMapping("/subscription/{subject}")
    public void subscription(@PathVariable String subject) throws InterruptedException, IOException, TimeoutException {
        natsStreamingService.subscription(subject);
    }

    @GetMapping("/unsubscribe")
    public void unsubscribe() throws IOException {
        natsStreamingService.unsubscribe();
    }

}
