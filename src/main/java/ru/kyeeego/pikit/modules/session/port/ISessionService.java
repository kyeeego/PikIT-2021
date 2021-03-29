package ru.kyeeego.pikit.modules.session.port;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.modules.auth.entity.TokenPair;

public interface ISessionService {
    TokenPair create(UserDetails userDetails, String fingerprint);
    TokenPair renew(String fingerprint, String refreshToken);
    void end(String fingerprint);
}
