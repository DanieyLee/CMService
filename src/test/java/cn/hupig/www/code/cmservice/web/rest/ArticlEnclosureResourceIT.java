package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.CmServiceApp;
import cn.hupig.www.code.cmservice.domain.ArticlEnclosure;
import cn.hupig.www.code.cmservice.repository.ArticlEnclosureRepository;
import cn.hupig.www.code.cmservice.service.ArticlEnclosureService;
import cn.hupig.www.code.cmservice.service.dto.ArticlEnclosureDTO;
import cn.hupig.www.code.cmservice.service.mapper.ArticlEnclosureMapper;

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

import cn.hupig.www.code.cmservice.domain.enumeration.FileType;
/**
 * Integration tests for the {@link ArticlEnclosureResource} REST controller.
 */
@SpringBootTest(classes = CmServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArticlEnclosureResourceIT {

    private static final String DEFAULT_ENCLOSURE_URL = "AAAAAAAAAA";
    private static final String UPDATED_ENCLOSURE_URL = "BBBBBBBBBB";

    private static final FileType DEFAULT_ENCLOSURE_TYPE = FileType.IMAGE;
    private static final FileType UPDATED_ENCLOSURE_TYPE = FileType.VIDEO;

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
    private ArticlEnclosureRepository articlEnclosureRepository;

    @Autowired
    private ArticlEnclosureMapper articlEnclosureMapper;

    @Autowired
    private ArticlEnclosureService articlEnclosureService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticlEnclosureMockMvc;

    private ArticlEnclosure articlEnclosure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticlEnclosure createEntity(EntityManager em) {
        ArticlEnclosure articlEnclosure = new ArticlEnclosure()
            .enclosureURL(DEFAULT_ENCLOSURE_URL)
            .enclosureType(DEFAULT_ENCLOSURE_TYPE)
            .createUser(DEFAULT_CREATE_USER)
            .creatTime(DEFAULT_CREAT_TIME)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME)
            .note(DEFAULT_NOTE);
        return articlEnclosure;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticlEnclosure createUpdatedEntity(EntityManager em) {
        ArticlEnclosure articlEnclosure = new ArticlEnclosure()
            .enclosureURL(UPDATED_ENCLOSURE_URL)
            .enclosureType(UPDATED_ENCLOSURE_TYPE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        return articlEnclosure;
    }

    @BeforeEach
    public void initTest() {
        articlEnclosure = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticlEnclosure() throws Exception {
        int databaseSizeBeforeCreate = articlEnclosureRepository.findAll().size();
        // Create the ArticlEnclosure
        ArticlEnclosureDTO articlEnclosureDTO = articlEnclosureMapper.toDto(articlEnclosure);
        restArticlEnclosureMockMvc.perform(post("/api/articl-enclosures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlEnclosureDTO)))
            .andExpect(status().isCreated());

        // Validate the ArticlEnclosure in the database
        List<ArticlEnclosure> articlEnclosureList = articlEnclosureRepository.findAll();
        assertThat(articlEnclosureList).hasSize(databaseSizeBeforeCreate + 1);
        ArticlEnclosure testArticlEnclosure = articlEnclosureList.get(articlEnclosureList.size() - 1);
        assertThat(testArticlEnclosure.getEnclosureURL()).isEqualTo(DEFAULT_ENCLOSURE_URL);
        assertThat(testArticlEnclosure.getEnclosureType()).isEqualTo(DEFAULT_ENCLOSURE_TYPE);
        assertThat(testArticlEnclosure.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testArticlEnclosure.getCreatTime()).isEqualTo(DEFAULT_CREAT_TIME);
        assertThat(testArticlEnclosure.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testArticlEnclosure.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testArticlEnclosure.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createArticlEnclosureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articlEnclosureRepository.findAll().size();

        // Create the ArticlEnclosure with an existing ID
        articlEnclosure.setId(1L);
        ArticlEnclosureDTO articlEnclosureDTO = articlEnclosureMapper.toDto(articlEnclosure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticlEnclosureMockMvc.perform(post("/api/articl-enclosures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlEnclosureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticlEnclosure in the database
        List<ArticlEnclosure> articlEnclosureList = articlEnclosureRepository.findAll();
        assertThat(articlEnclosureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllArticlEnclosures() throws Exception {
        // Initialize the database
        articlEnclosureRepository.saveAndFlush(articlEnclosure);

        // Get all the articlEnclosureList
        restArticlEnclosureMockMvc.perform(get("/api/articl-enclosures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articlEnclosure.getId().intValue())))
            .andExpect(jsonPath("$.[*].enclosureURL").value(hasItem(DEFAULT_ENCLOSURE_URL)))
            .andExpect(jsonPath("$.[*].enclosureType").value(hasItem(DEFAULT_ENCLOSURE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].creatTime").value(hasItem(DEFAULT_CREAT_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getArticlEnclosure() throws Exception {
        // Initialize the database
        articlEnclosureRepository.saveAndFlush(articlEnclosure);

        // Get the articlEnclosure
        restArticlEnclosureMockMvc.perform(get("/api/articl-enclosures/{id}", articlEnclosure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articlEnclosure.getId().intValue()))
            .andExpect(jsonPath("$.enclosureURL").value(DEFAULT_ENCLOSURE_URL))
            .andExpect(jsonPath("$.enclosureType").value(DEFAULT_ENCLOSURE_TYPE.toString()))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.creatTime").value(DEFAULT_CREAT_TIME.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingArticlEnclosure() throws Exception {
        // Get the articlEnclosure
        restArticlEnclosureMockMvc.perform(get("/api/articl-enclosures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticlEnclosure() throws Exception {
        // Initialize the database
        articlEnclosureRepository.saveAndFlush(articlEnclosure);

        int databaseSizeBeforeUpdate = articlEnclosureRepository.findAll().size();

        // Update the articlEnclosure
        ArticlEnclosure updatedArticlEnclosure = articlEnclosureRepository.findById(articlEnclosure.getId()).get();
        // Disconnect from session so that the updates on updatedArticlEnclosure are not directly saved in db
        em.detach(updatedArticlEnclosure);
        updatedArticlEnclosure
            .enclosureURL(UPDATED_ENCLOSURE_URL)
            .enclosureType(UPDATED_ENCLOSURE_TYPE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        ArticlEnclosureDTO articlEnclosureDTO = articlEnclosureMapper.toDto(updatedArticlEnclosure);

        restArticlEnclosureMockMvc.perform(put("/api/articl-enclosures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlEnclosureDTO)))
            .andExpect(status().isOk());

        // Validate the ArticlEnclosure in the database
        List<ArticlEnclosure> articlEnclosureList = articlEnclosureRepository.findAll();
        assertThat(articlEnclosureList).hasSize(databaseSizeBeforeUpdate);
        ArticlEnclosure testArticlEnclosure = articlEnclosureList.get(articlEnclosureList.size() - 1);
        assertThat(testArticlEnclosure.getEnclosureURL()).isEqualTo(UPDATED_ENCLOSURE_URL);
        assertThat(testArticlEnclosure.getEnclosureType()).isEqualTo(UPDATED_ENCLOSURE_TYPE);
        assertThat(testArticlEnclosure.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testArticlEnclosure.getCreatTime()).isEqualTo(UPDATED_CREAT_TIME);
        assertThat(testArticlEnclosure.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testArticlEnclosure.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testArticlEnclosure.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingArticlEnclosure() throws Exception {
        int databaseSizeBeforeUpdate = articlEnclosureRepository.findAll().size();

        // Create the ArticlEnclosure
        ArticlEnclosureDTO articlEnclosureDTO = articlEnclosureMapper.toDto(articlEnclosure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticlEnclosureMockMvc.perform(put("/api/articl-enclosures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlEnclosureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticlEnclosure in the database
        List<ArticlEnclosure> articlEnclosureList = articlEnclosureRepository.findAll();
        assertThat(articlEnclosureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArticlEnclosure() throws Exception {
        // Initialize the database
        articlEnclosureRepository.saveAndFlush(articlEnclosure);

        int databaseSizeBeforeDelete = articlEnclosureRepository.findAll().size();

        // Delete the articlEnclosure
        restArticlEnclosureMockMvc.perform(delete("/api/articl-enclosures/{id}", articlEnclosure.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArticlEnclosure> articlEnclosureList = articlEnclosureRepository.findAll();
        assertThat(articlEnclosureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
