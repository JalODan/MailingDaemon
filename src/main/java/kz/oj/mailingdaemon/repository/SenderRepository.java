package kz.oj.mailingdaemon.repository;

import io.jmix.core.repository.JmixDataRepository;
import kz.oj.mailingdaemon.entity.Sender;
import org.springframework.stereotype.Repository;

@Repository
public interface SenderRepository extends JmixDataRepository<Sender, Long> {
}
