package ru.kyeeego.pikit.modules.requisition.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.dto.RequisitionCreateDto;
import ru.kyeeego.pikit.modules.requisition.port.ICreateRequisition;
import ru.kyeeego.pikit.modules.requisition.port.RequisitionRepository;

@Service
public class CreateRequisition implements ICreateRequisition {

    private final RequisitionRepository repository;

    @Autowired
    public CreateRequisition(RequisitionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Requisition create(RequisitionCreateDto createRequisitionDto) {
        Requisition requisition = new Requisition(createRequisitionDto);

        return repository.save(requisition);
    }
}
