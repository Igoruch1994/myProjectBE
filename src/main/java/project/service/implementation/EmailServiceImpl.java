package project.service.implementation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import project.exception.GeneralServiceException;
import project.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.encoding.options}")
    private String ENCODING_OPTIONS;

    @Value("${mail.header.param}")
    private String HEADER_PARAM;

    private static final Logger LOG = Logger.getLogger(EmailServiceImpl.class);

    @Override
    public void sendHtmlEmail(String to, String subject, String body) {
        try {
            final MimeMessage message = mailSender.createMimeMessage();
            message.setHeader(HEADER_PARAM, ENCODING_OPTIONS);
            message.setFrom(new InternetAddress("info@acquisitionsimplicity.com"));
            message.saveChanges();
            final MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
            LOG.info("Send e-mails to " + to);
        } catch (final MessagingException e) {
            LOG.error("Failed to send e-mails to " + to);
            throw new GeneralServiceException("Error with sending HTML mails ", e);
        }
    }

}
