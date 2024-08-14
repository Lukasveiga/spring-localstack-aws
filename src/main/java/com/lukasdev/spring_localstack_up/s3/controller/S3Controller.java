package com.lukasdev.spring_localstack_up.s3.controller;

import com.lukasdev.spring_localstack_up.s3.dto.S3FileDTO;
import com.lukasdev.spring_localstack_up.s3.service.S3FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class S3Controller {

    private static final Logger logger = LoggerFactory.getLogger(S3Controller.class);

    private static final String FILE_NOT_EXISTS = "Arquivo não existe";

    private final S3FileService s3FileService;

    public S3Controller(S3FileService s3FileService) {
        this.s3FileService = s3FileService;
    }

    @PostMapping("/s3/files")
    private ResponseEntity<S3FileDTO> createFile() {
        Path tempFile;

        try {
            tempFile = Files.createTempFile("prefixo-", ".txt");
            Files.writeString(tempFile, "Conteúdo do Arquivo: " + UUID.randomUUID() + "",
                    StandardCharsets.UTF_8, StandardOpenOption.APPEND);

            this.s3FileService.saveFile(Files.newInputStream(tempFile), tempFile.toFile().getName());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(new S3FileDTO(ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(new S3FileDTO(tempFile.toFile().getName(), null));
    }

    @GetMapping("/s3/files")
    public ResponseEntity<List<S3FileDTO>> listFiles(@RequestParam("fileName") String fileName) {
        List<S3FileDTO> s3Files = new ArrayList<>();

        try {
            var resources = this.s3FileService.searchFile(fileName, false);

            if (resources.isEmpty()) {
                s3Files.add(new S3FileDTO(FILE_NOT_EXISTS, null));
                return new ResponseEntity<>(s3Files, HttpStatus.NOT_FOUND);
            }

            for (var resource : resources) {
                s3Files.add(new S3FileDTO(resource.getFilename(), null));
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(s3Files,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(s3Files);
    }

    @GetMapping("/s3/files/{file}")
    public ResponseEntity<S3FileDTO> contentFile(@PathVariable("file") String file) {
        if (this.s3FileService.isFileExists(file)) {
            var resources = this.s3FileService.searchFile(file, true);
            var resource = resources.get(0);
            return ResponseEntity.ok(new S3FileDTO(resource.getFilename(), this.s3FileService.contentFile(resource.getFilename())));
        }

        return new ResponseEntity<>(new S3FileDTO(FILE_NOT_EXISTS, null), HttpStatus.NOT_FOUND);
    }
}
