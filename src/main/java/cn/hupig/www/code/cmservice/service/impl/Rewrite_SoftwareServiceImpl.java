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

import cn.hupig.www.code.cmservice.domain.Software;
import cn.hupig.www.code.cmservice.repository.SoftwareRepository;
import cn.hupig.www.code.cmservice.repository.UserLinkRepository;
import cn.hupig.www.code.cmservice.service.Rewrite_SoftwareService;
import cn.hupig.www.code.cmservice.service.dto.SoftwareDTO;
import cn.hupig.www.code.cmservice.service.mapper.SoftwareMapper;

/**
 * Service Implementation for managing {@link Software}.
 */
@Service
@Transactional
public class Rewrite_SoftwareServiceImpl implements Rewrite_SoftwareService {

    private final Logger log = LoggerFactory.getLogger(Rewrite_SoftwareServiceImpl.class);

    private final SoftwareRepository softwareRepository;
    
    private final UserLinkRepository userLinkRepository;

    private final SoftwareMapper softwareMapper;

    public Rewrite_SoftwareServiceImpl(SoftwareRepository softwareRepository, UserLinkRepository userLinkRepository, SoftwareMapper softwareMapper) {
        this.softwareRepository = softwareRepository;
        this.userLinkRepository= userLinkRepository;
        this.softwareMapper = softwareMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoftwareDTO> findAllState(Long id, Pageable pageable) {
        log.debug("Request to get all Software by type");
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),Sort.Direction.DESC,"updateTime");
		
		return (id == 0 ? softwareRepository.findAllByStateTrueAndShowTrue(pageable):
			softwareRepository.findAllByStateTrueAndSoftwareTypeId(pageable, id)).map(softwareMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoftwareDTO> findTopState() {
        log.debug("Request to get all Software");
        Pageable pageable = PageRequest.of(0, 12,Sort.Direction.DESC,"updateTime");
        return softwareRepository.findAllByStateTrueAndShowTrue(pageable)
            .map(softwareMapper::toDto);
    }
    
    @Override
    public Optional<SoftwareDTO> findOneState(Long id) {
        log.debug("Request to get Software : {}", id);
        return softwareRepository.findByIdAndStateTrue(id)
        	.map(software -> {
        		software.setBrowseNumber(software.getBrowseNumber() + 1);
        		return softwareRepository.save(software);
        	})
            .map(softwareMapper::toDto);
    }
    
    @Override
	public Optional<SoftwareDTO> findOneLikeAndState(Long id) {
        log.debug("Request to get software : {}", id);
        return softwareRepository.findByIdAndStateTrue(id)
        	.map(software -> {
        		software.setScore(software.getScore() + 1);
        		return softwareRepository.save(software);
        	})
            .map(softwareMapper::toDto);
	}
    
    @Override
	public Optional<SoftwareDTO> findOneDownloadAndState(Long id) {
        log.debug("Request to get software : {}", id);
        return softwareRepository.findByIdAndStateTrue(id)
        	.map(software -> {
        		software.setDownloadNumber(software.getDownloadNumber() + 1);
        		return softwareRepository.save(software);
        	})
            .map(softwareMapper::toDto);
	}

	@Override
	public SoftwareDTO createSoftware(SoftwareDTO softwareDTO) {
		log.debug("Request to save Software : {}", softwareDTO);
		softwareDTO.setScore(0.0);
		softwareDTO.setDownloadNumber(0L);
		softwareDTO.setBrowseNumber(0L);
		softwareDTO.setUserLinkId(userLinkRepository.findOneByUserId(softwareDTO.getUserLinkId()).get().getId());
        Software software = softwareMapper.toEntity(softwareDTO);
        software = softwareRepository.save(software);
        return softwareMapper.toDto(software);
	}

	@Override
	public SoftwareDTO updateSoftware(SoftwareDTO softwareDTO) {
		log.debug("Request to save Software : {}", softwareDTO);
        Software software = softwareMapper.toEntity(softwareDTO);
        software = softwareRepository.save(software);
        return softwareMapper.toDto(software);
	}
}
