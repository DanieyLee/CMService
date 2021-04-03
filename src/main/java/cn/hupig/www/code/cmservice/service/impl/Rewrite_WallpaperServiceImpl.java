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

import cn.hupig.www.code.cmservice.domain.User;
import cn.hupig.www.code.cmservice.domain.UserLink;
import cn.hupig.www.code.cmservice.domain.Wallpaper;
import cn.hupig.www.code.cmservice.repository.UserLinkRepository;
import cn.hupig.www.code.cmservice.repository.WallpaperRepository;
import cn.hupig.www.code.cmservice.service.Rewrite_WallpaperService;
import cn.hupig.www.code.cmservice.service.UserService;
import cn.hupig.www.code.cmservice.service.dto.WallpaperDTO;
import cn.hupig.www.code.cmservice.service.mapper.WallpaperMapper;
import cn.hupig.www.code.cmservice.service.utils.FileOperation;
import cn.hupig.www.code.cmservice.service.utils.Times;
import cn.hupig.www.code.cmservice.web.rest.errors.FileOperationException;
import cn.hupig.www.code.cmservice.web.rest.errors.FindWallpaperException;
import cn.hupig.www.code.cmservice.web.rest.vm.ImageAndWallpaperVM;

/**
 * Service Implementation for managing {@link Wallpaper}.
 */
@Service
@Transactional
public class Rewrite_WallpaperServiceImpl implements Rewrite_WallpaperService {

    private final Logger log = LoggerFactory.getLogger(Rewrite_WallpaperServiceImpl.class);

    private final UserService userService;
    
    private final WallpaperRepository wallpaperRepository;
    
    private final UserLinkRepository userLinkRepository;

    private final WallpaperMapper wallpaperMapper;

    public Rewrite_WallpaperServiceImpl(
    		WallpaperRepository wallpaperRepository,
    		UserLinkRepository userLinkRepository,
    		WallpaperMapper wallpaperMapper,
    		UserService userService) {
        this.wallpaperRepository = wallpaperRepository;
        this.userLinkRepository = userLinkRepository;
        this.wallpaperMapper = wallpaperMapper;
        this.userService = userService;
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

	@Override
	public WallpaperDTO UpdateWallpaper(WallpaperDTO wallpaperDTO) {
        log.debug("Request to save Wallpaper : {}", wallpaperDTO);
        Wallpaper wallpaper = wallpaperMapper.toEntity(wallpaperDTO);
        wallpaper = wallpaperRepository.save(wallpaper);
        return wallpaperMapper.toDto(wallpaper);
	}

	@Override
	public void deleteWallpaper(Long id) {
        log.debug("Request to delete Wallpaper : {}", id);
        Optional<Wallpaper> wallpaper = wallpaperRepository.findById(id);
        if (wallpaper.isPresent()) {
        	FileOperation.deleteFile(wallpaper.get().getImageUrl(), "image"); // 删除文件 
        	wallpaper = null;
        }
        wallpaperRepository.deleteById(id);
	}

	@Override
	public WallpaperDTO createWallpaper(ImageAndWallpaperVM imageAndWallpaperVM) {
        log.debug("Request to save Wallpaper : {}", imageAndWallpaperVM);
        if (!imageAndWallpaperVM.isImgSwitch()) { // ImgSwitch 开关没有打开，直接报错
        	throw new FileOperationException();
        }
        imageAndWallpaperVM.setImageUrl(FileOperation.save( // 上传文件,获取文件名,设置文件地址
        		imageAndWallpaperVM.getImage(),
        		imageAndWallpaperVM.getImgName(), "image"));
        userService.getUserWithAuthorities().ifPresent(user -> {
        	imageAndWallpaperVM.setCreateUser(user.getFirstName());
        	imageAndWallpaperVM.setCreatTime(Times.getInstant());
        	imageAndWallpaperVM.setUpdateUser(user.getFirstName());
            imageAndWallpaperVM.setUpdateTime(Times.getInstant());
        	userLinkRepository.findOneByUserId(user.getId()).ifPresent(userLink -> {
        		imageAndWallpaperVM.setUserLinkId(userLink.getId());
        		imageAndWallpaperVM.setUserLinkFirstName(userLink.getFirstName());
        	});
        });
        imageAndWallpaperVM.setVisitorVolume(0);
        imageAndWallpaperVM.setLike(0L);
        imageAndWallpaperVM.setIsDownload(true);
        Wallpaper wallpaper = wallpaperMapper.toEntity(imageAndWallpaperVM);
        wallpaper = wallpaperRepository.save(wallpaper);
        return wallpaperMapper.toDto(wallpaper);
	}
}
