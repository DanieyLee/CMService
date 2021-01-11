package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.CmServiceApp;
import cn.hupig.www.code.cmservice.domain.KeyBox;
import cn.hupig.www.code.cmservice.repository.KeyBoxRepository;
import cn.hupig.www.code.cmservice.service.KeyBoxService;
import cn.hupig.www.code.cmservice.service.dto.KeyBoxDTO;
import cn.hupig.www.code.cmservice.service.mapper.KeyBoxMapper;

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
 * Integration tests for the {@link KeyBoxResource} REST controller.
 */
@SpringBootTest(classes = CmServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class KeyBoxResourceIT {

    private static final String DEFAULT_USER_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_USER_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EXPLAIN = "AAAAAAAAAA";
    private static final String UPDATED_EXPLAIN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPLAY = false;
    private static final Boolean UPDATED_DISPLAY = true;

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
    private KeyBoxRepository keyBoxRepository;

    @Autowired
    private KeyBoxMapper keyBoxMapper;

    @Autowired
    private KeyBoxService keyBoxService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKeyBoxMockMvc;

    private KeyBox keyBox;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeyBox createEntity(EntityManager em) {
        KeyBox keyBox = new KeyBox()
            .userAccount(DEFAULT_USER_ACCOUNT)
            .password(DEFAULT_PASSWORD)
            .secondPassword(DEFAULT_SECOND_PASSWORD)
            .loginAddress(DEFAULT_LOGIN_ADDRESS)
            .explain(DEFAULT_EXPLAIN)
            .display(DEFAULT_DISPLAY)
            .createUser(DEFAULT_CREATE_USER)
            .creatTime(DEFAULT_CREAT_TIME)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME)
            .note(DEFAULT_NOTE);
        return keyBox;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeyBox createUpdatedEntity(EntityManager em) {
        KeyBox keyBox = new KeyBox()
            .userAccount(UPDATED_USER_ACCOUNT)
            .password(UPDATED_PASSWORD)
            .secondPassword(UPDATED_SECOND_PASSWORD)
            .loginAddress(UPDATED_LOGIN_ADDRESS)
            .explain(UPDATED_EXPLAIN)
            .display(UPDATED_DISPLAY)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        return keyBox;
    }

    @BeforeEach
    public void initTest() {
        keyBox = createEntity(em);
    }

    @Test
    @Transactional
    public void createKeyBox() throws Exception {
        int databaseSizeBeforeCreate = keyBoxRepository.findAll().size();
        // Create the KeyBox
        KeyBoxDTO keyBoxDTO = keyBoxMapper.toDto(keyBox);
        restKeyBoxMockMvc.perform(post("/api/key-boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyBoxDTO)))
            .andExpect(status().isCreated());

        // Validate the KeyBox in the database
        List<KeyBox> keyBoxList = keyBoxRepository.findAll();
        assertThat(keyBoxList).hasSize(databaseSizeBeforeCreate + 1);
        KeyBox testKeyBox = keyBoxList.get(keyBoxList.size() - 1);
        assertThat(testKeyBox.getUserAccount()).isEqualTo(DEFAULT_USER_ACCOUNT);
        assertThat(testKeyBox.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testKeyBox.getSecondPassword()).isEqualTo(DEFAULT_SECOND_PASSWORD);
        assertThat(testKeyBox.getLoginAddress()).isEqualTo(DEFAULT_LOGIN_ADDRESS);
        assertThat(testKeyBox.getExplain()).isEqualTo(DEFAULT_EXPLAIN);
        assertThat(testKeyBox.isDisplay()).isEqualTo(DEFAULT_DISPLAY);
        assertThat(testKeyBox.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testKeyBox.getCreatTime()).isEqualTo(DEFAULT_CREAT_TIME);
        assertThat(testKeyBox.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testKeyBox.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testKeyBox.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createKeyBoxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = keyBoxRepository.findAll().size();

        // Create the KeyBox with an existing ID
        keyBox.setId(1L);
        KeyBoxDTO keyBoxDTO = keyBoxMapper.toDto(keyBox);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeyBoxMockMvc.perform(post("/api/key-boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyBoxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KeyBox in the database
        List<KeyBox> keyBoxList = keyBoxRepository.findAll();
        assertThat(keyBoxList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllKeyBoxes() throws Exception {
        // Initialize the database
        keyBoxRepository.saveAndFlush(keyBox);

        // Get all the keyBoxList
        restKeyBoxMockMvc.perform(get("/api/key-boxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keyBox.getId().intValue())))
            .andExpect(jsonPath("$.[*].userAccount").value(hasItem(DEFAULT_USER_ACCOUNT)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].secondPassword").value(hasItem(DEFAULT_SECOND_PASSWORD)))
            .andExpect(jsonPath("$.[*].loginAddress").value(hasItem(DEFAULT_LOGIN_ADDRESS)))
            .andExpect(jsonPath("$.[*].explain").value(hasItem(DEFAULT_EXPLAIN)))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.booleanValue())))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].creatTime").value(hasItem(DEFAULT_CREAT_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getKeyBox() throws Exception {
        // Initialize the database
        keyBoxRepository.saveAndFlush(keyBox);

        // Get the keyBox
        restKeyBoxMockMvc.perform(get("/api/key-boxes/{id}", keyBox.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(keyBox.getId().intValue()))
            .andExpect(jsonPath("$.userAccount").value(DEFAULT_USER_ACCOUNT))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.secondPassword").value(DEFAULT_SECOND_PASSWORD))
            .andExpect(jsonPath("$.loginAddress").value(DEFAULT_LOGIN_ADDRESS))
            .andExpect(jsonPath("$.explain").value(DEFAULT_EXPLAIN))
            .andExpect(jsonPath("$.display").value(DEFAULT_DISPLAY.booleanValue()))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.creatTime").value(DEFAULT_CREAT_TIME.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingKeyBox() throws Exception {
        // Get the keyBox
        restKeyBoxMockMvc.perform(get("/api/key-boxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKeyBox() throws Exception {
        // Initialize the database
        keyBoxRepository.saveAndFlush(keyBox);

        int databaseSizeBeforeUpdate = keyBoxRepository.findAll().size();

        // Update the keyBox
        KeyBox updatedKeyBox = keyBoxRepository.findById(keyBox.getId()).get();
        // Disconnect from session so that the updates on updatedKeyBox are not directly saved in db
        em.detach(updatedKeyBox);
        updatedKeyBox
            .userAccount(UPDATED_USER_ACCOUNT)
            .password(UPDATED_PASSWORD)
            .secondPassword(UPDATED_SECOND_PASSWORD)
            .loginAddress(UPDATED_LOGIN_ADDRESS)
            .explain(UPDATED_EXPLAIN)
            .display(UPDATED_DISPLAY)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        KeyBoxDTO keyBoxDTO = keyBoxMapper.toDto(updatedKeyBox);

        restKeyBoxMockMvc.perform(put("/api/key-boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyBoxDTO)))
            .andExpect(status().isOk());

        // Validate the KeyBox in the database
        List<KeyBox> keyBoxList = keyBoxRepository.findAll();
        assertThat(keyBoxList).hasSize(databaseSizeBeforeUpdate);
        KeyBox testKeyBox = keyBoxList.get(keyBoxList.size() - 1);
        assertThat(testKeyBox.getUserAccount()).isEqualTo(UPDATED_USER_ACCOUNT);
        assertThat(testKeyBox.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testKeyBox.getSecondPassword()).isEqualTo(UPDATED_SECOND_PASSWORD);
        assertThat(testKeyBox.getLoginAddress()).isEqualTo(UPDATED_LOGIN_ADDRESS);
        assertThat(testKeyBox.getExplain()).isEqualTo(UPDATED_EXPLAIN);
        assertThat(testKeyBox.isDisplay()).isEqualTo(UPDATED_DISPLAY);
        assertThat(testKeyBox.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testKeyBox.getCreatTime()).isEqualTo(UPDATED_CREAT_TIME);
        assertThat(testKeyBox.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testKeyBox.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testKeyBox.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingKeyBox() throws Exception {
        int databaseSizeBeforeUpdate = keyBoxRepository.findAll().size();

        // Create the KeyBox
        KeyBoxDTO keyBoxDTO = keyBoxMapper.toDto(keyBox);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeyBoxMockMvc.perform(put("/api/key-boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyBoxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KeyBox in the database
        List<KeyBox> keyBoxList = keyBoxRepository.findAll();
        assertThat(keyBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKeyBox() throws Exception {
        // Initialize the database
        keyBoxRepository.saveAndFlush(keyBox);

        int databaseSizeBeforeDelete = keyBoxRepository.findAll().size();

        // Delete the keyBox
        restKeyBoxMockMvc.perform(delete("/api/key-boxes/{id}", keyBox.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KeyBox> keyBoxList = keyBoxRepository.findAll();
        assertThat(keyBoxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
