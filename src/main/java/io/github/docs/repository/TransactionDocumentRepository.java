package io.github.docs.repository;

import io.github.docs.domain.TransactionDocument;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TransactionDocument entity.
 */
@Repository
public interface TransactionDocumentRepository extends JpaRepository<TransactionDocument, Long>, JpaSpecificationExecutor<TransactionDocument> {

    @Query(value = "select distinct transactionDocument from TransactionDocument transactionDocument left join fetch transactionDocument.schemes",
        countQuery = "select count(distinct transactionDocument) from TransactionDocument transactionDocument")
    Page<TransactionDocument> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct transactionDocument from TransactionDocument transactionDocument left join fetch transactionDocument.schemes")
    List<TransactionDocument> findAllWithEagerRelationships();

    @Query("select transactionDocument from TransactionDocument transactionDocument left join fetch transactionDocument.schemes where transactionDocument.id =:id")
    Optional<TransactionDocument> findOneWithEagerRelationships(@Param("id") Long id);
}
