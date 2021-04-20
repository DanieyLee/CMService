package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.CmServiceApp;
import cn.hupig.www.code.cmservice.domain.Phone;
import cn.hupig.www.code.cmservice.repository.PhoneRepository;
import cn.hupig.www.code.cmservice.service.PhoneService;
import cn.hupig.www.code.cmservice.service.dto.PhoneDTO;
import cn.hupig.www.code.cmservice.service.mapper.PhoneMapper;

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
 * Integration tests for the {@link PhoneResource} REST controller.
 */
@SpringBootTest(classes = CmServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PhoneResourceIT {

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final Instant DEFAULT_EFFECTIVE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EFFECTIVE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SEND_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SEND_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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
    private PhoneRepository phoneRepository;

    @Autowired
    private PhoneMapper phoneMapper;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhoneMockMvc;

    private Phone phone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phone createEntity(EntityManager em) {
        Phone phone = new Phone()
            .phone(DEFAULT_PHONE)
            .code(DEFAULT_CODE)
            .effectiveTime(DEFAULT_EFFECTIVE_TIME)
            .sendTime(DEFAULT_SEND_TIME)
            .createUser(DEFAULT_CREATE_USER)
            .creatTime(DEFAULT_CREAT_TIME)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME)
            .note(DEFAULT_NOTE);
        return phone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phone createUpdatedEntity(EntityManager em) {
        Phone phone = new Phone()
            .phone(UPDATED_PHONE)
            .code(UPDATED_CODE)
            .effectiveTime(UPDATED_EFFECTIVE_TIME)
            .sendTime(UPDATED_SEND_TIME)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        return phone;
    }

    @BeforeEach
    public void initTest() {
        phone = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhone() throws Exception {
        int databaseSizeBeforeCreate = phoneRepository.findAll().size();
        // Create the Phone
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);
        restPhoneMockMvc.perform(post("/api/phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneDTO)))
            .andExpect(status().isCreated());

        // Validate the Phone in the database
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeCreate + 1);
        Phone testPhone = phoneList.get(phoneList.size() - 1);
        assertThat(testPhone.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPhone.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPhone.getEffectiveTime()).isEqualTo(DEFAULT_EFFECTIVE_TIME);
        assertThat(testPhone.getSendTime()).isEqualTo(DEFAULT_SEND_TIME);
        assertThat(testPhone.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testPhone.getCreatTime()).isEqualTo(DEFAULT_CREAT_TIME);
        assertThat(testPhone.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testPhone.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testPhone.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createPhoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phoneRepository.findAll().size();

        // Create the Phone with an existing ID
        phone.setId(1L);
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhoneMockMvc.perform(post("/api/phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Phone in the database
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPhones() throws Exception {
        // Initialize the database
        phoneRepository.saveAndFlush(phone);

        // Get all the phoneList
        restPhoneMockMvc.perform(get("/api/phones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phone.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].effectiveTime").value(hasItem(DEFAULT_EFFECTIVE_TIME.toString())))
            .andExpect(jsonPath("$.[*].sendTime").value(hasItem(DEFAULT_SEND_TIME.toString())))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].creatTime").value(hasItem(DEFAULT_CREAT_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getPhone() throws Exception {
        // Initialize the database
        phoneRepository.saveAndFlush(phone);

        // Get the phone
        restPhoneMockMvc.perform(get("/api/phones/{id}", phone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phone.getId().intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.effectiveTime").value(DEFAULT_EFFECTIVE_TIME.toString()))
            .andExpect(jsonPath("$.sendTime").value(DEFAULT_SEND_TIME.toString()))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.creatTime").value(DEFAULT_CREAT_TIME.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingPhone() throws Exception {
        // Get the phone
        restPhoneMockMvc.perform(get("/api/phones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhone() throws Exception {
        // Initialize the database
        phoneRepository.saveAndFlush(phone);

        int databaseSizeBeforeUpdate = phoneRepository.findAll().size();

        // Update the phone
        Phone updatedPhone = phoneRepository.findById(phone.getId()).get();
        // Disconnect from session so that the updates on updatedPhone are not directly saved in db
        em.detach(updatedPhone);
        updatedPhone
            .phone(UPDATED_PHONE)
            .code(UPDATED_CODE)
            .effectiveTime(UPDATED_EFFECTIVE_TIME)
            .sendTime(UPDATED_SEND_TIME)
            .createUser(UPDATED_CREATE_USER)
            .creatTime(UPDATED_CREAT_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .note(UPDATED_NOTE);
        PhoneDTO phoneDTO = phoneMapper.toDto(updatedPhone);

        restPhoneMockMvc.perform(put("/api/phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneDTO)))
            .andExpect(status().isOk());

        // Validate the Phone in the database
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeUpdate);
        Phone testPhone = phoneList.get(phoneList.size() - 1);
        assertThat(testPhone.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPhone.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPhone.getEffectiveTime()).isEqualTo(UPDATED_EFFECTIVE_TIME);
        assertThat(testPhone.getSendTime()).isEqualTo(UPDATED_SEND_TIME);
        assertThat(testPhone.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testPhone.getCreatTime()).isEqualTo(UPDATED_CREAT_TIME);
        assertThat(testPhone.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testPhone.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testPhone.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingPhone() throws Exception {
        int databaseSizeBeforeUpdate = phoneRepository.findAll().size();

        // Create the Phone
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhoneMockMvc.perform(put("/api/phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Phone in the database
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhone() throws Exception {
        // Initialize the database
        phoneRepository.saveAndFlush(phone);

        int databaseSizeBeforeDelete = phoneRepository.findAll().size();

        // Delete the phone
        restPhoneMockMvc.perform(delete("/api/phones/{id}", phone.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
