package ru.kyeeego.pikit.modules.requisition.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.dto.RequisitionCreateDto;
import ru.kyeeego.pikit.modules.requisition.port.ICreateRequisition;
import ru.kyeeego.pikit.modules.requisition.port.RequisitionRepository;

@Service
@RequiredArgsConstructor
public class CreateRequisition implements ICreateRequisition {

    private final RequisitionRepository repository;

    @Override
    public Requisition create(RequisitionCreateDto createRequisitionDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Requisition requisition = new Requisition(createRequisitionDto);
        requisition.setAuthorEmail(authentication.getName());

        return repository.save(requisition);
    }
}
