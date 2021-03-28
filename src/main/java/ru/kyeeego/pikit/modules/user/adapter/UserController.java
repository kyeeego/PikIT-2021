package ru.kyeeego.pikit.modules.user.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kyeeego.pikit.modules.user.entity.User;
import ru.kyeeego.pikit.modules.user.entity.dto.UserCreateDto;
import ru.kyeeego.pikit.modules.user.entity.dto.UserResponse;
import ru.kyeeego.pikit.modules.user.port.IFindUser;
import ru.kyeeego.pikit.modules.user.port.IModifyUser;
import ru.kyeeego.pikit.modules.user.port.UserRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IFindUser findUser;
    private final IModifyUser modifyUser;

    @Autowired
    public UserController(IFindUser findUser, IModifyUser modifyUser) {
        this.findUser = findUser;
        this.modifyUser = modifyUser;
    }

    @GetMapping
    public List<UserResponse> find() {
        return findUser.all();
    }

    @GetMapping("/")
    public UserResponse findByid(@RequestParam("id") Long id) {
        return findUser.byId(id);
    }

    @PostMapping("/create")
    public UserResponse create(@RequestBody @Valid UserCreateDto userCreateDto) {
        return new UserResponse(
          modifyUser.create(userCreateDto)
        );
    }

}
