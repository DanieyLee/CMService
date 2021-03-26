package cn.hupig.www.code.cmservice.service;

import java.util.Optional;

import cn.hupig.www.code.cmservice.service.dto.UserLinkDTO;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.UserLink}.
 */
public interface Rewrite_UserLinkService {

    /**
     * Get the "id" userLink.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserLinkDTO> findByUser(Long id);
   
}
