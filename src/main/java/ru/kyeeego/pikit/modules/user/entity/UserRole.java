package ru.kyeeego.pikit.modules.user.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class UserRole {
    public static String[] DEFAULT = {"DEFAULT"};

    public static String[] MOD = {"DEFAULT", "MOD"};

    public static String[] EXPERT = {"DEFAULT", "EXPERT"};

    public static String[] SUPER = {"DEFAULT", "MOD", "EXPERT", "SUPER"};
}
