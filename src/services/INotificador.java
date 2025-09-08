package services;

public interface INotificador {
    void sendEmail(String receiver, String body, String subject);
}
