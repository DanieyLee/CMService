package cn.hupig.www.code.cmservice.repository;

import cn.hupig.www.code.cmservice.domain.SoftwareComments;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SoftwareComments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoftwareCommentsRepository extends JpaRepository<SoftwareComments, Long> {
}
