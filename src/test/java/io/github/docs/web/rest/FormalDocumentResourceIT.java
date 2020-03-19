package io.github.docs.web.rest;

import io.github.docs.DocumentsApp;
import io.github.docs.domain.FormalDocument;
import io.github.docs.repository.FormalDocumentRepository;
import io.github.docs.service.FormalDocumentService;
import io.github.docs.service.dto.FormalDocumentDTO;
import io.github.docs.service.mapper.FormalDocumentMapper;
import io.github.docs.service.dto.FormalDocumentCriteria;
import io.github.docs.service.FormalDocumentQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.docs.domain.enumeration.DocumentType;
/**
 * Integration tests for the {@link FormalDocumentResource} REST controller.
 */
@SpringBootTest(classes = DocumentsApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class FormalDocumentResourceIT {

    private static final String DEFAULT_DOCUMENT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_BRIEF_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_BRIEF_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOCUMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOCUMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOCUMENT_DATE = LocalDate.ofEpochDay(-1L);

    private static final DocumentType DEFAULT_DOCUMENT_TYPE = DocumentType.CONTRACT;
    private static final DocumentType UPDATED_DOCUMENT_TYPE = DocumentType.LICENSE;

    private static final String DEFAULT_DOCUMENT_STANDARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_STANDARD_NUMBER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_ATTACHMENT_CONTENT_TYPE = "image/png";

    @Autowired
    private FormalDocumentRepository formalDocumentRepository;

    @Autowired
    private FormalDocumentMapper formalDocumentMapper;

    @Autowired
    private FormalDocumentService formalDocumentService;

    @Autowired
    private FormalDocumentQueryService formalDocumentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormalDocumentMockMvc;

    private FormalDocument formalDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormalDocument createEntity(EntityManager em) {
        FormalDocument formalDocument = new FormalDocument()
            .documentTitle(DEFAULT_DOCUMENT_TITLE)
            .documentSubject(DEFAULT_DOCUMENT_SUBJECT)
            .briefDescription(DEFAULT_BRIEF_DESCRIPTION)
            .documentDate(DEFAULT_DOCUMENT_DATE)
            .documentType(DEFAULT_DOCUMENT_TYPE)
            .documentStandardNumber(DEFAULT_DOCUMENT_STANDARD_NUMBER)
            .documentAttachment(DEFAULT_DOCUMENT_ATTACHMENT)
            .documentAttachmentContentType(DEFAULT_DOCUMENT_ATTACHMENT_CONTENT_TYPE);
        return formalDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormalDocument createUpdatedEntity(EntityManager em) {
        FormalDocument formalDocument = new FormalDocument()
            .documentTitle(UPDATED_DOCUMENT_TITLE)
            .documentSubject(UPDATED_DOCUMENT_SUBJECT)
            .briefDescription(UPDATED_BRIEF_DESCRIPTION)
            .documentDate(UPDATED_DOCUMENT_DATE)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentStandardNumber(UPDATED_DOCUMENT_STANDARD_NUMBER)
            .documentAttachment(UPDATED_DOCUMENT_ATTACHMENT)
            .documentAttachmentContentType(UPDATED_DOCUMENT_ATTACHMENT_CONTENT_TYPE);
        return formalDocument;
    }

    @BeforeEach
    public void initTest() {
        formalDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormalDocument() throws Exception {
        int databaseSizeBeforeCreate = formalDocumentRepository.findAll().size();

        // Create the FormalDocument
        FormalDocumentDTO formalDocumentDTO = formalDocumentMapper.toDto(formalDocument);
        restFormalDocumentMockMvc.perform(post("/api/formal-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formalDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the FormalDocument in the database
        List<FormalDocument> formalDocumentList = formalDocumentRepository.findAll();
        assertThat(formalDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        FormalDocument testFormalDocument = formalDocumentList.get(formalDocumentList.size() - 1);
        assertThat(testFormalDocument.getDocumentTitle()).isEqualTo(DEFAULT_DOCUMENT_TITLE);
        assertThat(testFormalDocument.getDocumentSubject()).isEqualTo(DEFAULT_DOCUMENT_SUBJECT);
        assertThat(testFormalDocument.getBriefDescription()).isEqualTo(DEFAULT_BRIEF_DESCRIPTION);
        assertThat(testFormalDocument.getDocumentDate()).isEqualTo(DEFAULT_DOCUMENT_DATE);
        assertThat(testFormalDocument.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testFormalDocument.getDocumentStandardNumber()).isEqualTo(DEFAULT_DOCUMENT_STANDARD_NUMBER);
        assertThat(testFormalDocument.getDocumentAttachment()).isEqualTo(DEFAULT_DOCUMENT_ATTACHMENT);
        assertThat(testFormalDocument.getDocumentAttachmentContentType()).isEqualTo(DEFAULT_DOCUMENT_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createFormalDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formalDocumentRepository.findAll().size();

        // Create the FormalDocument with an existing ID
        formalDocument.setId(1L);
        FormalDocumentDTO formalDocumentDTO = formalDocumentMapper.toDto(formalDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormalDocumentMockMvc.perform(post("/api/formal-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formalDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormalDocument in the database
        List<FormalDocument> formalDocumentList = formalDocumentRepository.findAll();
        assertThat(formalDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = formalDocumentRepository.findAll().size();
        // set the field null
        formalDocument.setDocumentTitle(null);

        // Create the FormalDocument, which fails.
        FormalDocumentDTO formalDocumentDTO = formalDocumentMapper.toDto(formalDocument);

        restFormalDocumentMockMvc.perform(post("/api/formal-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formalDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<FormalDocument> formalDocumentList = formalDocumentRepository.findAll();
        assertThat(formalDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormalDocuments() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList
        restFormalDocumentMockMvc.perform(get("/api/formal-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formalDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentTitle").value(hasItem(DEFAULT_DOCUMENT_TITLE)))
            .andExpect(jsonPath("$.[*].documentSubject").value(hasItem(DEFAULT_DOCUMENT_SUBJECT)))
            .andExpect(jsonPath("$.[*].briefDescription").value(hasItem(DEFAULT_BRIEF_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].documentDate").value(hasItem(DEFAULT_DOCUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].documentStandardNumber").value(hasItem(DEFAULT_DOCUMENT_STANDARD_NUMBER)))
            .andExpect(jsonPath("$.[*].documentAttachmentContentType").value(hasItem(DEFAULT_DOCUMENT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_ATTACHMENT))));
    }
    
    @Test
    @Transactional
    public void getFormalDocument() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get the formalDocument
        restFormalDocumentMockMvc.perform(get("/api/formal-documents/{id}", formalDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formalDocument.getId().intValue()))
            .andExpect(jsonPath("$.documentTitle").value(DEFAULT_DOCUMENT_TITLE))
            .andExpect(jsonPath("$.documentSubject").value(DEFAULT_DOCUMENT_SUBJECT))
            .andExpect(jsonPath("$.briefDescription").value(DEFAULT_BRIEF_DESCRIPTION))
            .andExpect(jsonPath("$.documentDate").value(DEFAULT_DOCUMENT_DATE.toString()))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE.toString()))
            .andExpect(jsonPath("$.documentStandardNumber").value(DEFAULT_DOCUMENT_STANDARD_NUMBER))
            .andExpect(jsonPath("$.documentAttachmentContentType").value(DEFAULT_DOCUMENT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentAttachment").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT_ATTACHMENT)));
    }


    @Test
    @Transactional
    public void getFormalDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        Long id = formalDocument.getId();

        defaultFormalDocumentShouldBeFound("id.equals=" + id);
        defaultFormalDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultFormalDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFormalDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultFormalDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFormalDocumentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentTitle equals to DEFAULT_DOCUMENT_TITLE
        defaultFormalDocumentShouldBeFound("documentTitle.equals=" + DEFAULT_DOCUMENT_TITLE);

        // Get all the formalDocumentList where documentTitle equals to UPDATED_DOCUMENT_TITLE
        defaultFormalDocumentShouldNotBeFound("documentTitle.equals=" + UPDATED_DOCUMENT_TITLE);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentTitle not equals to DEFAULT_DOCUMENT_TITLE
        defaultFormalDocumentShouldNotBeFound("documentTitle.notEquals=" + DEFAULT_DOCUMENT_TITLE);

        // Get all the formalDocumentList where documentTitle not equals to UPDATED_DOCUMENT_TITLE
        defaultFormalDocumentShouldBeFound("documentTitle.notEquals=" + UPDATED_DOCUMENT_TITLE);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentTitleIsInShouldWork() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentTitle in DEFAULT_DOCUMENT_TITLE or UPDATED_DOCUMENT_TITLE
        defaultFormalDocumentShouldBeFound("documentTitle.in=" + DEFAULT_DOCUMENT_TITLE + "," + UPDATED_DOCUMENT_TITLE);

        // Get all the formalDocumentList where documentTitle equals to UPDATED_DOCUMENT_TITLE
        defaultFormalDocumentShouldNotBeFound("documentTitle.in=" + UPDATED_DOCUMENT_TITLE);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentTitle is not null
        defaultFormalDocumentShouldBeFound("documentTitle.specified=true");

        // Get all the formalDocumentList where documentTitle is null
        defaultFormalDocumentShouldNotBeFound("documentTitle.specified=false");
    }
                @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentTitleContainsSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentTitle contains DEFAULT_DOCUMENT_TITLE
        defaultFormalDocumentShouldBeFound("documentTitle.contains=" + DEFAULT_DOCUMENT_TITLE);

        // Get all the formalDocumentList where documentTitle contains UPDATED_DOCUMENT_TITLE
        defaultFormalDocumentShouldNotBeFound("documentTitle.contains=" + UPDATED_DOCUMENT_TITLE);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentTitleNotContainsSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentTitle does not contain DEFAULT_DOCUMENT_TITLE
        defaultFormalDocumentShouldNotBeFound("documentTitle.doesNotContain=" + DEFAULT_DOCUMENT_TITLE);

        // Get all the formalDocumentList where documentTitle does not contain UPDATED_DOCUMENT_TITLE
        defaultFormalDocumentShouldBeFound("documentTitle.doesNotContain=" + UPDATED_DOCUMENT_TITLE);
    }


    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentSubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentSubject equals to DEFAULT_DOCUMENT_SUBJECT
        defaultFormalDocumentShouldBeFound("documentSubject.equals=" + DEFAULT_DOCUMENT_SUBJECT);

        // Get all the formalDocumentList where documentSubject equals to UPDATED_DOCUMENT_SUBJECT
        defaultFormalDocumentShouldNotBeFound("documentSubject.equals=" + UPDATED_DOCUMENT_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentSubjectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentSubject not equals to DEFAULT_DOCUMENT_SUBJECT
        defaultFormalDocumentShouldNotBeFound("documentSubject.notEquals=" + DEFAULT_DOCUMENT_SUBJECT);

        // Get all the formalDocumentList where documentSubject not equals to UPDATED_DOCUMENT_SUBJECT
        defaultFormalDocumentShouldBeFound("documentSubject.notEquals=" + UPDATED_DOCUMENT_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentSubjectIsInShouldWork() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentSubject in DEFAULT_DOCUMENT_SUBJECT or UPDATED_DOCUMENT_SUBJECT
        defaultFormalDocumentShouldBeFound("documentSubject.in=" + DEFAULT_DOCUMENT_SUBJECT + "," + UPDATED_DOCUMENT_SUBJECT);

        // Get all the formalDocumentList where documentSubject equals to UPDATED_DOCUMENT_SUBJECT
        defaultFormalDocumentShouldNotBeFound("documentSubject.in=" + UPDATED_DOCUMENT_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentSubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentSubject is not null
        defaultFormalDocumentShouldBeFound("documentSubject.specified=true");

        // Get all the formalDocumentList where documentSubject is null
        defaultFormalDocumentShouldNotBeFound("documentSubject.specified=false");
    }
                @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentSubjectContainsSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentSubject contains DEFAULT_DOCUMENT_SUBJECT
        defaultFormalDocumentShouldBeFound("documentSubject.contains=" + DEFAULT_DOCUMENT_SUBJECT);

        // Get all the formalDocumentList where documentSubject contains UPDATED_DOCUMENT_SUBJECT
        defaultFormalDocumentShouldNotBeFound("documentSubject.contains=" + UPDATED_DOCUMENT_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentSubjectNotContainsSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentSubject does not contain DEFAULT_DOCUMENT_SUBJECT
        defaultFormalDocumentShouldNotBeFound("documentSubject.doesNotContain=" + DEFAULT_DOCUMENT_SUBJECT);

        // Get all the formalDocumentList where documentSubject does not contain UPDATED_DOCUMENT_SUBJECT
        defaultFormalDocumentShouldBeFound("documentSubject.doesNotContain=" + UPDATED_DOCUMENT_SUBJECT);
    }


    @Test
    @Transactional
    public void getAllFormalDocumentsByBriefDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where briefDescription equals to DEFAULT_BRIEF_DESCRIPTION
        defaultFormalDocumentShouldBeFound("briefDescription.equals=" + DEFAULT_BRIEF_DESCRIPTION);

        // Get all the formalDocumentList where briefDescription equals to UPDATED_BRIEF_DESCRIPTION
        defaultFormalDocumentShouldNotBeFound("briefDescription.equals=" + UPDATED_BRIEF_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByBriefDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where briefDescription not equals to DEFAULT_BRIEF_DESCRIPTION
        defaultFormalDocumentShouldNotBeFound("briefDescription.notEquals=" + DEFAULT_BRIEF_DESCRIPTION);

        // Get all the formalDocumentList where briefDescription not equals to UPDATED_BRIEF_DESCRIPTION
        defaultFormalDocumentShouldBeFound("briefDescription.notEquals=" + UPDATED_BRIEF_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByBriefDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where briefDescription in DEFAULT_BRIEF_DESCRIPTION or UPDATED_BRIEF_DESCRIPTION
        defaultFormalDocumentShouldBeFound("briefDescription.in=" + DEFAULT_BRIEF_DESCRIPTION + "," + UPDATED_BRIEF_DESCRIPTION);

        // Get all the formalDocumentList where briefDescription equals to UPDATED_BRIEF_DESCRIPTION
        defaultFormalDocumentShouldNotBeFound("briefDescription.in=" + UPDATED_BRIEF_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByBriefDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where briefDescription is not null
        defaultFormalDocumentShouldBeFound("briefDescription.specified=true");

        // Get all the formalDocumentList where briefDescription is null
        defaultFormalDocumentShouldNotBeFound("briefDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllFormalDocumentsByBriefDescriptionContainsSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where briefDescription contains DEFAULT_BRIEF_DESCRIPTION
        defaultFormalDocumentShouldBeFound("briefDescription.contains=" + DEFAULT_BRIEF_DESCRIPTION);

        // Get all the formalDocumentList where briefDescription contains UPDATED_BRIEF_DESCRIPTION
        defaultFormalDocumentShouldNotBeFound("briefDescription.contains=" + UPDATED_BRIEF_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByBriefDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where briefDescription does not contain DEFAULT_BRIEF_DESCRIPTION
        defaultFormalDocumentShouldNotBeFound("briefDescription.doesNotContain=" + DEFAULT_BRIEF_DESCRIPTION);

        // Get all the formalDocumentList where briefDescription does not contain UPDATED_BRIEF_DESCRIPTION
        defaultFormalDocumentShouldBeFound("briefDescription.doesNotContain=" + UPDATED_BRIEF_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentDate equals to DEFAULT_DOCUMENT_DATE
        defaultFormalDocumentShouldBeFound("documentDate.equals=" + DEFAULT_DOCUMENT_DATE);

        // Get all the formalDocumentList where documentDate equals to UPDATED_DOCUMENT_DATE
        defaultFormalDocumentShouldNotBeFound("documentDate.equals=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentDate not equals to DEFAULT_DOCUMENT_DATE
        defaultFormalDocumentShouldNotBeFound("documentDate.notEquals=" + DEFAULT_DOCUMENT_DATE);

        // Get all the formalDocumentList where documentDate not equals to UPDATED_DOCUMENT_DATE
        defaultFormalDocumentShouldBeFound("documentDate.notEquals=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentDateIsInShouldWork() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentDate in DEFAULT_DOCUMENT_DATE or UPDATED_DOCUMENT_DATE
        defaultFormalDocumentShouldBeFound("documentDate.in=" + DEFAULT_DOCUMENT_DATE + "," + UPDATED_DOCUMENT_DATE);

        // Get all the formalDocumentList where documentDate equals to UPDATED_DOCUMENT_DATE
        defaultFormalDocumentShouldNotBeFound("documentDate.in=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentDate is not null
        defaultFormalDocumentShouldBeFound("documentDate.specified=true");

        // Get all the formalDocumentList where documentDate is null
        defaultFormalDocumentShouldNotBeFound("documentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentDate is greater than or equal to DEFAULT_DOCUMENT_DATE
        defaultFormalDocumentShouldBeFound("documentDate.greaterThanOrEqual=" + DEFAULT_DOCUMENT_DATE);

        // Get all the formalDocumentList where documentDate is greater than or equal to UPDATED_DOCUMENT_DATE
        defaultFormalDocumentShouldNotBeFound("documentDate.greaterThanOrEqual=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentDate is less than or equal to DEFAULT_DOCUMENT_DATE
        defaultFormalDocumentShouldBeFound("documentDate.lessThanOrEqual=" + DEFAULT_DOCUMENT_DATE);

        // Get all the formalDocumentList where documentDate is less than or equal to SMALLER_DOCUMENT_DATE
        defaultFormalDocumentShouldNotBeFound("documentDate.lessThanOrEqual=" + SMALLER_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentDate is less than DEFAULT_DOCUMENT_DATE
        defaultFormalDocumentShouldNotBeFound("documentDate.lessThan=" + DEFAULT_DOCUMENT_DATE);

        // Get all the formalDocumentList where documentDate is less than UPDATED_DOCUMENT_DATE
        defaultFormalDocumentShouldBeFound("documentDate.lessThan=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentDate is greater than DEFAULT_DOCUMENT_DATE
        defaultFormalDocumentShouldNotBeFound("documentDate.greaterThan=" + DEFAULT_DOCUMENT_DATE);

        // Get all the formalDocumentList where documentDate is greater than SMALLER_DOCUMENT_DATE
        defaultFormalDocumentShouldBeFound("documentDate.greaterThan=" + SMALLER_DOCUMENT_DATE);
    }


    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentType equals to DEFAULT_DOCUMENT_TYPE
        defaultFormalDocumentShouldBeFound("documentType.equals=" + DEFAULT_DOCUMENT_TYPE);

        // Get all the formalDocumentList where documentType equals to UPDATED_DOCUMENT_TYPE
        defaultFormalDocumentShouldNotBeFound("documentType.equals=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentType not equals to DEFAULT_DOCUMENT_TYPE
        defaultFormalDocumentShouldNotBeFound("documentType.notEquals=" + DEFAULT_DOCUMENT_TYPE);

        // Get all the formalDocumentList where documentType not equals to UPDATED_DOCUMENT_TYPE
        defaultFormalDocumentShouldBeFound("documentType.notEquals=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentType in DEFAULT_DOCUMENT_TYPE or UPDATED_DOCUMENT_TYPE
        defaultFormalDocumentShouldBeFound("documentType.in=" + DEFAULT_DOCUMENT_TYPE + "," + UPDATED_DOCUMENT_TYPE);

        // Get all the formalDocumentList where documentType equals to UPDATED_DOCUMENT_TYPE
        defaultFormalDocumentShouldNotBeFound("documentType.in=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentType is not null
        defaultFormalDocumentShouldBeFound("documentType.specified=true");

        // Get all the formalDocumentList where documentType is null
        defaultFormalDocumentShouldNotBeFound("documentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentStandardNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentStandardNumber equals to DEFAULT_DOCUMENT_STANDARD_NUMBER
        defaultFormalDocumentShouldBeFound("documentStandardNumber.equals=" + DEFAULT_DOCUMENT_STANDARD_NUMBER);

        // Get all the formalDocumentList where documentStandardNumber equals to UPDATED_DOCUMENT_STANDARD_NUMBER
        defaultFormalDocumentShouldNotBeFound("documentStandardNumber.equals=" + UPDATED_DOCUMENT_STANDARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentStandardNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentStandardNumber not equals to DEFAULT_DOCUMENT_STANDARD_NUMBER
        defaultFormalDocumentShouldNotBeFound("documentStandardNumber.notEquals=" + DEFAULT_DOCUMENT_STANDARD_NUMBER);

        // Get all the formalDocumentList where documentStandardNumber not equals to UPDATED_DOCUMENT_STANDARD_NUMBER
        defaultFormalDocumentShouldBeFound("documentStandardNumber.notEquals=" + UPDATED_DOCUMENT_STANDARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentStandardNumberIsInShouldWork() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentStandardNumber in DEFAULT_DOCUMENT_STANDARD_NUMBER or UPDATED_DOCUMENT_STANDARD_NUMBER
        defaultFormalDocumentShouldBeFound("documentStandardNumber.in=" + DEFAULT_DOCUMENT_STANDARD_NUMBER + "," + UPDATED_DOCUMENT_STANDARD_NUMBER);

        // Get all the formalDocumentList where documentStandardNumber equals to UPDATED_DOCUMENT_STANDARD_NUMBER
        defaultFormalDocumentShouldNotBeFound("documentStandardNumber.in=" + UPDATED_DOCUMENT_STANDARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentStandardNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentStandardNumber is not null
        defaultFormalDocumentShouldBeFound("documentStandardNumber.specified=true");

        // Get all the formalDocumentList where documentStandardNumber is null
        defaultFormalDocumentShouldNotBeFound("documentStandardNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentStandardNumberContainsSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentStandardNumber contains DEFAULT_DOCUMENT_STANDARD_NUMBER
        defaultFormalDocumentShouldBeFound("documentStandardNumber.contains=" + DEFAULT_DOCUMENT_STANDARD_NUMBER);

        // Get all the formalDocumentList where documentStandardNumber contains UPDATED_DOCUMENT_STANDARD_NUMBER
        defaultFormalDocumentShouldNotBeFound("documentStandardNumber.contains=" + UPDATED_DOCUMENT_STANDARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllFormalDocumentsByDocumentStandardNumberNotContainsSomething() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        // Get all the formalDocumentList where documentStandardNumber does not contain DEFAULT_DOCUMENT_STANDARD_NUMBER
        defaultFormalDocumentShouldNotBeFound("documentStandardNumber.doesNotContain=" + DEFAULT_DOCUMENT_STANDARD_NUMBER);

        // Get all the formalDocumentList where documentStandardNumber does not contain UPDATED_DOCUMENT_STANDARD_NUMBER
        defaultFormalDocumentShouldBeFound("documentStandardNumber.doesNotContain=" + UPDATED_DOCUMENT_STANDARD_NUMBER);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFormalDocumentShouldBeFound(String filter) throws Exception {
        restFormalDocumentMockMvc.perform(get("/api/formal-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formalDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentTitle").value(hasItem(DEFAULT_DOCUMENT_TITLE)))
            .andExpect(jsonPath("$.[*].documentSubject").value(hasItem(DEFAULT_DOCUMENT_SUBJECT)))
            .andExpect(jsonPath("$.[*].briefDescription").value(hasItem(DEFAULT_BRIEF_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].documentDate").value(hasItem(DEFAULT_DOCUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].documentStandardNumber").value(hasItem(DEFAULT_DOCUMENT_STANDARD_NUMBER)))
            .andExpect(jsonPath("$.[*].documentAttachmentContentType").value(hasItem(DEFAULT_DOCUMENT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_ATTACHMENT))));

        // Check, that the count call also returns 1
        restFormalDocumentMockMvc.perform(get("/api/formal-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFormalDocumentShouldNotBeFound(String filter) throws Exception {
        restFormalDocumentMockMvc.perform(get("/api/formal-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFormalDocumentMockMvc.perform(get("/api/formal-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFormalDocument() throws Exception {
        // Get the formalDocument
        restFormalDocumentMockMvc.perform(get("/api/formal-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormalDocument() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        int databaseSizeBeforeUpdate = formalDocumentRepository.findAll().size();

        // Update the formalDocument
        FormalDocument updatedFormalDocument = formalDocumentRepository.findById(formalDocument.getId()).get();
        // Disconnect from session so that the updates on updatedFormalDocument are not directly saved in db
        em.detach(updatedFormalDocument);
        updatedFormalDocument
            .documentTitle(UPDATED_DOCUMENT_TITLE)
            .documentSubject(UPDATED_DOCUMENT_SUBJECT)
            .briefDescription(UPDATED_BRIEF_DESCRIPTION)
            .documentDate(UPDATED_DOCUMENT_DATE)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentStandardNumber(UPDATED_DOCUMENT_STANDARD_NUMBER)
            .documentAttachment(UPDATED_DOCUMENT_ATTACHMENT)
            .documentAttachmentContentType(UPDATED_DOCUMENT_ATTACHMENT_CONTENT_TYPE);
        FormalDocumentDTO formalDocumentDTO = formalDocumentMapper.toDto(updatedFormalDocument);

        restFormalDocumentMockMvc.perform(put("/api/formal-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formalDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the FormalDocument in the database
        List<FormalDocument> formalDocumentList = formalDocumentRepository.findAll();
        assertThat(formalDocumentList).hasSize(databaseSizeBeforeUpdate);
        FormalDocument testFormalDocument = formalDocumentList.get(formalDocumentList.size() - 1);
        assertThat(testFormalDocument.getDocumentTitle()).isEqualTo(UPDATED_DOCUMENT_TITLE);
        assertThat(testFormalDocument.getDocumentSubject()).isEqualTo(UPDATED_DOCUMENT_SUBJECT);
        assertThat(testFormalDocument.getBriefDescription()).isEqualTo(UPDATED_BRIEF_DESCRIPTION);
        assertThat(testFormalDocument.getDocumentDate()).isEqualTo(UPDATED_DOCUMENT_DATE);
        assertThat(testFormalDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testFormalDocument.getDocumentStandardNumber()).isEqualTo(UPDATED_DOCUMENT_STANDARD_NUMBER);
        assertThat(testFormalDocument.getDocumentAttachment()).isEqualTo(UPDATED_DOCUMENT_ATTACHMENT);
        assertThat(testFormalDocument.getDocumentAttachmentContentType()).isEqualTo(UPDATED_DOCUMENT_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingFormalDocument() throws Exception {
        int databaseSizeBeforeUpdate = formalDocumentRepository.findAll().size();

        // Create the FormalDocument
        FormalDocumentDTO formalDocumentDTO = formalDocumentMapper.toDto(formalDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormalDocumentMockMvc.perform(put("/api/formal-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formalDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormalDocument in the database
        List<FormalDocument> formalDocumentList = formalDocumentRepository.findAll();
        assertThat(formalDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFormalDocument() throws Exception {
        // Initialize the database
        formalDocumentRepository.saveAndFlush(formalDocument);

        int databaseSizeBeforeDelete = formalDocumentRepository.findAll().size();

        // Delete the formalDocument
        restFormalDocumentMockMvc.perform(delete("/api/formal-documents/{id}", formalDocument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormalDocument> formalDocumentList = formalDocumentRepository.findAll();
        assertThat(formalDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
