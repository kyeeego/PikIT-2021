package ru.kyeeego.pikit.modules.user.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class UserRole {
    public static String[] DEFAULT = {"DEFAULT"};
    public static String[] MOD = {"DEFAULT", "MOD"};
    public static String[] EXPERT = {"DEFAULT", "EXPERT"};
    public static String[] SUPER = {"DEFAULT", "MOD", "EXPERT", "SUPER"};

    public static class Access {
        public static String[] SUPER = {"SUPER"};
        public static String[] MOD = {"SUPER", "MOD"};
        public static String[] EXPERT = {"SUPER", "EXPERT"};
        public static String[] DEFAULT = {"SUPER", "MOD", "EXPERT", "DEFAULT"};
    }
}
