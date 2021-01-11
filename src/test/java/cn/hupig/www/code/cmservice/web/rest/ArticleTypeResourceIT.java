package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.CmServiceApp;
import cn.hupig.www.code.cmservice.domain.ArticleType;
import cn.hupig.www.code.cmservice.repository.ArticleTypeRepository;
import cn.hupig.www.code.cmservice.service.ArticleTypeService;
import cn.hupig.www.code.cmservice.service.dto.ArticleTypeDTO;
import cn.hupig.www.code.cmservice.service.mapper.ArticleTypeMapper;

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
 * Integration tests for the {@link ArticleTypeResource} REST controller.
 */
@SpringBootTest(classes = CmServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArticleTypeResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

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
    private ArticleTypeRepository articleTypeRepository;

    @Autowired
    private ArticleTypeMapper articleTypeMapper;

    @Autowired
    private ArticleTypeService articleTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleTypeMockMvc;

    private ArticleType articleType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleType createEntity(EntityManager em) {
        ArticleType articleType = new ArticleType()
            .type(DEFAULT_TYPE)
            .createUser(DEFAULT_CREATE_USER)
            .creatTime(DEFAULT_CREAT_TIME)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME)
            .note(DEFAULT_NOTE);
        return articleType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleType createUpdatedEntity(EntityManager em) {
        ArticleType articleType = new ArticleType()
            .type(UPDATED_TYPE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        return articleType;
    }

    @BeforeEach
    public void initTest() {
        articleType = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticleType() throws Exception {
        int databaseSizeBeforeCreate = articleTypeRepository.findAll().size();
        // Create the ArticleType
        ArticleTypeDTO articleTypeDTO = articleTypeMapper.toDto(articleType);
        restArticleTypeMockMvc.perform(post("/api/article-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ArticleType in the database
        List<ArticleType> articleTypeList = articleTypeRepository.findAll();
        assertThat(articleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ArticleType testArticleType = articleTypeList.get(articleTypeList.size() - 1);
        assertThat(testArticleType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testArticleType.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testArticleType.getCreatTime()).isEqualTo(DEFAULT_CREAT_TIME);
        assertThat(testArticleType.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testArticleType.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testArticleType.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createArticleTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articleTypeRepository.findAll().size();

        // Create the ArticleType with an existing ID
        articleType.setId(1L);
        ArticleTypeDTO articleTypeDTO = articleTypeMapper.toDto(articleType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleTypeMockMvc.perform(post("/api/article-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleType in the database
        List<ArticleType> articleTypeList = articleTypeRepository.findAll();
        assertThat(articleTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleTypeRepository.findAll().size();
        // set the field null
        articleType.setType(null);

        // Create the ArticleType, which fails.
        ArticleTypeDTO articleTypeDTO = articleTypeMapper.toDto(articleType);


        restArticleTypeMockMvc.perform(post("/api/article-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ArticleType> articleTypeList = articleTypeRepository.findAll();
        assertThat(articleTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArticleTypes() throws Exception {
        // Initialize the database
        articleTypeRepository.saveAndFlush(articleType);

        // Get all the articleTypeList
        restArticleTypeMockMvc.perform(get("/api/article-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].creatTime").value(hasItem(DEFAULT_CREAT_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getArticleType() throws Exception {
        // Initialize the database
        articleTypeRepository.saveAndFlush(articleType);

        // Get the articleType
        restArticleTypeMockMvc.perform(get("/api/article-types/{id}", articleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articleType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.creatTime").value(DEFAULT_CREAT_TIME.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingArticleType() throws Exception {
        // Get the articleType
        restArticleTypeMockMvc.perform(get("/api/article-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticleType() throws Exception {
        // Initialize the database
        articleTypeRepository.saveAndFlush(articleType);

        int databaseSizeBeforeUpdate = articleTypeRepository.findAll().size();

        // Update the articleType
        ArticleType updatedArticleType = articleTypeRepository.findById(articleType.getId()).get();
        // Disconnect from session so that the updates on updatedArticleType are not directly saved in db
        em.detach(updatedArticleType);
        updatedArticleType
            .type(UPDATED_TYPE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        ArticleTypeDTO articleTypeDTO = articleTypeMapper.toDto(updatedArticleType);

        restArticleTypeMockMvc.perform(put("/api/article-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ArticleType in the database
        List<ArticleType> articleTypeList = articleTypeRepository.findAll();
        assertThat(articleTypeList).hasSize(databaseSizeBeforeUpdate);
        ArticleType testArticleType = articleTypeList.get(articleTypeList.size() - 1);
        assertThat(testArticleType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testArticleType.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testArticleType.getCreatTime()).isEqualTo(UPDATED_CREAT_TIME);
        assertThat(testArticleType.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testArticleType.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testArticleType.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingArticleType() throws Exception {
        int databaseSizeBeforeUpdate = articleTypeRepository.findAll().size();

        // Create the ArticleType
        ArticleTypeDTO articleTypeDTO = articleTypeMapper.toDto(articleType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleTypeMockMvc.perform(put("/api/article-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleType in the database
        List<ArticleType> articleTypeList = articleTypeRepository.findAll();
        assertThat(articleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArticleType() throws Exception {
        // Initialize the database
        articleTypeRepository.saveAndFlush(articleType);

        int databaseSizeBeforeDelete = articleTypeRepository.findAll().size();

        // Delete the articleType
        restArticleTypeMockMvc.perform(delete("/api/article-types/{id}", articleType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArticleType> articleTypeList = articleTypeRepository.findAll();
        assertThat(articleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
