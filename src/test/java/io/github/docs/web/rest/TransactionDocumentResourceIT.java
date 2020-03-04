package io.github.docs.web.rest;

import io.github.docs.DocumentsApp;
import io.github.docs.domain.TransactionDocument;
import io.github.docs.domain.UserProfile;
import io.github.docs.repository.TransactionDocumentRepository;
import io.github.docs.service.TransactionDocumentService;
import io.github.docs.service.dto.TransactionDocumentDTO;
import io.github.docs.service.mapper.TransactionDocumentMapper;
import io.github.docs.web.rest.errors.ExceptionTranslator;
import io.github.docs.service.dto.TransactionDocumentCriteria;
import io.github.docs.service.TransactionDocumentQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.github.docs.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TransactionDocumentResource} REST controller.
 */
@SpringBootTest(classes = DocumentsApp.class)
public class TransactionDocumentResourceIT {

    private static final String DEFAULT_TRANSACTION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TRANSACTION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_BRIEF_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_BRIEF_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_JUSTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_JUSTIFICATION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TRANSACTION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRANSACTION_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TRANSACTION_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_PAYEE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAYEE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LPO_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_LPO_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DEBIT_NOTE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DEBIT_NOTE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LOGISTIC_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_LOGISTIC_REFERENCE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MEMO_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MEMO_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_STANDARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_STANDARD_NUMBER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_TRANSACTION_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TRANSACTION_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_TRANSACTION_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TRANSACTION_ATTACHMENT_CONTENT_TYPE = "image/png";

    @Autowired
    private TransactionDocumentRepository transactionDocumentRepository;

    @Autowired
    private TransactionDocumentMapper transactionDocumentMapper;

    @Autowired
    private TransactionDocumentService transactionDocumentService;

    @Autowired
    private TransactionDocumentQueryService transactionDocumentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTransactionDocumentMockMvc;

