package ru.kyeeego.pikit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kyeeego.pikit.modules.auth.entity.dto.LogInDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserRequests {
    public static Object ValidUser = new Object() {
        public final String email = "Users@user.user";
        public final String password = "pass";
        public final String fingerprint = "uuid";
    };

    public static Object InvalidPasswordUser = new Object() {
        public final String email = "Users@user.user";
        public final String password = "passs";
        public final String fingerprint = "uuid";
    };

    public static Object InvalidEmailUser = new Object() {
        public final String email = "ss@s.r";
        public final String password = "pass";
        public final String fingerprint = "uuid";
    };

    public static Object InvalidFingerprintUser = new Object() {
        public final String email = "Users@user.user";
        public final String password = "pass";
        public final String fingerprint = "invalid";
    };

    public static Object BadRequestUser = new Object() {
        public final String email = "Usersuser.user";
        public final String password = "pass";
        public final String fingerprint = "uuid";
    };

    public static String json(Object o) throws Exception {
        return new ObjectMapper().writeValueAsString(o);
    }
}
