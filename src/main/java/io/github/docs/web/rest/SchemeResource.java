package io.github.docs.web.rest;

import io.github.docs.security.AuthoritiesConstants;
import io.github.docs.service.SchemeService;
import io.github.docs.web.rest.errors.BadRequestAlertException;
import io.github.docs.service.dto.SchemeDTO;
import io.github.docs.service.dto.SchemeCriteria;
import io.github.docs.service.SchemeQueryService;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.docs.domain.Scheme}.
 *
 * Pre- Authorize directives have been installed to prevent users from managing schemes
 */
@RestController
@RequestMapping("/api")
public class SchemeResource {

    private final Logger log = LoggerFactory.getLogger(SchemeResource.class);

    private static final String ENTITY_NAME = "scheme";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchemeService schemeService;

    private final SchemeQueryService schemeQueryService;

    public SchemeResource(SchemeService schemeService, SchemeQueryService schemeQueryService) {
        this.schemeService = schemeService;
        this.schemeQueryService = schemeQueryService;
    }

    /**
     * {@code POST  /schemes} : Create a new scheme.
     *
     * @param schemeDTO the schemeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schemeDTO, or with status {@code 400 (Bad Request)} if the scheme has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schemes")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<SchemeDTO> createScheme(@Valid @RequestBody SchemeDTO schemeDTO) throws URISyntaxException {
        log.debug("REST request to save Scheme : {}", schemeDTO);
        if (schemeDTO.getId() != null) {
            throw new BadRequestAlertException("A new scheme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchemeDTO result = schemeService.save(schemeDTO);
        return ResponseEntity.created(new URI("/api/schemes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /schemes} : Updates an existing scheme.
     *
     * @param schemeDTO the schemeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schemeDTO,
     * or with status {@code 400 (Bad Request)} if the schemeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schemeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schemes")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<SchemeDTO> updateScheme(@Valid @RequestBody SchemeDTO schemeDTO) throws URISyntaxException {
        log.debug("REST request to update Scheme : {}", schemeDTO);
        if (schemeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SchemeDTO result = schemeService.save(schemeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, schemeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /schemes} : get all the schemes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schemes in body.
     */
    @GetMapping("/schemes")
    public ResponseEntity<List<SchemeDTO>> getAllSchemes(SchemeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Schemes by criteria: {}", criteria);
        Page<SchemeDTO> page = schemeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /schemes/count} : count all the schemes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/schemes/count")
    public ResponseEntity<Long> countSchemes(SchemeCriteria criteria) {
        log.debug("REST request to count Schemes by criteria: {}", criteria);
        return ResponseEntity.ok().body(schemeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /schemes/:id} : get the "id" scheme.
     *
     * @param id the id of the schemeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schemeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schemes/{id}")
    public ResponseEntity<SchemeDTO> getScheme(@PathVariable Long id) {
        log.debug("REST request to get Scheme : {}", id);
        Optional<SchemeDTO> schemeDTO = schemeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schemeDTO);
    }

    /**
     * {@code DELETE  /schemes/:id} : delete the "id" scheme.
     *
     * @param id the id of the schemeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schemes/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteScheme(@PathVariable Long id) {
        log.debug("REST request to delete Scheme : {}", id);
        schemeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
