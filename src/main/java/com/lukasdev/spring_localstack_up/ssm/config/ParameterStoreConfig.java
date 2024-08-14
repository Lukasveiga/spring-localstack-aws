package com.lukasdev.spring_localstack_up.ssm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParameterStoreConfig {

    @Value("${helloWorld}")
    private String helloWorld;

    public String getHelloWorld() {
        return helloWorld;
    }

    public void setHelloWorld(String helloWorld) {
        this.helloWorld = helloWorld;
    }
}
