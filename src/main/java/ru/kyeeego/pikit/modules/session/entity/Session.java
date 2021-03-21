package ru.kyeeego.pikit.modules.session.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "refreshToken")
    private String refreshToken;

    @Column(name = "userEmail")
    private String userEmail;

    @Column(name = "expiresAt")
    private Long expiresAt;

    // уникальный идентификатор определенного устройства, с которого приходит запрос на сервер
    @Column(name = "fingerprint")
    private String fingerprint;

    public Session(String userEmail, Long expiresAt, String fingerprint) {
        this.userEmail = userEmail;
        this.expiresAt = expiresAt;
        this.fingerprint = fingerprint;
    }
}
