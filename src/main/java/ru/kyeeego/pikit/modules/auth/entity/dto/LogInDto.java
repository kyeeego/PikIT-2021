package ru.kyeeego.pikit.modules.auth.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class LogInDto {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;
}
