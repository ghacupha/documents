package io.github.docs.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * This is a simple request object containing parameters for calls requesting to share
 * <p>
 * emails with attachments
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MailAttachmentRequest implements Serializable {

    private String recipientUsername;

    private String titlePart1;

    private String titlePart2;

    @NotNull
    private String recipientEmail;

    private List<TransactionDocumentMetadata> transactionDocumentMetadata;
}
