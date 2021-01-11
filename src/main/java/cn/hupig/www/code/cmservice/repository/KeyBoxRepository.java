package cn.hupig.www.code.cmservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.hupig.www.code.cmservice.domain.KeyBox;
import cn.hupig.www.code.cmservice.service.dto.KeyBoxDTO;

/**
 * Spring Data  repository for the KeyBox entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeyBoxRepository extends JpaRepository<KeyBox, Long> {
	
	Page<KeyBox> findAllByUserLinkId(Pageable pageable, Long userLinkId);
	
}
