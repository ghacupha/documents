package io.github.docs.web.rest;

import io.github.docs.DocumentsApp;
import io.github.docs.domain.UserProfile;
import io.github.docs.domain.User;
import io.github.docs.domain.Department;
import io.github.docs.domain.TransactionDocument;
import io.github.docs.repository.UserProfileRepository;
import io.github.docs.service.UserProfileService;
import io.github.docs.service.dto.UserProfileDTO;
import io.github.docs.service.mapper.UserProfileMapper;
import io.github.docs.web.rest.errors.ExceptionTranslator;
import io.github.docs.service.dto.UserProfileCriteria;
import io.github.docs.service.UserProfileQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static io.github.docs.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserProfileResource} REST controller.
 */
@SpringBootTest(classes = DocumentsApp.class)
public class UserProfileResourceIT {

    private static final String DEFAULT_STAFF_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_STAFF_NUMBER = "BBBBBBBBBB";

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserProfileRepository userProfileRepositoryMock;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Mock
    private UserProfileService userProfileServiceMock;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserProfileQueryService userProfileQueryService;

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

    private MockMvc restUserProfileMockMvc;

    private UserProfile userProfile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserProfileResource userProfileResource = new UserProfileResource(userProfileService, userProfileQueryService);
        this.restUserProfileMockMvc = MockMvcBuilders.standaloneSetup(userProfileResource)
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
    public static UserProfile createEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .staffNumber(DEFAULT_STAFF_NUMBER);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userProfile.setUser(user);
        return userProfile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createUpdatedEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .staffNumber(UPDATED_STAFF_NUMBER);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userProfile.setUser(user);
        return userProfile;
    }

    @BeforeEach
    public void initTest() {
        userProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserProfile() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // Create the UserProfile
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);
        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate + 1);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getStaffNumber()).isEqualTo(DEFAULT_STAFF_NUMBER);

        // Validate the id for MapsId, the ids must be same
        assertThat(testUserProfile.getId()).isEqualTo(testUserProfile.getUser().getId());
    }

    @Test
    @Transactional
    public void createUserProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // Create the UserProfile with an existing ID
        userProfile.setId(1L);
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateUserProfileMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // Add a new parent entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();

        // Load the userProfile
        UserProfile updatedUserProfile = userProfileRepository.findById(userProfile.getId()).get();
        // Disconnect from session so that the updates on updatedUserProfile are not directly saved in db
        em.detach(updatedUserProfile);

        // Update the User with new association value
        updatedUserProfile.setUser(user);
        UserProfileDTO updatedUserProfileDTO = userProfileMapper.toDto(updatedUserProfile);

        // Update the entity
        restUserProfileMockMvc.perform(put("/api/user-profiles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserProfileDTO)))
            .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testUserProfile.getId()).isEqualTo(testUserProfile.getUser().getId());
    }

    @Test
    @Transactional
    public void getAllUserProfiles() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].staffNumber").value(hasItem(DEFAULT_STAFF_NUMBER)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUserProfilesWithEagerRelationshipsIsEnabled() throws Exception {
        UserProfileResource userProfileResource = new UserProfileResource(userProfileServiceMock, userProfileQueryService);
        when(userProfileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restUserProfileMockMvc = MockMvcBuilders.standaloneSetup(userProfileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserProfileMockMvc.perform(get("/api/user-profiles?eagerload=true"))
        .andExpect(status().isOk());

        verify(userProfileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUserProfilesWithEagerRelationshipsIsNotEnabled() throws Exception {
        UserProfileResource userProfileResource = new UserProfileResource(userProfileServiceMock, userProfileQueryService);
            when(userProfileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUserProfileMockMvc = MockMvcBuilders.standaloneSetup(userProfileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserProfileMockMvc.perform(get("/api/user-profiles?eagerload=true"))
        .andExpect(status().isOk());

            verify(userProfileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/user-profiles/{id}", userProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userProfile.getId().intValue()))
            .andExpect(jsonPath("$.staffNumber").value(DEFAULT_STAFF_NUMBER));
    }


    @Test
    @Transactional
    public void getUserProfilesByIdFiltering() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        Long id = userProfile.getId();

        defaultUserProfileShouldBeFound("id.equals=" + id);
        defaultUserProfileShouldNotBeFound("id.notEquals=" + id);

        defaultUserProfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserProfileShouldNotBeFound("id.greaterThan=" + id);

        defaultUserProfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserProfileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByStaffNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where staffNumber equals to DEFAULT_STAFF_NUMBER
        defaultUserProfileShouldBeFound("staffNumber.equals=" + DEFAULT_STAFF_NUMBER);

        // Get all the userProfileList where staffNumber equals to UPDATED_STAFF_NUMBER
        defaultUserProfileShouldNotBeFound("staffNumber.equals=" + UPDATED_STAFF_NUMBER);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByStaffNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where staffNumber not equals to DEFAULT_STAFF_NUMBER
        defaultUserProfileShouldNotBeFound("staffNumber.notEquals=" + DEFAULT_STAFF_NUMBER);

        // Get all the userProfileList where staffNumber not equals to UPDATED_STAFF_NUMBER
        defaultUserProfileShouldBeFound("staffNumber.notEquals=" + UPDATED_STAFF_NUMBER);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByStaffNumberIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where staffNumber in DEFAULT_STAFF_NUMBER or UPDATED_STAFF_NUMBER
        defaultUserProfileShouldBeFound("staffNumber.in=" + DEFAULT_STAFF_NUMBER + "," + UPDATED_STAFF_NUMBER);

        // Get all the userProfileList where staffNumber equals to UPDATED_STAFF_NUMBER
        defaultUserProfileShouldNotBeFound("staffNumber.in=" + UPDATED_STAFF_NUMBER);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByStaffNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where staffNumber is not null
        defaultUserProfileShouldBeFound("staffNumber.specified=true");

        // Get all the userProfileList where staffNumber is null
        defaultUserProfileShouldNotBeFound("staffNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByStaffNumberContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where staffNumber contains DEFAULT_STAFF_NUMBER
        defaultUserProfileShouldBeFound("staffNumber.contains=" + DEFAULT_STAFF_NUMBER);

        // Get all the userProfileList where staffNumber contains UPDATED_STAFF_NUMBER
        defaultUserProfileShouldNotBeFound("staffNumber.contains=" + UPDATED_STAFF_NUMBER);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByStaffNumberNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where staffNumber does not contain DEFAULT_STAFF_NUMBER
        defaultUserProfileShouldNotBeFound("staffNumber.doesNotContain=" + DEFAULT_STAFF_NUMBER);

        // Get all the userProfileList where staffNumber does not contain UPDATED_STAFF_NUMBER
        defaultUserProfileShouldBeFound("staffNumber.doesNotContain=" + UPDATED_STAFF_NUMBER);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = userProfile.getUser();
        userProfileRepository.saveAndFlush(userProfile);
        Long userId = user.getId();

        // Get all the userProfileList where user equals to userId
        defaultUserProfileShouldBeFound("userId.equals=" + userId);

        // Get all the userProfileList where user equals to userId + 1
        defaultUserProfileShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllUserProfilesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);
        Department department = DepartmentResourceIT.createEntity(em);
        em.persist(department);
        em.flush();
        userProfile.setDepartment(department);
        userProfileRepository.saveAndFlush(userProfile);
        Long departmentId = department.getId();

        // Get all the userProfileList where department equals to departmentId
        defaultUserProfileShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the userProfileList where department equals to departmentId + 1
        defaultUserProfileShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllUserProfilesByTransactionDocumentsIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);
        TransactionDocument transactionDocuments = TransactionDocumentResourceIT.createEntity(em);
        em.persist(transactionDocuments);
        em.flush();
        userProfile.addTransactionDocuments(transactionDocuments);
        userProfileRepository.saveAndFlush(userProfile);
        Long transactionDocumentsId = transactionDocuments.getId();

        // Get all the userProfileList where transactionDocuments equals to transactionDocumentsId
        defaultUserProfileShouldBeFound("transactionDocumentsId.equals=" + transactionDocumentsId);

        // Get all the userProfileList where transactionDocuments equals to transactionDocumentsId + 1
        defaultUserProfileShouldNotBeFound("transactionDocumentsId.equals=" + (transactionDocumentsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserProfileShouldBeFound(String filter) throws Exception {
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].staffNumber").value(hasItem(DEFAULT_STAFF_NUMBER)));

        // Check, that the count call also returns 1
        restUserProfileMockMvc.perform(get("/api/user-profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserProfileShouldNotBeFound(String filter) throws Exception {
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserProfileMockMvc.perform(get("/api/user-profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUserProfile() throws Exception {
        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/user-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Update the userProfile
        UserProfile updatedUserProfile = userProfileRepository.findById(userProfile.getId()).get();
        // Disconnect from session so that the updates on updatedUserProfile are not directly saved in db
        em.detach(updatedUserProfile);
        updatedUserProfile
            .staffNumber(UPDATED_STAFF_NUMBER);
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(updatedUserProfile);

        restUserProfileMockMvc.perform(put("/api/user-profiles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getStaffNumber()).isEqualTo(UPDATED_STAFF_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Create the UserProfile
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserProfileMockMvc.perform(put("/api/user-profiles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeDelete = userProfileRepository.findAll().size();

        // Delete the userProfile
        restUserProfileMockMvc.perform(delete("/api/user-profiles/{id}", userProfile.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
