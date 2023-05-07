package kz.oj.mailingdaemon.screen.sender;

import io.jmix.ui.screen.*;
import kz.oj.mailingdaemon.entity.Sender;

@UiController("Sender.edit")
@UiDescriptor("sender-edit.xml")
@EditedEntityContainer("senderDc")
public class SenderEdit extends StandardEditor<Sender> {
}