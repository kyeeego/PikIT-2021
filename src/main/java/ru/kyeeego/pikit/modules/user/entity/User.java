package ru.kyeeego.pikit.modules.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kyeeego.pikit.modules.user.entity.dto.UserCreateDto;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "role", nullable = false)
    private UserRole role;

    public User(UserCreateDto dto) {
        this.name = dto.getName();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.role = UserRole.DEFAULT;
    }
}
