package ru.kyeeego.pikit.modules.user.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kyeeego.pikit.modules.user.entity.User;
import ru.kyeeego.pikit.modules.user.entity.dto.UserResponse;
import ru.kyeeego.pikit.modules.user.port.IFindUser;
import ru.kyeeego.pikit.modules.user.port.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IFindUser findUser;

    @Autowired
    public UserController(IFindUser findUser) {
        this.findUser = findUser;
    }

    @GetMapping
    public List<UserResponse> find() {
        return findUser.all();
    }

    @GetMapping("/{id}")
    public UserResponse findByid(@PathVariable Long id) {
        return findUser.byId(id);
    }

}
