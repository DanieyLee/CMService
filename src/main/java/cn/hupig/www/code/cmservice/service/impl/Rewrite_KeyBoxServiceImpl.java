package cn.hupig.www.code.cmservice.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hupig.www.code.cmservice.domain.KeyBox;
import cn.hupig.www.code.cmservice.repository.KeyBoxRepository;
import cn.hupig.www.code.cmservice.service.Rewrite_KeyBoxService;
import cn.hupig.www.code.cmservice.service.dto.KeyBoxDTO;
import cn.hupig.www.code.cmservice.service.mapper.KeyBoxMapper;
import cn.hupig.www.code.cmservice.service.utils.Times;
import cn.hupig.www.code.cmservice.web.rest.errors.KeyBoxException;

/**
 * Service Implementation for managing {@link KeyBox}.
 */
@Service
@Transactional
public class Rewrite_KeyBoxServiceImpl implements Rewrite_KeyBoxService {

    private final Logger log = LoggerFactory.getLogger(Rewrite_KeyBoxServiceImpl.class);

    private final KeyBoxRepository keyBoxRepository;

    private final KeyBoxMapper keyBoxMapper;

    public Rewrite_KeyBoxServiceImpl(KeyBoxRepository keyBoxRepository, KeyBoxMapper keyBoxMapper) {
        this.keyBoxRepository = keyBoxRepository;
        this.keyBoxMapper = keyBoxMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KeyBoxDTO> findMyAll(Pageable pageable, Long userLinkId) {
        log.debug("Request to get all KeyBoxes");
        return keyBoxRepository.findAllByUserLinkId(pageable, userLinkId)
            .map(keyBoxMapper::toDto);
    }

	@Override
	public Optional<KeyBoxDTO> findOneShowHideAndState(Long id) {
	    log.debug("Request to get software : {}", id);
	    return keyBoxRepository.findById(id)
	    	.map(keyBox -> {
	    		keyBox.setDisplay(!keyBox.isDisplay());
	    		return keyBoxRepository.save(keyBox);
	    	})
	        .map(keyBoxMapper::toDto);
	}
	
    @Override
    public void delete(Long id, Long userLinkId) {
    	log.debug("Request to delete KeyBox : {}", id);
    	Optional<KeyBox> keyBox = keyBoxRepository.findByIdAndUserLinkId(id,userLinkId);
    	if (keyBox.isPresent()) {
    		keyBoxRepository.deleteById(id);    		
    	} else {
    		throw new KeyBoxException("keyBox");
    	}
    }

	@Override
	public KeyBoxDTO updateKeyBox(KeyBoxDTO keyBoxDTO, Long userLinkId) {
		log.debug("Request to save KeyBox : {}", keyBoxDTO);
		Optional<KeyBox> kb = keyBoxRepository.findByIdAndUserLinkId(keyBoxDTO.getId(), userLinkId);
		if (!kb.isPresent() && keyBoxDTO.getUserLinkId() != userLinkId) {
			throw new KeyBoxException("keyBox");
    	}
		keyBoxDTO.setUpdateTime(Times.getInstant());
        KeyBox keyBox = keyBoxMapper.toEntity(keyBoxDTO);
        keyBox = keyBoxRepository.save(keyBox);
        return keyBoxMapper.toDto(keyBox);
	}
	
	@Override
	public KeyBoxDTO createKeyBox(KeyBoxDTO keyBoxDTO, Long userLinkId) {
		log.debug("Request to save KeyBox : {}", keyBoxDTO);
		keyBoxDTO.setUserLinkId(userLinkId);
		keyBoxDTO.setCreatTime(Times.getInstant());
		keyBoxDTO.setUpdateTime(Times.getInstant());
        KeyBox keyBox = keyBoxMapper.toEntity(keyBoxDTO);
        keyBox = keyBoxRepository.save(keyBox);
        return keyBoxMapper.toDto(keyBox);
	}
}
