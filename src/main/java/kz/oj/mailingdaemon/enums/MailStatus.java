package kz.oj.mailingdaemon.enums;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum MailStatus implements EnumClass<String> {

    NOT_ATTEMPTED, SENT_SUCCESSFULLY, FAILED;

    public String getId() {
        return name();
    }

    @Nullable
    public static MailStatus fromId(String id) {
        for (MailStatus at : MailStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}