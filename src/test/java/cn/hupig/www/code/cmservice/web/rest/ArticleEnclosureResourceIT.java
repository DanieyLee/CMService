package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.CmServiceApp;
import cn.hupig.www.code.cmservice.domain.ArticleEnclosure;
import cn.hupig.www.code.cmservice.repository.ArticleEnclosureRepository;
import cn.hupig.www.code.cmservice.service.ArticleEnclosureService;
import cn.hupig.www.code.cmservice.service.dto.ArticleEnclosureDTO;
import cn.hupig.www.code.cmservice.service.mapper.ArticleEnclosureMapper;

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
 * Integration tests for the {@link ArticleEnclosureResource} REST controller.
 */
@SpringBootTest(classes = CmServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArticleEnclosureResourceIT {

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
    private ArticleEnclosureRepository articleEnclosureRepository;

    @Autowired
    private ArticleEnclosureMapper articleEnclosureMapper;

    @Autowired
    private ArticleEnclosureService articleEnclosureService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleEnclosureMockMvc;

    private ArticleEnclosure articleEnclosure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleEnclosure createEntity(EntityManager em) {
        ArticleEnclosure articleEnclosure = new ArticleEnclosure()
            .enclosureURL(DEFAULT_ENCLOSURE_URL)
            .enclosureType(DEFAULT_ENCLOSURE_TYPE)
            .createUser(DEFAULT_CREATE_USER)
            .creatTime(DEFAULT_CREAT_TIME)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME)
            .note(DEFAULT_NOTE);
        return articleEnclosure;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleEnclosure createUpdatedEntity(EntityManager em) {
        ArticleEnclosure articleEnclosure = new ArticleEnclosure()
            .enclosureURL(UPDATED_ENCLOSURE_URL)
            .enclosureType(UPDATED_ENCLOSURE_TYPE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        return articleEnclosure;
    }

    @BeforeEach
    public void initTest() {
        articleEnclosure = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticleEnclosure() throws Exception {
        int databaseSizeBeforeCreate = articleEnclosureRepository.findAll().size();
        // Create the ArticleEnclosure
        ArticleEnclosureDTO articleEnclosureDTO = articleEnclosureMapper.toDto(articleEnclosure);
        restArticleEnclosureMockMvc.perform(post("/api/article-enclosures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleEnclosureDTO)))
            .andExpect(status().isCreated());

        // Validate the ArticleEnclosure in the database
        List<ArticleEnclosure> articleEnclosureList = articleEnclosureRepository.findAll();
        assertThat(articleEnclosureList).hasSize(databaseSizeBeforeCreate + 1);
        ArticleEnclosure testarticleEnclosure = articleEnclosureList.get(articleEnclosureList.size() - 1);
        assertThat(testarticleEnclosure.getEnclosureURL()).isEqualTo(DEFAULT_ENCLOSURE_URL);
        assertThat(testarticleEnclosure.getEnclosureType()).isEqualTo(DEFAULT_ENCLOSURE_TYPE);
        assertThat(testarticleEnclosure.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testarticleEnclosure.getCreatTime()).isEqualTo(DEFAULT_CREAT_TIME);
        assertThat(testarticleEnclosure.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testarticleEnclosure.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testarticleEnclosure.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createArticleEnclosureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articleEnclosureRepository.findAll().size();

        // Create the ArticleEnclosure with an existing ID
        articleEnclosure.setId(1L);
        ArticleEnclosureDTO articleEnclosureDTO = articleEnclosureMapper.toDto(articleEnclosure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleEnclosureMockMvc.perform(post("/api/article-enclosures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleEnclosureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleEnclosure in the database
        List<ArticleEnclosure> articleEnclosureList = articleEnclosureRepository.findAll();
        assertThat(articleEnclosureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllArticleEnclosures() throws Exception {
        // Initialize the database
        articleEnclosureRepository.saveAndFlush(articleEnclosure);

        // Get all the ArticleEnclosureList
        restArticleEnclosureMockMvc.perform(get("/api/article-enclosures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleEnclosure.getId().intValue())))
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
    public void getArticleEnclosure() throws Exception {
        // Initialize the database
        articleEnclosureRepository.saveAndFlush(articleEnclosure);

        // Get the ArticleEnclosure
        restArticleEnclosureMockMvc.perform(get("/api/article-enclosures/{id}", articleEnclosure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articleEnclosure.getId().intValue()))
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
    public void getNonExistingArticleEnclosure() throws Exception {
        // Get the ArticleEnclosure
    	restArticleEnclosureMockMvc.perform(get("/api/article-enclosures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatearticleEnclosure() throws Exception {
        // Initialize the database
        articleEnclosureRepository.saveAndFlush(articleEnclosure);

        int databaseSizeBeforeUpdate = articleEnclosureRepository.findAll().size();

        // Update the articleEnclosure
        ArticleEnclosure updatedarticleEnclosure = articleEnclosureRepository.findById(articleEnclosure.getId()).get();
        // Disconnect from session so that the updates on updatedarticleEnclosure are not directly saved in db
        em.detach(updatedarticleEnclosure);
        updatedarticleEnclosure
            .enclosureURL(UPDATED_ENCLOSURE_URL)
            .enclosureType(UPDATED_ENCLOSURE_TYPE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        ArticleEnclosureDTO articleEnclosureDTO = articleEnclosureMapper.toDto(updatedarticleEnclosure);

        restArticleEnclosureMockMvc.perform(put("/api/article-enclosures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleEnclosureDTO)))
            .andExpect(status().isOk());

        // Validate the articleEnclosure in the database
        List<ArticleEnclosure> articleEnclosureList = articleEnclosureRepository.findAll();
        assertThat(articleEnclosureList).hasSize(databaseSizeBeforeUpdate);
        ArticleEnclosure testarticleEnclosure = articleEnclosureList.get(articleEnclosureList.size() - 1);
        assertThat(testarticleEnclosure.getEnclosureURL()).isEqualTo(UPDATED_ENCLOSURE_URL);
        assertThat(testarticleEnclosure.getEnclosureType()).isEqualTo(UPDATED_ENCLOSURE_TYPE);
        assertThat(testarticleEnclosure.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testarticleEnclosure.getCreatTime()).isEqualTo(UPDATED_CREAT_TIME);
        assertThat(testarticleEnclosure.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testarticleEnclosure.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testarticleEnclosure.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingarticleEnclosure() throws Exception {
        int databaseSizeBeforeUpdate = articleEnclosureRepository.findAll().size();

        // Create the ArticleEnclosure
        ArticleEnclosureDTO articleEnclosureDTO = articleEnclosureMapper.toDto(articleEnclosure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleEnclosureMockMvc.perform(put("/api/article-enclosures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleEnclosureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleEnclosure in the database
        List<ArticleEnclosure> articleEnclosureList = articleEnclosureRepository.findAll();
        assertThat(articleEnclosureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletearticleEnclosure() throws Exception {
        // Initialize the database
        articleEnclosureRepository.saveAndFlush(articleEnclosure);

        int databaseSizeBeforeDelete = articleEnclosureRepository.findAll().size();

        // Delete the ArticleEnclosure
        restArticleEnclosureMockMvc.perform(delete("/api/article-enclosures/{id}", articleEnclosure.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArticleEnclosure> articleEnclosureList = articleEnclosureRepository.findAll();
        assertThat(articleEnclosureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
