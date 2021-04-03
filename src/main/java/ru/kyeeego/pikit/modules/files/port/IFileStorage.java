package ru.kyeeego.pikit.modules.files.port;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileStorage {
    void save(MultipartFile file);
    Resource load(String filename);
}
