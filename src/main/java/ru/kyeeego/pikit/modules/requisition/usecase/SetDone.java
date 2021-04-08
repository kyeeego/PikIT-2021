package ru.kyeeego.pikit.modules.requisition.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.RequisitionStatus;
import ru.kyeeego.pikit.modules.requisition.exception.RequisitionNotFoundException;
import ru.kyeeego.pikit.modules.requisition.port.ISetDone;
import ru.kyeeego.pikit.modules.requisition.port.RequisitionRepository;

@Service
@RequiredArgsConstructor
public class SetDone implements ISetDone {

    private final RequisitionRepository repository;

    @Override
    public void setDone(Long id) {
        Requisition req = repository
                .findByIdAndStatus(id, RequisitionStatus.IN_PROGRESS)
                .orElseThrow(RequisitionNotFoundException::new);

        req.setStatus(RequisitionStatus.DONE);

        repository.save(req);
    }

    @Override
    public void setInProgress(Long id) {
        Requisition req = repository
                .findByIdAndStatus(id, RequisitionStatus.EXP_VOTING)
                .orElseThrow(RequisitionNotFoundException::new);

        req.setStatus(RequisitionStatus.IN_PROGRESS);

        repository.save(req);
    }
}
