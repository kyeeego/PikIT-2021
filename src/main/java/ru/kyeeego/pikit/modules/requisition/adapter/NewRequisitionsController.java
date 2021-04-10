package ru.kyeeego.pikit.modules.requisition.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kyeeego.pikit.modules.email.port.IEmailService;
import ru.kyeeego.pikit.modules.files.port.IFileStorage;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.RequisitionStatus;
import ru.kyeeego.pikit.modules.requisition.entity.dto.RequisitionCreateDto;
import ru.kyeeego.pikit.modules.requisition.entity.dto.RequisitionUpdateDto;
import ru.kyeeego.pikit.modules.requisition.entity.dto.VotingDto;
import ru.kyeeego.pikit.modules.requisition.port.ICreateRequisition;
import ru.kyeeego.pikit.modules.requisition.port.IFindRequisition;
import ru.kyeeego.pikit.modules.requisition.port.IModifyRequisition;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/req/new")
public class NewRequisitionsController {

    private final ICreateRequisition createRequisition;
    private final IFindRequisition findRequisition;
    private final IModifyRequisition modifyRequisition;
    private final IEmailService emailService;

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
        Requisition req = modifyRequisition.updateOne(id, body);
        emailService.send(req.getAuthorEmail(), "Your requisition has been updated: " + req.getTitle());
        return req;
    }

    @PutMapping("/approve")
    public Requisition approveOne(@RequestParam("id") Long id,
                                  @RequestBody @Valid VotingDto votingDto) {
        Requisition req = modifyRequisition.approve(id, votingDto);
        emailService.send(req.getAuthorEmail(), "Your requisition has been approved: " + req.getTitle());
        return req;
    }

    @PutMapping("/close")
    public Requisition closeOne(@RequestParam("id") Long id) {
        Requisition req = modifyRequisition.close(id);
        emailService.send(req.getAuthorEmail(), "Unfortunately, your requisition has been closed: " + req.getTitle());
        return req;
    }

    @DeleteMapping("/delete")
    public void deleteOne(@RequestParam("id") Long id) {
        Requisition req = modifyRequisition.delete(id);
        emailService.send(req.getAuthorEmail(), "Your requisition has been deleted: " + req.getTitle());
    }
}
