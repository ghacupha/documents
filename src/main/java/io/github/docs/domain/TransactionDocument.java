package io.github.docs.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TransactionDocument.
 */
@Entity
@Table(name = "transaction_document")
public class TransactionDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "transaction_number", nullable = false)
    private String transactionNumber;

    @NotNull
    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "brief_description")
    private String briefDescription;

    @Column(name = "justification")
    private String justification;

    @Column(name = "transaction_amount", precision = 21, scale = 2)
    private BigDecimal transactionAmount;

    @Column(name = "payee_name")
    private String payeeName;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "lpo_number")
    private String lpoNumber;

    @Column(name = "debit_note_number")
    private String debitNoteNumber;

    @Column(name = "logistic_reference_number")
    private String logisticReferenceNumber;

    @Column(name = "memo_number")
    private String memoNumber;

    @Column(name = "document_standard_number")
    private String documentStandardNumber;

    
    @Lob
    @Column(name = "transaction_attachment", nullable = false)
    private byte[] transactionAttachment;

    @Column(name = "transaction_attachment_content_type", nullable = false)
    private String transactionAttachmentContentType;

    @ManyToMany
    @JoinTable(name = "transaction_document_document_owners",
               joinColumns = @JoinColumn(name = "transaction_document_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "document_owners_id", referencedColumnName = "id"))
    private Set<UserProfile> documentOwners = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public TransactionDocument transactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
        return this;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public TransactionDocument transactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public TransactionDocument briefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
        return this;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getJustification() {
        return justification;
    }

    public TransactionDocument justification(String justification) {
        this.justification = justification;
        return this;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public TransactionDocument transactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public TransactionDocument payeeName(String payeeName) {
        this.payeeName = payeeName;
        return this;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public TransactionDocument invoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getLpoNumber() {
        return lpoNumber;
    }

    public TransactionDocument lpoNumber(String lpoNumber) {
        this.lpoNumber = lpoNumber;
        return this;
    }

    public void setLpoNumber(String lpoNumber) {
        this.lpoNumber = lpoNumber;
    }

    public String getDebitNoteNumber() {
        return debitNoteNumber;
    }

    public TransactionDocument debitNoteNumber(String debitNoteNumber) {
        this.debitNoteNumber = debitNoteNumber;
        return this;
    }

    public void setDebitNoteNumber(String debitNoteNumber) {
        this.debitNoteNumber = debitNoteNumber;
    }

    public String getLogisticReferenceNumber() {
        return logisticReferenceNumber;
    }

    public TransactionDocument logisticReferenceNumber(String logisticReferenceNumber) {
        this.logisticReferenceNumber = logisticReferenceNumber;
        return this;
    }

    public void setLogisticReferenceNumber(String logisticReferenceNumber) {
        this.logisticReferenceNumber = logisticReferenceNumber;
    }

    public String getMemoNumber() {
        return memoNumber;
    }

    public TransactionDocument memoNumber(String memoNumber) {
        this.memoNumber = memoNumber;
        return this;
    }

    public void setMemoNumber(String memoNumber) {
        this.memoNumber = memoNumber;
    }

    public String getDocumentStandardNumber() {
        return documentStandardNumber;
    }

    public TransactionDocument documentStandardNumber(String documentStandardNumber) {
        this.documentStandardNumber = documentStandardNumber;
        return this;
    }

    public void setDocumentStandardNumber(String documentStandardNumber) {
        this.documentStandardNumber = documentStandardNumber;
    }

    public byte[] getTransactionAttachment() {
        return transactionAttachment;
    }

    public TransactionDocument transactionAttachment(byte[] transactionAttachment) {
        this.transactionAttachment = transactionAttachment;
        return this;
    }

    public void setTransactionAttachment(byte[] transactionAttachment) {
        this.transactionAttachment = transactionAttachment;
    }

    public String getTransactionAttachmentContentType() {
        return transactionAttachmentContentType;
    }

    public TransactionDocument transactionAttachmentContentType(String transactionAttachmentContentType) {
        this.transactionAttachmentContentType = transactionAttachmentContentType;
        return this;
    }

    public void setTransactionAttachmentContentType(String transactionAttachmentContentType) {
        this.transactionAttachmentContentType = transactionAttachmentContentType;
    }

    public Set<UserProfile> getDocumentOwners() {
        return documentOwners;
    }

    public TransactionDocument documentOwners(Set<UserProfile> userProfiles) {
        this.documentOwners = userProfiles;
        return this;
    }

    public TransactionDocument addDocumentOwners(UserProfile userProfile) {
        this.documentOwners.add(userProfile);
        userProfile.getTransactionDocuments().add(this);
        return this;
    }

    public TransactionDocument removeDocumentOwners(UserProfile userProfile) {
        this.documentOwners.remove(userProfile);
        userProfile.getTransactionDocuments().remove(this);
        return this;
    }

    public void setDocumentOwners(Set<UserProfile> userProfiles) {
        this.documentOwners = userProfiles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDocument)) {
            return false;
        }
        return id != null && id.equals(((TransactionDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransactionDocument{" +
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
            ", transactionAttachmentContentType='" + getTransactionAttachmentContentType() + "'" +
            "}";
    }
}
