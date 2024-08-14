package com.lukasdev.spring_localstack_up.sns.service;

import io.awspring.cloud.sns.core.SnsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SNSNotificationService {

    @Value("${snsNotificationName:snsHelloWorld}")
    private String notificationName;

    private final SnsTemplate snsTemplate;

    public SNSNotificationService(SnsTemplate snsTemplate) {
        this.snsTemplate = snsTemplate;
    }

    public void sendNotification(String subject, Object message, Map<String, Object> headers) {
        this.snsTemplate.convertAndSend(notificationName, message, headers);
    }
}
