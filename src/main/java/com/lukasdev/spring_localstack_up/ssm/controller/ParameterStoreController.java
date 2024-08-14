package com.lukasdev.spring_localstack_up.ssm.controller;

import com.lukasdev.spring_localstack_up.ssm.config.ParameterStoreConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ParameterStoreController {

    private final ParameterStoreConfig parameterStoreConfig;

    public ParameterStoreController(ParameterStoreConfig parameterStoreConfig) {
        this.parameterStoreConfig = parameterStoreConfig;
    }

    @GetMapping("/parameter-store-config")
    public ResponseEntity<String> getConfig() {
        return ResponseEntity.ok(this.parameterStoreConfig.getHelloWorld());
    }
}
