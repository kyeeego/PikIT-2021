package ru.kyeeego.pikit.modules.session.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kyeeego.pikit.modules.session.entity.Session;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUserEmail(String userEmail);
    void deleteByUserEmail(String userEmail);
    void deleteByFingerprint(String fingerprint);
    Optional<Session> findByRefreshToken(String refreshToken);
}
