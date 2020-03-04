package io.github.docs.service.impl;

import io.github.docs.service.FormalDocumentService;
import io.github.docs.domain.FormalDocument;
import io.github.docs.repository.FormalDocumentRepository;
import io.github.docs.service.dto.FormalDocumentDTO;
import io.github.docs.service.mapper.FormalDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FormalDocument}.
 */
@Service
@Transactional
public class FormalDocumentServiceImpl implements FormalDocumentService {

    private final Logger log = LoggerFactory.getLogger(FormalDocumentServiceImpl.class);

    private final FormalDocumentRepository formalDocumentRepository;

    private final FormalDocumentMapper formalDocumentMapper;

    public FormalDocumentServiceImpl(FormalDocumentRepository formalDocumentRepository, FormalDocumentMapper formalDocumentMapper) {
        this.formalDocumentRepository = formalDocumentRepository;
        this.formalDocumentMapper = formalDocumentMapper;
    }

    /**
     * Save a formalDocument.
     *
     * @param formalDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FormalDocumentDTO save(FormalDocumentDTO formalDocumentDTO) {
        log.debug("Request to save FormalDocument : {}", formalDocumentDTO);
        FormalDocument formalDocument = formalDocumentMapper.toEntity(formalDocumentDTO);
        formalDocument = formalDocumentRepository.save(formalDocument);
        return formalDocumentMapper.toDto(formalDocument);
    }

    /**
     * Get all the formalDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormalDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FormalDocuments");
        return formalDocumentRepository.findAll(pageable)
            .map(formalDocumentMapper::toDto);
    }

    /**
     * Get one formalDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FormalDocumentDTO> findOne(Long id) {
        log.debug("Request to get FormalDocument : {}", id);
        return formalDocumentRepository.findById(id)
            .map(formalDocumentMapper::toDto);
    }

    /**
     * Delete the formalDocument by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormalDocument : {}", id);
        formalDocumentRepository.deleteById(id);
    }
}
