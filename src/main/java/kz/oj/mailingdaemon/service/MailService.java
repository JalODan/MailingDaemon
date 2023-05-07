package kz.oj.mailingdaemon.service;

import kz.oj.mailingdaemon.entity.Attachment;
import kz.oj.mailingdaemon.entity.Sender;
import org.slf4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;

@Service
public class MailService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MailService.class);
    private final FileService fileService;

    public MailService(FileService fileService) {
        this.fileService = fileService;
    }

    public void send(
            Sender sender, String subject, String html, String recipient, List<Attachment> attachments
    ) throws MessagingException {
        log.info("Sending email to " + recipient + "...");

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(sender.getSenderType().getHost());
        javaMailSender.setPort(Integer.parseInt(sender.getSenderType().getPort()));
        javaMailSender.setUsername(sender.getEmail());
        javaMailSender.setPassword(sender.getPassword());
        javaMailSender.setProtocol("smtps");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(sender.getEmail());
        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(html, true);

        attachments.forEach(attachment -> {
            try {

                helper.addAttachment(
                        attachment.getFileRef().getFileName(),
                        new ByteArrayResource(fileService.getInputStream(attachment.getFileRef()).readAllBytes())
                );

            } catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        javaMailSender.send(message);

        log.info("Mail to " + recipient + "has been sent successfully");
    }
}
