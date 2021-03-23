package ru.kyeeego.pikit.modules.requisition.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.RequisitionStatus;
import ru.kyeeego.pikit.modules.requisition.exception.RequisitionNotFoundException;
import ru.kyeeego.pikit.modules.requisition.port.IFindRequisition;
import ru.kyeeego.pikit.modules.requisition.port.RequisitionRepository;

import java.util.List;

@Service
public class FindRequisition implements IFindRequisition {

    private final RequisitionRepository repository;

    @Autowired
    public FindRequisition(RequisitionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Requisition findOne(Long id, RequisitionStatus status) {
        return repository
                .findByIdAndStatus(id, status)
                .orElseThrow(RequisitionNotFoundException::new);
    }

    @Override
    public List<Requisition> findByStatus(RequisitionStatus status) {
        return repository.findByStatus(status);
    }
}
