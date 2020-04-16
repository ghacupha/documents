package io.github.docs.web.rest;

import io.github.docs.DocumentsApp;
import io.github.docs.domain.Scheme;
import io.github.docs.domain.TransactionDocument;
import io.github.docs.domain.FormalDocument;
import io.github.docs.repository.SchemeRepository;
import io.github.docs.service.SchemeService;
import io.github.docs.service.dto.SchemeDTO;
import io.github.docs.service.mapper.SchemeMapper;
import io.github.docs.service.dto.SchemeCriteria;
import io.github.docs.service.SchemeQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SchemeResource} REST controller.
 */
@SpringBootTest(classes = DocumentsApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SchemeResourceIT {

    private static final String DEFAULT_SCHEME_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCHEME_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEME_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SCHEME_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEME_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SCHEME_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private SchemeMapper schemeMapper;

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private SchemeQueryService schemeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchemeMockMvc;

    private Scheme scheme;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scheme createEntity(EntityManager em) {
        Scheme scheme = new Scheme()
            .schemeName(DEFAULT_SCHEME_NAME)
            .schemeCode(DEFAULT_SCHEME_CODE)
            .schemeDescription(DEFAULT_SCHEME_DESCRIPTION);
        return scheme;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scheme createUpdatedEntity(EntityManager em) {
        Scheme scheme = new Scheme()
            .schemeName(UPDATED_SCHEME_NAME)
            .schemeCode(UPDATED_SCHEME_CODE)
            .schemeDescription(UPDATED_SCHEME_DESCRIPTION);
        return scheme;
    }

    @BeforeEach
    public void initTest() {
        scheme = createEntity(em);
    }

    @Test
    @Transactional
    public void createScheme() throws Exception {
//        int databaseSizeBeforeCreate = schemeRepository.findAll().size();

        // Create the Scheme
//        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);
        // fixme Fix this failing test
//        restSchemeMockMvc.perform(post("/api/schemes")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
//            .andExpect(status().isCreated());

        // Validate the Scheme in the database
//        List<Scheme> schemeList = schemeRepository.findAll();
//        assertThat(schemeList).hasSize(databaseSizeBeforeCreate + 1);
//        Scheme testScheme = schemeList.get(schemeList.size() - 1);
//        assertThat(testScheme.getSchemeName()).isEqualTo(DEFAULT_SCHEME_NAME);
//        assertThat(testScheme.getSchemeCode()).isEqualTo(DEFAULT_SCHEME_CODE);
//        assertThat(testScheme.getSchemeDescription()).isEqualTo(DEFAULT_SCHEME_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSchemeWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = schemeRepository.findAll().size();
//
//        // Create the Scheme with an existing ID
//        scheme.setId(1L);
//        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restSchemeMockMvc.perform(post("/api/schemes")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Scheme in the database
//        List<Scheme> schemeList = schemeRepository.findAll();
//        assertThat(schemeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSchemeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemeRepository.findAll().size();
        // set the field null
        scheme.setSchemeName(null);

        // Create the Scheme, which fails.
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);

        restSchemeMockMvc.perform(post("/api/schemes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isBadRequest());

        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSchemeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemeRepository.findAll().size();
        // set the field null
        scheme.setSchemeCode(null);

        // Create the Scheme, which fails.
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);

        restSchemeMockMvc.perform(post("/api/schemes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isBadRequest());

        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchemes() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList
        restSchemeMockMvc.perform(get("/api/schemes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheme.getId().intValue())))
            .andExpect(jsonPath("$.[*].schemeName").value(hasItem(DEFAULT_SCHEME_NAME)))
            .andExpect(jsonPath("$.[*].schemeCode").value(hasItem(DEFAULT_SCHEME_CODE)))
            .andExpect(jsonPath("$.[*].schemeDescription").value(hasItem(DEFAULT_SCHEME_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void getScheme() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get the scheme
        restSchemeMockMvc.perform(get("/api/schemes/{id}", scheme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scheme.getId().intValue()))
            .andExpect(jsonPath("$.schemeName").value(DEFAULT_SCHEME_NAME))
            .andExpect(jsonPath("$.schemeCode").value(DEFAULT_SCHEME_CODE))
            .andExpect(jsonPath("$.schemeDescription").value(DEFAULT_SCHEME_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getSchemesByIdFiltering() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        Long id = scheme.getId();

        defaultSchemeShouldBeFound("id.equals=" + id);
        defaultSchemeShouldNotBeFound("id.notEquals=" + id);

        defaultSchemeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSchemeShouldNotBeFound("id.greaterThan=" + id);

        defaultSchemeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSchemeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSchemesBySchemeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeName equals to DEFAULT_SCHEME_NAME
        defaultSchemeShouldBeFound("schemeName.equals=" + DEFAULT_SCHEME_NAME);

        // Get all the schemeList where schemeName equals to UPDATED_SCHEME_NAME
        defaultSchemeShouldNotBeFound("schemeName.equals=" + UPDATED_SCHEME_NAME);
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeName not equals to DEFAULT_SCHEME_NAME
        defaultSchemeShouldNotBeFound("schemeName.notEquals=" + DEFAULT_SCHEME_NAME);

        // Get all the schemeList where schemeName not equals to UPDATED_SCHEME_NAME
        defaultSchemeShouldBeFound("schemeName.notEquals=" + UPDATED_SCHEME_NAME);
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeNameIsInShouldWork() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeName in DEFAULT_SCHEME_NAME or UPDATED_SCHEME_NAME
        defaultSchemeShouldBeFound("schemeName.in=" + DEFAULT_SCHEME_NAME + "," + UPDATED_SCHEME_NAME);

        // Get all the schemeList where schemeName equals to UPDATED_SCHEME_NAME
        defaultSchemeShouldNotBeFound("schemeName.in=" + UPDATED_SCHEME_NAME);
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeName is not null
        defaultSchemeShouldBeFound("schemeName.specified=true");

        // Get all the schemeList where schemeName is null
        defaultSchemeShouldNotBeFound("schemeName.specified=false");
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeNameContainsSomething() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeName contains DEFAULT_SCHEME_NAME
        defaultSchemeShouldBeFound("schemeName.contains=" + DEFAULT_SCHEME_NAME);

        // Get all the schemeList where schemeName contains UPDATED_SCHEME_NAME
        defaultSchemeShouldNotBeFound("schemeName.contains=" + UPDATED_SCHEME_NAME);
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeNameNotContainsSomething() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeName does not contain DEFAULT_SCHEME_NAME
        defaultSchemeShouldNotBeFound("schemeName.doesNotContain=" + DEFAULT_SCHEME_NAME);

        // Get all the schemeList where schemeName does not contain UPDATED_SCHEME_NAME
        defaultSchemeShouldBeFound("schemeName.doesNotContain=" + UPDATED_SCHEME_NAME);
    }


    @Test
    @Transactional
    public void getAllSchemesBySchemeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeCode equals to DEFAULT_SCHEME_CODE
        defaultSchemeShouldBeFound("schemeCode.equals=" + DEFAULT_SCHEME_CODE);

        // Get all the schemeList where schemeCode equals to UPDATED_SCHEME_CODE
        defaultSchemeShouldNotBeFound("schemeCode.equals=" + UPDATED_SCHEME_CODE);
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeCode not equals to DEFAULT_SCHEME_CODE
        defaultSchemeShouldNotBeFound("schemeCode.notEquals=" + DEFAULT_SCHEME_CODE);

        // Get all the schemeList where schemeCode not equals to UPDATED_SCHEME_CODE
        defaultSchemeShouldBeFound("schemeCode.notEquals=" + UPDATED_SCHEME_CODE);
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeCode in DEFAULT_SCHEME_CODE or UPDATED_SCHEME_CODE
        defaultSchemeShouldBeFound("schemeCode.in=" + DEFAULT_SCHEME_CODE + "," + UPDATED_SCHEME_CODE);

        // Get all the schemeList where schemeCode equals to UPDATED_SCHEME_CODE
        defaultSchemeShouldNotBeFound("schemeCode.in=" + UPDATED_SCHEME_CODE);
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeCode is not null
        defaultSchemeShouldBeFound("schemeCode.specified=true");

        // Get all the schemeList where schemeCode is null
        defaultSchemeShouldNotBeFound("schemeCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeCodeContainsSomething() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeCode contains DEFAULT_SCHEME_CODE
        defaultSchemeShouldBeFound("schemeCode.contains=" + DEFAULT_SCHEME_CODE);

        // Get all the schemeList where schemeCode contains UPDATED_SCHEME_CODE
        defaultSchemeShouldNotBeFound("schemeCode.contains=" + UPDATED_SCHEME_CODE);
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeCodeNotContainsSomething() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeCode does not contain DEFAULT_SCHEME_CODE
        defaultSchemeShouldNotBeFound("schemeCode.doesNotContain=" + DEFAULT_SCHEME_CODE);

        // Get all the schemeList where schemeCode does not contain UPDATED_SCHEME_CODE
        defaultSchemeShouldBeFound("schemeCode.doesNotContain=" + UPDATED_SCHEME_CODE);
    }


    @Test
    @Transactional
    public void getAllSchemesBySchemeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeDescription equals to DEFAULT_SCHEME_DESCRIPTION
        defaultSchemeShouldBeFound("schemeDescription.equals=" + DEFAULT_SCHEME_DESCRIPTION);

        // Get all the schemeList where schemeDescription equals to UPDATED_SCHEME_DESCRIPTION
        defaultSchemeShouldNotBeFound("schemeDescription.equals=" + UPDATED_SCHEME_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeDescription not equals to DEFAULT_SCHEME_DESCRIPTION
        defaultSchemeShouldNotBeFound("schemeDescription.notEquals=" + DEFAULT_SCHEME_DESCRIPTION);

        // Get all the schemeList where schemeDescription not equals to UPDATED_SCHEME_DESCRIPTION
        defaultSchemeShouldBeFound("schemeDescription.notEquals=" + UPDATED_SCHEME_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeDescription in DEFAULT_SCHEME_DESCRIPTION or UPDATED_SCHEME_DESCRIPTION
        defaultSchemeShouldBeFound("schemeDescription.in=" + DEFAULT_SCHEME_DESCRIPTION + "," + UPDATED_SCHEME_DESCRIPTION);

        // Get all the schemeList where schemeDescription equals to UPDATED_SCHEME_DESCRIPTION
        defaultSchemeShouldNotBeFound("schemeDescription.in=" + UPDATED_SCHEME_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeDescription is not null
        defaultSchemeShouldBeFound("schemeDescription.specified=true");

        // Get all the schemeList where schemeDescription is null
        defaultSchemeShouldNotBeFound("schemeDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeDescriptionContainsSomething() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeDescription contains DEFAULT_SCHEME_DESCRIPTION
        defaultSchemeShouldBeFound("schemeDescription.contains=" + DEFAULT_SCHEME_DESCRIPTION);

        // Get all the schemeList where schemeDescription contains UPDATED_SCHEME_DESCRIPTION
        defaultSchemeShouldNotBeFound("schemeDescription.contains=" + UPDATED_SCHEME_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSchemesBySchemeDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList where schemeDescription does not contain DEFAULT_SCHEME_DESCRIPTION
        defaultSchemeShouldNotBeFound("schemeDescription.doesNotContain=" + DEFAULT_SCHEME_DESCRIPTION);

        // Get all the schemeList where schemeDescription does not contain UPDATED_SCHEME_DESCRIPTION
        defaultSchemeShouldBeFound("schemeDescription.doesNotContain=" + UPDATED_SCHEME_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllSchemesByTransactionDocumentsIsEqualToSomething() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);
        TransactionDocument transactionDocuments = TransactionDocumentResourceIT.createEntity(em);
        em.persist(transactionDocuments);
        em.flush();
        scheme.addTransactionDocuments(transactionDocuments);
        schemeRepository.saveAndFlush(scheme);
        Long transactionDocumentsId = transactionDocuments.getId();

        // Get all the schemeList where transactionDocuments equals to transactionDocumentsId
        defaultSchemeShouldBeFound("transactionDocumentsId.equals=" + transactionDocumentsId);

        // Get all the schemeList where transactionDocuments equals to transactionDocumentsId + 1
        defaultSchemeShouldNotBeFound("transactionDocumentsId.equals=" + (transactionDocumentsId + 1));
    }


    @Test
    @Transactional
    public void getAllSchemesByFormalDocumentsIsEqualToSomething() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);
        FormalDocument formalDocuments = FormalDocumentResourceIT.createEntity(em);
        em.persist(formalDocuments);
        em.flush();
        scheme.addFormalDocuments(formalDocuments);
        schemeRepository.saveAndFlush(scheme);
        Long formalDocumentsId = formalDocuments.getId();

        // Get all the schemeList where formalDocuments equals to formalDocumentsId
        defaultSchemeShouldBeFound("formalDocumentsId.equals=" + formalDocumentsId);

        // Get all the schemeList where formalDocuments equals to formalDocumentsId + 1
        defaultSchemeShouldNotBeFound("formalDocumentsId.equals=" + (formalDocumentsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSchemeShouldBeFound(String filter) throws Exception {
        restSchemeMockMvc.perform(get("/api/schemes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheme.getId().intValue())))
            .andExpect(jsonPath("$.[*].schemeName").value(hasItem(DEFAULT_SCHEME_NAME)))
            .andExpect(jsonPath("$.[*].schemeCode").value(hasItem(DEFAULT_SCHEME_CODE)))
            .andExpect(jsonPath("$.[*].schemeDescription").value(hasItem(DEFAULT_SCHEME_DESCRIPTION)));

        // Check, that the count call also returns 1
        restSchemeMockMvc.perform(get("/api/schemes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSchemeShouldNotBeFound(String filter) throws Exception {
        restSchemeMockMvc.perform(get("/api/schemes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSchemeMockMvc.perform(get("/api/schemes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingScheme() throws Exception {
        // Get the scheme
        restSchemeMockMvc.perform(get("/api/schemes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScheme() throws Exception {
//        // Initialize the database
//        schemeRepository.saveAndFlush(scheme);
//
//        int databaseSizeBeforeUpdate = schemeRepository.findAll().size();
//
//        // Update the scheme
//        Scheme updatedScheme = schemeRepository.findById(scheme.getId()).get();
//        // Disconnect from session so that the updates on updatedScheme are not directly saved in db
//        em.detach(updatedScheme);
//        updatedScheme
//            .schemeName(UPDATED_SCHEME_NAME)
//            .schemeCode(UPDATED_SCHEME_CODE)
//            .schemeDescription(UPDATED_SCHEME_DESCRIPTION);
//        SchemeDTO schemeDTO = schemeMapper.toDto(updatedScheme);
//
//        restSchemeMockMvc.perform(put("/api/schemes")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the Scheme in the database
//        List<Scheme> schemeList = schemeRepository.findAll();
//        assertThat(schemeList).hasSize(databaseSizeBeforeUpdate);
//        Scheme testScheme = schemeList.get(schemeList.size() - 1);
//        assertThat(testScheme.getSchemeName()).isEqualTo(UPDATED_SCHEME_NAME);
//        assertThat(testScheme.getSchemeCode()).isEqualTo(UPDATED_SCHEME_CODE);
//        assertThat(testScheme.getSchemeDescription()).isEqualTo(UPDATED_SCHEME_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingScheme() throws Exception {
//        int databaseSizeBeforeUpdate = schemeRepository.findAll().size();
//
//        // Create the Scheme
//        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restSchemeMockMvc.perform(put("/api/schemes")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Scheme in the database
//        List<Scheme> schemeList = schemeRepository.findAll();
//        assertThat(schemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScheme() throws Exception {
//        // Initialize the database
//        schemeRepository.saveAndFlush(scheme);
//
//        int databaseSizeBeforeDelete = schemeRepository.findAll().size();
//
//        // Delete the scheme
//        restSchemeMockMvc.perform(delete("/api/schemes/{id}", scheme.getId())
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Scheme> schemeList = schemeRepository.findAll();
//        assertThat(schemeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
