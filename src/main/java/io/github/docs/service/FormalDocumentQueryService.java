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

import io.github.docs.domain.FormalDocument;
import io.github.docs.domain.*; // for static metamodels
import io.github.docs.repository.FormalDocumentRepository;
import io.github.docs.service.dto.FormalDocumentCriteria;
import io.github.docs.service.dto.FormalDocumentDTO;
import io.github.docs.service.mapper.FormalDocumentMapper;

/**
 * Service for executing complex queries for {@link FormalDocument} entities in the database.
 * The main input is a {@link FormalDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FormalDocumentDTO} or a {@link Page} of {@link FormalDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FormalDocumentQueryService extends QueryService<FormalDocument> {

    private final Logger log = LoggerFactory.getLogger(FormalDocumentQueryService.class);

    private final FormalDocumentRepository formalDocumentRepository;

    private final FormalDocumentMapper formalDocumentMapper;

    public FormalDocumentQueryService(FormalDocumentRepository formalDocumentRepository, FormalDocumentMapper formalDocumentMapper) {
        this.formalDocumentRepository = formalDocumentRepository;
        this.formalDocumentMapper = formalDocumentMapper;
    }

    /**
     * Return a {@link List} of {@link FormalDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FormalDocumentDTO> findByCriteria(FormalDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FormalDocument> specification = createSpecification(criteria);
        return formalDocumentMapper.toDto(formalDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FormalDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FormalDocumentDTO> findByCriteria(FormalDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FormalDocument> specification = createSpecification(criteria);
        return formalDocumentRepository.findAll(specification, page)
            .map(formalDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FormalDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FormalDocument> specification = createSpecification(criteria);
        return formalDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link FormalDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FormalDocument> createSpecification(FormalDocumentCriteria criteria) {
        Specification<FormalDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FormalDocument_.id));
            }
            if (criteria.getDocumentTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentTitle(), FormalDocument_.documentTitle));
            }
            if (criteria.getDocumentSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentSubject(), FormalDocument_.documentSubject));
            }
            if (criteria.getBriefDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBriefDescription(), FormalDocument_.briefDescription));
            }
            if (criteria.getDocumentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDocumentDate(), FormalDocument_.documentDate));
            }
            if (criteria.getDocumentType() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentType(), FormalDocument_.documentType));
            }
            if (criteria.getDocumentStandardNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStandardNumber(), FormalDocument_.documentStandardNumber));
            }
            if (criteria.getDocumentOwnersId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentOwnersId(),
                    root -> root.join(FormalDocument_.documentOwners, JoinType.LEFT).get(UserProfile_.id)));
            }
        }
        return specification;
    }
}
