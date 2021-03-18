package ru.kyeeego.pikit.modules.user.port;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kyeeego.pikit.modules.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
