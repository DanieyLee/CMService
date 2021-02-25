package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.SoftwareDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.Software}.
 */
public interface SoftwareService {

    /**
     * Save a software.
     *
     * @param softwareDTO the entity to save.
     * @return the persisted entity.
     */
    SoftwareDTO save(SoftwareDTO softwareDTO);

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

    /**
     * Delete the "id" software.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
