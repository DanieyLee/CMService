package cn.hupig.www.code.cmservice.repository;

import cn.hupig.www.code.cmservice.domain.SystemImage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SystemImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemImageRepository extends JpaRepository<SystemImage, Long> {
}
