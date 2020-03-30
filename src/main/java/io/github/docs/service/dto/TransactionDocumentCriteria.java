package io.github.docs.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.docs.domain.TransactionDocument} entity. This class is used
 * in {@link io.github.docs.web.rest.TransactionDocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transaction-documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransactionDocumentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter transactionNumber;

    private LocalDateFilter transactionDate;

    private StringFilter briefDescription;

    private StringFilter justification;

    private BigDecimalFilter transactionAmount;

    private StringFilter payeeName;

    private StringFilter invoiceNumber;

    private StringFilter lpoNumber;

    private StringFilter debitNoteNumber;

    private StringFilter logisticReferenceNumber;

    private StringFilter memoNumber;

    private StringFilter documentStandardNumber;

    private StringFilter filename;

    private LongFilter schemesId;

    public TransactionDocumentCriteria() {
    }

    public TransactionDocumentCriteria(TransactionDocumentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.transactionNumber = other.transactionNumber == null ? null : other.transactionNumber.copy();
        this.transactionDate = other.transactionDate == null ? null : other.transactionDate.copy();
        this.briefDescription = other.briefDescription == null ? null : other.briefDescription.copy();
        this.justification = other.justification == null ? null : other.justification.copy();
        this.transactionAmount = other.transactionAmount == null ? null : other.transactionAmount.copy();
        this.payeeName = other.payeeName == null ? null : other.payeeName.copy();
        this.invoiceNumber = other.invoiceNumber == null ? null : other.invoiceNumber.copy();
        this.lpoNumber = other.lpoNumber == null ? null : other.lpoNumber.copy();
        this.debitNoteNumber = other.debitNoteNumber == null ? null : other.debitNoteNumber.copy();
        this.logisticReferenceNumber = other.logisticReferenceNumber == null ? null : other.logisticReferenceNumber.copy();
        this.memoNumber = other.memoNumber == null ? null : other.memoNumber.copy();
        this.documentStandardNumber = other.documentStandardNumber == null ? null : other.documentStandardNumber.copy();
        this.filename = other.filename == null ? null : other.filename.copy();
        this.schemesId = other.schemesId == null ? null : other.schemesId.copy();
    }

    @Override
    public TransactionDocumentCriteria copy() {
        return new TransactionDocumentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(StringFilter transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public LocalDateFilter getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateFilter transactionDate) {
        this.transactionDate = transactionDate;
    }

    public StringFilter getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(StringFilter briefDescription) {
        this.briefDescription = briefDescription;
    }

    public StringFilter getJustification() {
        return justification;
    }

    public void setJustification(StringFilter justification) {
        this.justification = justification;
    }

    public BigDecimalFilter getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimalFilter transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public StringFilter getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(StringFilter payeeName) {
        this.payeeName = payeeName;
    }

    public StringFilter getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(StringFilter invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public StringFilter getLpoNumber() {
        return lpoNumber;
    }

    public void setLpoNumber(StringFilter lpoNumber) {
        this.lpoNumber = lpoNumber;
    }

    public StringFilter getDebitNoteNumber() {
        return debitNoteNumber;
    }

    public void setDebitNoteNumber(StringFilter debitNoteNumber) {
        this.debitNoteNumber = debitNoteNumber;
    }

    public StringFilter getLogisticReferenceNumber() {
        return logisticReferenceNumber;
    }

    public void setLogisticReferenceNumber(StringFilter logisticReferenceNumber) {
        this.logisticReferenceNumber = logisticReferenceNumber;
    }

    public StringFilter getMemoNumber() {
        return memoNumber;
    }

    public void setMemoNumber(StringFilter memoNumber) {
        this.memoNumber = memoNumber;
    }

    public StringFilter getDocumentStandardNumber() {
        return documentStandardNumber;
    }

    public void setDocumentStandardNumber(StringFilter documentStandardNumber) {
        this.documentStandardNumber = documentStandardNumber;
    }

    public StringFilter getFilename() {
        return filename;
    }

    public void setFilename(StringFilter filename) {
        this.filename = filename;
    }

    public LongFilter getSchemesId() {
        return schemesId;
    }

    public void setSchemesId(LongFilter schemesId) {
        this.schemesId = schemesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TransactionDocumentCriteria that = (TransactionDocumentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(transactionNumber, that.transactionNumber) &&
            Objects.equals(transactionDate, that.transactionDate) &&
            Objects.equals(briefDescription, that.briefDescription) &&
            Objects.equals(justification, that.justification) &&
            Objects.equals(transactionAmount, that.transactionAmount) &&
            Objects.equals(payeeName, that.payeeName) &&
            Objects.equals(invoiceNumber, that.invoiceNumber) &&
            Objects.equals(lpoNumber, that.lpoNumber) &&
            Objects.equals(debitNoteNumber, that.debitNoteNumber) &&
            Objects.equals(logisticReferenceNumber, that.logisticReferenceNumber) &&
            Objects.equals(memoNumber, that.memoNumber) &&
            Objects.equals(documentStandardNumber, that.documentStandardNumber) &&
            Objects.equals(filename, that.filename) &&
            Objects.equals(schemesId, that.schemesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        transactionNumber,
        transactionDate,
        briefDescription,
        justification,
        transactionAmount,
        payeeName,
        invoiceNumber,
        lpoNumber,
        debitNoteNumber,
        logisticReferenceNumber,
        memoNumber,
        documentStandardNumber,
        filename,
        schemesId
        );
    }

    @Override
    public String toString() {
        return "TransactionDocumentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (transactionNumber != null ? "transactionNumber=" + transactionNumber + ", " : "") +
                (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "") +
                (briefDescription != null ? "briefDescription=" + briefDescription + ", " : "") +
                (justification != null ? "justification=" + justification + ", " : "") +
                (transactionAmount != null ? "transactionAmount=" + transactionAmount + ", " : "") +
                (payeeName != null ? "payeeName=" + payeeName + ", " : "") +
                (invoiceNumber != null ? "invoiceNumber=" + invoiceNumber + ", " : "") +
                (lpoNumber != null ? "lpoNumber=" + lpoNumber + ", " : "") +
                (debitNoteNumber != null ? "debitNoteNumber=" + debitNoteNumber + ", " : "") +
                (logisticReferenceNumber != null ? "logisticReferenceNumber=" + logisticReferenceNumber + ", " : "") +
                (memoNumber != null ? "memoNumber=" + memoNumber + ", " : "") +
                (documentStandardNumber != null ? "documentStandardNumber=" + documentStandardNumber + ", " : "") +
                (filename != null ? "filename=" + filename + ", " : "") +
                (schemesId != null ? "schemesId=" + schemesId + ", " : "") +
            "}";
    }

}
