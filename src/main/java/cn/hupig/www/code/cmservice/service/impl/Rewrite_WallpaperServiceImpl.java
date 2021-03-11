package cn.hupig.www.code.cmservice.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hupig.www.code.cmservice.domain.Wallpaper;
import cn.hupig.www.code.cmservice.repository.WallpaperRepository;
import cn.hupig.www.code.cmservice.service.Rewrite_WallpaperService;
import cn.hupig.www.code.cmservice.service.dto.WallpaperDTO;
import cn.hupig.www.code.cmservice.service.mapper.WallpaperMapper;

/**
 * Service Implementation for managing {@link Wallpaper}.
 */
@Service
@Transactional
public class Rewrite_WallpaperServiceImpl implements Rewrite_WallpaperService {

    private final Logger log = LoggerFactory.getLogger(Rewrite_WallpaperServiceImpl.class);

    private final WallpaperRepository wallpaperRepository;

    private final WallpaperMapper wallpaperMapper;

    public Rewrite_WallpaperServiceImpl(WallpaperRepository wallpaperRepository, WallpaperMapper wallpaperMapper) {
        this.wallpaperRepository = wallpaperRepository;
        this.wallpaperMapper = wallpaperMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WallpaperDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Wallpapers");
        return wallpaperRepository.findAllByState(pageable, true)
            .map(wallpaperMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WallpaperDTO> findTop() {
        log.debug("Request to get all Wallpapers");
        Pageable pageable = PageRequest.of(0, 4,Sort.Direction.DESC,"updateTime");
        return wallpaperRepository.findAllByState(pageable, true)
            .map(wallpaperMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WallpaperDTO> findOne(Long id) {
        log.debug("Request to get Wallpaper : {}", id);
        return wallpaperRepository.findByIdAndState(id, true)
            .map(wallpaperMapper::toDto);
    }

}
