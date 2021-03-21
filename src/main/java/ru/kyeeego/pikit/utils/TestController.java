package ru.kyeeego.pikit.utils;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This one is being used for authentication tests only
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/user")
    public String regular() {
        return "Got to regular";
    }

    @GetMapping("/super")
    public String admin() {
        return "Woah u got admin";
    }

}
