package kz.oj.mailingdaemon.screen.sendertype;

import io.jmix.ui.screen.*;
import kz.oj.mailingdaemon.entity.SenderType;

@UiController("SenderType.browse")
@UiDescriptor("sender-type-browse.xml")
@LookupComponent("senderTypesTable")
public class SenderTypeBrowse extends StandardLookup<SenderType> {
}