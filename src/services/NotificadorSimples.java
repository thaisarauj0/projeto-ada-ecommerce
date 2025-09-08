package services;

public class NotificadorSimples implements INotificador {


    @Override
    public void sendEmail(String receiver, String body, String subject) {
        System.out.println("E-mail sent to " + receiver);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("-------------");
    }
}
