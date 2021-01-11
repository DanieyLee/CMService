package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.UserLinkDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.UserLink}.
 */
public interface UserLinkService {

    /**
     * Save a userLink.
     *
     * @param userLinkDTO the entity to save.
     * @return the persisted entity.
     */
    UserLinkDTO save(UserLinkDTO userLinkDTO);

    /**
     * Get all the userLinks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserLinkDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userLink.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserLinkDTO> findOne(Long id);

    /**
     * Delete the "id" userLink.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
