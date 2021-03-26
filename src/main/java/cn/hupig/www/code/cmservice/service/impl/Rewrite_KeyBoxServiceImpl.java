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
}
