package ru.kyeeego.pikit.modules.email.port;

public interface IEmailService {
    void send(String to, String text);
}
