package cn.hupig.www.code.cmservice.service.impl;

import cn.hupig.www.code.cmservice.service.SystemImageService;
import cn.hupig.www.code.cmservice.domain.SystemImage;
import cn.hupig.www.code.cmservice.repository.SystemImageRepository;
import cn.hupig.www.code.cmservice.service.dto.SystemImageDTO;
import cn.hupig.www.code.cmservice.service.mapper.SystemImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SystemImage}.
 */
@Service
@Transactional
public class SystemImageServiceImpl implements SystemImageService {

    private final Logger log = LoggerFactory.getLogger(SystemImageServiceImpl.class);

    private final SystemImageRepository systemImageRepository;

    private final SystemImageMapper systemImageMapper;

    public SystemImageServiceImpl(SystemImageRepository systemImageRepository, SystemImageMapper systemImageMapper) {
        this.systemImageRepository = systemImageRepository;
        this.systemImageMapper = systemImageMapper;
    }

    @Override
    public SystemImageDTO save(SystemImageDTO systemImageDTO) {
        log.debug("Request to save SystemImage : {}", systemImageDTO);
        SystemImage systemImage = systemImageMapper.toEntity(systemImageDTO);
        systemImage = systemImageRepository.save(systemImage);
        return systemImageMapper.toDto(systemImage);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SystemImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SystemImages");
        return systemImageRepository.findAll(pageable)
            .map(systemImageMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SystemImageDTO> findOne(Long id) {
        log.debug("Request to get SystemImage : {}", id);
        return systemImageRepository.findById(id)
            .map(systemImageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SystemImage : {}", id);
        systemImageRepository.deleteById(id);
    }
}
