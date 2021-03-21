package ru.kyeeego.pikit.modules.session.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kyeeego.pikit.modules.session.entity.Session;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUserEmail(String userEmail);
    void deleteByUserEmail(String userEmail);
    Optional<Session> findByRefreshToken(String refreshToken);
}
