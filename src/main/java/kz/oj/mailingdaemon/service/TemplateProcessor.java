package kz.oj.mailingdaemon.service;

import io.jmix.core.FileRef;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class TemplateProcessor {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TemplateProcessor.class);
    private final FileService fileService;

    public TemplateProcessor(FileService fileService) {
        this.fileService = fileService;
    }

    public String process(FileRef fileRef) {

        StringBuilder stringBuilder = new StringBuilder();

        try {
            InputStream inputStream = fileService.asResource(fileRef).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

        } catch (IOException e) {
            log.error("Error", e);
        }

        return stringBuilder.toString();
    }
}
