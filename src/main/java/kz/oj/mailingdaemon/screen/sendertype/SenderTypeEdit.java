package kz.oj.mailingdaemon.screen.sendertype;

import io.jmix.ui.screen.*;
import kz.oj.mailingdaemon.entity.SenderType;

@UiController("SenderType.edit")
@UiDescriptor("sender-type-edit.xml")
@EditedEntityContainer("senderTypeDc")
public class SenderTypeEdit extends StandardEditor<SenderType> {
}