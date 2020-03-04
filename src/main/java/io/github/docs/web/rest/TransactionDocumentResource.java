package io.github.docs.web.rest;

import io.github.docs.service.TransactionDocumentService;
import io.github.docs.web.rest.errors.BadRequestAlertException;
import io.github.docs.service.dto.TransactionDocumentDTO;
import io.github.docs.service.dto.TransactionDocumentCriteria;
import io.github.docs.service.TransactionDocumentQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.docs.domain.TransactionDocument}.
 */
@RestController
@RequestMapping("/api")
public class TransactionDocumentResource {

    private final Logger log = LoggerFactory.getLogger(TransactionDocumentResource.class);

    private static final String ENTITY_NAME = "transactionDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionDocumentService transactionDocumentService;

    private final TransactionDocumentQueryService transactionDocumentQueryService;

    public TransactionDocumentResource(TransactionDocumentService transactionDocumentService, TransactionDocumentQueryService transactionDocumentQueryService) {
        this.transactionDocumentService = transactionDocumentService;
        this.transactionDocumentQueryService = transactionDocumentQueryService;
    }

    /**
     * {@code POST  /transaction-documents} : Create a new transactionDocument.
     *
     * @param transactionDocumentDTO the transactionDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionDocumentDTO, or with status {@code 400 (Bad Request)} if the transactionDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-documents")
    public ResponseEntity<TransactionDocumentDTO> createTransactionDocument(@Valid @RequestBody TransactionDocumentDTO transactionDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionDocument : {}", transactionDocumentDTO);
        if (transactionDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionDocumentDTO result = transactionDocumentService.save(transactionDocumentDTO);
        return ResponseEntity.created(new URI("/api/transaction-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-documents} : Updates an existing transactionDocument.
     *
     * @param transactionDocumentDTO the transactionDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the transactionDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-documents")
    public ResponseEntity<TransactionDocumentDTO> updateTransactionDocument(@Valid @RequestBody TransactionDocumentDTO transactionDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionDocument : {}", transactionDocumentDTO);
        if (transactionDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionDocumentDTO result = transactionDocumentService.save(transactionDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactionDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-documents} : get all the transactionDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionDocuments in body.
     */
    @GetMapping("/transaction-documents")
    public ResponseEntity<List<TransactionDocumentDTO>> getAllTransactionDocuments(TransactionDocumentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TransactionDocuments by criteria: {}", criteria);
        Page<TransactionDocumentDTO> page = transactionDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transaction-documents/count} : count all the transactionDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transaction-documents/count")
    public ResponseEntity<Long> countTransactionDocuments(TransactionDocumentCriteria criteria) {
        log.debug("REST request to count TransactionDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(transactionDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transaction-documents/:id} : get the "id" transactionDocument.
     *
     * @param id the id of the transactionDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-documents/{id}")
    public ResponseEntity<TransactionDocumentDTO> getTransactionDocument(@PathVariable Long id) {
        log.debug("REST request to get TransactionDocument : {}", id);
        Optional<TransactionDocumentDTO> transactionDocumentDTO = transactionDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionDocumentDTO);
    }

    /**
     * {@code DELETE  /transaction-documents/:id} : delete the "id" transactionDocument.
     *
     * @param id the id of the transactionDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-documents/{id}")
    public ResponseEntity<Void> deleteTransactionDocument(@PathVariable Long id) {
        log.debug("REST request to delete TransactionDocument : {}", id);
        transactionDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
