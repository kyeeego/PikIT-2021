package ru.kyeeego.pikit.modules.auth.port;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface IAccessTokenService {
    String generateToken(UserDetails userDetails);
    boolean validateToken(String token, UserDetails userDetails);
}
