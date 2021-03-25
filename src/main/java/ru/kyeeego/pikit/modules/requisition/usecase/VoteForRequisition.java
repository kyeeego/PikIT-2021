package ru.kyeeego.pikit.modules.requisition.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.exception.ForbiddenException;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.RequisitionStatus;
import ru.kyeeego.pikit.modules.requisition.exception.RequisitionNotFoundException;
import ru.kyeeego.pikit.modules.requisition.port.IVoteForRequisition;
import ru.kyeeego.pikit.modules.requisition.port.RequisitionRepository;
import ru.kyeeego.pikit.modules.user.entity.User;


@Service
public class VoteForRequisition implements IVoteForRequisition {

    private final RequisitionRepository repository;

    @Autowired
    public VoteForRequisition(RequisitionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void vote(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = authentication.getName();

        Requisition req = repository
                .findByIdAndStatus(id, RequisitionStatus.STUD_VOTING)
                .orElseThrow(RequisitionNotFoundException::new);

        if (req.getVoted().contains(email))
            throw new ForbiddenException("U have voted already ;)");

        req.addVoter(email);
        repository.save(req);
    }

    @Override
    public void update(Long id) {
        Requisition req = repository
                .findByIdAndStatus(id, RequisitionStatus.STUD_VOTING)
                .orElseThrow(RequisitionNotFoundException::new);

        if (req.getVoted().size() >= 200) {
            req.setStatus(RequisitionStatus.EXP_VOTING);
            repository.save(req);
        }
    }
}
