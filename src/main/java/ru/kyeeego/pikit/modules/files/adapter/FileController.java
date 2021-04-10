package ru.kyeeego.pikit.modules.files.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kyeeego.pikit.modules.files.port.IFileStorage;
import ru.kyeeego.pikit.modules.requisition.port.IModifyRequisition;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final IFileStorage fileStorage;
    private final IModifyRequisition modifyRequisition;

    private final Map<String, MediaType> extensionToMediaType = Map.of(
            "png", MediaType.IMAGE_PNG,
            "jpeg", MediaType.IMAGE_JPEG,
            "jpg", MediaType.IMAGE_JPEG,
            "html", MediaType.TEXT_HTML,
            "xml", MediaType.TEXT_XML,
            "pdf", MediaType.APPLICATION_PDF,
            "gif", MediaType.IMAGE_GIF
    );

    @PostMapping("/new")
    public void uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        modifyRequisition.addFile(id, file.getOriginalFilename());

        fileStorage.save(file);
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
        String[] splitted = filename.split("\\.");
        String ext = splitted[splitted.length - 1];
        Optional<MediaType> mediaType = Optional.ofNullable(extensionToMediaType.get(ext));

        return ResponseEntity
                .ok()
                .contentType(mediaType.orElse(MediaType.TEXT_PLAIN))
                .body(fileStorage.load(filename));
    }

}
