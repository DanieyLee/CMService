package cn.hupig.www.code.cmservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.hupig.www.code.cmservice.domain.SoftwareComments;

/**
 * Spring Data  repository for the SoftwareComments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoftwareCommentsRepository extends JpaRepository<SoftwareComments, Long> {
	Page<SoftwareComments> findAllBySoftwareId(Pageable pageable, Long softwareId);
}
