package ru.kyeeego.pikit.modules.auth.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RefreshDto {

    @NotNull
    private String fingerprint;

    @NotNull
    private String refreshToken;

}
