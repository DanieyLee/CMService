package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.CmServiceApp;
import cn.hupig.www.code.cmservice.domain.SystemImage;
import cn.hupig.www.code.cmservice.repository.SystemImageRepository;
import cn.hupig.www.code.cmservice.service.SystemImageService;
import cn.hupig.www.code.cmservice.service.dto.SystemImageDTO;
import cn.hupig.www.code.cmservice.service.mapper.SystemImageMapper;

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

import cn.hupig.www.code.cmservice.domain.enumeration.ImageType;
/**
 * Integration tests for the {@link SystemImageResource} REST controller.
 */
@SpringBootTest(classes = CmServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SystemImageResourceIT {

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final ImageType DEFAULT_IMAGE_TYPE = ImageType.PNG;
    private static final ImageType UPDATED_IMAGE_TYPE = ImageType.JPG;

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
    private SystemImageRepository systemImageRepository;

    @Autowired
    private SystemImageMapper systemImageMapper;

    @Autowired
    private SystemImageService systemImageService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSystemImageMockMvc;

    private SystemImage systemImage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemImage createEntity(EntityManager em) {
        SystemImage systemImage = new SystemImage()
            .imageURL(DEFAULT_IMAGE_URL)
            .imageType(DEFAULT_IMAGE_TYPE)
            .createUser(DEFAULT_CREATE_USER)
            .creatTime(DEFAULT_CREAT_TIME)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME)
            .note(DEFAULT_NOTE);
        return systemImage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemImage createUpdatedEntity(EntityManager em) {
        SystemImage systemImage = new SystemImage()
            .imageURL(UPDATED_IMAGE_URL)
            .imageType(UPDATED_IMAGE_TYPE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        return systemImage;
    }

    @BeforeEach
    public void initTest() {
        systemImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemImage() throws Exception {
        int databaseSizeBeforeCreate = systemImageRepository.findAll().size();
        // Create the SystemImage
        SystemImageDTO systemImageDTO = systemImageMapper.toDto(systemImage);
        restSystemImageMockMvc.perform(post("/api/system-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemImageDTO)))
            .andExpect(status().isCreated());

        // Validate the SystemImage in the database
        List<SystemImage> systemImageList = systemImageRepository.findAll();
        assertThat(systemImageList).hasSize(databaseSizeBeforeCreate + 1);
        SystemImage testSystemImage = systemImageList.get(systemImageList.size() - 1);
        assertThat(testSystemImage.getImageURL()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testSystemImage.getImageType()).isEqualTo(DEFAULT_IMAGE_TYPE);
        assertThat(testSystemImage.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testSystemImage.getCreatTime()).isEqualTo(DEFAULT_CREAT_TIME);
        assertThat(testSystemImage.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testSystemImage.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testSystemImage.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createSystemImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemImageRepository.findAll().size();

        // Create the SystemImage with an existing ID
        systemImage.setId(1L);
        SystemImageDTO systemImageDTO = systemImageMapper.toDto(systemImage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemImageMockMvc.perform(post("/api/system-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemImage in the database
        List<SystemImage> systemImageList = systemImageRepository.findAll();
        assertThat(systemImageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkImageURLIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemImageRepository.findAll().size();
        // set the field null
        systemImage.setImageURL(null);

        // Create the SystemImage, which fails.
        SystemImageDTO systemImageDTO = systemImageMapper.toDto(systemImage);


        restSystemImageMockMvc.perform(post("/api/system-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemImageDTO)))
            .andExpect(status().isBadRequest());

        List<SystemImage> systemImageList = systemImageRepository.findAll();
        assertThat(systemImageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSystemImages() throws Exception {
        // Initialize the database
        systemImageRepository.saveAndFlush(systemImage);

        // Get all the systemImageList
        restSystemImageMockMvc.perform(get("/api/system-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageURL").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].imageType").value(hasItem(DEFAULT_IMAGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].creatTime").value(hasItem(DEFAULT_CREAT_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getSystemImage() throws Exception {
        // Initialize the database
        systemImageRepository.saveAndFlush(systemImage);

        // Get the systemImage
        restSystemImageMockMvc.perform(get("/api/system-images/{id}", systemImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(systemImage.getId().intValue()))
            .andExpect(jsonPath("$.imageURL").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.imageType").value(DEFAULT_IMAGE_TYPE.toString()))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.creatTime").value(DEFAULT_CREAT_TIME.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingSystemImage() throws Exception {
        // Get the systemImage
        restSystemImageMockMvc.perform(get("/api/system-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemImage() throws Exception {
        // Initialize the database
        systemImageRepository.saveAndFlush(systemImage);

        int databaseSizeBeforeUpdate = systemImageRepository.findAll().size();

        // Update the systemImage
        SystemImage updatedSystemImage = systemImageRepository.findById(systemImage.getId()).get();
        // Disconnect from session so that the updates on updatedSystemImage are not directly saved in db
        em.detach(updatedSystemImage);
        updatedSystemImage
            .imageURL(UPDATED_IMAGE_URL)
            .imageType(UPDATED_IMAGE_TYPE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        SystemImageDTO systemImageDTO = systemImageMapper.toDto(updatedSystemImage);

        restSystemImageMockMvc.perform(put("/api/system-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemImageDTO)))
            .andExpect(status().isOk());

        // Validate the SystemImage in the database
        List<SystemImage> systemImageList = systemImageRepository.findAll();
        assertThat(systemImageList).hasSize(databaseSizeBeforeUpdate);
        SystemImage testSystemImage = systemImageList.get(systemImageList.size() - 1);
        assertThat(testSystemImage.getImageURL()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSystemImage.getImageType()).isEqualTo(UPDATED_IMAGE_TYPE);
        assertThat(testSystemImage.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testSystemImage.getCreatTime()).isEqualTo(UPDATED_CREAT_TIME);
        assertThat(testSystemImage.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testSystemImage.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testSystemImage.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemImage() throws Exception {
        int databaseSizeBeforeUpdate = systemImageRepository.findAll().size();

        // Create the SystemImage
        SystemImageDTO systemImageDTO = systemImageMapper.toDto(systemImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemImageMockMvc.perform(put("/api/system-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemImage in the database
        List<SystemImage> systemImageList = systemImageRepository.findAll();
        assertThat(systemImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSystemImage() throws Exception {
        // Initialize the database
        systemImageRepository.saveAndFlush(systemImage);

        int databaseSizeBeforeDelete = systemImageRepository.findAll().size();

        // Delete the systemImage
        restSystemImageMockMvc.perform(delete("/api/system-images/{id}", systemImage.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SystemImage> systemImageList = systemImageRepository.findAll();
        assertThat(systemImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
