package io.github.docs.app;

import io.github.docs.app.model.TransactionDocumentMetadata;
import io.github.docs.domain.TransactionDocument;
import io.github.docs.service.dto.TransactionDocumentDTO;
import io.github.docs.service.mapper.SchemeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * A low-tech scaffold for mapping between transaction-document-dto's and transaction-document-metadata
 */
@Mapper(componentModel = "spring", uses = {SchemeMapper.class})
public interface TransactionDocumentMetadataMapper extends io.github.docs.app.Mapping<TransactionDocumentDTO, TransactionDocumentMetadata> {

    @Mapping(target = "removeSchemes", ignore = true)

    default TransactionDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionDocument transactionDocument = new TransactionDocument();
        transactionDocument.setId(id);
        return transactionDocument;
    }
}
