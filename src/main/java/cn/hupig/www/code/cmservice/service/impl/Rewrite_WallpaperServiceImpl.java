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
import cn.hupig.www.code.cmservice.web.rest.errors.FindWallpaperException;

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
    public Optional<WallpaperDTO> findOne(Long id) {
        log.debug("Request to get Wallpaper : {}", id);
        return wallpaperRepository.findByIdAndState(id, true)
        		.map(wallpaper -> {
        			wallpaper.setVisitorVolume(wallpaper.getVisitorVolume() + 1);
        			return wallpaperRepository.save(wallpaper);
        		})
            .map(wallpaperMapper::toDto);
    }

    @Override
	public Optional<WallpaperDTO> findOneLikeAndState(Long id) {
        log.debug("Request to get Wallpaper : {}", id);
        return wallpaperRepository.findByIdAndState(id, true)
        	.map(wallpaper -> {
        		wallpaper.setLike(wallpaper.getLike() + 1);
        		return wallpaperRepository.save(wallpaper);
        	})
            .map(wallpaperMapper::toDto);
	}
    
    /**
     * desc从大到小-向前翻页用
     * acs从小到大-向后翻页用
     * LessThan     等价于 SQL 中的 "<",向前翻页用
     * GreaterThan  等价于 SQL 中的 ">",向后翻页用
     * near:true为向前-false为向后
     */
    @Override
    public Optional<WallpaperDTO> findOneNearWallpaperAndState(Long id, Boolean near) {
    	log.debug("Request to get all Wallpapers");
        Pageable pageable = PageRequest.of(0, 1, near ? Sort.Direction.DESC : Sort.Direction.ASC,"id");
        Page<WallpaperDTO> wallpaperDTO = (near ? wallpaperRepository.findAllByIdLessThanAndState(pageable, id, true)
        		: wallpaperRepository.findAllByIdGreaterThanAndState(pageable, id, true))
        		.map(wallpaperMapper::toDto);
        if (wallpaperDTO.getContent().isEmpty()) {
        	throw new FindWallpaperException();
        }
        return Optional.of(wallpaperDTO.getContent().get(0));
    }
}
