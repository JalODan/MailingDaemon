package kz.oj.mailingdaemon.repository;

import io.jmix.core.repository.JmixDataRepository;
import kz.oj.mailingdaemon.entity.Mail;
import kz.oj.mailingdaemon.entity.MailingList;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailRepository extends JmixDataRepository<Mail, Long> {
    Optional<Mail> findFirstByMailingListAndStatus(MailingList mailingList, String status);
}
