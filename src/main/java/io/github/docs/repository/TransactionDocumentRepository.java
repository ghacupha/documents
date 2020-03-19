package io.github.docs.repository;

import io.github.docs.domain.TransactionDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TransactionDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionDocumentRepository extends JpaRepository<TransactionDocument, Long>, JpaSpecificationExecutor<TransactionDocument> {
}
