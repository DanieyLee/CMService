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
	
	Page<Wallpaper> findAllByStateTrue(Pageable pageable);
	
	Optional<Wallpaper> findByIdAndStateTrue(Long id);
	
	Page<Wallpaper> findAllByIdLessThanAndStateTrue(Pageable pageable, Long id);
	
	Page<Wallpaper> findAllByIdGreaterThanAndStateTrue(Pageable pageable, Long id);
	
}
