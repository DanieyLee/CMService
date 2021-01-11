package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.CmServiceApp;
import cn.hupig.www.code.cmservice.domain.SoftwareComments;
import cn.hupig.www.code.cmservice.repository.SoftwareCommentsRepository;
import cn.hupig.www.code.cmservice.service.SoftwareCommentsService;
import cn.hupig.www.code.cmservice.service.dto.SoftwareCommentsDTO;
import cn.hupig.www.code.cmservice.service.mapper.SoftwareCommentsMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SoftwareCommentsResource} REST controller.
 */
@SpringBootTest(classes = CmServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SoftwareCommentsResourceIT {

    private static final String DEFAULT_TX_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TX_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREAT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREAT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private SoftwareCommentsRepository softwareCommentsRepository;

    @Autowired
    private SoftwareCommentsMapper softwareCommentsMapper;

    @Autowired
    private SoftwareCommentsService softwareCommentsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoftwareCommentsMockMvc;

    private SoftwareComments softwareComments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoftwareComments createEntity(EntityManager em) {
        SoftwareComments softwareComments = new SoftwareComments()
            .txTitle(DEFAULT_TX_TITLE)
            .content(DEFAULT_CONTENT)
            .createUser(DEFAULT_CREATE_USER)
            .creatTime(DEFAULT_CREAT_TIME)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME)
            .note(DEFAULT_NOTE);
        return softwareComments;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoftwareComments createUpdatedEntity(EntityManager em) {
        SoftwareComments softwareComments = new SoftwareComments()
            .txTitle(UPDATED_TX_TITLE)
            .content(UPDATED_CONTENT)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        return softwareComments;
    }

    @BeforeEach
    public void initTest() {
        softwareComments = createEntity(em);
    }

    @Test
    @Transactional
    public void createSoftwareComments() throws Exception {
        int databaseSizeBeforeCreate = softwareCommentsRepository.findAll().size();
        // Create the SoftwareComments
        SoftwareCommentsDTO softwareCommentsDTO = softwareCommentsMapper.toDto(softwareComments);
        restSoftwareCommentsMockMvc.perform(post("/api/software-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareCommentsDTO)))
            .andExpect(status().isCreated());

        // Validate the SoftwareComments in the database
        List<SoftwareComments> softwareCommentsList = softwareCommentsRepository.findAll();
        assertThat(softwareCommentsList).hasSize(databaseSizeBeforeCreate + 1);
        SoftwareComments testSoftwareComments = softwareCommentsList.get(softwareCommentsList.size() - 1);
        assertThat(testSoftwareComments.getTxTitle()).isEqualTo(DEFAULT_TX_TITLE);
        assertThat(testSoftwareComments.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testSoftwareComments.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testSoftwareComments.getCreatTime()).isEqualTo(DEFAULT_CREAT_TIME);
        assertThat(testSoftwareComments.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testSoftwareComments.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testSoftwareComments.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createSoftwareCommentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = softwareCommentsRepository.findAll().size();

        // Create the SoftwareComments with an existing ID
        softwareComments.setId(1L);
        SoftwareCommentsDTO softwareCommentsDTO = softwareCommentsMapper.toDto(softwareComments);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoftwareCommentsMockMvc.perform(post("/api/software-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareCommentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SoftwareComments in the database
        List<SoftwareComments> softwareCommentsList = softwareCommentsRepository.findAll();
        assertThat(softwareCommentsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTxTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = softwareCommentsRepository.findAll().size();
        // set the field null
        softwareComments.setTxTitle(null);

        // Create the SoftwareComments, which fails.
        SoftwareCommentsDTO softwareCommentsDTO = softwareCommentsMapper.toDto(softwareComments);


        restSoftwareCommentsMockMvc.perform(post("/api/software-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareCommentsDTO)))
            .andExpect(status().isBadRequest());

        List<SoftwareComments> softwareCommentsList = softwareCommentsRepository.findAll();
        assertThat(softwareCommentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = softwareCommentsRepository.findAll().size();
        // set the field null
        softwareComments.setContent(null);

        // Create the SoftwareComments, which fails.
        SoftwareCommentsDTO softwareCommentsDTO = softwareCommentsMapper.toDto(softwareComments);


        restSoftwareCommentsMockMvc.perform(post("/api/software-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareCommentsDTO)))
            .andExpect(status().isBadRequest());

        List<SoftwareComments> softwareCommentsList = softwareCommentsRepository.findAll();
        assertThat(softwareCommentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSoftwareComments() throws Exception {
        // Initialize the database
        softwareCommentsRepository.saveAndFlush(softwareComments);

        // Get all the softwareCommentsList
        restSoftwareCommentsMockMvc.perform(get("/api/software-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(softwareComments.getId().intValue())))
            .andExpect(jsonPath("$.[*].txTitle").value(hasItem(DEFAULT_TX_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].creatTime").value(hasItem(DEFAULT_CREAT_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getSoftwareComments() throws Exception {
        // Initialize the database
        softwareCommentsRepository.saveAndFlush(softwareComments);

        // Get the softwareComments
        restSoftwareCommentsMockMvc.perform(get("/api/software-comments/{id}", softwareComments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(softwareComments.getId().intValue()))
            .andExpect(jsonPath("$.txTitle").value(DEFAULT_TX_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.creatTime").value(DEFAULT_CREAT_TIME.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingSoftwareComments() throws Exception {
        // Get the softwareComments
        restSoftwareCommentsMockMvc.perform(get("/api/software-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSoftwareComments() throws Exception {
        // Initialize the database
        softwareCommentsRepository.saveAndFlush(softwareComments);

        int databaseSizeBeforeUpdate = softwareCommentsRepository.findAll().size();

        // Update the softwareComments
        SoftwareComments updatedSoftwareComments = softwareCommentsRepository.findById(softwareComments.getId()).get();
        // Disconnect from session so that the updates on updatedSoftwareComments are not directly saved in db
        em.detach(updatedSoftwareComments);
        updatedSoftwareComments
            .txTitle(UPDATED_TX_TITLE)
            .content(UPDATED_CONTENT)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        SoftwareCommentsDTO softwareCommentsDTO = softwareCommentsMapper.toDto(updatedSoftwareComments);

        restSoftwareCommentsMockMvc.perform(put("/api/software-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareCommentsDTO)))
            .andExpect(status().isOk());

        // Validate the SoftwareComments in the database
        List<SoftwareComments> softwareCommentsList = softwareCommentsRepository.findAll();
        assertThat(softwareCommentsList).hasSize(databaseSizeBeforeUpdate);
        SoftwareComments testSoftwareComments = softwareCommentsList.get(softwareCommentsList.size() - 1);
        assertThat(testSoftwareComments.getTxTitle()).isEqualTo(UPDATED_TX_TITLE);
        assertThat(testSoftwareComments.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testSoftwareComments.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testSoftwareComments.getCreatTime()).isEqualTo(UPDATED_CREAT_TIME);
        assertThat(testSoftwareComments.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testSoftwareComments.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testSoftwareComments.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingSoftwareComments() throws Exception {
        int databaseSizeBeforeUpdate = softwareCommentsRepository.findAll().size();

        // Create the SoftwareComments
        SoftwareCommentsDTO softwareCommentsDTO = softwareCommentsMapper.toDto(softwareComments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoftwareCommentsMockMvc.perform(put("/api/software-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareCommentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SoftwareComments in the database
        List<SoftwareComments> softwareCommentsList = softwareCommentsRepository.findAll();
        assertThat(softwareCommentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSoftwareComments() throws Exception {
        // Initialize the database
        softwareCommentsRepository.saveAndFlush(softwareComments);

        int databaseSizeBeforeDelete = softwareCommentsRepository.findAll().size();

        // Delete the softwareComments
        restSoftwareCommentsMockMvc.perform(delete("/api/software-comments/{id}", softwareComments.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SoftwareComments> softwareCommentsList = softwareCommentsRepository.findAll();
        assertThat(softwareCommentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
