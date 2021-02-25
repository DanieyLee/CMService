package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.WallpaperDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.Wallpaper}.
 */
public interface WallpaperService {

    /**
     * Save a wallpaper.
     *
     * @param wallpaperDTO the entity to save.
     * @return the persisted entity.
     */
    WallpaperDTO save(WallpaperDTO wallpaperDTO);

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
     * Delete the "id" wallpaper.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
