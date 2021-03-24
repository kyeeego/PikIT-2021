package ru.kyeeego.pikit.modules.requisition.port;

import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.dto.RequisitionUpdateDto;
import ru.kyeeego.pikit.modules.requisition.entity.dto.VotingTypes;

import java.security.Principal;

public interface IModifyRequisition {
    Requisition approve(Long id, VotingTypes votingDto);

    Requisition close(Long id);

    Requisition updateOne(Long id, RequisitionUpdateDto requisitionUpdateDto, Principal principal);
}