    private TransactionDocument transactionDocument;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionDocumentResource transactionDocumentResource = new TransactionDocumentResource(transactionDocumentService, transactionDocumentQueryService);
        this.restTransactionDocumentMockMvc = MockMvcBuilders.standaloneSetup(transactionDocumentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionDocument createEntity(EntityManager em) {
        TransactionDocument transactionDocument = new TransactionDocument()
            .transactionNumber(DEFAULT_TRANSACTION_NUMBER)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .briefDescription(DEFAULT_BRIEF_DESCRIPTION)
            .justification(DEFAULT_JUSTIFICATION)
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT)
            .payeeName(DEFAULT_PAYEE_NAME)
            .invoiceNumber(DEFAULT_INVOICE_NUMBER)
            .lpoNumber(DEFAULT_LPO_NUMBER)
            .debitNoteNumber(DEFAULT_DEBIT_NOTE_NUMBER)
            .logisticReferenceNumber(DEFAULT_LOGISTIC_REFERENCE_NUMBER)
            .memoNumber(DEFAULT_MEMO_NUMBER)
            .documentStandardNumber(DEFAULT_DOCUMENT_STANDARD_NUMBER)
            .transactionAttachment(DEFAULT_TRANSACTION_ATTACHMENT)
            .transactionAttachmentContentType(DEFAULT_TRANSACTION_ATTACHMENT_CONTENT_TYPE);
        return transactionDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionDocument createUpdatedEntity(EntityManager em) {
        TransactionDocument transactionDocument = new TransactionDocument()
            .transactionNumber(UPDATED_TRANSACTION_NUMBER)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .briefDescription(UPDATED_BRIEF_DESCRIPTION)
            .justification(UPDATED_JUSTIFICATION)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .payeeName(UPDATED_PAYEE_NAME)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .lpoNumber(UPDATED_LPO_NUMBER)
            .debitNoteNumber(UPDATED_DEBIT_NOTE_NUMBER)
            .logisticReferenceNumber(UPDATED_LOGISTIC_REFERENCE_NUMBER)
            .memoNumber(UPDATED_MEMO_NUMBER)
            .documentStandardNumber(UPDATED_DOCUMENT_STANDARD_NUMBER)
            .transactionAttachment(UPDATED_TRANSACTION_ATTACHMENT)
            .transactionAttachmentContentType(UPDATED_TRANSACTION_ATTACHMENT_CONTENT_TYPE);
        return transactionDocument;
    }

    @BeforeEach
    public void initTest() {
        transactionDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionDocument() throws Exception {
        int databaseSizeBeforeCreate = transactionDocumentRepository.findAll().size();

        // Create the TransactionDocument
        TransactionDocumentDTO transactionDocumentDTO = transactionDocumentMapper.toDto(transactionDocument);
        restTransactionDocumentMockMvc.perform(post("/api/transaction-documents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionDocument in the database
        List<TransactionDocument> transactionDocumentList = transactionDocumentRepository.findAll();
        assertThat(transactionDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionDocument testTransactionDocument = transactionDocumentList.get(transactionDocumentList.size() - 1);
        assertThat(testTransactionDocument.getTransactionNumber()).isEqualTo(DEFAULT_TRANSACTION_NUMBER);
        assertThat(testTransactionDocument.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testTransactionDocument.getBriefDescription()).isEqualTo(DEFAULT_BRIEF_DESCRIPTION);
        assertThat(testTransactionDocument.getJustification()).isEqualTo(DEFAULT_JUSTIFICATION);
        assertThat(testTransactionDocument.getTransactionAmount()).isEqualTo(DEFAULT_TRANSACTION_AMOUNT);
        assertThat(testTransactionDocument.getPayeeName()).isEqualTo(DEFAULT_PAYEE_NAME);
        assertThat(testTransactionDocument.getInvoiceNumber()).isEqualTo(DEFAULT_INVOICE_NUMBER);
        assertThat(testTransactionDocument.getLpoNumber()).isEqualTo(DEFAULT_LPO_NUMBER);
        assertThat(testTransactionDocument.getDebitNoteNumber()).isEqualTo(DEFAULT_DEBIT_NOTE_NUMBER);
        assertThat(testTransactionDocument.getLogisticReferenceNumber()).isEqualTo(DEFAULT_LOGISTIC_REFERENCE_NUMBER);
        assertThat(testTransactionDocument.getMemoNumber()).isEqualTo(DEFAULT_MEMO_NUMBER);
        assertThat(testTransactionDocument.getDocumentStandardNumber()).isEqualTo(DEFAULT_DOCUMENT_STANDARD_NUMBER);
        assertThat(testTransactionDocument.getTransactionAttachment()).isEqualTo(DEFAULT_TRANSACTION_ATTACHMENT);
        assertThat(testTransactionDocument.getTransactionAttachmentContentType()).isEqualTo(DEFAULT_TRANSACTION_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createTransactionDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionDocumentRepository.findAll().size();

        // Create the TransactionDocument with an existing ID
        transactionDocument.setId(1L);
        TransactionDocumentDTO transactionDocumentDTO = transactionDocumentMapper.toDto(transactionDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionDocumentMockMvc.perform(post("/api/transaction-documents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionDocument in the database
        List<TransactionDocument> transactionDocumentList = transactionDocumentRepository.findAll();
        assertThat(transactionDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransactionNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionDocumentRepository.findAll().size();
        // set the field null
        transactionDocument.setTransactionNumber(null);

        // Create the TransactionDocument, which fails.
        TransactionDocumentDTO transactionDocumentDTO = transactionDocumentMapper.toDto(transactionDocument);

        restTransactionDocumentMockMvc.perform(post("/api/transaction-documents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionDocument> transactionDocumentList = transactionDocumentRepository.findAll();
        assertThat(transactionDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionDocumentRepository.findAll().size();
        // set the field null
        transactionDocument.setTransactionDate(null);

        // Create the TransactionDocument, which fails.
        TransactionDocumentDTO transactionDocumentDTO = transactionDocumentMapper.toDto(transactionDocument);

        restTransactionDocumentMockMvc.perform(post("/api/transaction-documents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionDocument> transactionDocumentList = transactionDocumentRepository.findAll();
        assertThat(transactionDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionDocuments() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList
        restTransactionDocumentMockMvc.perform(get("/api/transaction-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionNumber").value(hasItem(DEFAULT_TRANSACTION_NUMBER)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].briefDescription").value(hasItem(DEFAULT_BRIEF_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].justification").value(hasItem(DEFAULT_JUSTIFICATION)))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].payeeName").value(hasItem(DEFAULT_PAYEE_NAME)))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].lpoNumber").value(hasItem(DEFAULT_LPO_NUMBER)))
            .andExpect(jsonPath("$.[*].debitNoteNumber").value(hasItem(DEFAULT_DEBIT_NOTE_NUMBER)))
            .andExpect(jsonPath("$.[*].logisticReferenceNumber").value(hasItem(DEFAULT_LOGISTIC_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].memoNumber").value(hasItem(DEFAULT_MEMO_NUMBER)))
            .andExpect(jsonPath("$.[*].documentStandardNumber").value(hasItem(DEFAULT_DOCUMENT_STANDARD_NUMBER)))
            .andExpect(jsonPath("$.[*].transactionAttachmentContentType").value(hasItem(DEFAULT_TRANSACTION_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].transactionAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_TRANSACTION_ATTACHMENT))));
    }
    
    @Test
    @Transactional
    public void getTransactionDocument() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get the transactionDocument
        restTransactionDocumentMockMvc.perform(get("/api/transaction-documents/{id}", transactionDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionDocument.getId().intValue()))
            .andExpect(jsonPath("$.transactionNumber").value(DEFAULT_TRANSACTION_NUMBER))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.briefDescription").value(DEFAULT_BRIEF_DESCRIPTION))
            .andExpect(jsonPath("$.justification").value(DEFAULT_JUSTIFICATION))
            .andExpect(jsonPath("$.transactionAmount").value(DEFAULT_TRANSACTION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.payeeName").value(DEFAULT_PAYEE_NAME))
            .andExpect(jsonPath("$.invoiceNumber").value(DEFAULT_INVOICE_NUMBER))
            .andExpect(jsonPath("$.lpoNumber").value(DEFAULT_LPO_NUMBER))
            .andExpect(jsonPath("$.debitNoteNumber").value(DEFAULT_DEBIT_NOTE_NUMBER))
            .andExpect(jsonPath("$.logisticReferenceNumber").value(DEFAULT_LOGISTIC_REFERENCE_NUMBER))
            .andExpect(jsonPath("$.memoNumber").value(DEFAULT_MEMO_NUMBER))
            .andExpect(jsonPath("$.documentStandardNumber").value(DEFAULT_DOCUMENT_STANDARD_NUMBER))
            .andExpect(jsonPath("$.transactionAttachmentContentType").value(DEFAULT_TRANSACTION_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.transactionAttachment").value(Base64Utils.encodeToString(DEFAULT_TRANSACTION_ATTACHMENT)));
    }


    @Test
    @Transactional
    public void getTransactionDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        Long id = transactionDocument.getId();

        defaultTransactionDocumentShouldBeFound("id.equals=" + id);
        defaultTransactionDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultTransactionDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransactionDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultTransactionDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransactionDocumentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionNumber equals to DEFAULT_TRANSACTION_NUMBER
        defaultTransactionDocumentShouldBeFound("transactionNumber.equals=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the transactionDocumentList where transactionNumber equals to UPDATED_TRANSACTION_NUMBER
        defaultTransactionDocumentShouldNotBeFound("transactionNumber.equals=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionNumber not equals to DEFAULT_TRANSACTION_NUMBER
        defaultTransactionDocumentShouldNotBeFound("transactionNumber.notEquals=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the transactionDocumentList where transactionNumber not equals to UPDATED_TRANSACTION_NUMBER
        defaultTransactionDocumentShouldBeFound("transactionNumber.notEquals=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionNumberIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionNumber in DEFAULT_TRANSACTION_NUMBER or UPDATED_TRANSACTION_NUMBER
        defaultTransactionDocumentShouldBeFound("transactionNumber.in=" + DEFAULT_TRANSACTION_NUMBER + "," + UPDATED_TRANSACTION_NUMBER);

        // Get all the transactionDocumentList where transactionNumber equals to UPDATED_TRANSACTION_NUMBER
        defaultTransactionDocumentShouldNotBeFound("transactionNumber.in=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionNumber is not null
        defaultTransactionDocumentShouldBeFound("transactionNumber.specified=true");

        // Get all the transactionDocumentList where transactionNumber is null
        defaultTransactionDocumentShouldNotBeFound("transactionNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionNumberContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionNumber contains DEFAULT_TRANSACTION_NUMBER
        defaultTransactionDocumentShouldBeFound("transactionNumber.contains=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the transactionDocumentList where transactionNumber contains UPDATED_TRANSACTION_NUMBER
        defaultTransactionDocumentShouldNotBeFound("transactionNumber.contains=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionNumberNotContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionNumber does not contain DEFAULT_TRANSACTION_NUMBER
        defaultTransactionDocumentShouldNotBeFound("transactionNumber.doesNotContain=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the transactionDocumentList where transactionNumber does not contain UPDATED_TRANSACTION_NUMBER
        defaultTransactionDocumentShouldBeFound("transactionNumber.doesNotContain=" + UPDATED_TRANSACTION_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionDate equals to DEFAULT_TRANSACTION_DATE
        defaultTransactionDocumentShouldBeFound("transactionDate.equals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the transactionDocumentList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultTransactionDocumentShouldNotBeFound("transactionDate.equals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionDate not equals to DEFAULT_TRANSACTION_DATE
        defaultTransactionDocumentShouldNotBeFound("transactionDate.notEquals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the transactionDocumentList where transactionDate not equals to UPDATED_TRANSACTION_DATE
        defaultTransactionDocumentShouldBeFound("transactionDate.notEquals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionDate in DEFAULT_TRANSACTION_DATE or UPDATED_TRANSACTION_DATE
        defaultTransactionDocumentShouldBeFound("transactionDate.in=" + DEFAULT_TRANSACTION_DATE + "," + UPDATED_TRANSACTION_DATE);

        // Get all the transactionDocumentList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultTransactionDocumentShouldNotBeFound("transactionDate.in=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionDate is not null
        defaultTransactionDocumentShouldBeFound("transactionDate.specified=true");

        // Get all the transactionDocumentList where transactionDate is null
        defaultTransactionDocumentShouldNotBeFound("transactionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionDate is greater than or equal to DEFAULT_TRANSACTION_DATE
        defaultTransactionDocumentShouldBeFound("transactionDate.greaterThanOrEqual=" + DEFAULT_TRANSACTION_DATE);

        // Get all the transactionDocumentList where transactionDate is greater than or equal to UPDATED_TRANSACTION_DATE
        defaultTransactionDocumentShouldNotBeFound("transactionDate.greaterThanOrEqual=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionDate is less than or equal to DEFAULT_TRANSACTION_DATE
        defaultTransactionDocumentShouldBeFound("transactionDate.lessThanOrEqual=" + DEFAULT_TRANSACTION_DATE);

        // Get all the transactionDocumentList where transactionDate is less than or equal to SMALLER_TRANSACTION_DATE
        defaultTransactionDocumentShouldNotBeFound("transactionDate.lessThanOrEqual=" + SMALLER_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionDate is less than DEFAULT_TRANSACTION_DATE
        defaultTransactionDocumentShouldNotBeFound("transactionDate.lessThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the transactionDocumentList where transactionDate is less than UPDATED_TRANSACTION_DATE
        defaultTransactionDocumentShouldBeFound("transactionDate.lessThan=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionDate is greater than DEFAULT_TRANSACTION_DATE
        defaultTransactionDocumentShouldNotBeFound("transactionDate.greaterThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the transactionDocumentList where transactionDate is greater than SMALLER_TRANSACTION_DATE
        defaultTransactionDocumentShouldBeFound("transactionDate.greaterThan=" + SMALLER_TRANSACTION_DATE);
    }


    @Test
    @Transactional
    public void getAllTransactionDocumentsByBriefDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where briefDescription equals to DEFAULT_BRIEF_DESCRIPTION
        defaultTransactionDocumentShouldBeFound("briefDescription.equals=" + DEFAULT_BRIEF_DESCRIPTION);

        // Get all the transactionDocumentList where briefDescription equals to UPDATED_BRIEF_DESCRIPTION
        defaultTransactionDocumentShouldNotBeFound("briefDescription.equals=" + UPDATED_BRIEF_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByBriefDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where briefDescription not equals to DEFAULT_BRIEF_DESCRIPTION
        defaultTransactionDocumentShouldNotBeFound("briefDescription.notEquals=" + DEFAULT_BRIEF_DESCRIPTION);

        // Get all the transactionDocumentList where briefDescription not equals to UPDATED_BRIEF_DESCRIPTION
        defaultTransactionDocumentShouldBeFound("briefDescription.notEquals=" + UPDATED_BRIEF_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByBriefDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where briefDescription in DEFAULT_BRIEF_DESCRIPTION or UPDATED_BRIEF_DESCRIPTION
        defaultTransactionDocumentShouldBeFound("briefDescription.in=" + DEFAULT_BRIEF_DESCRIPTION + "," + UPDATED_BRIEF_DESCRIPTION);

        // Get all the transactionDocumentList where briefDescription equals to UPDATED_BRIEF_DESCRIPTION
        defaultTransactionDocumentShouldNotBeFound("briefDescription.in=" + UPDATED_BRIEF_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByBriefDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where briefDescription is not null
        defaultTransactionDocumentShouldBeFound("briefDescription.specified=true");

        // Get all the transactionDocumentList where briefDescription is null
        defaultTransactionDocumentShouldNotBeFound("briefDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionDocumentsByBriefDescriptionContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where briefDescription contains DEFAULT_BRIEF_DESCRIPTION
        defaultTransactionDocumentShouldBeFound("briefDescription.contains=" + DEFAULT_BRIEF_DESCRIPTION);

        // Get all the transactionDocumentList where briefDescription contains UPDATED_BRIEF_DESCRIPTION
        defaultTransactionDocumentShouldNotBeFound("briefDescription.contains=" + UPDATED_BRIEF_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByBriefDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where briefDescription does not contain DEFAULT_BRIEF_DESCRIPTION
        defaultTransactionDocumentShouldNotBeFound("briefDescription.doesNotContain=" + DEFAULT_BRIEF_DESCRIPTION);

        // Get all the transactionDocumentList where briefDescription does not contain UPDATED_BRIEF_DESCRIPTION
        defaultTransactionDocumentShouldBeFound("briefDescription.doesNotContain=" + UPDATED_BRIEF_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllTransactionDocumentsByJustificationIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where justification equals to DEFAULT_JUSTIFICATION
        defaultTransactionDocumentShouldBeFound("justification.equals=" + DEFAULT_JUSTIFICATION);

        // Get all the transactionDocumentList where justification equals to UPDATED_JUSTIFICATION
        defaultTransactionDocumentShouldNotBeFound("justification.equals=" + UPDATED_JUSTIFICATION);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByJustificationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where justification not equals to DEFAULT_JUSTIFICATION
        defaultTransactionDocumentShouldNotBeFound("justification.notEquals=" + DEFAULT_JUSTIFICATION);

        // Get all the transactionDocumentList where justification not equals to UPDATED_JUSTIFICATION
        defaultTransactionDocumentShouldBeFound("justification.notEquals=" + UPDATED_JUSTIFICATION);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByJustificationIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where justification in DEFAULT_JUSTIFICATION or UPDATED_JUSTIFICATION
        defaultTransactionDocumentShouldBeFound("justification.in=" + DEFAULT_JUSTIFICATION + "," + UPDATED_JUSTIFICATION);

        // Get all the transactionDocumentList where justification equals to UPDATED_JUSTIFICATION
        defaultTransactionDocumentShouldNotBeFound("justification.in=" + UPDATED_JUSTIFICATION);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByJustificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where justification is not null
        defaultTransactionDocumentShouldBeFound("justification.specified=true");

        // Get all the transactionDocumentList where justification is null
        defaultTransactionDocumentShouldNotBeFound("justification.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionDocumentsByJustificationContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where justification contains DEFAULT_JUSTIFICATION
        defaultTransactionDocumentShouldBeFound("justification.contains=" + DEFAULT_JUSTIFICATION);

        // Get all the transactionDocumentList where justification contains UPDATED_JUSTIFICATION
        defaultTransactionDocumentShouldNotBeFound("justification.contains=" + UPDATED_JUSTIFICATION);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByJustificationNotContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where justification does not contain DEFAULT_JUSTIFICATION
        defaultTransactionDocumentShouldNotBeFound("justification.doesNotContain=" + DEFAULT_JUSTIFICATION);

        // Get all the transactionDocumentList where justification does not contain UPDATED_JUSTIFICATION
        defaultTransactionDocumentShouldBeFound("justification.doesNotContain=" + UPDATED_JUSTIFICATION);
    }


    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionAmount equals to DEFAULT_TRANSACTION_AMOUNT
        defaultTransactionDocumentShouldBeFound("transactionAmount.equals=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the transactionDocumentList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultTransactionDocumentShouldNotBeFound("transactionAmount.equals=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionAmount not equals to DEFAULT_TRANSACTION_AMOUNT
        defaultTransactionDocumentShouldNotBeFound("transactionAmount.notEquals=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the transactionDocumentList where transactionAmount not equals to UPDATED_TRANSACTION_AMOUNT
        defaultTransactionDocumentShouldBeFound("transactionAmount.notEquals=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionAmountIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionAmount in DEFAULT_TRANSACTION_AMOUNT or UPDATED_TRANSACTION_AMOUNT
        defaultTransactionDocumentShouldBeFound("transactionAmount.in=" + DEFAULT_TRANSACTION_AMOUNT + "," + UPDATED_TRANSACTION_AMOUNT);

        // Get all the transactionDocumentList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultTransactionDocumentShouldNotBeFound("transactionAmount.in=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionAmount is not null
        defaultTransactionDocumentShouldBeFound("transactionAmount.specified=true");

        // Get all the transactionDocumentList where transactionAmount is null
        defaultTransactionDocumentShouldNotBeFound("transactionAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionAmount is greater than or equal to DEFAULT_TRANSACTION_AMOUNT
        defaultTransactionDocumentShouldBeFound("transactionAmount.greaterThanOrEqual=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the transactionDocumentList where transactionAmount is greater than or equal to UPDATED_TRANSACTION_AMOUNT
        defaultTransactionDocumentShouldNotBeFound("transactionAmount.greaterThanOrEqual=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionAmount is less than or equal to DEFAULT_TRANSACTION_AMOUNT
        defaultTransactionDocumentShouldBeFound("transactionAmount.lessThanOrEqual=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the transactionDocumentList where transactionAmount is less than or equal to SMALLER_TRANSACTION_AMOUNT
        defaultTransactionDocumentShouldNotBeFound("transactionAmount.lessThanOrEqual=" + SMALLER_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionAmount is less than DEFAULT_TRANSACTION_AMOUNT
        defaultTransactionDocumentShouldNotBeFound("transactionAmount.lessThan=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the transactionDocumentList where transactionAmount is less than UPDATED_TRANSACTION_AMOUNT
        defaultTransactionDocumentShouldBeFound("transactionAmount.lessThan=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByTransactionAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where transactionAmount is greater than DEFAULT_TRANSACTION_AMOUNT
        defaultTransactionDocumentShouldNotBeFound("transactionAmount.greaterThan=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the transactionDocumentList where transactionAmount is greater than SMALLER_TRANSACTION_AMOUNT
        defaultTransactionDocumentShouldBeFound("transactionAmount.greaterThan=" + SMALLER_TRANSACTION_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllTransactionDocumentsByPayeeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where payeeName equals to DEFAULT_PAYEE_NAME
        defaultTransactionDocumentShouldBeFound("payeeName.equals=" + DEFAULT_PAYEE_NAME);

        // Get all the transactionDocumentList where payeeName equals to UPDATED_PAYEE_NAME
        defaultTransactionDocumentShouldNotBeFound("payeeName.equals=" + UPDATED_PAYEE_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByPayeeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where payeeName not equals to DEFAULT_PAYEE_NAME
        defaultTransactionDocumentShouldNotBeFound("payeeName.notEquals=" + DEFAULT_PAYEE_NAME);

        // Get all the transactionDocumentList where payeeName not equals to UPDATED_PAYEE_NAME
        defaultTransactionDocumentShouldBeFound("payeeName.notEquals=" + UPDATED_PAYEE_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByPayeeNameIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where payeeName in DEFAULT_PAYEE_NAME or UPDATED_PAYEE_NAME
        defaultTransactionDocumentShouldBeFound("payeeName.in=" + DEFAULT_PAYEE_NAME + "," + UPDATED_PAYEE_NAME);

        // Get all the transactionDocumentList where payeeName equals to UPDATED_PAYEE_NAME
        defaultTransactionDocumentShouldNotBeFound("payeeName.in=" + UPDATED_PAYEE_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByPayeeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where payeeName is not null
        defaultTransactionDocumentShouldBeFound("payeeName.specified=true");

        // Get all the transactionDocumentList where payeeName is null
        defaultTransactionDocumentShouldNotBeFound("payeeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionDocumentsByPayeeNameContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where payeeName contains DEFAULT_PAYEE_NAME
        defaultTransactionDocumentShouldBeFound("payeeName.contains=" + DEFAULT_PAYEE_NAME);

        // Get all the transactionDocumentList where payeeName contains UPDATED_PAYEE_NAME
        defaultTransactionDocumentShouldNotBeFound("payeeName.contains=" + UPDATED_PAYEE_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByPayeeNameNotContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where payeeName does not contain DEFAULT_PAYEE_NAME
        defaultTransactionDocumentShouldNotBeFound("payeeName.doesNotContain=" + DEFAULT_PAYEE_NAME);

        // Get all the transactionDocumentList where payeeName does not contain UPDATED_PAYEE_NAME
        defaultTransactionDocumentShouldBeFound("payeeName.doesNotContain=" + UPDATED_PAYEE_NAME);
    }


    @Test
    @Transactional
    public void getAllTransactionDocumentsByInvoiceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where invoiceNumber equals to DEFAULT_INVOICE_NUMBER
        defaultTransactionDocumentShouldBeFound("invoiceNumber.equals=" + DEFAULT_INVOICE_NUMBER);

        // Get all the transactionDocumentList where invoiceNumber equals to UPDATED_INVOICE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("invoiceNumber.equals=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByInvoiceNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where invoiceNumber not equals to DEFAULT_INVOICE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("invoiceNumber.notEquals=" + DEFAULT_INVOICE_NUMBER);

        // Get all the transactionDocumentList where invoiceNumber not equals to UPDATED_INVOICE_NUMBER
        defaultTransactionDocumentShouldBeFound("invoiceNumber.notEquals=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByInvoiceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where invoiceNumber in DEFAULT_INVOICE_NUMBER or UPDATED_INVOICE_NUMBER
        defaultTransactionDocumentShouldBeFound("invoiceNumber.in=" + DEFAULT_INVOICE_NUMBER + "," + UPDATED_INVOICE_NUMBER);

        // Get all the transactionDocumentList where invoiceNumber equals to UPDATED_INVOICE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("invoiceNumber.in=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByInvoiceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where invoiceNumber is not null
        defaultTransactionDocumentShouldBeFound("invoiceNumber.specified=true");

        // Get all the transactionDocumentList where invoiceNumber is null
        defaultTransactionDocumentShouldNotBeFound("invoiceNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionDocumentsByInvoiceNumberContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where invoiceNumber contains DEFAULT_INVOICE_NUMBER
        defaultTransactionDocumentShouldBeFound("invoiceNumber.contains=" + DEFAULT_INVOICE_NUMBER);

        // Get all the transactionDocumentList where invoiceNumber contains UPDATED_INVOICE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("invoiceNumber.contains=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByInvoiceNumberNotContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where invoiceNumber does not contain DEFAULT_INVOICE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("invoiceNumber.doesNotContain=" + DEFAULT_INVOICE_NUMBER);

        // Get all the transactionDocumentList where invoiceNumber does not contain UPDATED_INVOICE_NUMBER
        defaultTransactionDocumentShouldBeFound("invoiceNumber.doesNotContain=" + UPDATED_INVOICE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTransactionDocumentsByLpoNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where lpoNumber equals to DEFAULT_LPO_NUMBER
        defaultTransactionDocumentShouldBeFound("lpoNumber.equals=" + DEFAULT_LPO_NUMBER);

        // Get all the transactionDocumentList where lpoNumber equals to UPDATED_LPO_NUMBER
        defaultTransactionDocumentShouldNotBeFound("lpoNumber.equals=" + UPDATED_LPO_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByLpoNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where lpoNumber not equals to DEFAULT_LPO_NUMBER
        defaultTransactionDocumentShouldNotBeFound("lpoNumber.notEquals=" + DEFAULT_LPO_NUMBER);

        // Get all the transactionDocumentList where lpoNumber not equals to UPDATED_LPO_NUMBER
        defaultTransactionDocumentShouldBeFound("lpoNumber.notEquals=" + UPDATED_LPO_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByLpoNumberIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where lpoNumber in DEFAULT_LPO_NUMBER or UPDATED_LPO_NUMBER
        defaultTransactionDocumentShouldBeFound("lpoNumber.in=" + DEFAULT_LPO_NUMBER + "," + UPDATED_LPO_NUMBER);

        // Get all the transactionDocumentList where lpoNumber equals to UPDATED_LPO_NUMBER
        defaultTransactionDocumentShouldNotBeFound("lpoNumber.in=" + UPDATED_LPO_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByLpoNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where lpoNumber is not null
        defaultTransactionDocumentShouldBeFound("lpoNumber.specified=true");

        // Get all the transactionDocumentList where lpoNumber is null
        defaultTransactionDocumentShouldNotBeFound("lpoNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionDocumentsByLpoNumberContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where lpoNumber contains DEFAULT_LPO_NUMBER
        defaultTransactionDocumentShouldBeFound("lpoNumber.contains=" + DEFAULT_LPO_NUMBER);

        // Get all the transactionDocumentList where lpoNumber contains UPDATED_LPO_NUMBER
        defaultTransactionDocumentShouldNotBeFound("lpoNumber.contains=" + UPDATED_LPO_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByLpoNumberNotContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where lpoNumber does not contain DEFAULT_LPO_NUMBER
        defaultTransactionDocumentShouldNotBeFound("lpoNumber.doesNotContain=" + DEFAULT_LPO_NUMBER);

        // Get all the transactionDocumentList where lpoNumber does not contain UPDATED_LPO_NUMBER
        defaultTransactionDocumentShouldBeFound("lpoNumber.doesNotContain=" + UPDATED_LPO_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTransactionDocumentsByDebitNoteNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where debitNoteNumber equals to DEFAULT_DEBIT_NOTE_NUMBER
        defaultTransactionDocumentShouldBeFound("debitNoteNumber.equals=" + DEFAULT_DEBIT_NOTE_NUMBER);

        // Get all the transactionDocumentList where debitNoteNumber equals to UPDATED_DEBIT_NOTE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("debitNoteNumber.equals=" + UPDATED_DEBIT_NOTE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByDebitNoteNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where debitNoteNumber not equals to DEFAULT_DEBIT_NOTE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("debitNoteNumber.notEquals=" + DEFAULT_DEBIT_NOTE_NUMBER);

        // Get all the transactionDocumentList where debitNoteNumber not equals to UPDATED_DEBIT_NOTE_NUMBER
        defaultTransactionDocumentShouldBeFound("debitNoteNumber.notEquals=" + UPDATED_DEBIT_NOTE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByDebitNoteNumberIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where debitNoteNumber in DEFAULT_DEBIT_NOTE_NUMBER or UPDATED_DEBIT_NOTE_NUMBER
        defaultTransactionDocumentShouldBeFound("debitNoteNumber.in=" + DEFAULT_DEBIT_NOTE_NUMBER + "," + UPDATED_DEBIT_NOTE_NUMBER);

        // Get all the transactionDocumentList where debitNoteNumber equals to UPDATED_DEBIT_NOTE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("debitNoteNumber.in=" + UPDATED_DEBIT_NOTE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByDebitNoteNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where debitNoteNumber is not null
        defaultTransactionDocumentShouldBeFound("debitNoteNumber.specified=true");

        // Get all the transactionDocumentList where debitNoteNumber is null
        defaultTransactionDocumentShouldNotBeFound("debitNoteNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionDocumentsByDebitNoteNumberContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where debitNoteNumber contains DEFAULT_DEBIT_NOTE_NUMBER
        defaultTransactionDocumentShouldBeFound("debitNoteNumber.contains=" + DEFAULT_DEBIT_NOTE_NUMBER);

        // Get all the transactionDocumentList where debitNoteNumber contains UPDATED_DEBIT_NOTE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("debitNoteNumber.contains=" + UPDATED_DEBIT_NOTE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByDebitNoteNumberNotContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where debitNoteNumber does not contain DEFAULT_DEBIT_NOTE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("debitNoteNumber.doesNotContain=" + DEFAULT_DEBIT_NOTE_NUMBER);

        // Get all the transactionDocumentList where debitNoteNumber does not contain UPDATED_DEBIT_NOTE_NUMBER
        defaultTransactionDocumentShouldBeFound("debitNoteNumber.doesNotContain=" + UPDATED_DEBIT_NOTE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTransactionDocumentsByLogisticReferenceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where logisticReferenceNumber equals to DEFAULT_LOGISTIC_REFERENCE_NUMBER
        defaultTransactionDocumentShouldBeFound("logisticReferenceNumber.equals=" + DEFAULT_LOGISTIC_REFERENCE_NUMBER);

        // Get all the transactionDocumentList where logisticReferenceNumber equals to UPDATED_LOGISTIC_REFERENCE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("logisticReferenceNumber.equals=" + UPDATED_LOGISTIC_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByLogisticReferenceNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where logisticReferenceNumber not equals to DEFAULT_LOGISTIC_REFERENCE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("logisticReferenceNumber.notEquals=" + DEFAULT_LOGISTIC_REFERENCE_NUMBER);

        // Get all the transactionDocumentList where logisticReferenceNumber not equals to UPDATED_LOGISTIC_REFERENCE_NUMBER
        defaultTransactionDocumentShouldBeFound("logisticReferenceNumber.notEquals=" + UPDATED_LOGISTIC_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByLogisticReferenceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where logisticReferenceNumber in DEFAULT_LOGISTIC_REFERENCE_NUMBER or UPDATED_LOGISTIC_REFERENCE_NUMBER
        defaultTransactionDocumentShouldBeFound("logisticReferenceNumber.in=" + DEFAULT_LOGISTIC_REFERENCE_NUMBER + "," + UPDATED_LOGISTIC_REFERENCE_NUMBER);

        // Get all the transactionDocumentList where logisticReferenceNumber equals to UPDATED_LOGISTIC_REFERENCE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("logisticReferenceNumber.in=" + UPDATED_LOGISTIC_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByLogisticReferenceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where logisticReferenceNumber is not null
        defaultTransactionDocumentShouldBeFound("logisticReferenceNumber.specified=true");

        // Get all the transactionDocumentList where logisticReferenceNumber is null
        defaultTransactionDocumentShouldNotBeFound("logisticReferenceNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionDocumentsByLogisticReferenceNumberContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where logisticReferenceNumber contains DEFAULT_LOGISTIC_REFERENCE_NUMBER
        defaultTransactionDocumentShouldBeFound("logisticReferenceNumber.contains=" + DEFAULT_LOGISTIC_REFERENCE_NUMBER);

        // Get all the transactionDocumentList where logisticReferenceNumber contains UPDATED_LOGISTIC_REFERENCE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("logisticReferenceNumber.contains=" + UPDATED_LOGISTIC_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByLogisticReferenceNumberNotContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where logisticReferenceNumber does not contain DEFAULT_LOGISTIC_REFERENCE_NUMBER
        defaultTransactionDocumentShouldNotBeFound("logisticReferenceNumber.doesNotContain=" + DEFAULT_LOGISTIC_REFERENCE_NUMBER);

        // Get all the transactionDocumentList where logisticReferenceNumber does not contain UPDATED_LOGISTIC_REFERENCE_NUMBER
        defaultTransactionDocumentShouldBeFound("logisticReferenceNumber.doesNotContain=" + UPDATED_LOGISTIC_REFERENCE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTransactionDocumentsByMemoNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where memoNumber equals to DEFAULT_MEMO_NUMBER
        defaultTransactionDocumentShouldBeFound("memoNumber.equals=" + DEFAULT_MEMO_NUMBER);

        // Get all the transactionDocumentList where memoNumber equals to UPDATED_MEMO_NUMBER
        defaultTransactionDocumentShouldNotBeFound("memoNumber.equals=" + UPDATED_MEMO_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByMemoNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where memoNumber not equals to DEFAULT_MEMO_NUMBER
        defaultTransactionDocumentShouldNotBeFound("memoNumber.notEquals=" + DEFAULT_MEMO_NUMBER);

        // Get all the transactionDocumentList where memoNumber not equals to UPDATED_MEMO_NUMBER
        defaultTransactionDocumentShouldBeFound("memoNumber.notEquals=" + UPDATED_MEMO_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByMemoNumberIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where memoNumber in DEFAULT_MEMO_NUMBER or UPDATED_MEMO_NUMBER
        defaultTransactionDocumentShouldBeFound("memoNumber.in=" + DEFAULT_MEMO_NUMBER + "," + UPDATED_MEMO_NUMBER);

        // Get all the transactionDocumentList where memoNumber equals to UPDATED_MEMO_NUMBER
        defaultTransactionDocumentShouldNotBeFound("memoNumber.in=" + UPDATED_MEMO_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByMemoNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where memoNumber is not null
        defaultTransactionDocumentShouldBeFound("memoNumber.specified=true");

        // Get all the transactionDocumentList where memoNumber is null
        defaultTransactionDocumentShouldNotBeFound("memoNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionDocumentsByMemoNumberContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where memoNumber contains DEFAULT_MEMO_NUMBER
        defaultTransactionDocumentShouldBeFound("memoNumber.contains=" + DEFAULT_MEMO_NUMBER);

        // Get all the transactionDocumentList where memoNumber contains UPDATED_MEMO_NUMBER
        defaultTransactionDocumentShouldNotBeFound("memoNumber.contains=" + UPDATED_MEMO_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByMemoNumberNotContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where memoNumber does not contain DEFAULT_MEMO_NUMBER
        defaultTransactionDocumentShouldNotBeFound("memoNumber.doesNotContain=" + DEFAULT_MEMO_NUMBER);

        // Get all the transactionDocumentList where memoNumber does not contain UPDATED_MEMO_NUMBER
        defaultTransactionDocumentShouldBeFound("memoNumber.doesNotContain=" + UPDATED_MEMO_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTransactionDocumentsByDocumentStandardNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where documentStandardNumber equals to DEFAULT_DOCUMENT_STANDARD_NUMBER
        defaultTransactionDocumentShouldBeFound("documentStandardNumber.equals=" + DEFAULT_DOCUMENT_STANDARD_NUMBER);

        // Get all the transactionDocumentList where documentStandardNumber equals to UPDATED_DOCUMENT_STANDARD_NUMBER
        defaultTransactionDocumentShouldNotBeFound("documentStandardNumber.equals=" + UPDATED_DOCUMENT_STANDARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByDocumentStandardNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where documentStandardNumber not equals to DEFAULT_DOCUMENT_STANDARD_NUMBER
        defaultTransactionDocumentShouldNotBeFound("documentStandardNumber.notEquals=" + DEFAULT_DOCUMENT_STANDARD_NUMBER);

        // Get all the transactionDocumentList where documentStandardNumber not equals to UPDATED_DOCUMENT_STANDARD_NUMBER
        defaultTransactionDocumentShouldBeFound("documentStandardNumber.notEquals=" + UPDATED_DOCUMENT_STANDARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByDocumentStandardNumberIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where documentStandardNumber in DEFAULT_DOCUMENT_STANDARD_NUMBER or UPDATED_DOCUMENT_STANDARD_NUMBER
        defaultTransactionDocumentShouldBeFound("documentStandardNumber.in=" + DEFAULT_DOCUMENT_STANDARD_NUMBER + "," + UPDATED_DOCUMENT_STANDARD_NUMBER);

        // Get all the transactionDocumentList where documentStandardNumber equals to UPDATED_DOCUMENT_STANDARD_NUMBER
        defaultTransactionDocumentShouldNotBeFound("documentStandardNumber.in=" + UPDATED_DOCUMENT_STANDARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByDocumentStandardNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where documentStandardNumber is not null
        defaultTransactionDocumentShouldBeFound("documentStandardNumber.specified=true");

        // Get all the transactionDocumentList where documentStandardNumber is null
        defaultTransactionDocumentShouldNotBeFound("documentStandardNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionDocumentsByDocumentStandardNumberContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where documentStandardNumber contains DEFAULT_DOCUMENT_STANDARD_NUMBER
        defaultTransactionDocumentShouldBeFound("documentStandardNumber.contains=" + DEFAULT_DOCUMENT_STANDARD_NUMBER);

        // Get all the transactionDocumentList where documentStandardNumber contains UPDATED_DOCUMENT_STANDARD_NUMBER
        defaultTransactionDocumentShouldNotBeFound("documentStandardNumber.contains=" + UPDATED_DOCUMENT_STANDARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionDocumentsByDocumentStandardNumberNotContainsSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        // Get all the transactionDocumentList where documentStandardNumber does not contain DEFAULT_DOCUMENT_STANDARD_NUMBER
        defaultTransactionDocumentShouldNotBeFound("documentStandardNumber.doesNotContain=" + DEFAULT_DOCUMENT_STANDARD_NUMBER);

        // Get all the transactionDocumentList where documentStandardNumber does not contain UPDATED_DOCUMENT_STANDARD_NUMBER
        defaultTransactionDocumentShouldBeFound("documentStandardNumber.doesNotContain=" + UPDATED_DOCUMENT_STANDARD_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTransactionDocumentsByDocumentOwnersIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);
        UserProfile documentOwners = UserProfileResourceIT.createEntity(em);
        em.persist(documentOwners);
        em.flush();
        transactionDocument.addDocumentOwners(documentOwners);
        transactionDocumentRepository.saveAndFlush(transactionDocument);
        Long documentOwnersId = documentOwners.getId();

        // Get all the transactionDocumentList where documentOwners equals to documentOwnersId
        defaultTransactionDocumentShouldBeFound("documentOwnersId.equals=" + documentOwnersId);

        // Get all the transactionDocumentList where documentOwners equals to documentOwnersId + 1
        defaultTransactionDocumentShouldNotBeFound("documentOwnersId.equals=" + (documentOwnersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionDocumentShouldBeFound(String filter) throws Exception {
        restTransactionDocumentMockMvc.perform(get("/api/transaction-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionNumber").value(hasItem(DEFAULT_TRANSACTION_NUMBER)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].briefDescription").value(hasItem(DEFAULT_BRIEF_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].justification").value(hasItem(DEFAULT_JUSTIFICATION)))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].payeeName").value(hasItem(DEFAULT_PAYEE_NAME)))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].lpoNumber").value(hasItem(DEFAULT_LPO_NUMBER)))
            .andExpect(jsonPath("$.[*].debitNoteNumber").value(hasItem(DEFAULT_DEBIT_NOTE_NUMBER)))
            .andExpect(jsonPath("$.[*].logisticReferenceNumber").value(hasItem(DEFAULT_LOGISTIC_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].memoNumber").value(hasItem(DEFAULT_MEMO_NUMBER)))
            .andExpect(jsonPath("$.[*].documentStandardNumber").value(hasItem(DEFAULT_DOCUMENT_STANDARD_NUMBER)))
            .andExpect(jsonPath("$.[*].transactionAttachmentContentType").value(hasItem(DEFAULT_TRANSACTION_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].transactionAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_TRANSACTION_ATTACHMENT))));

        // Check, that the count call also returns 1
        restTransactionDocumentMockMvc.perform(get("/api/transaction-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionDocumentShouldNotBeFound(String filter) throws Exception {
        restTransactionDocumentMockMvc.perform(get("/api/transaction-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionDocumentMockMvc.perform(get("/api/transaction-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTransactionDocument() throws Exception {
        // Get the transactionDocument
        restTransactionDocumentMockMvc.perform(get("/api/transaction-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionDocument() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        int databaseSizeBeforeUpdate = transactionDocumentRepository.findAll().size();

        // Update the transactionDocument
        TransactionDocument updatedTransactionDocument = transactionDocumentRepository.findById(transactionDocument.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionDocument are not directly saved in db
        em.detach(updatedTransactionDocument);
        updatedTransactionDocument
            .transactionNumber(UPDATED_TRANSACTION_NUMBER)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .briefDescription(UPDATED_BRIEF_DESCRIPTION)
            .justification(UPDATED_JUSTIFICATION)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .payeeName(UPDATED_PAYEE_NAME)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .lpoNumber(UPDATED_LPO_NUMBER)
            .debitNoteNumber(UPDATED_DEBIT_NOTE_NUMBER)
            .logisticReferenceNumber(UPDATED_LOGISTIC_REFERENCE_NUMBER)
            .memoNumber(UPDATED_MEMO_NUMBER)
            .documentStandardNumber(UPDATED_DOCUMENT_STANDARD_NUMBER)
            .transactionAttachment(UPDATED_TRANSACTION_ATTACHMENT)
            .transactionAttachmentContentType(UPDATED_TRANSACTION_ATTACHMENT_CONTENT_TYPE);
        TransactionDocumentDTO transactionDocumentDTO = transactionDocumentMapper.toDto(updatedTransactionDocument);

        restTransactionDocumentMockMvc.perform(put("/api/transaction-documents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionDocument in the database
        List<TransactionDocument> transactionDocumentList = transactionDocumentRepository.findAll();
        assertThat(transactionDocumentList).hasSize(databaseSizeBeforeUpdate);
        TransactionDocument testTransactionDocument = transactionDocumentList.get(transactionDocumentList.size() - 1);
        assertThat(testTransactionDocument.getTransactionNumber()).isEqualTo(UPDATED_TRANSACTION_NUMBER);
        assertThat(testTransactionDocument.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testTransactionDocument.getBriefDescription()).isEqualTo(UPDATED_BRIEF_DESCRIPTION);
        assertThat(testTransactionDocument.getJustification()).isEqualTo(UPDATED_JUSTIFICATION);
        assertThat(testTransactionDocument.getTransactionAmount()).isEqualTo(UPDATED_TRANSACTION_AMOUNT);
        assertThat(testTransactionDocument.getPayeeName()).isEqualTo(UPDATED_PAYEE_NAME);
        assertThat(testTransactionDocument.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testTransactionDocument.getLpoNumber()).isEqualTo(UPDATED_LPO_NUMBER);
        assertThat(testTransactionDocument.getDebitNoteNumber()).isEqualTo(UPDATED_DEBIT_NOTE_NUMBER);
        assertThat(testTransactionDocument.getLogisticReferenceNumber()).isEqualTo(UPDATED_LOGISTIC_REFERENCE_NUMBER);
        assertThat(testTransactionDocument.getMemoNumber()).isEqualTo(UPDATED_MEMO_NUMBER);
        assertThat(testTransactionDocument.getDocumentStandardNumber()).isEqualTo(UPDATED_DOCUMENT_STANDARD_NUMBER);
        assertThat(testTransactionDocument.getTransactionAttachment()).isEqualTo(UPDATED_TRANSACTION_ATTACHMENT);
        assertThat(testTransactionDocument.getTransactionAttachmentContentType()).isEqualTo(UPDATED_TRANSACTION_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionDocument() throws Exception {
        int databaseSizeBeforeUpdate = transactionDocumentRepository.findAll().size();

        // Create the TransactionDocument
        TransactionDocumentDTO transactionDocumentDTO = transactionDocumentMapper.toDto(transactionDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionDocumentMockMvc.perform(put("/api/transaction-documents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionDocument in the database
        List<TransactionDocument> transactionDocumentList = transactionDocumentRepository.findAll();
        assertThat(transactionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionDocument() throws Exception {
        // Initialize the database
        transactionDocumentRepository.saveAndFlush(transactionDocument);

        int databaseSizeBeforeDelete = transactionDocumentRepository.findAll().size();

        // Delete the transactionDocument
        restTransactionDocumentMockMvc.perform(delete("/api/transaction-documents/{id}", transactionDocument.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionDocument> transactionDocumentList = transactionDocumentRepository.findAll();
        assertThat(transactionDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
