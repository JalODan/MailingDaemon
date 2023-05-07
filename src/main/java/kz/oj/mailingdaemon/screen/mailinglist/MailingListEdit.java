package kz.oj.mailingdaemon.screen.mailinglist;

import io.jmix.core.DataManager;
import io.jmix.ui.component.SingleFileUploadField;
import io.jmix.ui.screen.*;
import kz.oj.mailingdaemon.entity.Mail;
import kz.oj.mailingdaemon.entity.MailingList;
import kz.oj.mailingdaemon.service.FileService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UiController("MailingList.edit")
@UiDescriptor("mailing-list-edit.xml")
@EditedEntityContainer("mailingListDc")
public class MailingListEdit extends StandardEditor<MailingList> {

    @Autowired
    protected DataManager dataManager;

    @Autowired
    private FileService fileService;

    @Subscribe("recipientsFileField")
    public void onRecipientsFileFieldFileUploadSucceed(SingleFileUploadField.FileUploadSucceedEvent event) throws IOException {

        MailingList mailingList = getEditedEntity();

        InputStream inputStream = fileService.asResource(mailingList.getRecipientsFile()).getInputStream();

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<String> emails = new ArrayList<>();
        for (Row row : sheet) {
            String email = row.getCell(0).getStringCellValue();
            emails.add(email);
        }

        List<Mail> mails = emails.stream().map(email -> {

            Mail mail = dataManager.create(Mail.class);

            mail.setMailingList(mailingList);
            mail.setRecipient(email);

            return mail;

        }).collect(Collectors.toList());

        mailingList.setMails(mails);

        workbook.close();
    }


}