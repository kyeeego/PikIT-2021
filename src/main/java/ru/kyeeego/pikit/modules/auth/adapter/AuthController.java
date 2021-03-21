package ru.kyeeego.pikit.modules.auth.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kyeeego.pikit.modules.auth.entity.TokenPair;
import ru.kyeeego.pikit.modules.auth.entity.dto.LogInDto;
import ru.kyeeego.pikit.modules.auth.entity.dto.RefreshDto;
import ru.kyeeego.pikit.modules.auth.port.IAuthenticate;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthenticate authenticate;

    @Autowired
    public AuthController(IAuthenticate authenticate) {
        this.authenticate = authenticate;
    }

    @PostMapping
    public TokenPair authenticate(@RequestBody @Valid LogInDto logInDto) {
        return authenticate.authenticate(logInDto);
    }

    @PostMapping("/refresh")
    public TokenPair refresh(@RequestBody @Valid RefreshDto refreshDto) {
        return authenticate.refreshTokens(refreshDto);
    }
}
