package kz.oj.mailingdaemon.screen.sender;

import io.jmix.ui.screen.*;
import kz.oj.mailingdaemon.entity.Sender;

@UiController("Sender.browse")
@UiDescriptor("sender-browse.xml")
@LookupComponent("sendersTable")
public class SenderBrowse extends StandardLookup<Sender> {
}