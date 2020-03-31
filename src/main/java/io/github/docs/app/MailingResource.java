package io.github.docs.app;

import io.github.docs.service.TransactionDocumentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Resource created for test purposes only
 */
@RestController
@RequestMapping("/api/share")
public class MailingResource {

    private final MailingService mailingService;
    private final TransactionDocumentService transactionDocumentService;

    public MailingResource(final MailingService mailingService, final TransactionDocumentService transactionDocumentService) {
        this.mailingService = mailingService;
        this.transactionDocumentService = transactionDocumentService;
    }

    @PostMapping("/transaction-documents")
    public void shareTransactionDocuments(@Valid @RequestBody MailAttachmentRequest attachmentRequest){

        byte[] fileBytes = transactionDocumentService.findOne(10l).get().getTransactionAttachment();
//        String fileName = transactionDocumentService.findOne(10l).get()

        final String FILE_ATTACHMENT_ID = "test_upload_file.png";


        File attachment = new File(FILE_ATTACHMENT_ID);


        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream(attachment);

            // Writes bytes from the specified byte array to this file output stream
            fos.write(fileBytes);

        }
        catch (Exception e) {
            System.out.println("File not found" + e);
        }

        final Map<String, File> DOCUMENT_MAP = new HashMap<>();

        DOCUMENT_MAP.put(FILE_ATTACHMENT_ID, attachment);

        mailingService.sendAttachmentFromTemplate(attachmentRequest.getRecipientEmail(), "mail/attachmentEmail", "email.activation.title", DOCUMENT_MAP);
    }
}
