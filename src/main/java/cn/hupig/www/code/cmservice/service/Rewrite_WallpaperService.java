package cn.hupig.www.code.cmservice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.hupig.www.code.cmservice.service.dto.WallpaperDTO;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.Wallpaper}.
 */
public interface Rewrite_WallpaperService {

    /**
     * Get all the wallpapers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WallpaperDTO> findAll(Pageable pageable);

    /**
     * Get top the wallpapers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WallpaperDTO> findTop();

    /**
     * Get the "id" wallpaper.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WallpaperDTO> findOne(Long id);

    /**
     * like "id" wallpaper.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WallpaperDTO> findOneLikeAndState(Long id);
    
    /**
     * get "id" near wallpaper.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WallpaperDTO> findOneNearWallpaperAndState(Long id, Boolean near);
    
}
