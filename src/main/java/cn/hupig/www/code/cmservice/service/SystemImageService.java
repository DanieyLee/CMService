package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.SystemImageDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.SystemImage}.
 */
public interface SystemImageService {

    /**
     * Save a systemImage.
     *
     * @param systemImageDTO the entity to save.
     * @return the persisted entity.
     */
    SystemImageDTO save(SystemImageDTO systemImageDTO);

    /**
     * Get all the systemImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SystemImageDTO> findAll(Pageable pageable);


    /**
     * Get the "id" systemImage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SystemImageDTO> findOne(Long id);

    /**
     * Delete the "id" systemImage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
