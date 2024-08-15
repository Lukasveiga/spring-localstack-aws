package com.lukasdev.spring_localstack_up.secretsmanager.controller;

import com.lukasdev.spring_localstack_up.secretsmanager.config.SecretsManagerConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SecretsManagerController {

    private final SecretsManagerConfig secretsManagerConfig;

    public SecretsManagerController(SecretsManagerConfig secretsManagerConfig) {
        this.secretsManagerConfig = secretsManagerConfig;
    }

    @GetMapping("/secrets-manager")
    public ResponseEntity<String> getSecrets() {
        return ResponseEntity.ok(
                String.format("%s - %s - %s",
                        this.secretsManagerConfig.getValor1(),
                        this.secretsManagerConfig.getValor2(),
                        this.secretsManagerConfig.getValor3())
        );
    }
}
