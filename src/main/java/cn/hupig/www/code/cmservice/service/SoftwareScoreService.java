package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.SoftwareScoreDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.SoftwareScore}.
 */
public interface SoftwareScoreService {

    /**
     * Save a softwareScore.
     *
     * @param softwareScoreDTO the entity to save.
     * @return the persisted entity.
     */
    SoftwareScoreDTO save(SoftwareScoreDTO softwareScoreDTO);

    /**
     * Get all the softwareScores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SoftwareScoreDTO> findAll(Pageable pageable);


    /**
     * Get the "id" softwareScore.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SoftwareScoreDTO> findOne(Long id);

    /**
     * Delete the "id" softwareScore.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
