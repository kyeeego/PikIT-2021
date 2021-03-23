package ru.kyeeego.pikit.modules.requisition.entity.dto;

import lombok.Data;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;

@Data
public class RequisitionUpdateDto {
    private String title;
    private String body;
    private String cost;
    private String adress;
}
