package cn.hupig.www.code.cmservice.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hupig.www.code.cmservice.domain.UserLink;
import cn.hupig.www.code.cmservice.repository.UserLinkRepository;
import cn.hupig.www.code.cmservice.repository.UserRepository;
import cn.hupig.www.code.cmservice.service.Rewrite_UserLinkService;
import cn.hupig.www.code.cmservice.service.dto.UserLinkDTO;
import cn.hupig.www.code.cmservice.service.mapper.UserLinkMapper;

/**
 * Service Implementation for managing {@link UserLink}.
 */
@Service
@Transactional
public class Rewrite_UserLinkServiceImpl implements Rewrite_UserLinkService {

    private final Logger log = LoggerFactory.getLogger(Rewrite_UserLinkServiceImpl.class);

    private final UserLinkRepository userLinkRepository;
    
    private final UserRepository userRepository;

    private final UserLinkMapper userLinkMapper;

    public Rewrite_UserLinkServiceImpl(UserLinkRepository userLinkRepository, UserLinkMapper userLinkMapper, UserRepository userRepository) {
        this.userLinkRepository = userLinkRepository;
        this.userLinkMapper = userLinkMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserLinkDTO> findByUser(Long id) {
        log.debug("Request to get UserLink : {}", id);
        return userLinkRepository.findOneByUserId(id)
            .map(userLinkMapper::toDto);
    }

    @Override
    public UserLinkDTO save(UserLinkDTO userLinkDTO) {
        log.debug("Request to save UserLink : {}", userLinkDTO);
        UserLink userLink = userLinkMapper.toEntity(userLinkDTO);
        userLink = userLinkRepository.save(userLink); // 一起更改userlink内的数据
        userRepository.findOneById(
        		userLink.getUser().getId()).ifPresent(user -> {
        			user.setFirstName(userLinkDTO.getFirstName());
        			userRepository.save(user);
        		});
        return userLinkMapper.toDto(userLink);
    }
  
}
