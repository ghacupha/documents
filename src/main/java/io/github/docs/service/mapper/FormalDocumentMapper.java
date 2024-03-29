package io.github.docs.service.mapper;


import io.github.docs.domain.*;
import io.github.docs.service.dto.FormalDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FormalDocument} and its DTO {@link FormalDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {SchemeMapper.class})
public interface FormalDocumentMapper extends EntityMapper<FormalDocumentDTO, FormalDocument> {


    @Mapping(target = "removeSchemes", ignore = true)

    default FormalDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        FormalDocument formalDocument = new FormalDocument();
        formalDocument.setId(id);
        return formalDocument;
    }
}
