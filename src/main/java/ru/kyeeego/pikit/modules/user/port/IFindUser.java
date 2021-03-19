package ru.kyeeego.pikit.modules.user.port;

import ru.kyeeego.pikit.modules.user.entity.User;
import ru.kyeeego.pikit.modules.user.entity.dto.UserResponse;

import java.util.List;

public interface IFindUser {
    UserResponse byId(Long id);
    List<UserResponse> all();
}
