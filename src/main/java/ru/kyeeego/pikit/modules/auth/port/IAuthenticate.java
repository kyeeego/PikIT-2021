package ru.kyeeego.pikit.modules.auth.port;

import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.modules.auth.entity.TokenPair;
import ru.kyeeego.pikit.modules.auth.entity.dto.LogInDto;
import ru.kyeeego.pikit.modules.auth.entity.dto.RefreshDto;

@Service
public interface IAuthenticate {
    TokenPair authenticate(LogInDto logInDto);
    void logout(String fingerprint);
    TokenPair refreshTokens(RefreshDto refreshDto);
}
