package ru.kyeeego.pikit.modules.user.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.modules.user.entity.User;
import ru.kyeeego.pikit.modules.user.entity.dto.UserCreateDto;
import ru.kyeeego.pikit.modules.user.entity.dto.UserResponse;
import ru.kyeeego.pikit.modules.user.exception.UserAlreadyExistsException;
import ru.kyeeego.pikit.modules.user.port.IModifyUser;
import ru.kyeeego.pikit.modules.user.port.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModifyUser implements IModifyUser {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(UserCreateDto userCreateDto) {
        User user = new User(userCreateDto);

        Optional<User> us = repository.findByEmail(user.getEmail());
        if (us.isPresent())
            throw new UserAlreadyExistsException();

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        return repository.save(user);
    }

}
