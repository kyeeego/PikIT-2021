package ru.kyeeego.pikit.modules.auth.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.modules.auth.entity.MyUserDetailsService;
import ru.kyeeego.pikit.modules.auth.entity.TokenPair;
import ru.kyeeego.pikit.modules.auth.entity.dto.LogInDto;
import ru.kyeeego.pikit.modules.auth.entity.dto.RefreshDto;
import ru.kyeeego.pikit.modules.auth.port.IAccessTokenService;
import ru.kyeeego.pikit.modules.auth.port.IAuthenticate;
import ru.kyeeego.pikit.modules.session.port.ISessionService;
import ru.kyeeego.pikit.modules.user.entity.User;
import ru.kyeeego.pikit.modules.user.exception.UserNotFoundException;
import ru.kyeeego.pikit.modules.user.port.UserRepository;


@Service
public class Authenticate implements IAuthenticate {

    private final ISessionService sessionService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public Authenticate(AuthenticationManager authenticationManager,
                        MyUserDetailsService myUserDetailsService,
                        ISessionService sessionService) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.sessionService = sessionService;
    }

    @Override
    public TokenPair authenticate(LogInDto logInDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            logInDto.getEmail(),
                            logInDto.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Bad credentials");
        }

        final UserDetails userDetails = myUserDetailsService
                .loadUserByUsername(logInDto.getEmail());

        return sessionService.create(userDetails, logInDto.getFingerprint());
    }

    @Override
    public TokenPair refreshTokens(RefreshDto refreshDto) {
        return sessionService.renew(
                refreshDto.getFingerprint(),
                refreshDto.getRefreshToken()
        );
    }

}
