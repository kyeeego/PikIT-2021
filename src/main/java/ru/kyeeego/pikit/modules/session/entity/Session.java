package ru.kyeeego.pikit.modules.session.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sessions")
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "refreshToken", nullable = false)
    private String refreshToken;

    @Column(name = "userEmail", nullable = false)
    private String userEmail;

    @Column(name = "expiresAt", nullable = false)
    private Long expiresAt;

    // уникальный идентификатор определенного устройства, с которого приходит запрос на сервер
    @Column(name = "fingerprint", nullable = false)
    private String fingerprint;

    public Session(String userEmail, Long expiresAt, String fingerprint) {
        this.userEmail = userEmail;
        this.expiresAt = expiresAt;
        this.fingerprint = fingerprint;
    }
}
