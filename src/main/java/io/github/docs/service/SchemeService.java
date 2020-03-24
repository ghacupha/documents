package io.github.docs.service;

import io.github.docs.service.dto.SchemeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.docs.domain.Scheme}.
 */
public interface SchemeService {

    /**
     * Save a scheme.
     *
     * @param schemeDTO the entity to save.
     * @return the persisted entity.
     */
    SchemeDTO save(SchemeDTO schemeDTO);

    /**
     * Get all the schemes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SchemeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" scheme.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchemeDTO> findOne(Long id);

    /**
     * Delete the "id" scheme.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
