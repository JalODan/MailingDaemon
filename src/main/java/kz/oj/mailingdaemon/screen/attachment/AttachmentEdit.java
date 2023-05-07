package kz.oj.mailingdaemon.screen.attachment;

import io.jmix.ui.screen.*;
import kz.oj.mailingdaemon.entity.Attachment;

@UiController("Attachment.edit")
@UiDescriptor("attachment-edit.xml")
@EditedEntityContainer("attachmentDc")
public class AttachmentEdit extends StandardEditor<Attachment> {
}