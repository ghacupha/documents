package io.github.docs.repository;

import io.github.docs.domain.FormalDocument;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the FormalDocument entity.
 */
@Repository
public interface FormalDocumentRepository extends JpaRepository<FormalDocument, Long>, JpaSpecificationExecutor<FormalDocument> {

    @Query(value = "select distinct formalDocument from FormalDocument formalDocument left join fetch formalDocument.schemes",
        countQuery = "select count(distinct formalDocument) from FormalDocument formalDocument")
    Page<FormalDocument> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct formalDocument from FormalDocument formalDocument left join fetch formalDocument.schemes")
    List<FormalDocument> findAllWithEagerRelationships();

    @Query("select formalDocument from FormalDocument formalDocument left join fetch formalDocument.schemes where formalDocument.id =:id")
    Optional<FormalDocument> findOneWithEagerRelationships(@Param("id") Long id);
}
