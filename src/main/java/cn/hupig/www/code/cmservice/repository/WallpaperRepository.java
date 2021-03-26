package cn.hupig.www.code.cmservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.hupig.www.code.cmservice.domain.Wallpaper;

/**
 * Spring Data  repository for the Wallpaper entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WallpaperRepository extends JpaRepository<Wallpaper, Long> {
	
	Page<Wallpaper> findAllByState(Pageable pageable, Boolean state);
	
	Optional<Wallpaper> findByIdAndState(Long id, Boolean state);
	
	Page<Wallpaper> findAllByIdLessThanAndState(Pageable pageable, Long id, Boolean state);
	
	Page<Wallpaper> findAllByIdGreaterThanAndState(Pageable pageable, Long id, Boolean state);
	
}
