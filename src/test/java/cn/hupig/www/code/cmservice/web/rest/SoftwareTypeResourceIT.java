package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.CmServiceApp;
import cn.hupig.www.code.cmservice.domain.SoftwareType;
import cn.hupig.www.code.cmservice.repository.SoftwareTypeRepository;
import cn.hupig.www.code.cmservice.service.SoftwareTypeService;
import cn.hupig.www.code.cmservice.service.dto.SoftwareTypeDTO;
import cn.hupig.www.code.cmservice.service.mapper.SoftwareTypeMapper;

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
 * Integration tests for the {@link SoftwareTypeResource} REST controller.
 */
@SpringBootTest(classes = CmServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SoftwareTypeResourceIT {

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
    private SoftwareTypeRepository softwareTypeRepository;

    @Autowired
    private SoftwareTypeMapper softwareTypeMapper;

    @Autowired
    private SoftwareTypeService softwareTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoftwareTypeMockMvc;

    private SoftwareType softwareType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoftwareType createEntity(EntityManager em) {
        SoftwareType softwareType = new SoftwareType()
            .type(DEFAULT_TYPE)
            .createUser(DEFAULT_CREATE_USER)
            .creatTime(DEFAULT_CREAT_TIME)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME)
            .note(DEFAULT_NOTE);
        return softwareType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoftwareType createUpdatedEntity(EntityManager em) {
        SoftwareType softwareType = new SoftwareType()
            .type(UPDATED_TYPE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        return softwareType;
    }

    @BeforeEach
    public void initTest() {
        softwareType = createEntity(em);
    }

    @Test
    @Transactional
    public void createSoftwareType() throws Exception {
        int databaseSizeBeforeCreate = softwareTypeRepository.findAll().size();
        // Create the SoftwareType
        SoftwareTypeDTO softwareTypeDTO = softwareTypeMapper.toDto(softwareType);
        restSoftwareTypeMockMvc.perform(post("/api/software-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the SoftwareType in the database
        List<SoftwareType> softwareTypeList = softwareTypeRepository.findAll();
        assertThat(softwareTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SoftwareType testSoftwareType = softwareTypeList.get(softwareTypeList.size() - 1);
        assertThat(testSoftwareType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSoftwareType.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testSoftwareType.getCreatTime()).isEqualTo(DEFAULT_CREAT_TIME);
        assertThat(testSoftwareType.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testSoftwareType.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testSoftwareType.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createSoftwareTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = softwareTypeRepository.findAll().size();

        // Create the SoftwareType with an existing ID
        softwareType.setId(1L);
        SoftwareTypeDTO softwareTypeDTO = softwareTypeMapper.toDto(softwareType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoftwareTypeMockMvc.perform(post("/api/software-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SoftwareType in the database
        List<SoftwareType> softwareTypeList = softwareTypeRepository.findAll();
        assertThat(softwareTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = softwareTypeRepository.findAll().size();
        // set the field null
        softwareType.setType(null);

        // Create the SoftwareType, which fails.
        SoftwareTypeDTO softwareTypeDTO = softwareTypeMapper.toDto(softwareType);


        restSoftwareTypeMockMvc.perform(post("/api/software-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareTypeDTO)))
            .andExpect(status().isBadRequest());

        List<SoftwareType> softwareTypeList = softwareTypeRepository.findAll();
        assertThat(softwareTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSoftwareTypes() throws Exception {
        // Initialize the database
        softwareTypeRepository.saveAndFlush(softwareType);

        // Get all the softwareTypeList
        restSoftwareTypeMockMvc.perform(get("/api/software-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(softwareType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].creatTime").value(hasItem(DEFAULT_CREAT_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getSoftwareType() throws Exception {
        // Initialize the database
        softwareTypeRepository.saveAndFlush(softwareType);

        // Get the softwareType
        restSoftwareTypeMockMvc.perform(get("/api/software-types/{id}", softwareType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(softwareType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.creatTime").value(DEFAULT_CREAT_TIME.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingSoftwareType() throws Exception {
        // Get the softwareType
        restSoftwareTypeMockMvc.perform(get("/api/software-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSoftwareType() throws Exception {
        // Initialize the database
        softwareTypeRepository.saveAndFlush(softwareType);

        int databaseSizeBeforeUpdate = softwareTypeRepository.findAll().size();

        // Update the softwareType
        SoftwareType updatedSoftwareType = softwareTypeRepository.findById(softwareType.getId()).get();
        // Disconnect from session so that the updates on updatedSoftwareType are not directly saved in db
        em.detach(updatedSoftwareType);
        updatedSoftwareType
            .type(UPDATED_TYPE)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        SoftwareTypeDTO softwareTypeDTO = softwareTypeMapper.toDto(updatedSoftwareType);

        restSoftwareTypeMockMvc.perform(put("/api/software-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareTypeDTO)))
            .andExpect(status().isOk());

        // Validate the SoftwareType in the database
        List<SoftwareType> softwareTypeList = softwareTypeRepository.findAll();
        assertThat(softwareTypeList).hasSize(databaseSizeBeforeUpdate);
        SoftwareType testSoftwareType = softwareTypeList.get(softwareTypeList.size() - 1);
        assertThat(testSoftwareType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSoftwareType.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testSoftwareType.getCreatTime()).isEqualTo(UPDATED_CREAT_TIME);
        assertThat(testSoftwareType.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testSoftwareType.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testSoftwareType.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingSoftwareType() throws Exception {
        int databaseSizeBeforeUpdate = softwareTypeRepository.findAll().size();

        // Create the SoftwareType
        SoftwareTypeDTO softwareTypeDTO = softwareTypeMapper.toDto(softwareType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoftwareTypeMockMvc.perform(put("/api/software-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SoftwareType in the database
        List<SoftwareType> softwareTypeList = softwareTypeRepository.findAll();
        assertThat(softwareTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSoftwareType() throws Exception {
        // Initialize the database
        softwareTypeRepository.saveAndFlush(softwareType);

        int databaseSizeBeforeDelete = softwareTypeRepository.findAll().size();

        // Delete the softwareType
        restSoftwareTypeMockMvc.perform(delete("/api/software-types/{id}", softwareType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SoftwareType> softwareTypeList = softwareTypeRepository.findAll();
        assertThat(softwareTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
