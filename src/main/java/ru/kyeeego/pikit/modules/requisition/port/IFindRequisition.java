package ru.kyeeego.pikit.modules.requisition.port;

import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.RequisitionStatus;

import java.util.List;
import java.util.Optional;

public interface IFindRequisition {
    Requisition findOne(Long id, RequisitionStatus status);
    List<Requisition> findByStatus(RequisitionStatus status);
}
