package ru.kyeeego.pikit.modules.requisition.entity.dto;

import lombok.Data;
import org.springframework.lang.Nullable;
import ru.kyeeego.pikit.modules.requisition.entity.VotingType;

import javax.validation.constraints.NotNull;

@Data
public class VotingDto {
    @NotNull
    private VotingType type;

    private int amount;
}
