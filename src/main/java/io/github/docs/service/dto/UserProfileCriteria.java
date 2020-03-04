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
 * Criteria class for the {@link io.github.docs.domain.UserProfile} entity. This class is used
 * in {@link io.github.docs.web.rest.UserProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserProfileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter staffNumber;

    private LongFilter userId;

    private LongFilter departmentId;

    private LongFilter transactionDocumentsId;

    private LongFilter formalDocumentsId;

    public UserProfileCriteria() {
    }

    public UserProfileCriteria(UserProfileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.staffNumber = other.staffNumber == null ? null : other.staffNumber.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.transactionDocumentsId = other.transactionDocumentsId == null ? null : other.transactionDocumentsId.copy();
        this.formalDocumentsId = other.formalDocumentsId == null ? null : other.formalDocumentsId.copy();
    }

    @Override
    public UserProfileCriteria copy() {
        return new UserProfileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(StringFilter staffNumber) {
        this.staffNumber = staffNumber;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
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
        final UserProfileCriteria that = (UserProfileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(staffNumber, that.staffNumber) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(transactionDocumentsId, that.transactionDocumentsId) &&
            Objects.equals(formalDocumentsId, that.formalDocumentsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        staffNumber,
        userId,
        departmentId,
        transactionDocumentsId,
        formalDocumentsId
        );
    }

    @Override
    public String toString() {
        return "UserProfileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (staffNumber != null ? "staffNumber=" + staffNumber + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
                (transactionDocumentsId != null ? "transactionDocumentsId=" + transactionDocumentsId + ", " : "") +
                (formalDocumentsId != null ? "formalDocumentsId=" + formalDocumentsId + ", " : "") +
            "}";
    }

}
