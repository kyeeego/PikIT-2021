package ru.kyeeego.pikit.modules.requisition.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.RequisitionStatus;
import ru.kyeeego.pikit.modules.requisition.port.IFindRequisition;
import ru.kyeeego.pikit.modules.requisition.port.IVoteForRequisition;

import java.util.List;

@RestController
@RequestMapping("/api/v1/req/vot")
public class VotingRequisitionController {

    private final IFindRequisition findRequisition;
    private final IVoteForRequisition voteForRequisition;

    @Autowired
    public VotingRequisitionController(IFindRequisition findRequisition, IVoteForRequisition voteForRequisition) {
        this.findRequisition = findRequisition;
        this.voteForRequisition = voteForRequisition;
    }

    @GetMapping
    public List<Requisition> findAllInVotingStatus() {
        return findRequisition.findByStatus(RequisitionStatus.STUD_VOTING);
    }

    @GetMapping("/{id}")
    public Requisition findOne(@PathVariable("id") Long id) {
        return findRequisition.findOne(id, RequisitionStatus.STUD_VOTING);
    }

    @PutMapping("/{id}")
    public void vote(@PathVariable("id") Long id) {
        voteForRequisition.vote(id);
    }

}
