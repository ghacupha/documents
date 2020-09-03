package io.github.docs.app.mail;

import io.github.docs.app.model.FormalDocumentMetadata;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Resource works but we now need to ensure we can send multiple attachments in a single email
 * <p>
 * otherwise the service has been sending emails to a user with a single attachment, so when sending
 * <p>
 * multiple attachments we can flood the user's email. This is preferably to be achieved by isolating
 * <p>
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
    public ResponseEntity<List<TransactionDocumentMetadata>> shareTransactionDocuments(@Valid @RequestBody MailAttachmentRequest<TransactionDocumentMetadata> attachmentRequest) {

        log.debug("Request received to share : {} documents with {}", attachmentRequest.getDocumentMetadata().size(), attachmentRequest.getRecipientEmail());

        Map<String, File> DOCUMENT_MAP = null;
        try {
            DOCUMENT_MAP = getAttachmentsMap(attachmentRequest.getDocumentMetadata()).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Execution of the document has been interrupted, see stack...", e);
        }

        // pick template and title-key for messages
        mailingService.sendAttachmentFromTemplate(attachmentRequest.getRecipientUsername(), attachmentRequest.getTitlePart1(), attachmentRequest.getTitlePart2(), attachmentRequest.getRecipientEmail(),
                                                  "mail/attachmentEmail", "email.attachment.title", DOCUMENT_MAP);

        String noOfDocumentsShared = String.valueOf(attachmentRequest.getDocumentMetadata().size());

        return ResponseEntity.accepted()
                             .headers(HeaderUtil.createAlert(applicationName, noOfDocumentsShared + " documents have been mailed, successfully", noOfDocumentsShared))
                             .body(attachmentRequest.getDocumentMetadata());
    }

    @PostMapping("/formal-documents")
    public ResponseEntity<List<FormalDocumentMetadata>> shareFormalDocuments(@Valid @RequestBody MailAttachmentRequest<FormalDocumentMetadata> attachmentRequest) {

        log.debug("Request received to share : {} documents with {}", attachmentRequest.getDocumentMetadata().size(), attachmentRequest.getRecipientEmail());

        Map<String, File> DOCUMENT_MAP = null;
        try {
            DOCUMENT_MAP = getAttachmentsFormalMap(attachmentRequest.getDocumentMetadata()).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Execution of the document has been interrupted, see stack...", e);;
        }

        // pick template and title-key for messages
        mailingService.sendAttachmentFromTemplate(attachmentRequest.getRecipientUsername(), attachmentRequest.getTitlePart1(), attachmentRequest.getTitlePart2(), attachmentRequest.getRecipientEmail(),
            "mail/attachmentEmail", "email.attachment.title", DOCUMENT_MAP);

        String noOfDocumentsShared = String.valueOf(attachmentRequest.getDocumentMetadata().size());

        return ResponseEntity.accepted()
            .headers(HeaderUtil.createAlert(applicationName, noOfDocumentsShared + " documents have been mailed, successfully", noOfDocumentsShared))
            .body(attachmentRequest.getDocumentMetadata());
    }

    @Async
    public CompletableFuture<Map<String, File>> getAttachmentsMap(List<TransactionDocumentMetadata> transactionDocuments) {

        final Map<String, File> DOCUMENT_MAP = new HashMap<>();

        transactionDocuments.forEach(metadata -> {

            long documentId = metadata.getId();

            TransactionDocumentDTO documentDTO =
                transactionDocumentService.findOne(documentId).orElseThrow(() -> new IllegalArgumentException("Transaction document id : " + documentId + " not " + "found!!!"));

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

        return CompletableFuture.completedFuture(DOCUMENT_MAP);
    }

    @Async
    public CompletableFuture<Map<String, File>> getAttachmentsFormalMap(List<FormalDocumentMetadata> transactionDocuments) {

        final Map<String, File> DOCUMENT_MAP = new HashMap<>();

        transactionDocuments.forEach(metadata -> {

            long documentId = metadata.getId();

            TransactionDocumentDTO documentDTO =
                transactionDocumentService.findOne(documentId).orElseThrow(() -> new IllegalArgumentException("Transaction document id : " + documentId + " not " + "found!!!"));

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

        return CompletableFuture.completedFuture(DOCUMENT_MAP);
    }
}
