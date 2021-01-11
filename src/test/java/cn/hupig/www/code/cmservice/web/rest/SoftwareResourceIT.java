package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.CmServiceApp;
import cn.hupig.www.code.cmservice.domain.Software;
import cn.hupig.www.code.cmservice.repository.SoftwareRepository;
import cn.hupig.www.code.cmservice.service.SoftwareService;
import cn.hupig.www.code.cmservice.service.dto.SoftwareDTO;
import cn.hupig.www.code.cmservice.service.mapper.SoftwareMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cn.hupig.www.code.cmservice.domain.enumeration.SystemType;
/**
 * Integration tests for the {@link SoftwareResource} REST controller.
 */
@SpringBootTest(classes = CmServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SoftwareResourceIT {

    private static final Boolean DEFAULT_STARS = false;
    private static final Boolean UPDATED_STARS = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXPLAIN = "AAAAAAAAAA";
    private static final String UPDATED_EXPLAIN = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SOFTWARE_ICO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SOFTWARE_ICO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SOFTWARE_ICO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SOFTWARE_ICO_CONTENT_TYPE = "image/png";

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;

    private static final Long DEFAULT_SIZE = 1L;
    private static final Long UPDATED_SIZE = 2L;

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final SystemType DEFAULT_APPLY_SYSTEM = SystemType.WIN;
    private static final SystemType UPDATED_APPLY_SYSTEM = SystemType.LINUX;

    private static final Boolean DEFAULT_SHOW = false;
    private static final Boolean UPDATED_SHOW = true;

    private static final Boolean DEFAULT_ALLOW = false;
    private static final Boolean UPDATED_ALLOW = true;

    private static final String DEFAULT_DOWNLOAD_URL = "AAAAAAAAAA";
    private static final String UPDATED_DOWNLOAD_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_DOWNLOAD_NUMBER = 1L;
    private static final Long UPDATED_DOWNLOAD_NUMBER = 2L;

    private static final Long DEFAULT_BROWSE_NUMBER = 1L;
    private static final Long UPDATED_BROWSE_NUMBER = 2L;

    private static final Boolean DEFAULT_STATE = false;
    private static final Boolean UPDATED_STATE = true;

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
    private SoftwareRepository softwareRepository;

    @Autowired
    private SoftwareMapper softwareMapper;

    @Autowired
    private SoftwareService softwareService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoftwareMockMvc;

    private Software software;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Software createEntity(EntityManager em) {
        Software software = new Software()
            .stars(DEFAULT_STARS)
            .name(DEFAULT_NAME)
            .explain(DEFAULT_EXPLAIN)
            .softwareICO(DEFAULT_SOFTWARE_ICO)
            .softwareICOContentType(DEFAULT_SOFTWARE_ICO_CONTENT_TYPE)
            .score(DEFAULT_SCORE)
            .size(DEFAULT_SIZE)
            .version(DEFAULT_VERSION)
            .applySystem(DEFAULT_APPLY_SYSTEM)
            .show(DEFAULT_SHOW)
            .allow(DEFAULT_ALLOW)
            .downloadUrl(DEFAULT_DOWNLOAD_URL)
            .downloadNumber(DEFAULT_DOWNLOAD_NUMBER)
            .browseNumber(DEFAULT_BROWSE_NUMBER)
            .state(DEFAULT_STATE)
            .createUser(DEFAULT_CREATE_USER)
            .creatTime(DEFAULT_CREAT_TIME)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME)
            .note(DEFAULT_NOTE);
        return software;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Software createUpdatedEntity(EntityManager em) {
        Software software = new Software()
            .stars(UPDATED_STARS)
            .name(UPDATED_NAME)
            .explain(UPDATED_EXPLAIN)
            .softwareICO(UPDATED_SOFTWARE_ICO)
            .softwareICOContentType(UPDATED_SOFTWARE_ICO_CONTENT_TYPE)
            .score(UPDATED_SCORE)
            .size(UPDATED_SIZE)
            .version(UPDATED_VERSION)
            .applySystem(UPDATED_APPLY_SYSTEM)
            .show(UPDATED_SHOW)
            .allow(UPDATED_ALLOW)
            .downloadUrl(UPDATED_DOWNLOAD_URL)
            .downloadNumber(UPDATED_DOWNLOAD_NUMBER)
            .browseNumber(UPDATED_BROWSE_NUMBER)
            .state(UPDATED_STATE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        return software;
    }

    @BeforeEach
    public void initTest() {
        software = createEntity(em);
    }

    @Test
    @Transactional
    public void createSoftware() throws Exception {
        int databaseSizeBeforeCreate = softwareRepository.findAll().size();
        // Create the Software
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);
        restSoftwareMockMvc.perform(post("/api/software")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isCreated());

        // Validate the Software in the database
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeCreate + 1);
        Software testSoftware = softwareList.get(softwareList.size() - 1);
        assertThat(testSoftware.isStars()).isEqualTo(DEFAULT_STARS);
        assertThat(testSoftware.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSoftware.getExplain()).isEqualTo(DEFAULT_EXPLAIN);
        assertThat(testSoftware.getSoftwareICO()).isEqualTo(DEFAULT_SOFTWARE_ICO);
        assertThat(testSoftware.getSoftwareICOContentType()).isEqualTo(DEFAULT_SOFTWARE_ICO_CONTENT_TYPE);
        assertThat(testSoftware.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testSoftware.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testSoftware.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testSoftware.getApplySystem()).isEqualTo(DEFAULT_APPLY_SYSTEM);
        assertThat(testSoftware.isShow()).isEqualTo(DEFAULT_SHOW);
        assertThat(testSoftware.isAllow()).isEqualTo(DEFAULT_ALLOW);
        assertThat(testSoftware.getDownloadUrl()).isEqualTo(DEFAULT_DOWNLOAD_URL);
        assertThat(testSoftware.getDownloadNumber()).isEqualTo(DEFAULT_DOWNLOAD_NUMBER);
        assertThat(testSoftware.getBrowseNumber()).isEqualTo(DEFAULT_BROWSE_NUMBER);
        assertThat(testSoftware.isState()).isEqualTo(DEFAULT_STATE);
        assertThat(testSoftware.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testSoftware.getCreatTime()).isEqualTo(DEFAULT_CREAT_TIME);
        assertThat(testSoftware.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testSoftware.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testSoftware.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createSoftwareWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = softwareRepository.findAll().size();

        // Create the Software with an existing ID
        software.setId(1L);
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoftwareMockMvc.perform(post("/api/software")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Software in the database
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = softwareRepository.findAll().size();
        // set the field null
        software.setName(null);

        // Create the Software, which fails.
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);


        restSoftwareMockMvc.perform(post("/api/software")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList
        restSoftwareMockMvc.perform(get("/api/software?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(software.getId().intValue())))
            .andExpect(jsonPath("$.[*].stars").value(hasItem(DEFAULT_STARS.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].explain").value(hasItem(DEFAULT_EXPLAIN)))
            .andExpect(jsonPath("$.[*].softwareICOContentType").value(hasItem(DEFAULT_SOFTWARE_ICO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].softwareICO").value(hasItem(Base64Utils.encodeToString(DEFAULT_SOFTWARE_ICO))))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].applySystem").value(hasItem(DEFAULT_APPLY_SYSTEM.toString())))
            .andExpect(jsonPath("$.[*].show").value(hasItem(DEFAULT_SHOW.booleanValue())))
            .andExpect(jsonPath("$.[*].allow").value(hasItem(DEFAULT_ALLOW.booleanValue())))
            .andExpect(jsonPath("$.[*].downloadUrl").value(hasItem(DEFAULT_DOWNLOAD_URL)))
            .andExpect(jsonPath("$.[*].downloadNumber").value(hasItem(DEFAULT_DOWNLOAD_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].browseNumber").value(hasItem(DEFAULT_BROWSE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.booleanValue())))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].creatTime").value(hasItem(DEFAULT_CREAT_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get the software
        restSoftwareMockMvc.perform(get("/api/software/{id}", software.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(software.getId().intValue()))
            .andExpect(jsonPath("$.stars").value(DEFAULT_STARS.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.explain").value(DEFAULT_EXPLAIN))
            .andExpect(jsonPath("$.softwareICOContentType").value(DEFAULT_SOFTWARE_ICO_CONTENT_TYPE))
            .andExpect(jsonPath("$.softwareICO").value(Base64Utils.encodeToString(DEFAULT_SOFTWARE_ICO)))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.applySystem").value(DEFAULT_APPLY_SYSTEM.toString()))
            .andExpect(jsonPath("$.show").value(DEFAULT_SHOW.booleanValue()))
            .andExpect(jsonPath("$.allow").value(DEFAULT_ALLOW.booleanValue()))
            .andExpect(jsonPath("$.downloadUrl").value(DEFAULT_DOWNLOAD_URL))
            .andExpect(jsonPath("$.downloadNumber").value(DEFAULT_DOWNLOAD_NUMBER.intValue()))
            .andExpect(jsonPath("$.browseNumber").value(DEFAULT_BROWSE_NUMBER.intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.booleanValue()))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.creatTime").value(DEFAULT_CREAT_TIME.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingSoftware() throws Exception {
        // Get the software
        restSoftwareMockMvc.perform(get("/api/software/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        int databaseSizeBeforeUpdate = softwareRepository.findAll().size();

        // Update the software
        Software updatedSoftware = softwareRepository.findById(software.getId()).get();
        // Disconnect from session so that the updates on updatedSoftware are not directly saved in db
        em.detach(updatedSoftware);
        updatedSoftware
            .stars(UPDATED_STARS)
            .name(UPDATED_NAME)
            .explain(UPDATED_EXPLAIN)
            .softwareICO(UPDATED_SOFTWARE_ICO)
            .softwareICOContentType(UPDATED_SOFTWARE_ICO_CONTENT_TYPE)
            .score(UPDATED_SCORE)
            .size(UPDATED_SIZE)
            .version(UPDATED_VERSION)
            .applySystem(UPDATED_APPLY_SYSTEM)
            .show(UPDATED_SHOW)
            .allow(UPDATED_ALLOW)
            .downloadUrl(UPDATED_DOWNLOAD_URL)
            .downloadNumber(UPDATED_DOWNLOAD_NUMBER)
            .browseNumber(UPDATED_BROWSE_NUMBER)
            .state(UPDATED_STATE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        SoftwareDTO softwareDTO = softwareMapper.toDto(updatedSoftware);

        restSoftwareMockMvc.perform(put("/api/software")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isOk());

        // Validate the Software in the database
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeUpdate);
        Software testSoftware = softwareList.get(softwareList.size() - 1);
        assertThat(testSoftware.isStars()).isEqualTo(UPDATED_STARS);
        assertThat(testSoftware.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSoftware.getExplain()).isEqualTo(UPDATED_EXPLAIN);
        assertThat(testSoftware.getSoftwareICO()).isEqualTo(UPDATED_SOFTWARE_ICO);
        assertThat(testSoftware.getSoftwareICOContentType()).isEqualTo(UPDATED_SOFTWARE_ICO_CONTENT_TYPE);
        assertThat(testSoftware.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testSoftware.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testSoftware.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testSoftware.getApplySystem()).isEqualTo(UPDATED_APPLY_SYSTEM);
        assertThat(testSoftware.isShow()).isEqualTo(UPDATED_SHOW);
        assertThat(testSoftware.isAllow()).isEqualTo(UPDATED_ALLOW);
        assertThat(testSoftware.getDownloadUrl()).isEqualTo(UPDATED_DOWNLOAD_URL);
        assertThat(testSoftware.getDownloadNumber()).isEqualTo(UPDATED_DOWNLOAD_NUMBER);
        assertThat(testSoftware.getBrowseNumber()).isEqualTo(UPDATED_BROWSE_NUMBER);
        assertThat(testSoftware.isState()).isEqualTo(UPDATED_STATE);
        assertThat(testSoftware.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testSoftware.getCreatTime()).isEqualTo(UPDATED_CREAT_TIME);
        assertThat(testSoftware.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testSoftware.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testSoftware.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingSoftware() throws Exception {
        int databaseSizeBeforeUpdate = softwareRepository.findAll().size();

        // Create the Software
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoftwareMockMvc.perform(put("/api/software")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Software in the database
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        int databaseSizeBeforeDelete = softwareRepository.findAll().size();

        // Delete the software
        restSoftwareMockMvc.perform(delete("/api/software/{id}", software.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
