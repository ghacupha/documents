package io.github.docs.app;

import io.github.docs.service.dto.TransactionDocumentCriteria;
import io.github.docs.service.dto.TransactionDocumentDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

/**
 * This interface is an exact copy of the resource scaffolded by the jhipster generator.
 *
 * Among other things the idea is that we need custom code to intercept API calls in a way
 * that the user accounting makes a UserProfile responsible only for the content the User
 * has created.
 *
 * This is further enforced by the fact that when a user profile is created the user is limited
 * to choosing only themselves and none other
 */
public interface ITransactionDocumentResource {
    /**
     * {@code POST  /transaction-documents} : Create a new transactionDocument.
     *
     * @param transactionDocumentDTO the transactionDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionDocumentDTO, or with status {@code 400 (Bad Request)} if the transactionDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-documents")
    ResponseEntity<TransactionDocumentDTO> createTransactionDocument(@Valid @RequestBody TransactionDocumentDTO transactionDocumentDTO) throws URISyntaxException;

    /**
     * {@code PUT  /transaction-documents} : Updates an existing transactionDocument.
     *
     * @param transactionDocumentDTO the transactionDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the transactionDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-documents")
    ResponseEntity<TransactionDocumentDTO> updateTransactionDocument(@Valid @RequestBody TransactionDocumentDTO transactionDocumentDTO) throws URISyntaxException;

    /**
     * {@code GET  /transaction-documents} : get all the transactionDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionDocuments in body.
     */
    @GetMapping("/transaction-documents")
    ResponseEntity<List<TransactionDocumentDTO>> getAllTransactionDocuments(TransactionDocumentCriteria criteria, Pageable pageable);

    /**
     * {@code GET  /transaction-documents/count} : count all the transactionDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transaction-documents/count")
    ResponseEntity<Long> countTransactionDocuments(TransactionDocumentCriteria criteria);

    /**
     * {@code GET  /transaction-documents/:id} : get the "id" transactionDocument.
     *
     * @param id the id of the transactionDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-documents/{id}")
    ResponseEntity<TransactionDocumentDTO> getTransactionDocument(@PathVariable Long id);

    /**
     * {@code DELETE  /transaction-documents/:id} : delete the "id" transactionDocument.
     *
     * @param id the id of the transactionDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-documents/{id}")
    ResponseEntity<Void> deleteTransactionDocument(@PathVariable Long id);
}
