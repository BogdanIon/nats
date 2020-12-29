package ch.aurachain.nats.controller;

import ch.aurachain.nats.service.NatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nats")
public class NatsController {

    @Autowired
    private NatsService natsService;

    @GetMapping("/publish/{subject}/{text}")
    public void publishing(@PathVariable String subject, @PathVariable String text) {
        natsService.publishing(subject, text);
    }

    @GetMapping("/subscription/{subject}")
    public String subscription(@PathVariable String subject) throws InterruptedException {
        return natsService.subscription(subject);
    }

    @GetMapping("/dispacher/{subject}")
    public void dispacher(@PathVariable String subject) {
        natsService.dispacher(subject);
    }
}
