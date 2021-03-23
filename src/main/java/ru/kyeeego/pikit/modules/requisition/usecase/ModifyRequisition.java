package ru.kyeeego.pikit.modules.requisition.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.RequisitionStatus;
import ru.kyeeego.pikit.modules.requisition.entity.dto.RequisitionUpdateDto;
import ru.kyeeego.pikit.modules.requisition.entity.dto.VotingTypes;
import ru.kyeeego.pikit.modules.requisition.exception.RequisitionNotFoundException;
import ru.kyeeego.pikit.modules.requisition.port.IModifyRequisition;
import ru.kyeeego.pikit.modules.requisition.port.RequisitionRepository;
import ru.kyeeego.pikit.utils.UpdateUtils;

import java.util.Optional;

@Service
public class ModifyRequisition implements IModifyRequisition {

    private final RequisitionRepository repository;

    @Autowired
    public ModifyRequisition(RequisitionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Requisition approve(Long id, VotingTypes votingDto) {
        Requisition requisition = repository
                .findByIdAndStatus(id, RequisitionStatus.MODERATING)
                .orElseThrow(RequisitionNotFoundException::new);

        requisition.setVotingTypes(votingDto);

        return votingDto.isStudent()
                ? setRequisitonStatus(requisition, RequisitionStatus.STUD_VOTING)
                : setRequisitonStatus(requisition, RequisitionStatus.EXP_VOTING);
    }

    @Override
    public Requisition close(Long id) {
        return setRequisitonStatusById(id, RequisitionStatus.CLOSED);
    }
    // TODO

    @Override
    public Requisition updateOne(Long id, RequisitionUpdateDto requisitionUpdateDto) {
        Requisition requisition = repository
                .findByIdAndStatus(id, RequisitionStatus.MODERATING)
                .orElseThrow(RequisitionNotFoundException::new);

        UpdateUtils.copyNonNullProperties(requisitionUpdateDto, requisition);

        return repository.save(requisition);
    }

    private Requisition setRequisitonStatusById(Long id, RequisitionStatus status) {
        Requisition requisition = repository
                .findByIdAndStatus(id, RequisitionStatus.MODERATING)
                .orElseThrow(RequisitionNotFoundException::new);

        requisition.setStatus(status);

        return repository.save(requisition);
    }

    private Requisition setRequisitonStatus(Requisition requisition, RequisitionStatus status) {
        requisition.setStatus(status);

        return repository.save(requisition);
    }
}
