package cn.hupig.www.code.cmservice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.hupig.www.code.cmservice.service.dto.WallpaperDTO;
import cn.hupig.www.code.cmservice.web.rest.vm.ImageAndWallpaperVM;

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
    
    /**
     * Update a wallpaper.
     *
     * @param wallpaperDTO the entity to save.
     * @return the persisted entity.
     */
    WallpaperDTO UpdateWallpaper(WallpaperDTO wallpaperDTO);
    
    /**
     * Delete the "id" wallpaper.
     *
     * @param id the id of the entity.
     */
    void deleteWallpaper(Long id);
    
    /**
     * create a wallpaper.
     *
     * @param wallpaperDTO the entity to save.
     * @return the persisted entity.
     */
    WallpaperDTO createWallpaper(ImageAndWallpaperVM imageAndWallpaperVM);
}
