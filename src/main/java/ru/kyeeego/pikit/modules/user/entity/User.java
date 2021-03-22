package ru.kyeeego.pikit.modules.user.entity;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import ru.kyeeego.pikit.modules.user.entity.dto.UserCreateDto;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )
})
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

    @Type(type = "string-array")
    @Column(name = "role",
            nullable = false,
            columnDefinition = "text[]")
    private String[] role;

    public User(UserCreateDto dto) {
        this.name = dto.getName();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.role = UserRole.DEFAULT;
    }
}
