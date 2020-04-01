package io.github.docs.app;

import io.github.docs.app.model.TransactionDocumentMetadata;
import io.github.docs.service.TransactionDocumentQueryService;
import io.github.docs.service.dto.TransactionDocumentCriteria;
import io.github.docs.service.dto.TransactionDocumentDTO;
import io.github.jhipster.web.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * This resource is temporary and is to serve document metadata to the frontend to enable batch
 * <p>
 * treatment of documents represented by such metadata.
 */
@Slf4j
@RestController
@RequestMapping("/api/app")
public class TransactionDocumentMetadataResource {

    private final TransactionDocumentMetadataMapper transactionDocumentMetadataMapper;
    private final TransactionDocumentQueryService transactionDocumentQueryService;

    public TransactionDocumentMetadataResource(final TransactionDocumentMetadataMapper transactionDocumentMetadataMapper, final TransactionDocumentQueryService transactionDocumentQueryService) {
        this.transactionDocumentMetadataMapper = transactionDocumentMetadataMapper;
        this.transactionDocumentQueryService = transactionDocumentQueryService;
    }

    /**
     * {@code GET  /transaction-documents} : get all the transactionDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionDocuments in body.
     */
    @GetMapping("/transaction-documents")
    public ResponseEntity<List<TransactionDocumentMetadata>> getAllTransactionDocuments(TransactionDocumentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TransactionDocuments by criteria: {}", criteria);
        Page<TransactionDocumentDTO> page = transactionDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        // map the transaction data
        List<TransactionDocumentMetadata> metadata = transactionDocumentMetadataMapper.toValue2(page.getContent());

        return ResponseEntity.ok().headers(headers).body(metadata);
    }

}
