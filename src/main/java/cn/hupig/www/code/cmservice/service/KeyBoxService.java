package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.KeyBoxDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.KeyBox}.
 */
public interface KeyBoxService {

    /**
     * Save a keyBox.
     *
     * @param keyBoxDTO the entity to save.
     * @return the persisted entity.
     */
    KeyBoxDTO save(KeyBoxDTO keyBoxDTO);

    /**
     * Get all the keyBoxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KeyBoxDTO> findAll(Pageable pageable);


    /**
     * Get the "id" keyBox.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KeyBoxDTO> findOne(Long id);

    /**
     * Delete the "id" keyBox.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
