package io.github.docs.service.impl;

import io.github.docs.service.SchemeService;
import io.github.docs.domain.Scheme;
import io.github.docs.repository.SchemeRepository;
import io.github.docs.service.dto.SchemeDTO;
import io.github.docs.service.mapper.SchemeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Scheme}.
 */
@Service
@Transactional
public class SchemeServiceImpl implements SchemeService {

    private final Logger log = LoggerFactory.getLogger(SchemeServiceImpl.class);

    private final SchemeRepository schemeRepository;

    private final SchemeMapper schemeMapper;

    public SchemeServiceImpl(SchemeRepository schemeRepository, SchemeMapper schemeMapper) {
        this.schemeRepository = schemeRepository;
        this.schemeMapper = schemeMapper;
    }

    /**
     * Save a scheme.
     *
     * @param schemeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SchemeDTO save(SchemeDTO schemeDTO) {
        log.debug("Request to save Scheme : {}", schemeDTO);
        Scheme scheme = schemeMapper.toEntity(schemeDTO);
        scheme = schemeRepository.save(scheme);
        return schemeMapper.toDto(scheme);
    }

    /**
     * Get all the schemes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SchemeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Schemes");
        return schemeRepository.findAll(pageable)
            .map(schemeMapper::toDto);
    }

    /**
     * Get one scheme by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SchemeDTO> findOne(Long id) {
        log.debug("Request to get Scheme : {}", id);
        return schemeRepository.findById(id)
            .map(schemeMapper::toDto);
    }

    /**
     * Delete the scheme by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Scheme : {}", id);
        schemeRepository.deleteById(id);
    }
}
