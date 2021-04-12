package cn.hupig.www.code.cmservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.hupig.www.code.cmservice.service.dto.SoftwareCommentsDTO;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.SoftwareComments}.
 */
public interface Rewrite_SoftwareCommentsService {

    /**
     * Get all the softwareComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SoftwareCommentsDTO> findAllBySoftware(Long id, Pageable pageable);


    /**
     * Get the "id" softwareComments.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Page<SoftwareCommentsDTO> createSoftwareComment(SoftwareCommentsDTO softwareCommentsDTO);

}
