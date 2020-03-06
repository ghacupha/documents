package io.github.docs.app;

import io.github.docs.service.dto.TransactionDocumentCriteria;
import io.github.docs.service.dto.TransactionDocumentDTO;
import io.github.docs.web.rest.TransactionDocumentResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/api")
public class AppTransactionDocument extends TransactionDocumentResourceDecorator implements ITransactionDocumentResource {

    public AppTransactionDocument(final TransactionDocumentResource transactionDocumentResource) {
        super(transactionDocumentResource);
    }

    /**
     * {@code POST  /transaction-documents} : Create a new transactionDocument.
     *
     * @param transactionDocumentDTO the transactionDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionDocumentDTO, or with status {@code 400 (Bad Request)} if the transactionDocument has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-documents")
    public ResponseEntity<TransactionDocumentDTO> createTransactionDocument(@Valid @RequestBody TransactionDocumentDTO transactionDocumentDTO) throws URISyntaxException {

        return super.createTransactionDocument(transactionDocumentDTO);
    }

    /**
     * {@code PUT  /transaction-documents} : Updates an existing transactionDocument.
     *
     * @param transactionDocumentDTO the transactionDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionDocumentDTO, or with status {@code 400 (Bad Request)} if the transactionDocumentDTO is not
     * valid, or with status {@code 500 (Internal Server Error)} if the transactionDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-documents")
    public ResponseEntity<TransactionDocumentDTO> updateTransactionDocument(@Valid @RequestBody TransactionDocumentDTO transactionDocumentDTO) throws URISyntaxException {

        return super.updateTransactionDocument(transactionDocumentDTO);
    }

    /**
     * {@code GET  /transaction-documents} : get all the transactionDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionDocuments in body.
     */
    @GetMapping("/transaction-documents")
    public ResponseEntity<List<TransactionDocumentDTO>> getAllTransactionDocuments(TransactionDocumentCriteria criteria, Pageable pageable) {

        // TODO enhance criteria to limit UserProfile scope to only self-created stuff
        return super.getAllTransactionDocuments(criteria, pageable);
    }

    /**
     * {@code GET  /transaction-documents/count} : count all the transactionDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transaction-documents/count")
    public ResponseEntity<Long> countTransactionDocuments(TransactionDocumentCriteria criteria) {

        // TODO Only documents created by current user
        return super.countTransactionDocuments(criteria);
    }

    /**
     * {@code GET  /transaction-documents/:id} : get the "id" transactionDocument.
     *
     * @param id the id of the transactionDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-documents/{id}")
    public ResponseEntity<TransactionDocumentDTO> getTransactionDocument(@PathVariable Long id) {
        // TODO remove access from TransactionDocuments not created by current UserProfile
        return super.getTransactionDocument(id);
    }

    /**
     * {@code DELETE  /transaction-documents/:id} : delete the "id" transactionDocument.
     *
     * @param id the id of the transactionDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-documents/{id}")
    public ResponseEntity<Void> deleteTransactionDocument(@PathVariable Long id) {
        // TODO remove this UserProfile from the Transaction document
        return super.deleteTransactionDocument(id);
    }
}
