package com.lukasdev.spring_localstack_up.sqs.controller;

import com.lukasdev.spring_localstack_up.sqs.config.MyMessage;
import com.lukasdev.spring_localstack_up.sqs.producer.MyProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SQSController {

    private final MyProducer producer;

    public SQSController(MyProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/message")
    public ResponseEntity<?> send(@RequestBody MyMessage message) {
        this.producer.send(message);
        return ResponseEntity.ok("Message sent successfully ");
    }
}
