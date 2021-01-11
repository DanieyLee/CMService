package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.CmServiceApp;
import cn.hupig.www.code.cmservice.domain.SoftwareScore;
import cn.hupig.www.code.cmservice.repository.SoftwareScoreRepository;
import cn.hupig.www.code.cmservice.service.SoftwareScoreService;
import cn.hupig.www.code.cmservice.service.dto.SoftwareScoreDTO;
import cn.hupig.www.code.cmservice.service.mapper.SoftwareScoreMapper;

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
 * Integration tests for the {@link SoftwareScoreResource} REST controller.
 */
@SpringBootTest(classes = CmServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SoftwareScoreResourceIT {

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;

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
    private SoftwareScoreRepository softwareScoreRepository;

    @Autowired
    private SoftwareScoreMapper softwareScoreMapper;

    @Autowired
    private SoftwareScoreService softwareScoreService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoftwareScoreMockMvc;

    private SoftwareScore softwareScore;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoftwareScore createEntity(EntityManager em) {
        SoftwareScore softwareScore = new SoftwareScore()
            .score(DEFAULT_SCORE)
            .createUser(DEFAULT_CREATE_USER)
            .creatTime(DEFAULT_CREAT_TIME)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME)
            .note(DEFAULT_NOTE);
        return softwareScore;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoftwareScore createUpdatedEntity(EntityManager em) {
        SoftwareScore softwareScore = new SoftwareScore()
            .score(UPDATED_SCORE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        return softwareScore;
    }

    @BeforeEach
    public void initTest() {
        softwareScore = createEntity(em);
    }

    @Test
    @Transactional
    public void createSoftwareScore() throws Exception {
        int databaseSizeBeforeCreate = softwareScoreRepository.findAll().size();
        // Create the SoftwareScore
        SoftwareScoreDTO softwareScoreDTO = softwareScoreMapper.toDto(softwareScore);
        restSoftwareScoreMockMvc.perform(post("/api/software-scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareScoreDTO)))
            .andExpect(status().isCreated());

        // Validate the SoftwareScore in the database
        List<SoftwareScore> softwareScoreList = softwareScoreRepository.findAll();
        assertThat(softwareScoreList).hasSize(databaseSizeBeforeCreate + 1);
        SoftwareScore testSoftwareScore = softwareScoreList.get(softwareScoreList.size() - 1);
        assertThat(testSoftwareScore.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testSoftwareScore.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testSoftwareScore.getCreatTime()).isEqualTo(DEFAULT_CREAT_TIME);
        assertThat(testSoftwareScore.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testSoftwareScore.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testSoftwareScore.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createSoftwareScoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = softwareScoreRepository.findAll().size();

        // Create the SoftwareScore with an existing ID
        softwareScore.setId(1L);
        SoftwareScoreDTO softwareScoreDTO = softwareScoreMapper.toDto(softwareScore);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoftwareScoreMockMvc.perform(post("/api/software-scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareScoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SoftwareScore in the database
        List<SoftwareScore> softwareScoreList = softwareScoreRepository.findAll();
        assertThat(softwareScoreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = softwareScoreRepository.findAll().size();
        // set the field null
        softwareScore.setScore(null);

        // Create the SoftwareScore, which fails.
        SoftwareScoreDTO softwareScoreDTO = softwareScoreMapper.toDto(softwareScore);


        restSoftwareScoreMockMvc.perform(post("/api/software-scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareScoreDTO)))
            .andExpect(status().isBadRequest());

        List<SoftwareScore> softwareScoreList = softwareScoreRepository.findAll();
        assertThat(softwareScoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSoftwareScores() throws Exception {
        // Initialize the database
        softwareScoreRepository.saveAndFlush(softwareScore);

        // Get all the softwareScoreList
        restSoftwareScoreMockMvc.perform(get("/api/software-scores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(softwareScore.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].creatTime").value(hasItem(DEFAULT_CREAT_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getSoftwareScore() throws Exception {
        // Initialize the database
        softwareScoreRepository.saveAndFlush(softwareScore);

        // Get the softwareScore
        restSoftwareScoreMockMvc.perform(get("/api/software-scores/{id}", softwareScore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(softwareScore.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.creatTime").value(DEFAULT_CREAT_TIME.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingSoftwareScore() throws Exception {
        // Get the softwareScore
        restSoftwareScoreMockMvc.perform(get("/api/software-scores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSoftwareScore() throws Exception {
        // Initialize the database
        softwareScoreRepository.saveAndFlush(softwareScore);

        int databaseSizeBeforeUpdate = softwareScoreRepository.findAll().size();

        // Update the softwareScore
        SoftwareScore updatedSoftwareScore = softwareScoreRepository.findById(softwareScore.getId()).get();
        // Disconnect from session so that the updates on updatedSoftwareScore are not directly saved in db
        em.detach(updatedSoftwareScore);
        updatedSoftwareScore
            .score(UPDATED_SCORE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        SoftwareScoreDTO softwareScoreDTO = softwareScoreMapper.toDto(updatedSoftwareScore);

        restSoftwareScoreMockMvc.perform(put("/api/software-scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareScoreDTO)))
            .andExpect(status().isOk());

        // Validate the SoftwareScore in the database
        List<SoftwareScore> softwareScoreList = softwareScoreRepository.findAll();
        assertThat(softwareScoreList).hasSize(databaseSizeBeforeUpdate);
        SoftwareScore testSoftwareScore = softwareScoreList.get(softwareScoreList.size() - 1);
        assertThat(testSoftwareScore.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testSoftwareScore.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testSoftwareScore.getCreatTime()).isEqualTo(UPDATED_CREAT_TIME);
        assertThat(testSoftwareScore.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testSoftwareScore.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testSoftwareScore.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingSoftwareScore() throws Exception {
        int databaseSizeBeforeUpdate = softwareScoreRepository.findAll().size();

        // Create the SoftwareScore
        SoftwareScoreDTO softwareScoreDTO = softwareScoreMapper.toDto(softwareScore);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoftwareScoreMockMvc.perform(put("/api/software-scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareScoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SoftwareScore in the database
        List<SoftwareScore> softwareScoreList = softwareScoreRepository.findAll();
        assertThat(softwareScoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSoftwareScore() throws Exception {
        // Initialize the database
        softwareScoreRepository.saveAndFlush(softwareScore);

        int databaseSizeBeforeDelete = softwareScoreRepository.findAll().size();

        // Delete the softwareScore
        restSoftwareScoreMockMvc.perform(delete("/api/software-scores/{id}", softwareScore.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SoftwareScore> softwareScoreList = softwareScoreRepository.findAll();
        assertThat(softwareScoreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
