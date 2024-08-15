package com.lukasdev.spring_localstack_up.secretsmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretsManagerConfig {

    @Value("${valor1}")
    private String valor1;

    @Value("${valor2}")
    private String valor2;

    @Value("${valor3}")
    private String valor3;

    public String getValor1() {
        return valor1;
    }

    public String getValor2() {
        return valor2;
    }

    public String getValor3() {
        return valor3;
    }
}
