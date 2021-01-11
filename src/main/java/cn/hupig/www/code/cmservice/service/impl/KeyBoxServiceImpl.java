package cn.hupig.www.code.cmservice.service.impl;

import cn.hupig.www.code.cmservice.service.KeyBoxService;
import cn.hupig.www.code.cmservice.domain.KeyBox;
import cn.hupig.www.code.cmservice.repository.KeyBoxRepository;
import cn.hupig.www.code.cmservice.service.dto.KeyBoxDTO;
import cn.hupig.www.code.cmservice.service.mapper.KeyBoxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link KeyBox}.
 */
@Service
@Transactional
public class KeyBoxServiceImpl implements KeyBoxService {

    private final Logger log = LoggerFactory.getLogger(KeyBoxServiceImpl.class);

    private final KeyBoxRepository keyBoxRepository;

    private final KeyBoxMapper keyBoxMapper;

    public KeyBoxServiceImpl(KeyBoxRepository keyBoxRepository, KeyBoxMapper keyBoxMapper) {
        this.keyBoxRepository = keyBoxRepository;
        this.keyBoxMapper = keyBoxMapper;
    }

    @Override
    public KeyBoxDTO save(KeyBoxDTO keyBoxDTO) {
        log.debug("Request to save KeyBox : {}", keyBoxDTO);
        KeyBox keyBox = keyBoxMapper.toEntity(keyBoxDTO);
        keyBox = keyBoxRepository.save(keyBox);
        return keyBoxMapper.toDto(keyBox);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KeyBoxDTO> findAll(Pageable pageable) {
        log.debug("Request to get all KeyBoxes");
        return keyBoxRepository.findAll(pageable)
            .map(keyBoxMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<KeyBoxDTO> findOne(Long id) {
        log.debug("Request to get KeyBox : {}", id);
        return keyBoxRepository.findById(id)
            .map(keyBoxMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KeyBox : {}", id);
        keyBoxRepository.deleteById(id);
    }
}
