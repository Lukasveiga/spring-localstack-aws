package com.lukasdev.spring_localstack_up.s3.service;

import com.lukasdev.spring_localstack_up.s3.exceptions.FileListenerException;
import io.awspring.cloud.s3.S3PathMatchingResourcePatternResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3FileService {

    private static final Logger logger = LoggerFactory.getLogger(S3FileService.class);

    @Value("helloworld")
    private String directory;

    @Value("s3://%s/%s")
    private String path;

    @Autowired
    private ResourceLoader resourceLoader;

    private ResourcePatternResolver resourcePatternResolver;

    @Autowired
    public void setupResolver(S3Client s3Client, ApplicationContext applicationContext) {
        this.resourcePatternResolver = new S3PathMatchingResourcePatternResolver(s3Client, applicationContext);
    }

    public boolean isFileExists(String file) {
        try {
            var resource = this.resourceLoader.getResource(String.format(path, directory, file));
            return resource.contentLength() > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    public void saveFile(InputStream from, String to) throws FileListenerException {
        var resource = this.resourceLoader.getResource(String.format(path, directory, to));
        var writableResource = (WritableResource) resource;

        try (var outputStream = writableResource.getOutputStream()) {
            from.transferTo(outputStream);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new FileListenerException(ex.getMessage(), ex);
        }
    }

    public List<Resource> searchFile(String name, boolean exact) {
        Resource[] resources = null;

        try {
            if (exact)
                resources = this.resourcePatternResolver.getResources(String.format(path, directory, name));
            else
                resources = this.resourcePatternResolver.getResources(String.format(path.concat("*.*"), directory, name));

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return Arrays.asList(resources).stream().sorted(Comparator.comparing(Resource::getFilename)).toList();
    }

    public String contentFile(String file) {
        try {
            var resource = this.resourceLoader.getResource(String.format(path,directory, file));
            return new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (Exception ex) {
            return null;
        }
    }
}
