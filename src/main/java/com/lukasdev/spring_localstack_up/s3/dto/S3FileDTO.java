package com.lukasdev.spring_localstack_up.s3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public record S3FileDTO(
        String fileName,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String content
) {
}
