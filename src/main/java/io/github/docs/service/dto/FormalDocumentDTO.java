package io.github.docs.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import io.github.docs.domain.enumeration.DocumentType;

/**
 * A DTO for the {@link io.github.docs.domain.FormalDocument} entity.
 */
public class FormalDocumentDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String documentTitle;

    private String documentSubject;

    private String briefDescription;

    private LocalDate documentDate;

    private DocumentType documentType;

    private String documentStandardNumber;

    
    @Lob
    private byte[] documentAttachment;

    private String documentAttachmentContentType;
    private Set<SchemeDTO> schemes = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getDocumentSubject() {
        return documentSubject;
    }

    public void setDocumentSubject(String documentSubject) {
        this.documentSubject = documentSubject;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public LocalDate getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentStandardNumber() {
        return documentStandardNumber;
    }

    public void setDocumentStandardNumber(String documentStandardNumber) {
        this.documentStandardNumber = documentStandardNumber;
    }

    public byte[] getDocumentAttachment() {
        return documentAttachment;
    }

    public void setDocumentAttachment(byte[] documentAttachment) {
        this.documentAttachment = documentAttachment;
    }

    public String getDocumentAttachmentContentType() {
        return documentAttachmentContentType;
    }

    public void setDocumentAttachmentContentType(String documentAttachmentContentType) {
        this.documentAttachmentContentType = documentAttachmentContentType;
    }

    public Set<SchemeDTO> getSchemes() {
        return schemes;
    }

    public void setSchemes(Set<SchemeDTO> schemes) {
        this.schemes = schemes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormalDocumentDTO formalDocumentDTO = (FormalDocumentDTO) o;
        if (formalDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formalDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormalDocumentDTO{" +
            "id=" + getId() +
            ", documentTitle='" + getDocumentTitle() + "'" +
            ", documentSubject='" + getDocumentSubject() + "'" +
            ", briefDescription='" + getBriefDescription() + "'" +
            ", documentDate='" + getDocumentDate() + "'" +
            ", documentType='" + getDocumentType() + "'" +
            ", documentStandardNumber='" + getDocumentStandardNumber() + "'" +
            ", documentAttachment='" + getDocumentAttachment() + "'" +
            ", schemes='" + getSchemes() + "'" +
            "}";
    }
}
