package ru.kyeeego.pikit.modules.requisition.port;

import ru.kyeeego.pikit.modules.requisition.entity.Requisition;

public interface IVoteForRequisition {
    Requisition vote(Long id);
}
