package io.github.docs.service.mapper;


import io.github.docs.domain.*;
import io.github.docs.service.dto.TransactionDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionDocument} and its DTO {@link TransactionDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionDocumentMapper extends EntityMapper<TransactionDocumentDTO, TransactionDocument> {


    @Mapping(target = "documentOwners", ignore = true)
    @Mapping(target = "removeDocumentOwners", ignore = true)
    TransactionDocument toEntity(TransactionDocumentDTO transactionDocumentDTO);

    default TransactionDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionDocument transactionDocument = new TransactionDocument();
        transactionDocument.setId(id);
        return transactionDocument;
    }
}
