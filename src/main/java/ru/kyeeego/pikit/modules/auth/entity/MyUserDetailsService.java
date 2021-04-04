package ru.kyeeego.pikit.modules.auth.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.modules.user.entity.User;
import ru.kyeeego.pikit.modules.user.entity.UserRole;
import ru.kyeeego.pikit.modules.user.port.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException(username);
        final User us = user.get();

        return new org.springframework.security.core.userdetails.User(
                us.getEmail(),
                us.getPassword(),
                Arrays.stream(us.getRole())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
    }
}
