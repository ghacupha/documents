package io.github.docs.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.github.docs.domain.TransactionDocument;
import io.github.docs.domain.*; // for static metamodels
import io.github.docs.repository.TransactionDocumentRepository;
import io.github.docs.service.dto.TransactionDocumentCriteria;
import io.github.docs.service.dto.TransactionDocumentDTO;
import io.github.docs.service.mapper.TransactionDocumentMapper;

/**
 * Service for executing complex queries for {@link TransactionDocument} entities in the database.
 * The main input is a {@link TransactionDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransactionDocumentDTO} or a {@link Page} of {@link TransactionDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransactionDocumentQueryService extends QueryService<TransactionDocument> {

    private final Logger log = LoggerFactory.getLogger(TransactionDocumentQueryService.class);

    private final TransactionDocumentRepository transactionDocumentRepository;

    private final TransactionDocumentMapper transactionDocumentMapper;

    public TransactionDocumentQueryService(TransactionDocumentRepository transactionDocumentRepository, TransactionDocumentMapper transactionDocumentMapper) {
        this.transactionDocumentRepository = transactionDocumentRepository;
        this.transactionDocumentMapper = transactionDocumentMapper;
    }

    /**
     * Return a {@link List} of {@link TransactionDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransactionDocumentDTO> findByCriteria(TransactionDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransactionDocument> specification = createSpecification(criteria);
        return transactionDocumentMapper.toDto(transactionDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransactionDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionDocumentDTO> findByCriteria(TransactionDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransactionDocument> specification = createSpecification(criteria);
        return transactionDocumentRepository.findAll(specification, page)
            .map(transactionDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransactionDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransactionDocument> specification = createSpecification(criteria);
        return transactionDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link TransactionDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TransactionDocument> createSpecification(TransactionDocumentCriteria criteria) {
        Specification<TransactionDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TransactionDocument_.id));
            }
            if (criteria.getTransactionNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransactionNumber(), TransactionDocument_.transactionNumber));
            }
            if (criteria.getTransactionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionDate(), TransactionDocument_.transactionDate));
            }
            if (criteria.getBriefDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBriefDescription(), TransactionDocument_.briefDescription));
            }
            if (criteria.getJustification() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJustification(), TransactionDocument_.justification));
            }
            if (criteria.getTransactionAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionAmount(), TransactionDocument_.transactionAmount));
            }
            if (criteria.getPayeeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPayeeName(), TransactionDocument_.payeeName));
            }
            if (criteria.getInvoiceNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceNumber(), TransactionDocument_.invoiceNumber));
            }
            if (criteria.getLpoNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLpoNumber(), TransactionDocument_.lpoNumber));
            }
            if (criteria.getDebitNoteNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDebitNoteNumber(), TransactionDocument_.debitNoteNumber));
            }
            if (criteria.getLogisticReferenceNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogisticReferenceNumber(), TransactionDocument_.logisticReferenceNumber));
            }
            if (criteria.getMemoNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemoNumber(), TransactionDocument_.memoNumber));
            }
            if (criteria.getDocumentStandardNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStandardNumber(), TransactionDocument_.documentStandardNumber));
            }
        }
        return specification;
    }
}
