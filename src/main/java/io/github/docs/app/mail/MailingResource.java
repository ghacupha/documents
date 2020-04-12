package io.github.docs.app.mail;

import io.github.docs.app.model.MailAttachmentRequest;
import io.github.docs.app.model.TransactionDocumentMetadata;
import io.github.docs.service.TransactionDocumentService;
import io.github.docs.service.dto.TransactionDocumentDTO;
import io.github.jhipster.web.util.HeaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
 * Resource works but we now need to ensure we can send multiple attachments in a single email
 *
 * otherwise the service has been sending emails to a user with a single attachment, so when sending
 *
 * multiple attachments we can flood the user's email. This is preferably to be achieved by isolating
 *
 * the mailing process and finding ways to zip up the attachments
 */
@Slf4j
@RestController
@RequestMapping("/api/share")
public class MailingResource {

    private final MailingService mailingService;
    private final TransactionDocumentService transactionDocumentService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public MailingResource(final MailingService mailingService, final TransactionDocumentService transactionDocumentService) {
        this.mailingService = mailingService;
        this.transactionDocumentService = transactionDocumentService;
    }

    @PostMapping("/transaction-documents")
    public ResponseEntity<List<TransactionDocumentMetadata>> shareTransactionDocuments(@Valid @RequestBody MailAttachmentRequest attachmentRequest) {

        log.debug("Request received to share : {} documents with {}", attachmentRequest.getTransactionDocumentMetadata().size(), attachmentRequest.getRecipientEmail());

        final Map<String, File> DOCUMENT_MAP = getAttachmentsMap(attachmentRequest.getTransactionDocumentMetadata());

        mailingService.sendAttachmentFromTemplate(attachmentRequest.getRecipientEmail(), "mail/attachmentEmail", "email.attachment.title", DOCUMENT_MAP);

        String noOfDocumentsShared = String.valueOf(attachmentRequest.getTransactionDocumentMetadata().size());

        return ResponseEntity.accepted()
            .headers(HeaderUtil.createAlert(applicationName, noOfDocumentsShared + " documents have been mailed, successfully", noOfDocumentsShared))
            .body(attachmentRequest.getTransactionDocumentMetadata());
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
