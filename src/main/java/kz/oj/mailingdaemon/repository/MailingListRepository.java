package kz.oj.mailingdaemon.repository;

import io.jmix.core.repository.JmixDataRepository;
import kz.oj.mailingdaemon.entity.MailingList;
import kz.oj.mailingdaemon.entity.Sender;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailingListRepository extends JmixDataRepository<MailingList, Long> {
    Optional<MailingList> findFirstBySenderAndStatusOrderByLastUpdatedAsc(Sender sender, String status);
}
