package io.github.docs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "user_profile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "staff_number")
    private String staffNumber;

    @OneToOne(optional = false)
    @NotNull

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("userProfiles")
    private Department department;

    @ManyToMany
    @JoinTable(name = "user_profile_transaction_documents",
               joinColumns = @JoinColumn(name = "user_profile_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "transaction_documents_id", referencedColumnName = "id"))
    private Set<TransactionDocument> transactionDocuments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_profile_formal_documents",
               joinColumns = @JoinColumn(name = "user_profile_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "formal_documents_id", referencedColumnName = "id"))
    private Set<FormalDocument> formalDocuments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public UserProfile staffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
        return this;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public User getUser() {
        return user;
    }

    public UserProfile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Department getDepartment() {
        return department;
    }

    public UserProfile department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<TransactionDocument> getTransactionDocuments() {
        return transactionDocuments;
    }

    public UserProfile transactionDocuments(Set<TransactionDocument> transactionDocuments) {
        this.transactionDocuments = transactionDocuments;
        return this;
    }

    public UserProfile addTransactionDocuments(TransactionDocument transactionDocument) {
        this.transactionDocuments.add(transactionDocument);
        transactionDocument.getDocumentOwners().add(this);
        return this;
    }

    public UserProfile removeTransactionDocuments(TransactionDocument transactionDocument) {
        this.transactionDocuments.remove(transactionDocument);
        transactionDocument.getDocumentOwners().remove(this);
        return this;
    }

    public void setTransactionDocuments(Set<TransactionDocument> transactionDocuments) {
        this.transactionDocuments = transactionDocuments;
    }

    public Set<FormalDocument> getFormalDocuments() {
        return formalDocuments;
    }

    public UserProfile formalDocuments(Set<FormalDocument> formalDocuments) {
        this.formalDocuments = formalDocuments;
        return this;
    }

    public UserProfile addFormalDocuments(FormalDocument formalDocument) {
        this.formalDocuments.add(formalDocument);
        formalDocument.getDocumentOwners().add(this);
        return this;
    }

    public UserProfile removeFormalDocuments(FormalDocument formalDocument) {
        this.formalDocuments.remove(formalDocument);
        formalDocument.getDocumentOwners().remove(this);
        return this;
    }

    public void setFormalDocuments(Set<FormalDocument> formalDocuments) {
        this.formalDocuments = formalDocuments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProfile)) {
            return false;
        }
        return id != null && id.equals(((UserProfile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + getId() +
            ", staffNumber='" + getStaffNumber() + "'" +
            "}";
    }
}
