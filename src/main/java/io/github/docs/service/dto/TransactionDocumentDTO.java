package io.github.docs.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link io.github.docs.domain.TransactionDocument} entity.
 */
public class TransactionDocumentDTO implements Serializable {

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

    
    @Lob
    private byte[] transactionAttachment;

    private String transactionAttachmentContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getLpoNumber() {
        return lpoNumber;
    }

    public void setLpoNumber(String lpoNumber) {
        this.lpoNumber = lpoNumber;
    }

    public String getDebitNoteNumber() {
        return debitNoteNumber;
    }

    public void setDebitNoteNumber(String debitNoteNumber) {
        this.debitNoteNumber = debitNoteNumber;
    }

    public String getLogisticReferenceNumber() {
        return logisticReferenceNumber;
    }

    public void setLogisticReferenceNumber(String logisticReferenceNumber) {
        this.logisticReferenceNumber = logisticReferenceNumber;
    }

    public String getMemoNumber() {
        return memoNumber;
    }

    public void setMemoNumber(String memoNumber) {
        this.memoNumber = memoNumber;
    }

    public String getDocumentStandardNumber() {
        return documentStandardNumber;
    }

    public void setDocumentStandardNumber(String documentStandardNumber) {
        this.documentStandardNumber = documentStandardNumber;
    }

    public byte[] getTransactionAttachment() {
        return transactionAttachment;
    }

    public void setTransactionAttachment(byte[] transactionAttachment) {
        this.transactionAttachment = transactionAttachment;
    }

    public String getTransactionAttachmentContentType() {
        return transactionAttachmentContentType;
    }

    public void setTransactionAttachmentContentType(String transactionAttachmentContentType) {
        this.transactionAttachmentContentType = transactionAttachmentContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransactionDocumentDTO transactionDocumentDTO = (TransactionDocumentDTO) o;
        if (transactionDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionDocumentDTO{" +
            "id=" + getId() +
            ", transactionNumber='" + getTransactionNumber() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", briefDescription='" + getBriefDescription() + "'" +
            ", justification='" + getJustification() + "'" +
            ", transactionAmount=" + getTransactionAmount() +
            ", payeeName='" + getPayeeName() + "'" +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", lpoNumber='" + getLpoNumber() + "'" +
            ", debitNoteNumber='" + getDebitNoteNumber() + "'" +
            ", logisticReferenceNumber='" + getLogisticReferenceNumber() + "'" +
            ", memoNumber='" + getMemoNumber() + "'" +
            ", documentStandardNumber='" + getDocumentStandardNumber() + "'" +
            ", transactionAttachment='" + getTransactionAttachment() + "'" +
            "}";
    }
}
