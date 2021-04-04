package ru.kyeeego.pikit.modules.auth.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kyeeego.pikit.modules.auth.entity.TokenPair;
import ru.kyeeego.pikit.modules.auth.entity.dto.LogInDto;
import ru.kyeeego.pikit.modules.auth.entity.dto.LogoutDto;
import ru.kyeeego.pikit.modules.auth.entity.dto.RefreshDto;
import ru.kyeeego.pikit.modules.auth.port.IAuthenticate;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthenticate authenticate;

    @PostMapping
    public TokenPair authenticate(@RequestBody @Valid LogInDto logInDto) {
        return authenticate.authenticate(logInDto);
    }

    @PostMapping("/refresh")
    public TokenPair refresh(@RequestBody @Valid RefreshDto refreshDto) {
        return authenticate.refreshTokens(refreshDto);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody @Valid LogoutDto dto) {
        authenticate.logout(dto.getFingerprint());
    }
}
