package kz.oj.mailingdaemon.enums;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum MailingListStatus implements EnumClass<String> {

    DRAFT, IN_PROGRESS, PAUSED, FINISHED;

    @Override
    public String getId() {
        return name();
    }

    @Nullable
    public static MailingListStatus fromId(String id) {
        for (MailingListStatus at : MailingListStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}