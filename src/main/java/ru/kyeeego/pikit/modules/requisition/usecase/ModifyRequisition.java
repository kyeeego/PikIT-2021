package ru.kyeeego.pikit.modules.requisition.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.exception.ForbiddenException;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.RequisitionStatus;
import ru.kyeeego.pikit.modules.requisition.entity.dto.RequisitionUpdateDto;
import ru.kyeeego.pikit.modules.requisition.entity.dto.VotingDto;
import ru.kyeeego.pikit.modules.requisition.exception.RequisitionNotFoundException;
import ru.kyeeego.pikit.modules.requisition.port.IModifyRequisition;
import ru.kyeeego.pikit.modules.requisition.port.RequisitionRepository;
import ru.kyeeego.pikit.modules.user.entity.UserRole;
import ru.kyeeego.pikit.utils.UpdateUtils;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModifyRequisition implements IModifyRequisition {

    private final RequisitionRepository repository;

    @Override
    public Requisition approve(Long id, VotingDto votingDto) {
        Requisition requisition = repository
                .findByIdAndStatus(id, RequisitionStatus.MODERATING)
                .orElseThrow(RequisitionNotFoundException::new);

        switch (votingDto.getType()) {
            case EXP:
                requisition.setStatus(RequisitionStatus.EXP_VOTING);
                break;
            case STUD:
                int requiredVotings = votingDto.getAmount() == 0 ? 200 : votingDto.getAmount();
                requisition.setStatus(RequisitionStatus.STUD_VOTING);
                requisition.setRequiredVotes(requiredVotings);
        }

        return repository.save(requisition);
    }

    @Override
    public Requisition close(Long id) {
        return setRequisitionStatusById(id, RequisitionStatus.CLOSED);
    }

    @Override
    public Requisition updateOne(Long id, RequisitionUpdateDto requisitionUpdateDto) {
        Requisition requisition = repository
                .findByIdAndStatus(id, RequisitionStatus.MODERATING)
                .orElseThrow(RequisitionNotFoundException::new);

        UpdateUtils.copyNonNullProperties(requisitionUpdateDto, requisition);

        return repository.save(requisition);
    }

    @Override
    public Requisition delete(Long id) {
        Requisition req = repository.findById(id).orElseThrow(RequisitionNotFoundException::new);
        repository.deleteById(id);
        return req;
    }

    private Requisition setRequisitionStatusById(Long id, RequisitionStatus status) {
        Requisition requisition = repository
                .findByIdAndStatus(id, RequisitionStatus.MODERATING)
                .orElseThrow(RequisitionNotFoundException::new);

        requisition.setStatus(status);

        return repository.save(requisition);
    }

    @Override
    public Requisition addFile(Long id, String filename) {
        Requisition req = repository
                .findByIdAndStatus(id, RequisitionStatus.MODERATING)
                .orElseThrow(RequisitionNotFoundException::new);

        req.addDoc(filename);

        return repository.save(req);
    }
}
