package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.UserLinkDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

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
