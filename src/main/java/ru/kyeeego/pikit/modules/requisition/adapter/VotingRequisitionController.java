package ru.kyeeego.pikit.modules.requisition.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.RequisitionStatus;
import ru.kyeeego.pikit.modules.requisition.port.IFindRequisition;
import ru.kyeeego.pikit.modules.requisition.port.IVoteForRequisition;

import java.util.List;

@RestController
@RequestMapping("/api/v1/req/vot")
@RequiredArgsConstructor
public class VotingRequisitionController {

    private final IFindRequisition findRequisition;
    private final IVoteForRequisition voteForRequisition;

    @GetMapping("/all")
    public List<Requisition> findAllInVotingStatus() {
        return findRequisition.findByStatus(RequisitionStatus.STUD_VOTING);
    }

    @GetMapping
    public Requisition findOne(@RequestParam("id") Long id) {
        return findRequisition.findOne(id, RequisitionStatus.STUD_VOTING);
    }

    @PutMapping
    public void vote(@RequestParam("id") Long id) {
        voteForRequisition.vote(id);
        voteForRequisition.update(id);
    }

}
