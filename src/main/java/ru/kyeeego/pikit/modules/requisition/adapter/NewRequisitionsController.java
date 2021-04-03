package ru.kyeeego.pikit.modules.requisition.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kyeeego.pikit.modules.files.port.IFileStorage;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.RequisitionStatus;
import ru.kyeeego.pikit.modules.requisition.entity.dto.RequisitionCreateDto;
import ru.kyeeego.pikit.modules.requisition.entity.dto.RequisitionUpdateDto;
import ru.kyeeego.pikit.modules.requisition.entity.dto.VotingDto;
import ru.kyeeego.pikit.modules.requisition.port.ICreateRequisition;
import ru.kyeeego.pikit.modules.requisition.port.IFindRequisition;
import ru.kyeeego.pikit.modules.requisition.port.IModifyRequisition;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/req/new")
public class NewRequisitionsController {

    private final ICreateRequisition createRequisition;
    private final IFindRequisition findRequisition;
    private final IModifyRequisition modifyRequisition;
    private final IFileStorage fileStorage;

    private final Map<String, MediaType> extensionToMediaType = Map.of(
            "png", MediaType.IMAGE_PNG,
            "jpeg", MediaType.IMAGE_JPEG,
            "jpg", MediaType.IMAGE_JPEG,
            "html", MediaType.TEXT_HTML,
            "xml", MediaType.TEXT_XML,
            "pdf", MediaType.APPLICATION_PDF,
            "gif", MediaType.IMAGE_GIF
    );

    @PostMapping
    @PreAuthorize("hasAnyAuthority('DEFAULT')")
    public Requisition create(@RequestBody @Valid RequisitionCreateDto createRequisitionDto) {
        return createRequisition.create(createRequisitionDto);
    }

    @GetMapping
    public List<Requisition> findAllNew() {
        return findRequisition.findByStatus(RequisitionStatus.MODERATING);
    }

    @GetMapping("/")
    public Requisition findOneNew(@RequestParam("id") Long id) {
        return findRequisition.findOne(id, RequisitionStatus.MODERATING);
    }

    @PutMapping("/update")
    public Requisition updateOne(@RequestParam("id") Long id,
                                 @RequestBody RequisitionUpdateDto body) {
        return modifyRequisition.updateOne(id, body);
    }

    @PutMapping("/approve")
    public Requisition approveOne(@RequestParam("id") Long id,
                                  @RequestBody @Valid VotingDto votingDto) {
        return modifyRequisition.approve(id, votingDto);
    }

    @PutMapping("/close")
    public Requisition closeOne(@RequestParam("id") Long id) {
        return modifyRequisition.close(id);
    }

    @DeleteMapping("/delete")
    public void deleteOne(@RequestParam("id") Long id) {
        modifyRequisition.delete(id);
    }


    @PostMapping("/file")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        fileStorage.save(file);
    }

    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
        // TODO: for every file
        String[] splitted = filename.split("\\.");
        String ext = splitted[splitted.length - 1];

        Optional<MediaType> mediaType = Optional.ofNullable(extensionToMediaType.get("ext"));

        return ResponseEntity
                .ok()
                .contentType(mediaType.orElse(MediaType.TEXT_PLAIN))
                .body(fileStorage.load(filename));
    }

//    @GetMapping("/file/{filename:.+}")
//    public Resource getFile(@PathVariable("filename") String filename) {
//        return fileStorage.load(filename);
//    }

    // TODO: email notifications on status change
}
