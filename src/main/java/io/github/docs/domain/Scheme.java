package io.github.docs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Scheme.
 */
@Entity
@Table(name = "scheme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Scheme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "scheme_name", nullable = false, unique = true)
    private String schemeName;

    @NotNull
    @Column(name = "scheme_code", nullable = false, unique = true)
    private String schemeCode;

    @Column(name = "scheme_description")
    private String schemeDescription;

    @ManyToMany(mappedBy = "schemes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<TransactionDocument> transactionDocuments = new HashSet<>();

    @ManyToMany(mappedBy = "schemes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<FormalDocument> formalDocuments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public Scheme schemeName(String schemeName) {
        this.schemeName = schemeName;
        return this;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public Scheme schemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
        return this;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeDescription() {
        return schemeDescription;
    }

    public Scheme schemeDescription(String schemeDescription) {
        this.schemeDescription = schemeDescription;
        return this;
    }

    public void setSchemeDescription(String schemeDescription) {
        this.schemeDescription = schemeDescription;
    }

    public Set<TransactionDocument> getTransactionDocuments() {
        return transactionDocuments;
    }

    public Scheme transactionDocuments(Set<TransactionDocument> transactionDocuments) {
        this.transactionDocuments = transactionDocuments;
        return this;
    }

    public Scheme addTransactionDocuments(TransactionDocument transactionDocument) {
        this.transactionDocuments.add(transactionDocument);
        transactionDocument.getSchemes().add(this);
        return this;
    }

    public Scheme removeTransactionDocuments(TransactionDocument transactionDocument) {
        this.transactionDocuments.remove(transactionDocument);
        transactionDocument.getSchemes().remove(this);
        return this;
    }

    public void setTransactionDocuments(Set<TransactionDocument> transactionDocuments) {
        this.transactionDocuments = transactionDocuments;
    }

    public Set<FormalDocument> getFormalDocuments() {
        return formalDocuments;
    }

    public Scheme formalDocuments(Set<FormalDocument> formalDocuments) {
        this.formalDocuments = formalDocuments;
        return this;
    }

    public Scheme addFormalDocuments(FormalDocument formalDocument) {
        this.formalDocuments.add(formalDocument);
        formalDocument.getSchemes().add(this);
        return this;
    }

    public Scheme removeFormalDocuments(FormalDocument formalDocument) {
        this.formalDocuments.remove(formalDocument);
        formalDocument.getSchemes().remove(this);
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
        if (!(o instanceof Scheme)) {
            return false;
        }
        return id != null && id.equals(((Scheme) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Scheme{" +
            "id=" + getId() +
            ", schemeName='" + getSchemeName() + "'" +
            ", schemeCode='" + getSchemeCode() + "'" +
            ", schemeDescription='" + getSchemeDescription() + "'" +
            "}";
    }
}
