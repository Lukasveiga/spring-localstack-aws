package com.lukasdev.spring_localstack_up.sqs.producer;

import com.lukasdev.spring_localstack_up.sqs.config.MyMessage;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyProducer {

    @Value("${sqsQueueName:sqsHelloWorld}")
    private String queueName;

    private final SqsTemplate sqsTemplate;

    public MyProducer(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void send(MyMessage message) {
        var SQS = "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/" + queueName;
        this.sqsTemplate.send(SQS, message);
    }
}
