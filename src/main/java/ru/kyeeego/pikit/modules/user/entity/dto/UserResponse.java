package ru.kyeeego.pikit.modules.user.entity.dto;

import lombok.Data;
import ru.kyeeego.pikit.modules.user.entity.User;

@Data
public class UserResponse {

    private Long id;

    private String name;
    private String email;
    private String phone;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
    }
}
