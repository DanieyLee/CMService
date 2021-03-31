package cn.hupig.www.code.cmservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.hupig.www.code.cmservice.service.dto.PhoneDTO;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.Phone}.
 */
public interface Rewrite_PhoneService {

    /**
     * Send code information to phone.
     *
     * @param phoneDTO the entity to save.
     * @return the persisted entity.
     */
	void sendCode(String phoneNumber);
   
    /**
     * Get all the phones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PhoneDTO> findAll(Pageable pageable);

}
