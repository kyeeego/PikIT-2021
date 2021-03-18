package ru.kyeeego.pikit.modules.user.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kyeeego.pikit.modules.user.entity.User;
import ru.kyeeego.pikit.modules.user.port.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> find() {
        return userRepository.findAll();
    }

    @GetMapping("/create")
    public String createUser() {
        User user = new User();
        user.setName("Karl");
        userRepository.save(user);
        return "Saved";
    }

}
