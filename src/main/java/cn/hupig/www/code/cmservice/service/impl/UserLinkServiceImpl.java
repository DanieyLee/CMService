package cn.hupig.www.code.cmservice.service.impl;

import cn.hupig.www.code.cmservice.service.UserLinkService;
import cn.hupig.www.code.cmservice.domain.UserLink;
import cn.hupig.www.code.cmservice.repository.UserLinkRepository;
import cn.hupig.www.code.cmservice.service.dto.UserLinkDTO;
import cn.hupig.www.code.cmservice.service.mapper.UserLinkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserLink}.
 */
@Service
@Transactional
public class UserLinkServiceImpl implements UserLinkService {

    private final Logger log = LoggerFactory.getLogger(UserLinkServiceImpl.class);

    private final UserLinkRepository userLinkRepository;

    private final UserLinkMapper userLinkMapper;

    public UserLinkServiceImpl(UserLinkRepository userLinkRepository, UserLinkMapper userLinkMapper) {
        this.userLinkRepository = userLinkRepository;
        this.userLinkMapper = userLinkMapper;
    }

    @Override
    public UserLinkDTO save(UserLinkDTO userLinkDTO) {
        log.debug("Request to save UserLink : {}", userLinkDTO);
        UserLink userLink = userLinkMapper.toEntity(userLinkDTO);
        userLink = userLinkRepository.save(userLink);
        return userLinkMapper.toDto(userLink);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserLinkDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserLinks");
        return userLinkRepository.findAll(pageable)
            .map(userLinkMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserLinkDTO> findOne(Long id) {
        log.debug("Request to get UserLink : {}", id);
        return userLinkRepository.findById(id)
            .map(userLinkMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserLink : {}", id);
        userLinkRepository.deleteById(id);
    }
}
