package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.SoftwareCommentsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.SoftwareComments}.
 */
public interface SoftwareCommentsService {

    /**
     * Save a softwareComments.
     *
     * @param softwareCommentsDTO the entity to save.
     * @return the persisted entity.
     */
    SoftwareCommentsDTO save(SoftwareCommentsDTO softwareCommentsDTO);

    /**
     * Get all the softwareComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SoftwareCommentsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" softwareComments.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SoftwareCommentsDTO> findOne(Long id);

    /**
     * Delete the "id" softwareComments.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
