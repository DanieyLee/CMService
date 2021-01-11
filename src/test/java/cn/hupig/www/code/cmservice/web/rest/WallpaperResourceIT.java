package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.CmServiceApp;
import cn.hupig.www.code.cmservice.domain.Wallpaper;
import cn.hupig.www.code.cmservice.repository.WallpaperRepository;
import cn.hupig.www.code.cmservice.service.WallpaperService;
import cn.hupig.www.code.cmservice.service.dto.WallpaperDTO;
import cn.hupig.www.code.cmservice.service.mapper.WallpaperMapper;

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
 * Integration tests for the {@link WallpaperResource} REST controller.
 */
@SpringBootTest(classes = CmServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WallpaperResourceIT {

    private static final String DEFAULT_IMAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_PIXEL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_PIXEL = "BBBBBBBBBB";

    private static final ImageType DEFAULT_IMAGE_TYPE = ImageType.PNG;
    private static final ImageType UPDATED_IMAGE_TYPE = ImageType.JPG;

    private static final Integer DEFAULT_VISITOR_VOLUME = 1;
    private static final Integer UPDATED_VISITOR_VOLUME = 2;

    private static final Boolean DEFAULT_IS_DOWNLOAD = false;
    private static final Boolean UPDATED_IS_DOWNLOAD = true;

    private static final Long DEFAULT_LIKE = 1L;
    private static final Long UPDATED_LIKE = 2L;

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
    private WallpaperRepository wallpaperRepository;

    @Autowired
    private WallpaperMapper wallpaperMapper;

    @Autowired
    private WallpaperService wallpaperService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWallpaperMockMvc;

    private Wallpaper wallpaper;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wallpaper createEntity(EntityManager em) {
        Wallpaper wallpaper = new Wallpaper()
            .imageName(DEFAULT_IMAGE_NAME)
            .imageUrl(DEFAULT_IMAGE_URL)
            .imagePixel(DEFAULT_IMAGE_PIXEL)
            .imageType(DEFAULT_IMAGE_TYPE)
            .visitorVolume(DEFAULT_VISITOR_VOLUME)
            .isDownload(DEFAULT_IS_DOWNLOAD)
            .like(DEFAULT_LIKE)
            .state(DEFAULT_STATE)
            .createUser(DEFAULT_CREATE_USER)
            .creatTime(DEFAULT_CREAT_TIME)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME)
            .note(DEFAULT_NOTE);
        return wallpaper;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wallpaper createUpdatedEntity(EntityManager em) {
        Wallpaper wallpaper = new Wallpaper()
            .imageName(UPDATED_IMAGE_NAME)
            .imageUrl(UPDATED_IMAGE_URL)
            .imagePixel(UPDATED_IMAGE_PIXEL)
            .imageType(UPDATED_IMAGE_TYPE)
            .visitorVolume(UPDATED_VISITOR_VOLUME)
            .isDownload(UPDATED_IS_DOWNLOAD)
            .like(UPDATED_LIKE)
            .state(UPDATED_STATE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        return wallpaper;
    }

    @BeforeEach
    public void initTest() {
        wallpaper = createEntity(em);
    }

    @Test
    @Transactional
    public void createWallpaper() throws Exception {
        int databaseSizeBeforeCreate = wallpaperRepository.findAll().size();
        // Create the Wallpaper
        WallpaperDTO wallpaperDTO = wallpaperMapper.toDto(wallpaper);
        restWallpaperMockMvc.perform(post("/api/wallpapers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wallpaperDTO)))
            .andExpect(status().isCreated());

        // Validate the Wallpaper in the database
        List<Wallpaper> wallpaperList = wallpaperRepository.findAll();
        assertThat(wallpaperList).hasSize(databaseSizeBeforeCreate + 1);
        Wallpaper testWallpaper = wallpaperList.get(wallpaperList.size() - 1);
        assertThat(testWallpaper.getImageName()).isEqualTo(DEFAULT_IMAGE_NAME);
        assertThat(testWallpaper.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testWallpaper.getImagePixel()).isEqualTo(DEFAULT_IMAGE_PIXEL);
        assertThat(testWallpaper.getImageType()).isEqualTo(DEFAULT_IMAGE_TYPE);
        assertThat(testWallpaper.getVisitorVolume()).isEqualTo(DEFAULT_VISITOR_VOLUME);
        assertThat(testWallpaper.isIsDownload()).isEqualTo(DEFAULT_IS_DOWNLOAD);
        assertThat(testWallpaper.getLike()).isEqualTo(DEFAULT_LIKE);
        assertThat(testWallpaper.isState()).isEqualTo(DEFAULT_STATE);
        assertThat(testWallpaper.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testWallpaper.getCreatTime()).isEqualTo(DEFAULT_CREAT_TIME);
        assertThat(testWallpaper.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testWallpaper.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testWallpaper.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createWallpaperWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wallpaperRepository.findAll().size();

        // Create the Wallpaper with an existing ID
        wallpaper.setId(1L);
        WallpaperDTO wallpaperDTO = wallpaperMapper.toDto(wallpaper);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWallpaperMockMvc.perform(post("/api/wallpapers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wallpaperDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wallpaper in the database
        List<Wallpaper> wallpaperList = wallpaperRepository.findAll();
        assertThat(wallpaperList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkImageUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = wallpaperRepository.findAll().size();
        // set the field null
        wallpaper.setImageUrl(null);

        // Create the Wallpaper, which fails.
        WallpaperDTO wallpaperDTO = wallpaperMapper.toDto(wallpaper);


        restWallpaperMockMvc.perform(post("/api/wallpapers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wallpaperDTO)))
            .andExpect(status().isBadRequest());

        List<Wallpaper> wallpaperList = wallpaperRepository.findAll();
        assertThat(wallpaperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWallpapers() throws Exception {
        // Initialize the database
        wallpaperRepository.saveAndFlush(wallpaper);

        // Get all the wallpaperList
        restWallpaperMockMvc.perform(get("/api/wallpapers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wallpaper.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageName").value(hasItem(DEFAULT_IMAGE_NAME)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].imagePixel").value(hasItem(DEFAULT_IMAGE_PIXEL)))
            .andExpect(jsonPath("$.[*].imageType").value(hasItem(DEFAULT_IMAGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].visitorVolume").value(hasItem(DEFAULT_VISITOR_VOLUME)))
            .andExpect(jsonPath("$.[*].isDownload").value(hasItem(DEFAULT_IS_DOWNLOAD.booleanValue())))
            .andExpect(jsonPath("$.[*].like").value(hasItem(DEFAULT_LIKE.intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.booleanValue())))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].creatTime").value(hasItem(DEFAULT_CREAT_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getWallpaper() throws Exception {
        // Initialize the database
        wallpaperRepository.saveAndFlush(wallpaper);

        // Get the wallpaper
        restWallpaperMockMvc.perform(get("/api/wallpapers/{id}", wallpaper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wallpaper.getId().intValue()))
            .andExpect(jsonPath("$.imageName").value(DEFAULT_IMAGE_NAME))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.imagePixel").value(DEFAULT_IMAGE_PIXEL))
            .andExpect(jsonPath("$.imageType").value(DEFAULT_IMAGE_TYPE.toString()))
            .andExpect(jsonPath("$.visitorVolume").value(DEFAULT_VISITOR_VOLUME))
            .andExpect(jsonPath("$.isDownload").value(DEFAULT_IS_DOWNLOAD.booleanValue()))
            .andExpect(jsonPath("$.like").value(DEFAULT_LIKE.intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.booleanValue()))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.creatTime").value(DEFAULT_CREAT_TIME.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingWallpaper() throws Exception {
        // Get the wallpaper
        restWallpaperMockMvc.perform(get("/api/wallpapers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWallpaper() throws Exception {
        // Initialize the database
        wallpaperRepository.saveAndFlush(wallpaper);

        int databaseSizeBeforeUpdate = wallpaperRepository.findAll().size();

        // Update the wallpaper
        Wallpaper updatedWallpaper = wallpaperRepository.findById(wallpaper.getId()).get();
        // Disconnect from session so that the updates on updatedWallpaper are not directly saved in db
        em.detach(updatedWallpaper);
        updatedWallpaper
            .imageName(UPDATED_IMAGE_NAME)
            .imageUrl(UPDATED_IMAGE_URL)
            .imagePixel(UPDATED_IMAGE_PIXEL)
            .imageType(UPDATED_IMAGE_TYPE)
            .visitorVolume(UPDATED_VISITOR_VOLUME)
            .isDownload(UPDATED_IS_DOWNLOAD)
            .like(UPDATED_LIKE)
            .state(UPDATED_STATE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        WallpaperDTO wallpaperDTO = wallpaperMapper.toDto(updatedWallpaper);

        restWallpaperMockMvc.perform(put("/api/wallpapers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wallpaperDTO)))
            .andExpect(status().isOk());

        // Validate the Wallpaper in the database
        List<Wallpaper> wallpaperList = wallpaperRepository.findAll();
        assertThat(wallpaperList).hasSize(databaseSizeBeforeUpdate);
        Wallpaper testWallpaper = wallpaperList.get(wallpaperList.size() - 1);
        assertThat(testWallpaper.getImageName()).isEqualTo(UPDATED_IMAGE_NAME);
        assertThat(testWallpaper.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testWallpaper.getImagePixel()).isEqualTo(UPDATED_IMAGE_PIXEL);
        assertThat(testWallpaper.getImageType()).isEqualTo(UPDATED_IMAGE_TYPE);
        assertThat(testWallpaper.getVisitorVolume()).isEqualTo(UPDATED_VISITOR_VOLUME);
        assertThat(testWallpaper.isIsDownload()).isEqualTo(UPDATED_IS_DOWNLOAD);
        assertThat(testWallpaper.getLike()).isEqualTo(UPDATED_LIKE);
        assertThat(testWallpaper.isState()).isEqualTo(UPDATED_STATE);
        assertThat(testWallpaper.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testWallpaper.getCreatTime()).isEqualTo(UPDATED_CREAT_TIME);
        assertThat(testWallpaper.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testWallpaper.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testWallpaper.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingWallpaper() throws Exception {
        int databaseSizeBeforeUpdate = wallpaperRepository.findAll().size();

        // Create the Wallpaper
        WallpaperDTO wallpaperDTO = wallpaperMapper.toDto(wallpaper);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWallpaperMockMvc.perform(put("/api/wallpapers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wallpaperDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wallpaper in the database
        List<Wallpaper> wallpaperList = wallpaperRepository.findAll();
        assertThat(wallpaperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWallpaper() throws Exception {
        // Initialize the database
        wallpaperRepository.saveAndFlush(wallpaper);

        int databaseSizeBeforeDelete = wallpaperRepository.findAll().size();

        // Delete the wallpaper
        restWallpaperMockMvc.perform(delete("/api/wallpapers/{id}", wallpaper.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wallpaper> wallpaperList = wallpaperRepository.findAll();
        assertThat(wallpaperList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
