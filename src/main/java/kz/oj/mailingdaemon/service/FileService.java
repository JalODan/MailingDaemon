package kz.oj.mailingdaemon.service;

import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class FileService {
    private final FileStorage fileStorage;

    public FileService(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    public Resource asResource(FileRef fileRef) {
        return new InputStreamResource(fileStorage.openStream(fileRef));
    }

    public InputStream getInputStream(FileRef fileRef) {
        return fileStorage.openStream(fileRef);
    }
}
