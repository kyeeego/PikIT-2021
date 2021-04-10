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

import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/req/new")
public class NewRequisitionsController {

    private final ICreateRequisition createRequisition;
    private final IFindRequisition findRequisition;
    private final IModifyRequisition modifyRequisition;

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

    // TODO: email notifications on status change
}
