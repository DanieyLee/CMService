package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.CmServiceApp;
import cn.hupig.www.code.cmservice.domain.ArticleComment;
import cn.hupig.www.code.cmservice.repository.ArticleCommentRepository;
import cn.hupig.www.code.cmservice.service.ArticleCommentService;
import cn.hupig.www.code.cmservice.service.dto.ArticleCommentDTO;
import cn.hupig.www.code.cmservice.service.mapper.ArticleCommentMapper;

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
 * Integration tests for the {@link ArticleCommentResource} REST controller.
 */
@SpringBootTest(classes = CmServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArticleCommentResourceIT {

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
    private ArticleCommentRepository articleCommentRepository;

    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    @Autowired
    private ArticleCommentService articleCommentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleCommentMockMvc;

    private ArticleComment articleComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleComment createEntity(EntityManager em) {
        ArticleComment articleComment = new ArticleComment()
            .content(DEFAULT_CONTENT)
            .createUser(DEFAULT_CREATE_USER)
            .creatTime(DEFAULT_CREAT_TIME)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME)
            .note(DEFAULT_NOTE);
        return articleComment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleComment createUpdatedEntity(EntityManager em) {
        ArticleComment articleComment = new ArticleComment()
            .content(UPDATED_CONTENT)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        return articleComment;
    }

    @BeforeEach
    public void initTest() {
        articleComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticleComment() throws Exception {
        int databaseSizeBeforeCreate = articleCommentRepository.findAll().size();
        // Create the ArticleComment
        ArticleCommentDTO articleCommentDTO = articleCommentMapper.toDto(articleComment);
        restArticleCommentMockMvc.perform(post("/api/article-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the ArticleComment in the database
        List<ArticleComment> articleCommentList = articleCommentRepository.findAll();
        assertThat(articleCommentList).hasSize(databaseSizeBeforeCreate + 1);
        ArticleComment testArticleComment = articleCommentList.get(articleCommentList.size() - 1);
        assertThat(testArticleComment.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testArticleComment.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testArticleComment.getCreatTime()).isEqualTo(DEFAULT_CREAT_TIME);
        assertThat(testArticleComment.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testArticleComment.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testArticleComment.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createArticleCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articleCommentRepository.findAll().size();

        // Create the ArticleComment with an existing ID
        articleComment.setId(1L);
        ArticleCommentDTO articleCommentDTO = articleCommentMapper.toDto(articleComment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleCommentMockMvc.perform(post("/api/article-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleComment in the database
        List<ArticleComment> articleCommentList = articleCommentRepository.findAll();
        assertThat(articleCommentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllArticleComments() throws Exception {
        // Initialize the database
        articleCommentRepository.saveAndFlush(articleComment);

        // Get all the articleCommentList
        restArticleCommentMockMvc.perform(get("/api/article-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].creatTime").value(hasItem(DEFAULT_CREAT_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getArticleComment() throws Exception {
        // Initialize the database
        articleCommentRepository.saveAndFlush(articleComment);

        // Get the articleComment
        restArticleCommentMockMvc.perform(get("/api/article-comments/{id}", articleComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articleComment.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.creatTime").value(DEFAULT_CREAT_TIME.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingArticleComment() throws Exception {
        // Get the articleComment
        restArticleCommentMockMvc.perform(get("/api/article-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticleComment() throws Exception {
        // Initialize the database
        articleCommentRepository.saveAndFlush(articleComment);

        int databaseSizeBeforeUpdate = articleCommentRepository.findAll().size();

        // Update the articleComment
        ArticleComment updatedArticleComment = articleCommentRepository.findById(articleComment.getId()).get();
        // Disconnect from session so that the updates on updatedArticleComment are not directly saved in db
        em.detach(updatedArticleComment);
        updatedArticleComment
            .content(UPDATED_CONTENT)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        ArticleCommentDTO articleCommentDTO = articleCommentMapper.toDto(updatedArticleComment);

        restArticleCommentMockMvc.perform(put("/api/article-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleCommentDTO)))
            .andExpect(status().isOk());

        // Validate the ArticleComment in the database
        List<ArticleComment> articleCommentList = articleCommentRepository.findAll();
        assertThat(articleCommentList).hasSize(databaseSizeBeforeUpdate);
        ArticleComment testArticleComment = articleCommentList.get(articleCommentList.size() - 1);
        assertThat(testArticleComment.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testArticleComment.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testArticleComment.getCreatTime()).isEqualTo(UPDATED_CREAT_TIME);
        assertThat(testArticleComment.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testArticleComment.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testArticleComment.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingArticleComment() throws Exception {
        int databaseSizeBeforeUpdate = articleCommentRepository.findAll().size();

        // Create the ArticleComment
        ArticleCommentDTO articleCommentDTO = articleCommentMapper.toDto(articleComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleCommentMockMvc.perform(put("/api/article-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleComment in the database
        List<ArticleComment> articleCommentList = articleCommentRepository.findAll();
        assertThat(articleCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArticleComment() throws Exception {
        // Initialize the database
        articleCommentRepository.saveAndFlush(articleComment);

        int databaseSizeBeforeDelete = articleCommentRepository.findAll().size();

        // Delete the articleComment
        restArticleCommentMockMvc.perform(delete("/api/article-comments/{id}", articleComment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArticleComment> articleCommentList = articleCommentRepository.findAll();
        assertThat(articleCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
