package io.github.docs.app;

import io.github.docs.app.model.FormalDocumentMetadata;
import io.github.docs.service.FormalDocumentQueryService;
import io.github.docs.service.dto.FormalDocumentCriteria;
import io.github.docs.service.dto.FormalDocumentDTO;
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
 * Resource for serving up the formal-document-metadata items
 */
@Slf4j
@RestController
@RequestMapping("/api/app")
public class FormalDocumentMetadataResource {

    private final FormalDocumentMetadataMapper metadataMapper;
    private final FormalDocumentQueryService queryService;

    public FormalDocumentMetadataResource(final FormalDocumentMetadataMapper metadataMapper, final FormalDocumentQueryService queryService) {
        this.metadataMapper = metadataMapper;
        this.queryService = queryService;
    }

    /**
     * {@code GET  /formal-document/metadata} : get all the transactionDocuments metadata.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionDocuments in body.
     */
    @GetMapping("/formal-document/metadata")
    public ResponseEntity<List<FormalDocumentMetadata>> getAllFormalDocuments(FormalDocumentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TransactionDocuments by criteria: {}", criteria);
        Page<FormalDocumentDTO> page = queryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        // map the formal documents data
        List<FormalDocumentMetadata> metadata = metadataMapper.toValue2(page.getContent());

        return ResponseEntity.ok().headers(headers).body(metadata);
    }

}
