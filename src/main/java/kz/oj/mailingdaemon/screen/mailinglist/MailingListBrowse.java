package kz.oj.mailingdaemon.screen.mailinglist;

import io.jmix.core.DataManager;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.*;
import kz.oj.mailingdaemon.entity.MailingList;
import kz.oj.mailingdaemon.enums.MailingListStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.Collection;
import java.util.Set;

@UiController("MailingList.browse")
@UiDescriptor("mailing-list-browse.xml")
@LookupComponent("mailingListsTable")
public class MailingListBrowse extends StandardLookup<MailingList> {

    @Autowired
    protected DataManager dataManager;
    @Named("mailingListsTable.pause")
    private BaseAction mailingListsTablePauseAction;

    @Named("mailingListsTable.start")
    private BaseAction mailingListsTableStartAction;

    @Autowired
    private GroupTable<MailingList> mailingListsTable;

    private boolean arePauseable(Collection<MailingList> mailingLists) {
        return mailingLists.stream()
                .map(MailingList::getStatus)
                .allMatch(status -> status == MailingListStatus.IN_PROGRESS);
    }

    private boolean areStartable(Collection<MailingList> mailingLists) {
        return mailingLists.stream()
                .map(MailingList::getStatus)
                .allMatch(status -> Set.of(MailingListStatus.DRAFT, MailingListStatus.PAUSED).contains(status));
    }

    @Subscribe("mailingListsTable")
    public void onMailingListsTableSelection(Table.SelectionEvent<MailingList> event) {
        Set<MailingList> mailingLists = event.getSelected();

        mailingListsTableStartAction.setEnabled(areStartable(mailingLists));
        mailingListsTablePauseAction.setEnabled(arePauseable(mailingLists));
    }

    @Subscribe("mailingListsTable.pause")
    public void onMailingListsTablePause(Action.ActionPerformedEvent event) {

        Set<MailingList> mailingLists = mailingListsTable.getSelected();

        if (!arePauseable(mailingLists)) {
            return;
        }

        mailingLists.forEach(mailingList -> {
            mailingList.setStatus(MailingListStatus.PAUSED);
            dataManager.save(mailingList);
        });

        mailingListsTablePauseAction.setEnabled(false);
        mailingListsTableStartAction.setEnabled(true);
    }

    @Subscribe("mailingListsTable.start")
    public void onMailingListsTableStart(Action.ActionPerformedEvent event) {

        Set<MailingList> mailingLists = mailingListsTable.getSelected();

        if (!areStartable(mailingLists)) {
            return;
        }

        mailingLists.forEach(mailingList -> {
            mailingList.setStatus(MailingListStatus.IN_PROGRESS);
            dataManager.save(mailingList);
        });

        mailingListsTablePauseAction.setEnabled(true);
        mailingListsTableStartAction.setEnabled(false);
    }



}