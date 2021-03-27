package ru.kyeeego.pikit.modules.requisition.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.RequisitionStatus;
import ru.kyeeego.pikit.modules.requisition.entity.dto.RequisitionCreateDto;
import ru.kyeeego.pikit.modules.requisition.entity.dto.RequisitionUpdateDto;
import ru.kyeeego.pikit.modules.requisition.entity.dto.VotingDto;
import ru.kyeeego.pikit.modules.requisition.port.ICreateRequisition;
import ru.kyeeego.pikit.modules.requisition.port.IFindRequisition;
import ru.kyeeego.pikit.modules.requisition.port.IModifyRequisition;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/req/new")
public class NewRequisitionsController {

    private final ICreateRequisition createRequisition;
    private final IFindRequisition findRequisition;
    private final IModifyRequisition modifyRequisition;

    @Autowired
    public NewRequisitionsController(ICreateRequisition createRequisition, IFindRequisition findRequisition, IModifyRequisition modifyRequisition) {
        this.createRequisition = createRequisition;
        this.findRequisition = findRequisition;
        this.modifyRequisition = modifyRequisition;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('DEFAULT')")
    public Requisition create(@RequestBody @Valid RequisitionCreateDto createRequisitionDto) {
        return createRequisition.create(createRequisitionDto);
    }

    @GetMapping
    public List<Requisition> findAllNew() {
        return findRequisition.findByStatus(RequisitionStatus.MODERATING);
    }

    @GetMapping("/{id}")
    public Requisition findOneNew(@PathVariable("id") Long id) {
        return findRequisition.findOne(id, RequisitionStatus.MODERATING);
    }

    @PutMapping("/{id}")
    public Requisition updateOne(@PathVariable("id") Long id,
                                 @RequestBody RequisitionUpdateDto body,
                                 Principal user) {
        return modifyRequisition.updateOne(id, body, user);
    }

    @PutMapping("/{id}/approve")
    public Requisition approveOne(@PathVariable("id") Long id,
                                  @RequestBody @Valid VotingDto votingDto) {
        return modifyRequisition.approve(id, votingDto);
    }

    @PutMapping("/{id}/close")
    public Requisition closeOne(@PathVariable("id") Long id) {
        return modifyRequisition.close(id);
    }
}
