package kz.oj.mailingdaemon.entity;

import io.jmix.core.FileRef;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@JmixEntity
@Table(name = "ATTACHMENT")
@Entity
@Getter
@Setter
public class Attachment {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MAILINGLIST_ID", nullable = false)
    private MailingList mailingList;

    @InstanceName
    @Column(name = "FILE_REF", nullable = false)
    private FileRef fileRef;
}