package io.github.docs.app.mail;

import io.github.docs.app.model.FormalDocumentMetadata;
import io.github.docs.app.model.MailAttachmentRequest;
import io.github.docs.app.model.TransactionDocumentMetadata;
import io.github.jhipster.web.util.HeaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.File;
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
    private final DocumentRetrieval<CompletableFuture<Map<String, File>>> documentRetrieval;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public MailingResource(final MailingService mailingService, final DocumentRetrieval<CompletableFuture<Map<String, File>>> documentRetrieval) {
        this.mailingService = mailingService;
        this.documentRetrieval = documentRetrieval;
    }

    @PostMapping("/transaction-documents")
    public ResponseEntity<List<TransactionDocumentMetadata>> shareTransactionDocuments(@Valid @RequestBody MailAttachmentRequest<TransactionDocumentMetadata> attachmentRequest) {

        log.debug("Request received to share : {} documents with {}", attachmentRequest.getDocumentMetadata().size(), attachmentRequest.getRecipientEmail());

        Map<String, File> DOCUMENT_MAP = null;
        try {
            DOCUMENT_MAP = documentRetrieval.transactionDocumentsMap(attachmentRequest.getDocumentMetadata()).get();
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
            DOCUMENT_MAP = documentRetrieval.formalDocumentMap(attachmentRequest.getDocumentMetadata()).get();
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
}
