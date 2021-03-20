package ru.kyeeego.pikit.modules.user.port;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kyeeego.pikit.modules.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
