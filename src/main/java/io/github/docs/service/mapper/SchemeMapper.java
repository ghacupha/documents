package io.github.docs.service.mapper;


import io.github.docs.domain.*;
import io.github.docs.service.dto.SchemeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Scheme} and its DTO {@link SchemeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchemeMapper extends EntityMapper<SchemeDTO, Scheme> {


    @Mapping(target = "transactionDocuments", ignore = true)
    @Mapping(target = "removeTransactionDocuments", ignore = true)
    @Mapping(target = "formalDocuments", ignore = true)
    @Mapping(target = "removeFormalDocuments", ignore = true)
    Scheme toEntity(SchemeDTO schemeDTO);

    default Scheme fromId(Long id) {
        if (id == null) {
            return null;
        }
        Scheme scheme = new Scheme();
        scheme.setId(id);
        return scheme;
    }
}
