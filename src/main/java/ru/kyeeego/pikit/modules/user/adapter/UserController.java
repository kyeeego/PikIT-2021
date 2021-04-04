package ru.kyeeego.pikit.modules.user.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kyeeego.pikit.modules.user.entity.dto.UserCreateDto;
import ru.kyeeego.pikit.modules.user.entity.dto.UserResponse;
import ru.kyeeego.pikit.modules.user.port.IFindUser;
import ru.kyeeego.pikit.modules.user.port.IModifyUser;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IFindUser findUser;
    private final IModifyUser modifyUser;

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
