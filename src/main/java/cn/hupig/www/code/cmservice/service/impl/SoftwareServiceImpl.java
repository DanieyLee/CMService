package cn.hupig.www.code.cmservice.service.impl;

import cn.hupig.www.code.cmservice.service.SoftwareService;
import cn.hupig.www.code.cmservice.domain.Software;
import cn.hupig.www.code.cmservice.repository.SoftwareRepository;
import cn.hupig.www.code.cmservice.service.dto.SoftwareDTO;
import cn.hupig.www.code.cmservice.service.mapper.SoftwareMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Software}.
 */
@Service
@Transactional
public class SoftwareServiceImpl implements SoftwareService {

    private final Logger log = LoggerFactory.getLogger(SoftwareServiceImpl.class);

    private final SoftwareRepository softwareRepository;

    private final SoftwareMapper softwareMapper;

    public SoftwareServiceImpl(SoftwareRepository softwareRepository, SoftwareMapper softwareMapper) {
        this.softwareRepository = softwareRepository;
        this.softwareMapper = softwareMapper;
    }

    @Override
    public SoftwareDTO save(SoftwareDTO softwareDTO) {
        log.debug("Request to save Software : {}", softwareDTO);
        Software software = softwareMapper.toEntity(softwareDTO);
        software = softwareRepository.save(software);
        return softwareMapper.toDto(software);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoftwareDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Software");
        return softwareRepository.findAll(pageable)
            .map(softwareMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SoftwareDTO> findOne(Long id) {
        log.debug("Request to get Software : {}", id);
        return softwareRepository.findById(id)
            .map(softwareMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Software : {}", id);
        softwareRepository.deleteById(id);
    }
}
