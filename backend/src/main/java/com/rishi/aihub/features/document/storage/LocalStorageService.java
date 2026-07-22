package com.rishi.aihub.features.document.storage;

import com.rishi.aihub.common.config.StorageProperties;
import com.rishi.aihub.common.exception.BusinessException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    private final StorageProperties storageProperties;

    private Path uploadPath;

    @PostConstruct
    public void init() {

        try {
            uploadPath = Paths.get(storageProperties.getUploadDir())
                    .toAbsolutePath()
                    .normalize();

            Files.createDirectories(uploadPath);

        } catch (IOException e) {
            throw new BusinessException("Unable to initialize upload directory.");
        }
    }

    @Override
    public String store(MultipartFile file) {

        try {

            String extension = StringUtils.getFilenameExtension(
                    file.getOriginalFilename()
            );

            String storedFileName =
                    UUID.randomUUID() + "." + extension;

            Path targetLocation = uploadPath.resolve(storedFileName);

            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return storedFileName;

        } catch (IOException ex) {

            throw new BusinessException("Unable to store file.");

        }

    }

    @Override
    public Resource loadAsResource(String fileName) {

        try {

            Path filePath = uploadPath.resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }

            throw new BusinessException("Unable to read file.");

        } catch (MalformedURLException e) {

            throw new BusinessException("Unable to load file.");

        }
    }

    @Override
    public void delete(String path) {

        try {

            Files.deleteIfExists(uploadPath.resolve(path));

        } catch (IOException e) {

            throw new BusinessException("Unable to delete file.");

        }

    }

    @Override
    public File loadAsFile(String fileName) {

        Path filePath = uploadPath.resolve(fileName).normalize();

        File file = filePath.toFile();

        if (!file.exists()) {
            throw new BusinessException("File not found: " + fileName);
        }

        return file;
    }
}