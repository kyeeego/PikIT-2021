package ru.kyeeego.pikit.modules.user.port;

import ru.kyeeego.pikit.modules.user.entity.User;
import ru.kyeeego.pikit.modules.user.entity.dto.UserCreateDto;

public interface IModifyUser {
    User create(UserCreateDto userCreateDto);
//    TODO
//    User update(UserUpdateDto userUpdateDto, Long id);
//    User delete(Long id);
}
