package com.rishi.aihub.features.document.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface StorageService {

    String store(MultipartFile file);

    Resource loadAsResource(String fileName);

    void delete(String fileName);

    File loadAsFile(String fileName);
}