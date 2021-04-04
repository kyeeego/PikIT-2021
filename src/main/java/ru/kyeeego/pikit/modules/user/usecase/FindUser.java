package ru.kyeeego.pikit.modules.user.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.modules.user.entity.User;
import ru.kyeeego.pikit.modules.user.entity.dto.UserResponse;
import ru.kyeeego.pikit.modules.user.exception.UserNotFoundException;
import ru.kyeeego.pikit.modules.user.port.IFindUser;
import ru.kyeeego.pikit.modules.user.port.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindUser implements IFindUser {

    private final UserRepository repository;

    @Override
    public UserResponse byId(Long id) {
        return new UserResponse(
                repository
                        .findById(id)
                        .orElseThrow(UserNotFoundException::new)
        );
    }

    @Override
    public List<UserResponse> all() {
        return repository.findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }
}
