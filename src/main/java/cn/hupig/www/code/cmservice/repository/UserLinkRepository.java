package cn.hupig.www.code.cmservice.repository;

import cn.hupig.www.code.cmservice.domain.User;
import cn.hupig.www.code.cmservice.domain.UserLink;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserLink entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserLinkRepository extends JpaRepository<UserLink, Long> {
	Optional<UserLink> findOneByUserId(Long userId);
}
