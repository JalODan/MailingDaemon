package kz.oj.mailingdaemon.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@JmixEntity
@Table(name = "SENDER")
@Entity
@Getter
@Setter
public class Sender {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Long id;

    @InstanceName
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SENDERTYPE_ID", nullable = false)
    private SenderType senderType;
}