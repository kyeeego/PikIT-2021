package ru.kyeeego.pikit.modules.requisition.port;

import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.dto.RequisitionCreateDto;

public interface ICreateRequisition {
    Requisition create(RequisitionCreateDto createRequisitionDto);
}
