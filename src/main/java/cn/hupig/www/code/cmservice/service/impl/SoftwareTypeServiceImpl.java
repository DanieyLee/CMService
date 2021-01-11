package cn.hupig.www.code.cmservice.service.impl;

import cn.hupig.www.code.cmservice.service.SoftwareTypeService;
import cn.hupig.www.code.cmservice.domain.SoftwareType;
import cn.hupig.www.code.cmservice.repository.SoftwareTypeRepository;
import cn.hupig.www.code.cmservice.service.dto.SoftwareTypeDTO;
import cn.hupig.www.code.cmservice.service.mapper.SoftwareTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SoftwareType}.
 */
@Service
@Transactional
public class SoftwareTypeServiceImpl implements SoftwareTypeService {

    private final Logger log = LoggerFactory.getLogger(SoftwareTypeServiceImpl.class);

    private final SoftwareTypeRepository softwareTypeRepository;

    private final SoftwareTypeMapper softwareTypeMapper;

    public SoftwareTypeServiceImpl(SoftwareTypeRepository softwareTypeRepository, SoftwareTypeMapper softwareTypeMapper) {
        this.softwareTypeRepository = softwareTypeRepository;
        this.softwareTypeMapper = softwareTypeMapper;
    }

    @Override
    public SoftwareTypeDTO save(SoftwareTypeDTO softwareTypeDTO) {
        log.debug("Request to save SoftwareType : {}", softwareTypeDTO);
        SoftwareType softwareType = softwareTypeMapper.toEntity(softwareTypeDTO);
        softwareType = softwareTypeRepository.save(softwareType);
        return softwareTypeMapper.toDto(softwareType);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoftwareTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SoftwareTypes");
        return softwareTypeRepository.findAll(pageable)
            .map(softwareTypeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SoftwareTypeDTO> findOne(Long id) {
        log.debug("Request to get SoftwareType : {}", id);
        return softwareTypeRepository.findById(id)
            .map(softwareTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SoftwareType : {}", id);
        softwareTypeRepository.deleteById(id);
    }
}
