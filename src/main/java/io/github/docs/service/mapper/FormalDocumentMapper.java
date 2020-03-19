package io.github.docs.service.mapper;


import io.github.docs.domain.*;
import io.github.docs.service.dto.FormalDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FormalDocument} and its DTO {@link FormalDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FormalDocumentMapper extends EntityMapper<FormalDocumentDTO, FormalDocument> {



    default FormalDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        FormalDocument formalDocument = new FormalDocument();
        formalDocument.setId(id);
        return formalDocument;
    }
}
