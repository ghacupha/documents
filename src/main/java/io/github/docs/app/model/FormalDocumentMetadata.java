package io.github.docs.app.model;

import io.github.docs.domain.enumeration.DocumentType;
import io.github.docs.service.dto.SchemeDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Metadata about a formal document
 */
@Data
@NoArgsConstructor
public class FormalDocumentMetadata {

    private Long id;

    @NotNull
    private String documentTitle;

    private String documentSubject;

    private String briefDescription;

    private LocalDate documentDate;

    private DocumentType documentType;

    private String documentStandardNumber;

    @NotNull
    private String filename;

    private Set<SchemeDTO> schemes = new HashSet<>();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FormalDocumentMetadata{");
        sb.append("id=").append(id);
        sb.append(", documentTitle='").append(documentTitle).append('\'');
        sb.append(", documentSubject='").append(documentSubject).append('\'');
        sb.append(", briefDescription='").append(briefDescription).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
