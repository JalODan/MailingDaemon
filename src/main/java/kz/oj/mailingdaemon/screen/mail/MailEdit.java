package kz.oj.mailingdaemon.screen.mail;

import io.jmix.ui.screen.*;
import kz.oj.mailingdaemon.entity.Mail;

@UiController("Mail.edit")
@UiDescriptor("mail-edit.xml")
@EditedEntityContainer("mailDc")
public class MailEdit extends StandardEditor<Mail> {
}