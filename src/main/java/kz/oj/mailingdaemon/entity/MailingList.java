package kz.oj.mailingdaemon.entity;

import io.jmix.core.FileRef;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import kz.oj.mailingdaemon.enums.MailingListStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@JmixEntity
@Table(name = "MAILING_LIST")
@Entity
@Getter
@Setter
public class MailingList {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Long id;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    private String name;

    @Composition
    @OneToMany(mappedBy = "mailingList", cascade = CascadeType.ALL)
    private List<Mail> mails;

    @Column(name = "STATUS", nullable = false)
    private String status = MailingListStatus.DRAFT.getId();

    public MailingListStatus getStatus() {
        return MailingListStatus.fromId(status);
    }

    public void setStatus(MailingListStatus status) {
        this.status = status.getId();
    }

    @Column(name = "CREATION_DATE", nullable = false)
    private OffsetDateTime creationDate = OffsetDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SENDER_ID", nullable = false)
    private Sender sender;

    @Column(name = "RECIPIENTS_FILE", nullable = false)
    private FileRef recipientsFile;

    @Column(name = "MAIL_TEMPLATE", nullable = false)
    private FileRef mailTemplate;

    @Column(name = "LAST_UPDATED", nullable = false)
    private OffsetDateTime lastUpdated = OffsetDateTime.now();

    @Column(nullable = false)
    private String subject;

    @Composition
    @OneToMany(mappedBy = "mailingList", cascade = CascadeType.ALL)
    List<Attachment> attachments;
}