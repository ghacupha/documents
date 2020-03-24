package io.github.docs.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import io.github.docs.domain.enumeration.DocumentType;

/**
 * A FormalDocument.
 */
@Entity
@Table(name = "formal_document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FormalDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "document_title", nullable = false)
    private String documentTitle;

    @Column(name = "document_subject")
    private String documentSubject;

    @Column(name = "brief_description")
    private String briefDescription;

    @Column(name = "document_date")
    private LocalDate documentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;

    @Column(name = "document_standard_number")
    private String documentStandardNumber;

    
    @Lob
    @Column(name = "document_attachment", nullable = false)
    private byte[] documentAttachment;

    @Column(name = "document_attachment_content_type", nullable = false)
    private String documentAttachmentContentType;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "formal_document_schemes",
               joinColumns = @JoinColumn(name = "formal_document_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "schemes_id", referencedColumnName = "id"))
    private Set<Scheme> schemes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public FormalDocument documentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
        return this;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getDocumentSubject() {
        return documentSubject;
    }

    public FormalDocument documentSubject(String documentSubject) {
        this.documentSubject = documentSubject;
        return this;
    }

    public void setDocumentSubject(String documentSubject) {
        this.documentSubject = documentSubject;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public FormalDocument briefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
        return this;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public LocalDate getDocumentDate() {
        return documentDate;
    }

    public FormalDocument documentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
        return this;
    }

    public void setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public FormalDocument documentType(DocumentType documentType) {
        this.documentType = documentType;
        return this;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentStandardNumber() {
        return documentStandardNumber;
    }

    public FormalDocument documentStandardNumber(String documentStandardNumber) {
        this.documentStandardNumber = documentStandardNumber;
        return this;
    }

    public void setDocumentStandardNumber(String documentStandardNumber) {
        this.documentStandardNumber = documentStandardNumber;
    }

    public byte[] getDocumentAttachment() {
        return documentAttachment;
    }

    public FormalDocument documentAttachment(byte[] documentAttachment) {
        this.documentAttachment = documentAttachment;
        return this;
    }

    public void setDocumentAttachment(byte[] documentAttachment) {
        this.documentAttachment = documentAttachment;
    }

    public String getDocumentAttachmentContentType() {
        return documentAttachmentContentType;
    }

    public FormalDocument documentAttachmentContentType(String documentAttachmentContentType) {
        this.documentAttachmentContentType = documentAttachmentContentType;
        return this;
    }

    public void setDocumentAttachmentContentType(String documentAttachmentContentType) {
        this.documentAttachmentContentType = documentAttachmentContentType;
    }

    public Set<Scheme> getSchemes() {
        return schemes;
    }

    public FormalDocument schemes(Set<Scheme> schemes) {
        this.schemes = schemes;
        return this;
    }

    public FormalDocument addSchemes(Scheme scheme) {
        this.schemes.add(scheme);
        scheme.getFormalDocuments().add(this);
        return this;
    }

    public FormalDocument removeSchemes(Scheme scheme) {
        this.schemes.remove(scheme);
        scheme.getFormalDocuments().remove(this);
        return this;
    }

    public void setSchemes(Set<Scheme> schemes) {
        this.schemes = schemes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormalDocument)) {
            return false;
        }
        return id != null && id.equals(((FormalDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FormalDocument{" +
            "id=" + getId() +
            ", documentTitle='" + getDocumentTitle() + "'" +
            ", documentSubject='" + getDocumentSubject() + "'" +
            ", briefDescription='" + getBriefDescription() + "'" +
            ", documentDate='" + getDocumentDate() + "'" +
            ", documentType='" + getDocumentType() + "'" +
            ", documentStandardNumber='" + getDocumentStandardNumber() + "'" +
            ", documentAttachment='" + getDocumentAttachment() + "'" +
            ", documentAttachmentContentType='" + getDocumentAttachmentContentType() + "'" +
            "}";
    }
}
