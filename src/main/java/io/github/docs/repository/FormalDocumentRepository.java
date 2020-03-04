package io.github.docs.repository;

import io.github.docs.domain.FormalDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FormalDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormalDocumentRepository extends JpaRepository<FormalDocument, Long>, JpaSpecificationExecutor<FormalDocument> {

}
