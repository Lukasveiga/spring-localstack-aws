package com.lukasdev.spring_localstack_up.sqs.consumer;

import com.lukasdev.spring_localstack_up.sqs.config.MyMessage;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class MyConsumer {

    @SqsListener("${sqsQueueName:sqsHelloWorld}")
    public void listen(MyMessage message) {
        System.out.println("Message received: " + message.content());
    }
}
