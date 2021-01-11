package cn.hupig.www.code.cmservice.repository;

import cn.hupig.www.code.cmservice.domain.SoftwareType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SoftwareType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoftwareTypeRepository extends JpaRepository<SoftwareType, Long> {
}
