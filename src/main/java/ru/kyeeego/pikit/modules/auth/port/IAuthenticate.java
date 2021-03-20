package ru.kyeeego.pikit.modules.auth.port;

import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.modules.auth.entity.TokenPair;
import ru.kyeeego.pikit.modules.auth.entity.dto.LogInDto;

@Service
public interface IAuthenticate {
    TokenPair authenticate(LogInDto logInDto);
    // TODO:
    // TokenPair refresh(String refreshToken);
    //
}
