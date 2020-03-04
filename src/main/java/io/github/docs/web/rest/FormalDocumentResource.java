package io.github.docs.web.rest;

import io.github.docs.service.FormalDocumentService;
import io.github.docs.web.rest.errors.BadRequestAlertException;
import io.github.docs.service.dto.FormalDocumentDTO;
import io.github.docs.service.dto.FormalDocumentCriteria;
import io.github.docs.service.FormalDocumentQueryService;

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
 * REST controller for managing {@link io.github.docs.domain.FormalDocument}.
 */
@RestController
@RequestMapping("/api")
public class FormalDocumentResource {

    private final Logger log = LoggerFactory.getLogger(FormalDocumentResource.class);

    private static final String ENTITY_NAME = "formalDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormalDocumentService formalDocumentService;

    private final FormalDocumentQueryService formalDocumentQueryService;

    public FormalDocumentResource(FormalDocumentService formalDocumentService, FormalDocumentQueryService formalDocumentQueryService) {
        this.formalDocumentService = formalDocumentService;
        this.formalDocumentQueryService = formalDocumentQueryService;
    }

    /**
     * {@code POST  /formal-documents} : Create a new formalDocument.
     *
     * @param formalDocumentDTO the formalDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formalDocumentDTO, or with status {@code 400 (Bad Request)} if the formalDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/formal-documents")
    public ResponseEntity<FormalDocumentDTO> createFormalDocument(@Valid @RequestBody FormalDocumentDTO formalDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save FormalDocument : {}", formalDocumentDTO);
        if (formalDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new formalDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormalDocumentDTO result = formalDocumentService.save(formalDocumentDTO);
        return ResponseEntity.created(new URI("/api/formal-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /formal-documents} : Updates an existing formalDocument.
     *
     * @param formalDocumentDTO the formalDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formalDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the formalDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formalDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/formal-documents")
    public ResponseEntity<FormalDocumentDTO> updateFormalDocument(@Valid @RequestBody FormalDocumentDTO formalDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update FormalDocument : {}", formalDocumentDTO);
        if (formalDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormalDocumentDTO result = formalDocumentService.save(formalDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formalDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /formal-documents} : get all the formalDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formalDocuments in body.
     */
    @GetMapping("/formal-documents")
    public ResponseEntity<List<FormalDocumentDTO>> getAllFormalDocuments(FormalDocumentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FormalDocuments by criteria: {}", criteria);
        Page<FormalDocumentDTO> page = formalDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /formal-documents/count} : count all the formalDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/formal-documents/count")
    public ResponseEntity<Long> countFormalDocuments(FormalDocumentCriteria criteria) {
        log.debug("REST request to count FormalDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(formalDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /formal-documents/:id} : get the "id" formalDocument.
     *
     * @param id the id of the formalDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formalDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/formal-documents/{id}")
    public ResponseEntity<FormalDocumentDTO> getFormalDocument(@PathVariable Long id) {
        log.debug("REST request to get FormalDocument : {}", id);
        Optional<FormalDocumentDTO> formalDocumentDTO = formalDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formalDocumentDTO);
    }

    /**
     * {@code DELETE  /formal-documents/:id} : delete the "id" formalDocument.
     *
     * @param id the id of the formalDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/formal-documents/{id}")
    public ResponseEntity<Void> deleteFormalDocument(@PathVariable Long id) {
        log.debug("REST request to delete FormalDocument : {}", id);
        formalDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
