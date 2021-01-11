package cn.hupig.www.code.cmservice.service.impl;

import cn.hupig.www.code.cmservice.service.SoftwareCommentsService;
import cn.hupig.www.code.cmservice.domain.SoftwareComments;
import cn.hupig.www.code.cmservice.repository.SoftwareCommentsRepository;
import cn.hupig.www.code.cmservice.service.dto.SoftwareCommentsDTO;
import cn.hupig.www.code.cmservice.service.mapper.SoftwareCommentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SoftwareComments}.
 */
@Service
@Transactional
public class SoftwareCommentsServiceImpl implements SoftwareCommentsService {

    private final Logger log = LoggerFactory.getLogger(SoftwareCommentsServiceImpl.class);

    private final SoftwareCommentsRepository softwareCommentsRepository;

    private final SoftwareCommentsMapper softwareCommentsMapper;

    public SoftwareCommentsServiceImpl(SoftwareCommentsRepository softwareCommentsRepository, SoftwareCommentsMapper softwareCommentsMapper) {
        this.softwareCommentsRepository = softwareCommentsRepository;
        this.softwareCommentsMapper = softwareCommentsMapper;
    }

    @Override
    public SoftwareCommentsDTO save(SoftwareCommentsDTO softwareCommentsDTO) {
        log.debug("Request to save SoftwareComments : {}", softwareCommentsDTO);
        SoftwareComments softwareComments = softwareCommentsMapper.toEntity(softwareCommentsDTO);
        softwareComments = softwareCommentsRepository.save(softwareComments);
        return softwareCommentsMapper.toDto(softwareComments);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoftwareCommentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SoftwareComments");
        return softwareCommentsRepository.findAll(pageable)
            .map(softwareCommentsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SoftwareCommentsDTO> findOne(Long id) {
        log.debug("Request to get SoftwareComments : {}", id);
        return softwareCommentsRepository.findById(id)
            .map(softwareCommentsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SoftwareComments : {}", id);
        softwareCommentsRepository.deleteById(id);
    }
}
