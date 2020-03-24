package io.github.docs.service;

import io.github.docs.service.dto.FormalDocumentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.docs.domain.FormalDocument}.
 */
public interface FormalDocumentService {

    /**
     * Save a formalDocument.
     *
     * @param formalDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    FormalDocumentDTO save(FormalDocumentDTO formalDocumentDTO);

    /**
     * Get all the formalDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormalDocumentDTO> findAll(Pageable pageable);

    /**
     * Get all the formalDocuments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<FormalDocumentDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" formalDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormalDocumentDTO> findOne(Long id);

    /**
     * Delete the "id" formalDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
