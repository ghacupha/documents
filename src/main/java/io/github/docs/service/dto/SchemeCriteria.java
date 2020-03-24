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

/**
 * Criteria class for the {@link io.github.docs.domain.Scheme} entity. This class is used
 * in {@link io.github.docs.web.rest.SchemeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /schemes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SchemeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter schemeName;

    private StringFilter schemeCode;

    private StringFilter schemeDescription;

    private LongFilter transactionDocumentsId;

    private LongFilter formalDocumentsId;

    public SchemeCriteria() {
    }

    public SchemeCriteria(SchemeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.schemeName = other.schemeName == null ? null : other.schemeName.copy();
        this.schemeCode = other.schemeCode == null ? null : other.schemeCode.copy();
        this.schemeDescription = other.schemeDescription == null ? null : other.schemeDescription.copy();
        this.transactionDocumentsId = other.transactionDocumentsId == null ? null : other.transactionDocumentsId.copy();
        this.formalDocumentsId = other.formalDocumentsId == null ? null : other.formalDocumentsId.copy();
    }

    @Override
    public SchemeCriteria copy() {
        return new SchemeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(StringFilter schemeName) {
        this.schemeName = schemeName;
    }

    public StringFilter getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(StringFilter schemeCode) {
        this.schemeCode = schemeCode;
    }

    public StringFilter getSchemeDescription() {
        return schemeDescription;
    }

    public void setSchemeDescription(StringFilter schemeDescription) {
        this.schemeDescription = schemeDescription;
    }

    public LongFilter getTransactionDocumentsId() {
        return transactionDocumentsId;
    }

    public void setTransactionDocumentsId(LongFilter transactionDocumentsId) {
        this.transactionDocumentsId = transactionDocumentsId;
    }

    public LongFilter getFormalDocumentsId() {
        return formalDocumentsId;
    }

    public void setFormalDocumentsId(LongFilter formalDocumentsId) {
        this.formalDocumentsId = formalDocumentsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SchemeCriteria that = (SchemeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(schemeName, that.schemeName) &&
            Objects.equals(schemeCode, that.schemeCode) &&
            Objects.equals(schemeDescription, that.schemeDescription) &&
            Objects.equals(transactionDocumentsId, that.transactionDocumentsId) &&
            Objects.equals(formalDocumentsId, that.formalDocumentsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        schemeName,
        schemeCode,
        schemeDescription,
        transactionDocumentsId,
        formalDocumentsId
        );
    }

    @Override
    public String toString() {
        return "SchemeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (schemeName != null ? "schemeName=" + schemeName + ", " : "") +
                (schemeCode != null ? "schemeCode=" + schemeCode + ", " : "") +
                (schemeDescription != null ? "schemeDescription=" + schemeDescription + ", " : "") +
                (transactionDocumentsId != null ? "transactionDocumentsId=" + transactionDocumentsId + ", " : "") +
                (formalDocumentsId != null ? "formalDocumentsId=" + formalDocumentsId + ", " : "") +
            "}";
    }

}
