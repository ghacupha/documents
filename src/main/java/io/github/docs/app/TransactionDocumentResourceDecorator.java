package io.github.docs.app;

import io.github.docs.service.dto.TransactionDocumentCriteria;
import io.github.docs.service.dto.TransactionDocumentDTO;
import io.github.docs.web.rest.TransactionDocumentResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

/**
 * This class implements an interface while using the methods of an existing implementation
 *
 * The idea is to make sure generated code does not have to account for changes made in the
 * custom code and interfaces
 *
 * This class can then be extended by the said custom code if only to modify individual rules
 * in individual methods or to enhance the criteria for security purposes
 */
public class TransactionDocumentResourceDecorator implements ITransactionDocumentResource {

    private final TransactionDocumentResource transactionDocumentResource;

    public TransactionDocumentResourceDecorator(final TransactionDocumentResource transactionDocumentResource) {
        this.transactionDocumentResource = transactionDocumentResource;
    }

    @Override
    public ResponseEntity<TransactionDocumentDTO> createTransactionDocument(@Valid final TransactionDocumentDTO transactionDocumentDTO) throws URISyntaxException {

        return transactionDocumentResource.createTransactionDocument(transactionDocumentDTO);
    }

    @Override
    public ResponseEntity<TransactionDocumentDTO> updateTransactionDocument(@Valid final TransactionDocumentDTO transactionDocumentDTO) throws URISyntaxException {
        return transactionDocumentResource.updateTransactionDocument(transactionDocumentDTO);
    }

    @Override
    public ResponseEntity<List<TransactionDocumentDTO>> getAllTransactionDocuments(final TransactionDocumentCriteria criteria, final Pageable pageable) {
        return transactionDocumentResource.getAllTransactionDocuments(criteria, pageable);
    }

    @Override
    public ResponseEntity<Long> countTransactionDocuments(final TransactionDocumentCriteria criteria) {
        return transactionDocumentResource.countTransactionDocuments(criteria);
    }

    @Override
    public ResponseEntity<TransactionDocumentDTO> getTransactionDocument(final Long id) {
        return transactionDocumentResource.getTransactionDocument(id);
    }

    @Override
    public ResponseEntity<Void> deleteTransactionDocument(final Long id) {
        return transactionDocumentResource.deleteTransactionDocument(id);
    }
}
