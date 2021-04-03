package ru.kyeeego.pikit.modules.files.usecase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kyeeego.pikit.exception.BadRequestException;
import ru.kyeeego.pikit.modules.files.FileStorageException;
import ru.kyeeego.pikit.modules.files.port.IFileStorage;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorage implements IFileStorage {

    @Value("${document.path}")
    private String path;

    @Override
    public void save(MultipartFile multipartFile) {
        File file = new File(path + multipartFile.getOriginalFilename());
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileOutputStream os = new FileOutputStream(file);
            os.write(multipartFile.getBytes());
        } catch (Exception e) {
            throw new FileStorageException("Couldn't save file: " + e);
        }
    }

    @Override
    public Resource load(String filename) {
        Path path = Paths.get(this.path + filename);

        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new BadRequestException("Bad file url");
        }
    }
}
