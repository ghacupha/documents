package io.github.docs.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.docs.domain.Scheme} entity.
 */
public class SchemeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String schemeName;

    @NotNull
    private String schemeCode;

    private String schemeDescription;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeDescription() {
        return schemeDescription;
    }

    public void setSchemeDescription(String schemeDescription) {
        this.schemeDescription = schemeDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SchemeDTO schemeDTO = (SchemeDTO) o;
        if (schemeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schemeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchemeDTO{" +
            "id=" + getId() +
            ", schemeName='" + getSchemeName() + "'" +
            ", schemeCode='" + getSchemeCode() + "'" +
            ", schemeDescription='" + getSchemeDescription() + "'" +
            "}";
    }
}
