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
import cn.hupig.www.code.cmservice.service.Rewrite_SoftwareService;
import cn.hupig.www.code.cmservice.service.dto.ArticleDTO;
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

    private final SoftwareMapper softwareMapper;

    public Rewrite_SoftwareServiceImpl(SoftwareRepository softwareRepository, SoftwareMapper softwareMapper) {
        this.softwareRepository = softwareRepository;
        this.softwareMapper = softwareMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoftwareDTO> findAllState(Long id, Pageable pageable) {
        log.debug("Request to get all Software by type");
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),Sort.Direction.DESC,"updateTime");
		
		return (id == 0 ? softwareRepository.findAllByState(pageable, true):
			softwareRepository.findAllByStateAndSoftwareTypeId(pageable, true, id)).map(softwareMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoftwareDTO> findTopState() {
        log.debug("Request to get all Software");
        Pageable pageable = PageRequest.of(0, 12,Sort.Direction.DESC,"updateTime");
        return softwareRepository.findAllByState(pageable, true)
            .map(softwareMapper::toDto);
    }
    
    @Override
    public Optional<SoftwareDTO> findOneState(Long id) {
        log.debug("Request to get Software : {}", id);
        return softwareRepository.findByIdAndState(id, true)
        	.map(software -> {
        		software.setBrowseNumber(software.getBrowseNumber() + 1);
        		return softwareRepository.save(software);
        	})
            .map(softwareMapper::toDto);
    }
    
    @Override
	public Optional<SoftwareDTO> findOneLikeAndState(Long id) {
        log.debug("Request to get software : {}", id);
        return softwareRepository.findByIdAndState(id, true)
        	.map(software -> {
        		software.setScore(software.getScore() + 1);
        		return softwareRepository.save(software);
        	})
            .map(softwareMapper::toDto);
	}
    
    @Override
	public Optional<SoftwareDTO> findOneDownloadAndState(Long id) {
        log.debug("Request to get software : {}", id);
        return softwareRepository.findByIdAndState(id, true)
        	.map(software -> {
        		software.setDownloadNumber(software.getDownloadNumber() + 1);
        		return softwareRepository.save(software);
        	})
            .map(softwareMapper::toDto);
	}
}
