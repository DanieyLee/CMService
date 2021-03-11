package cn.hupig.www.code.cmservice.service.impl;

import cn.hupig.www.code.cmservice.domain.Wallpaper;
import cn.hupig.www.code.cmservice.repository.WallpaperRepository;
import cn.hupig.www.code.cmservice.service.WallpaperService;
import cn.hupig.www.code.cmservice.service.dto.WallpaperDTO;
import cn.hupig.www.code.cmservice.service.mapper.WallpaperMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Wallpaper}.
 */
@Service
@Transactional
public class WallpaperServiceImpl implements WallpaperService {

    private final Logger log = LoggerFactory.getLogger(WallpaperServiceImpl.class);

    private final WallpaperRepository wallpaperRepository;

    private final WallpaperMapper wallpaperMapper;

    public WallpaperServiceImpl(WallpaperRepository wallpaperRepository, WallpaperMapper wallpaperMapper) {
        this.wallpaperRepository = wallpaperRepository;
        this.wallpaperMapper = wallpaperMapper;
    }

    @Override
    public WallpaperDTO save(WallpaperDTO wallpaperDTO) {
        log.debug("Request to save Wallpaper : {}", wallpaperDTO);
        Wallpaper wallpaper = wallpaperMapper.toEntity(wallpaperDTO);
        wallpaper = wallpaperRepository.save(wallpaper);
        return wallpaperMapper.toDto(wallpaper);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WallpaperDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Wallpapers");
        return wallpaperRepository.findAll(pageable)
            .map(wallpaperMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WallpaperDTO> findOne(Long id) {
        log.debug("Request to get Wallpaper : {}", id);
        return wallpaperRepository.findById(id)
            .map(wallpaperMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wallpaper : {}", id);
        wallpaperRepository.deleteById(id);
    }
}
