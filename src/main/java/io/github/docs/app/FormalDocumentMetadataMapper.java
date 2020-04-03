package io.github.docs.app;

import io.github.docs.app.model.FormalDocumentMetadata;
import io.github.docs.domain.FormalDocument;
import io.github.docs.service.dto.FormalDocumentDTO;
import io.github.docs.service.mapper.SchemeMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SchemeMapper.class})
public interface FormalDocumentMetadataMapper extends Mapping<FormalDocumentDTO, FormalDocumentMetadata> {

    @org.mapstruct.Mapping(target = "removeSchemes", ignore = true)

    default FormalDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        FormalDocument formalDocument = new FormalDocument();
        formalDocument.setId(id);
        return formalDocument;
    }
}
