package com.lukasdev.spring_localstack_up.sns.controller;

import com.lukasdev.spring_localstack_up.sns.service.SNSNotificationService;
import com.lukasdev.spring_localstack_up.sqs.config.MyMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SNSController {

    private final SNSNotificationService snsNotificationService;

    public SNSController(SNSNotificationService snsNotificationService) {
        this.snsNotificationService = snsNotificationService;
    }

    @PostMapping("/topic-message")
    public ResponseEntity<?> sendMessage(@RequestBody MyMessage message) {
        this.snsNotificationService.sendNotification("Subject: Hello", message, null);
        return ResponseEntity.ok("Sns message sent successfully");
    }
}
