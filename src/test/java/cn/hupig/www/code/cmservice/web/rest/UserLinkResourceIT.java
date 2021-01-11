package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.CmServiceApp;
import cn.hupig.www.code.cmservice.domain.UserLink;
import cn.hupig.www.code.cmservice.repository.UserLinkRepository;
import cn.hupig.www.code.cmservice.service.UserLinkService;
import cn.hupig.www.code.cmservice.service.dto.UserLinkDTO;
import cn.hupig.www.code.cmservice.service.mapper.UserLinkMapper;

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
 * Integration tests for the {@link UserLinkResource} REST controller.
 */
@SpringBootTest(classes = CmServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserLinkResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SEX = false;
    private static final Boolean UPDATED_SEX = true;

    private static final Long DEFAULT_AGE = 1L;
    private static final Long UPDATED_AGE = 2L;

    private static final String DEFAULT_THEME = "AAAAAAAAAA";
    private static final String UPDATED_THEME = "BBBBBBBBBB";

    private static final Long DEFAULT_PASSWORD_KEY = 6L;
    private static final Long UPDATED_PASSWORD_KEY = 7L;

    @Autowired
    private UserLinkRepository userLinkRepository;

    @Autowired
    private UserLinkMapper userLinkMapper;

    @Autowired
    private UserLinkService userLinkService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserLinkMockMvc;

    private UserLink userLink;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserLink createEntity(EntityManager em) {
        UserLink userLink = new UserLink()
            .firstName(DEFAULT_FIRST_NAME)
            .sex(DEFAULT_SEX)
            .age(DEFAULT_AGE)
            .theme(DEFAULT_THEME)
            .passwordKey(DEFAULT_PASSWORD_KEY);
        return userLink;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserLink createUpdatedEntity(EntityManager em) {
        UserLink userLink = new UserLink()
            .firstName(UPDATED_FIRST_NAME)
            .sex(UPDATED_SEX)
            .age(UPDATED_AGE)
            .theme(UPDATED_THEME)
            .passwordKey(UPDATED_PASSWORD_KEY);
        return userLink;
    }

    @BeforeEach
    public void initTest() {
        userLink = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserLink() throws Exception {
        int databaseSizeBeforeCreate = userLinkRepository.findAll().size();
        // Create the UserLink
        UserLinkDTO userLinkDTO = userLinkMapper.toDto(userLink);
        restUserLinkMockMvc.perform(post("/api/user-links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userLinkDTO)))
            .andExpect(status().isCreated());

        // Validate the UserLink in the database
        List<UserLink> userLinkList = userLinkRepository.findAll();
        assertThat(userLinkList).hasSize(databaseSizeBeforeCreate + 1);
        UserLink testUserLink = userLinkList.get(userLinkList.size() - 1);
        assertThat(testUserLink.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUserLink.isSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testUserLink.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testUserLink.getTheme()).isEqualTo(DEFAULT_THEME);
        assertThat(testUserLink.getPasswordKey()).isEqualTo(DEFAULT_PASSWORD_KEY);
    }

    @Test
    @Transactional
    public void createUserLinkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userLinkRepository.findAll().size();

        // Create the UserLink with an existing ID
        userLink.setId(1L);
        UserLinkDTO userLinkDTO = userLinkMapper.toDto(userLink);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserLinkMockMvc.perform(post("/api/user-links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userLinkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserLink in the database
        List<UserLink> userLinkList = userLinkRepository.findAll();
        assertThat(userLinkList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userLinkRepository.findAll().size();
        // set the field null
        userLink.setFirstName(null);

        // Create the UserLink, which fails.
        UserLinkDTO userLinkDTO = userLinkMapper.toDto(userLink);


        restUserLinkMockMvc.perform(post("/api/user-links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userLinkDTO)))
            .andExpect(status().isBadRequest());

        List<UserLink> userLinkList = userLinkRepository.findAll();
        assertThat(userLinkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserLinks() throws Exception {
        // Initialize the database
        userLinkRepository.saveAndFlush(userLink);

        // Get all the userLinkList
        restUserLinkMockMvc.perform(get("/api/user-links?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userLink.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.booleanValue())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.intValue())))
            .andExpect(jsonPath("$.[*].theme").value(hasItem(DEFAULT_THEME)))
            .andExpect(jsonPath("$.[*].passwordKey").value(hasItem(DEFAULT_PASSWORD_KEY.intValue())));
    }
    
    @Test
    @Transactional
    public void getUserLink() throws Exception {
        // Initialize the database
        userLinkRepository.saveAndFlush(userLink);

        // Get the userLink
        restUserLinkMockMvc.perform(get("/api/user-links/{id}", userLink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userLink.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.booleanValue()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.intValue()))
            .andExpect(jsonPath("$.theme").value(DEFAULT_THEME))
            .andExpect(jsonPath("$.passwordKey").value(DEFAULT_PASSWORD_KEY.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserLink() throws Exception {
        // Get the userLink
        restUserLinkMockMvc.perform(get("/api/user-links/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserLink() throws Exception {
        // Initialize the database
        userLinkRepository.saveAndFlush(userLink);

        int databaseSizeBeforeUpdate = userLinkRepository.findAll().size();

        // Update the userLink
        UserLink updatedUserLink = userLinkRepository.findById(userLink.getId()).get();
        // Disconnect from session so that the updates on updatedUserLink are not directly saved in db
        em.detach(updatedUserLink);
        updatedUserLink
            .firstName(UPDATED_FIRST_NAME)
            .sex(UPDATED_SEX)
            .age(UPDATED_AGE)
            .theme(UPDATED_THEME)
            .passwordKey(UPDATED_PASSWORD_KEY);
        UserLinkDTO userLinkDTO = userLinkMapper.toDto(updatedUserLink);

        restUserLinkMockMvc.perform(put("/api/user-links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userLinkDTO)))
            .andExpect(status().isOk());

        // Validate the UserLink in the database
        List<UserLink> userLinkList = userLinkRepository.findAll();
        assertThat(userLinkList).hasSize(databaseSizeBeforeUpdate);
        UserLink testUserLink = userLinkList.get(userLinkList.size() - 1);
        assertThat(testUserLink.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUserLink.isSex()).isEqualTo(UPDATED_SEX);
        assertThat(testUserLink.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testUserLink.getTheme()).isEqualTo(UPDATED_THEME);
        assertThat(testUserLink.getPasswordKey()).isEqualTo(UPDATED_PASSWORD_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingUserLink() throws Exception {
        int databaseSizeBeforeUpdate = userLinkRepository.findAll().size();

        // Create the UserLink
        UserLinkDTO userLinkDTO = userLinkMapper.toDto(userLink);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserLinkMockMvc.perform(put("/api/user-links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userLinkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserLink in the database
        List<UserLink> userLinkList = userLinkRepository.findAll();
        assertThat(userLinkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserLink() throws Exception {
        // Initialize the database
        userLinkRepository.saveAndFlush(userLink);

        int databaseSizeBeforeDelete = userLinkRepository.findAll().size();

        // Delete the userLink
        restUserLinkMockMvc.perform(delete("/api/user-links/{id}", userLink.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserLink> userLinkList = userLinkRepository.findAll();
        assertThat(userLinkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
