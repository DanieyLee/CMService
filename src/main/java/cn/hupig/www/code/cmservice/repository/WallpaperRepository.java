package cn.hupig.www.code.cmservice.repository;

import cn.hupig.www.code.cmservice.domain.Wallpaper;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Wallpaper entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WallpaperRepository extends JpaRepository<Wallpaper, Long> {
}
