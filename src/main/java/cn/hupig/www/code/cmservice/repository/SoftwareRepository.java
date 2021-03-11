package cn.hupig.www.code.cmservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.hupig.www.code.cmservice.domain.Software;

/**
 * Spring Data  repository for the Software entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoftwareRepository extends JpaRepository<Software, Long> {
	
	Page<Software> findAllByState(Pageable pageable, Boolean state);
	
	Optional<Software> findByIdAndState(Long id, Boolean state);
	
}
