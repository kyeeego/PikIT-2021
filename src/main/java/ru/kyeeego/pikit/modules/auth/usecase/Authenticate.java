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
import ru.kyeeego.pikit.modules.auth.port.IAccessTokenService;
import ru.kyeeego.pikit.modules.auth.port.IAuthenticate;
import ru.kyeeego.pikit.modules.user.entity.User;
import ru.kyeeego.pikit.modules.user.exception.UserNotFoundException;
import ru.kyeeego.pikit.modules.user.port.UserRepository;


@Service
public class Authenticate implements IAuthenticate {

    private final UserRepository userRepository;
    private final IAccessTokenService accessTokenService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public Authenticate(AuthenticationManager authenticationManager,
                        MyUserDetailsService myUserDetailsService,
                        UserRepository userRepository,
                        IAccessTokenService accessTokenService) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.accessTokenService = accessTokenService;
        this.userRepository = userRepository;
    }

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

        // TODO: generate session

        final String accessToken = accessTokenService.generateToken(userDetails);

        User user = userRepository
                .findByEmail(logInDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        userRepository.save(user);

        return new TokenPair(accessToken, "refreshToken");
    }

}
