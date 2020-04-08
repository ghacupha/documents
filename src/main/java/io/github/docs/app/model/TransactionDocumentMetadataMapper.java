package io.github.docs.app.model;

import io.github.docs.domain.TransactionDocument;
import io.github.docs.service.dto.TransactionDocumentDTO;
import io.github.docs.service.mapper.SchemeMapper;
import org.mapstruct.Mapper;

/**
 * A low-tech scaffold for mapping between transaction-document-dto's and transaction-document-metadata
 */
@Mapper(componentModel = "spring", uses = {SchemeMapper.class})
public interface TransactionDocumentMetadataMapper extends Mapping<TransactionDocumentDTO, TransactionDocumentMetadata> {

    @org.mapstruct.Mapping(target = "removeSchemes", ignore = true)
    default TransactionDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionDocument transactionDocument = new TransactionDocument();
        transactionDocument.setId(id);
        return transactionDocument;
    }
}
