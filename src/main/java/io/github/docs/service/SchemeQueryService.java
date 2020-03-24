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

import io.github.docs.domain.Scheme;
import io.github.docs.domain.*; // for static metamodels
import io.github.docs.repository.SchemeRepository;
import io.github.docs.service.dto.SchemeCriteria;
import io.github.docs.service.dto.SchemeDTO;
import io.github.docs.service.mapper.SchemeMapper;

/**
 * Service for executing complex queries for {@link Scheme} entities in the database.
 * The main input is a {@link SchemeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SchemeDTO} or a {@link Page} of {@link SchemeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SchemeQueryService extends QueryService<Scheme> {

    private final Logger log = LoggerFactory.getLogger(SchemeQueryService.class);

    private final SchemeRepository schemeRepository;

    private final SchemeMapper schemeMapper;

    public SchemeQueryService(SchemeRepository schemeRepository, SchemeMapper schemeMapper) {
        this.schemeRepository = schemeRepository;
        this.schemeMapper = schemeMapper;
    }

    /**
     * Return a {@link List} of {@link SchemeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SchemeDTO> findByCriteria(SchemeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Scheme> specification = createSpecification(criteria);
        return schemeMapper.toDto(schemeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SchemeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SchemeDTO> findByCriteria(SchemeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Scheme> specification = createSpecification(criteria);
        return schemeRepository.findAll(specification, page)
            .map(schemeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SchemeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Scheme> specification = createSpecification(criteria);
        return schemeRepository.count(specification);
    }

    /**
     * Function to convert {@link SchemeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Scheme> createSpecification(SchemeCriteria criteria) {
        Specification<Scheme> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Scheme_.id));
            }
            if (criteria.getSchemeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSchemeName(), Scheme_.schemeName));
            }
            if (criteria.getSchemeCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSchemeCode(), Scheme_.schemeCode));
            }
            if (criteria.getSchemeDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSchemeDescription(), Scheme_.schemeDescription));
            }
            if (criteria.getTransactionDocumentsId() != null) {
                specification = specification.and(buildSpecification(criteria.getTransactionDocumentsId(),
                    root -> root.join(Scheme_.transactionDocuments, JoinType.LEFT).get(TransactionDocument_.id)));
            }
            if (criteria.getFormalDocumentsId() != null) {
                specification = specification.and(buildSpecification(criteria.getFormalDocumentsId(),
                    root -> root.join(Scheme_.formalDocuments, JoinType.LEFT).get(FormalDocument_.id)));
            }
        }
        return specification;
    }
}
