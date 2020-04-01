package io.github.docs.app;

import io.github.docs.service.dto.SchemeDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * This object contains the core metadata elements to display at the front end to represent
 * <p>
 * data about an attached document if only to be able to select a document without necessarily
 * <p>
 * reading it first, and further share by means like reassignment or  email
 */
@Data
@NoArgsConstructor
public class TransactionDocumentMetadata {


    private Long id;

    @NotNull
    private String transactionNumber;

    @NotNull
    private LocalDate transactionDate;

    private String briefDescription;

    private String justification;

    private BigDecimal transactionAmount;

    private String payeeName;

    private String invoiceNumber;

    private String lpoNumber;

    private String debitNoteNumber;

    private String logisticReferenceNumber;

    private String memoNumber;

    private String documentStandardNumber;

    private String transactionAttachmentContentType;

    @NotNull
    private String filename;

    private Set<SchemeDTO> schemes = new HashSet<>();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TransactionDocumentMetadataResource{");
        sb.append("transactionNumber='").append(transactionNumber).append('\'');
        sb.append(", transactionDate=").append(transactionDate);
        sb.append(", briefDescription='").append(briefDescription).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
