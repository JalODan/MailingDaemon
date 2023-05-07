package kz.oj.mailingdaemon.service;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import kz.oj.mailingdaemon.constants.TimeUnits;
import kz.oj.mailingdaemon.entity.Mail;
import kz.oj.mailingdaemon.entity.MailingList;
import kz.oj.mailingdaemon.entity.Sender;
import kz.oj.mailingdaemon.enums.MailStatus;
import kz.oj.mailingdaemon.enums.MailingListStatus;
import kz.oj.mailingdaemon.repository.MailRepository;
import kz.oj.mailingdaemon.repository.MailingListRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSendException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableScheduling
public class Scheduler {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Scheduler.class);
    @Autowired
    private SystemAuthenticator systemAuthenticator;
    @Autowired
    private MailService mailService;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private MailingListRepository mailingListRepository;
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private TemplateProcessor templateProcessor;

    @Scheduled(initialDelay = 10 * TimeUnits.SECOND, fixedRate = 15 * TimeUnits.SECOND)
    public void run() {

        log.info("Scheduler wakes up...");

        systemAuthenticator.withSystem(() -> {
            List<Sender> senders = dataManager.load(Sender.class).all().list();

            senders.forEach(sender -> {

                Optional<MailingList> mailingListOptional = mailingListRepository
                        .findFirstBySenderAndStatusOrderByLastUpdatedAsc(
                                sender, MailingListStatus.IN_PROGRESS.getId()
                        );

                if (mailingListOptional.isEmpty()) {
                    log.info("No active mailing lists present.");
                    return;
                }

                MailingList mailingList = mailingListOptional.get();

                Optional<Mail> mailOptional = mailRepository
                        .findFirstByMailingListAndStatus(mailingList, MailStatus.NOT_ATTEMPTED.getId());


                mailOptional.ifPresentOrElse(
                        mail -> {

                            try {

                                mailService.send(
                                        mailingList.getSender(),
                                        mailingList.getSubject(),
                                        templateProcessor.process(mailingList.getMailTemplate()),
                                        mail.getRecipient(),
                                        mail.getMailingList().getAttachments()
                                );

                                mail.setStatus(MailStatus.SENT_SUCCESSFULLY);
                                mailRepository.save(mail);

                            } catch (MailSendException e) {

                                log.error("Failed to send the email to " + mail.getRecipient()
                                        + ". Marking it as attempted.");

                                mail.setStatus(MailStatus.FAILED);
                                mailRepository.save(mail);

                            } catch (MessagingException e) {
                                throw new RuntimeException("Error", e);
                            }
                        },
                        () -> {
                            mailingList.setStatus(MailingListStatus.FINISHED);
                            mailingListRepository.save(mailingList);
                        }
                );
            });

            return null;
        });
    }
}
