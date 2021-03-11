package cn.hupig.www.code.cmservice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.hupig.www.code.cmservice.service.dto.SoftwareDTO;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.Software}.
 */
public interface Rewrite_SoftwareService {

    /**
     * Get all the software.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SoftwareDTO> findAll(Pageable pageable);
    
    /**
     * Get top the software.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SoftwareDTO> findTop();
    
    /**
     * Get the "id" software.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SoftwareDTO> findOne(Long id);
}
