package io.github.docs.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.docs.domain.enumeration.DocumentType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.docs.domain.FormalDocument} entity. This class is used
 * in {@link io.github.docs.web.rest.FormalDocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /formal-documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FormalDocumentCriteria implements Serializable, Criteria {
    /**
     * Class for filtering DocumentType
     */
    public static class DocumentTypeFilter extends Filter<DocumentType> {

        public DocumentTypeFilter() {
        }

        public DocumentTypeFilter(DocumentTypeFilter filter) {
            super(filter);
        }

        @Override
        public DocumentTypeFilter copy() {
            return new DocumentTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter documentTitle;

    private StringFilter documentSubject;

    private StringFilter briefDescription;

    private LocalDateFilter documentDate;

    private DocumentTypeFilter documentType;

    private StringFilter documentStandardNumber;

    public FormalDocumentCriteria() {
    }

    public FormalDocumentCriteria(FormalDocumentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentTitle = other.documentTitle == null ? null : other.documentTitle.copy();
        this.documentSubject = other.documentSubject == null ? null : other.documentSubject.copy();
        this.briefDescription = other.briefDescription == null ? null : other.briefDescription.copy();
        this.documentDate = other.documentDate == null ? null : other.documentDate.copy();
        this.documentType = other.documentType == null ? null : other.documentType.copy();
        this.documentStandardNumber = other.documentStandardNumber == null ? null : other.documentStandardNumber.copy();
    }

    @Override
    public FormalDocumentCriteria copy() {
        return new FormalDocumentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(StringFilter documentTitle) {
        this.documentTitle = documentTitle;
    }

    public StringFilter getDocumentSubject() {
        return documentSubject;
    }

    public void setDocumentSubject(StringFilter documentSubject) {
        this.documentSubject = documentSubject;
    }

    public StringFilter getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(StringFilter briefDescription) {
        this.briefDescription = briefDescription;
    }

    public LocalDateFilter getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDateFilter documentDate) {
        this.documentDate = documentDate;
    }

    public DocumentTypeFilter getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypeFilter documentType) {
        this.documentType = documentType;
    }

    public StringFilter getDocumentStandardNumber() {
        return documentStandardNumber;
    }

    public void setDocumentStandardNumber(StringFilter documentStandardNumber) {
        this.documentStandardNumber = documentStandardNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FormalDocumentCriteria that = (FormalDocumentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(documentTitle, that.documentTitle) &&
            Objects.equals(documentSubject, that.documentSubject) &&
            Objects.equals(briefDescription, that.briefDescription) &&
            Objects.equals(documentDate, that.documentDate) &&
            Objects.equals(documentType, that.documentType) &&
            Objects.equals(documentStandardNumber, that.documentStandardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        documentTitle,
        documentSubject,
        briefDescription,
        documentDate,
        documentType,
        documentStandardNumber
        );
    }

    @Override
    public String toString() {
        return "FormalDocumentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentTitle != null ? "documentTitle=" + documentTitle + ", " : "") +
                (documentSubject != null ? "documentSubject=" + documentSubject + ", " : "") +
                (briefDescription != null ? "briefDescription=" + briefDescription + ", " : "") +
                (documentDate != null ? "documentDate=" + documentDate + ", " : "") +
                (documentType != null ? "documentType=" + documentType + ", " : "") +
                (documentStandardNumber != null ? "documentStandardNumber=" + documentStandardNumber + ", " : "") +
            "}";
    }

}
