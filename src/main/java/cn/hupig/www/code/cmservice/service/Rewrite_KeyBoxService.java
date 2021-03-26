package cn.hupig.www.code.cmservice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.hupig.www.code.cmservice.service.dto.KeyBoxDTO;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.KeyBox}.
 */
public interface Rewrite_KeyBoxService {

    /**
     * Get all the keyBoxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KeyBoxDTO> findMyAll(Pageable pageable, Long userLinkId);

    /**
     * ShowHide "id" KeyBox.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KeyBoxDTO> findOneShowHideAndState(Long id);
    
}
