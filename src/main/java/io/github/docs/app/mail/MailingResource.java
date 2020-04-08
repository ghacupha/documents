package io.github.docs.app.mail;

import io.github.docs.app.model.MailAttachmentRequest;
import io.github.docs.app.model.TransactionDocumentMetadata;
import io.github.docs.service.TransactionDocumentService;
import io.github.docs.service.dto.TransactionDocumentDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
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
    public void shareTransactionDocuments(@Valid @RequestBody MailAttachmentRequest attachmentRequest) {

        final Map<String, File> DOCUMENT_MAP = getAttachmentsMap(attachmentRequest.getTransactionDocumentMetadata());

        mailingService.sendAttachmentFromTemplate(attachmentRequest.getRecipientEmail(), "mail/attachmentEmail", "email.activation.title", DOCUMENT_MAP);
    }

    @Async
    public Map<String, File> getAttachmentsMap(List<TransactionDocumentMetadata> transactionDocuments) {

        final Map<String, File> DOCUMENT_MAP = new HashMap<>();

        transactionDocuments.forEach(metadata -> {

            long documentId = metadata.getId();

            TransactionDocumentDTO documentDTO = transactionDocumentService.findOne(documentId).orElseThrow(() -> new IllegalArgumentException("Transaction document id : " + documentId + " not " +
                                                                                                                                                 "found!!!"));

                File attachment = new File(documentDTO.getFilename());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(attachment);

                // Writes bytes from the specified byte array to this file output stream
                fos.write(documentDTO.getTransactionAttachment());
            } catch (Exception e) {
                System.out.println("File not found" + e);
            }

            DOCUMENT_MAP.put(documentDTO.getFilename(), attachment);

        });

        return DOCUMENT_MAP;
    }
}
