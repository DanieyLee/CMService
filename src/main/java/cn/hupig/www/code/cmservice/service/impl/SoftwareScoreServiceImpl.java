package cn.hupig.www.code.cmservice.service.impl;

import cn.hupig.www.code.cmservice.service.SoftwareScoreService;
import cn.hupig.www.code.cmservice.domain.SoftwareScore;
import cn.hupig.www.code.cmservice.repository.SoftwareScoreRepository;
import cn.hupig.www.code.cmservice.service.dto.SoftwareScoreDTO;
import cn.hupig.www.code.cmservice.service.mapper.SoftwareScoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SoftwareScore}.
 */
@Service
@Transactional
public class SoftwareScoreServiceImpl implements SoftwareScoreService {

    private final Logger log = LoggerFactory.getLogger(SoftwareScoreServiceImpl.class);

    private final SoftwareScoreRepository softwareScoreRepository;

    private final SoftwareScoreMapper softwareScoreMapper;

    public SoftwareScoreServiceImpl(SoftwareScoreRepository softwareScoreRepository, SoftwareScoreMapper softwareScoreMapper) {
        this.softwareScoreRepository = softwareScoreRepository;
        this.softwareScoreMapper = softwareScoreMapper;
    }

    @Override
    public SoftwareScoreDTO save(SoftwareScoreDTO softwareScoreDTO) {
        log.debug("Request to save SoftwareScore : {}", softwareScoreDTO);
        SoftwareScore softwareScore = softwareScoreMapper.toEntity(softwareScoreDTO);
        softwareScore = softwareScoreRepository.save(softwareScore);
        return softwareScoreMapper.toDto(softwareScore);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoftwareScoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SoftwareScores");
        return softwareScoreRepository.findAll(pageable)
            .map(softwareScoreMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SoftwareScoreDTO> findOne(Long id) {
        log.debug("Request to get SoftwareScore : {}", id);
        return softwareScoreRepository.findById(id)
            .map(softwareScoreMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SoftwareScore : {}", id);
        softwareScoreRepository.deleteById(id);
    }
}
