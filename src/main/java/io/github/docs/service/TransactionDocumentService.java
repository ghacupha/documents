package io.github.docs.service;

import io.github.docs.service.dto.TransactionDocumentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.docs.domain.TransactionDocument}.
 */
public interface TransactionDocumentService {

    /**
     * Save a transactionDocument.
     *
     * @param transactionDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionDocumentDTO save(TransactionDocumentDTO transactionDocumentDTO);

    /**
     * Get all the transactionDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransactionDocumentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" transactionDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionDocumentDTO> findOne(Long id);

    /**
     * Delete the "id" transactionDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
