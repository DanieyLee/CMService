package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.SoftwareTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.SoftwareType}.
 */
public interface SoftwareTypeService {

    /**
     * Save a softwareType.
     *
     * @param softwareTypeDTO the entity to save.
     * @return the persisted entity.
     */
    SoftwareTypeDTO save(SoftwareTypeDTO softwareTypeDTO);

    /**
     * Get all the softwareTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SoftwareTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" softwareType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SoftwareTypeDTO> findOne(Long id);

    /**
     * Delete the "id" softwareType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
