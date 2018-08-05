package project.service;

public interface EmailService {

    void sendHtmlEmail(final String to, final String subject, final String body);

}
