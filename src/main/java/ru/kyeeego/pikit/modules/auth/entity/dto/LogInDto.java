package ru.kyeeego.pikit.modules.auth.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class LogInDto {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String fingerprint;
}
