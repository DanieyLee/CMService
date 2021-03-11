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
    public Page<SoftwareDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Software");
        return softwareRepository.findAllByState(pageable, true)
            .map(softwareMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoftwareDTO> findTop() {
        log.debug("Request to get all Software");
        Pageable pageable = PageRequest.of(0, 12,Sort.Direction.DESC,"updateTime");
        return softwareRepository.findAllByState(pageable, true)
            .map(softwareMapper::toDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<SoftwareDTO> findOne(Long id) {
        log.debug("Request to get Software : {}", id);
        return softwareRepository.findByIdAndState(id, true)
            .map(softwareMapper::toDto);
    }

}
