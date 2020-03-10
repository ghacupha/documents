package io.github.docs.service.impl;

import io.github.docs.service.TransactionDocumentService;
import io.github.docs.domain.TransactionDocument;
import io.github.docs.repository.TransactionDocumentRepository;
import io.github.docs.service.dto.TransactionDocumentDTO;
import io.github.docs.service.mapper.TransactionDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TransactionDocument}.
 */
@Service
@Transactional
public class TransactionDocumentServiceImpl implements TransactionDocumentService {

    private final Logger log = LoggerFactory.getLogger(TransactionDocumentServiceImpl.class);

    private final TransactionDocumentRepository transactionDocumentRepository;

    private final TransactionDocumentMapper transactionDocumentMapper;

    public TransactionDocumentServiceImpl(TransactionDocumentRepository transactionDocumentRepository, TransactionDocumentMapper transactionDocumentMapper) {
        this.transactionDocumentRepository = transactionDocumentRepository;
        this.transactionDocumentMapper = transactionDocumentMapper;
    }

    /**
     * Save a transactionDocument.
     *
     * @param transactionDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionDocumentDTO save(TransactionDocumentDTO transactionDocumentDTO) {
        log.debug("Request to save TransactionDocument : {}", transactionDocumentDTO);
        TransactionDocument transactionDocument = transactionDocumentMapper.toEntity(transactionDocumentDTO);
        transactionDocument = transactionDocumentRepository.save(transactionDocument);
        return transactionDocumentMapper.toDto(transactionDocument);
    }

    /**
     * Get all the transactionDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransactionDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionDocuments");
        return transactionDocumentRepository.findAll(pageable)
            .map(transactionDocumentMapper::toDto);
    }

    /**
     * Get all the transactionDocuments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TransactionDocumentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return transactionDocumentRepository.findAllWithEagerRelationships(pageable).map(transactionDocumentMapper::toDto);
    }

    /**
     * Get one transactionDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionDocumentDTO> findOne(Long id) {
        log.debug("Request to get TransactionDocument : {}", id);
        return transactionDocumentRepository.findOneWithEagerRelationships(id)
            .map(transactionDocumentMapper::toDto);
    }

    /**
     * Delete the transactionDocument by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionDocument : {}", id);
        transactionDocumentRepository.deleteById(id);
    }
}
