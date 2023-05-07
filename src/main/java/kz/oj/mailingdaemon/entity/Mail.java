package kz.oj.mailingdaemon.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import kz.oj.mailingdaemon.enums.MailStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@JmixEntity
@Table(name = "MAIL")
@Entity
@Getter
@Setter
public class Mail {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MAILINGLIST_ID", nullable = false)
    private MailingList mailingList;

    @InstanceName
    @Column(name = "RECIPIENT", nullable = false)
    private String recipient;

    @Column(name = "STATUS", nullable = false)
    private String status = MailStatus.NOT_ATTEMPTED.getId();

    public MailStatus getStatus() {
        return MailStatus.fromId(status);
    }

    public void setStatus(MailStatus status) {
        if (status == null) {
            return;
        }
        this.status = status.getId();
    }
}