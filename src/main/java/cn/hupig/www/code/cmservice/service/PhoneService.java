package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.PhoneDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.Phone}.
 */
public interface PhoneService {

    /**
     * Save a phone.
     *
     * @param phoneDTO the entity to save.
     * @return the persisted entity.
     */
    PhoneDTO save(PhoneDTO phoneDTO);

    /**
     * Get all the phones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PhoneDTO> findAll(Pageable pageable);


    /**
     * Get the "id" phone.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PhoneDTO> findOne(Long id);

    /**
     * Delete the "id" phone.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
