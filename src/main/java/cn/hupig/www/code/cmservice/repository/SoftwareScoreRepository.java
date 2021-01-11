package cn.hupig.www.code.cmservice.repository;

import cn.hupig.www.code.cmservice.domain.SoftwareScore;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SoftwareScore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoftwareScoreRepository extends JpaRepository<SoftwareScore, Long> {
}
