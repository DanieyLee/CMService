package cn.hupig.www.code.cmservice.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hupig.www.code.cmservice.domain.ArticleComment;
import cn.hupig.www.code.cmservice.domain.SoftwareComments;
import cn.hupig.www.code.cmservice.repository.SoftwareCommentsRepository;
import cn.hupig.www.code.cmservice.repository.UserLinkRepository;
import cn.hupig.www.code.cmservice.service.Rewrite_SoftwareCommentsService;
import cn.hupig.www.code.cmservice.service.dto.SoftwareCommentsDTO;
import cn.hupig.www.code.cmservice.service.dto.UserLinkDTO;
import cn.hupig.www.code.cmservice.service.mapper.SoftwareCommentsMapper;
import cn.hupig.www.code.cmservice.service.mapper.UserLinkMapper;
import cn.hupig.www.code.cmservice.service.utils.Times;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;

/**
 * Service Implementation for managing {@link SoftwareComments}.
 */
@Service
@Transactional
public class Rewrite_SoftwareCommentsServiceImpl implements Rewrite_SoftwareCommentsService {

    private final Logger log = LoggerFactory.getLogger(Rewrite_SoftwareCommentsServiceImpl.class);

    private static final String ENTITY_NAME = "userLink";
    
    private final SoftwareCommentsRepository softwareCommentsRepository;

    private final SoftwareCommentsMapper softwareCommentsMapper;
    
    private final UserLinkRepository userLinkRepository;
    
    private final UserLinkMapper userLinkMapper;

    public Rewrite_SoftwareCommentsServiceImpl(SoftwareCommentsRepository softwareCommentsRepository,
    		SoftwareCommentsMapper softwareCommentsMapper,
    		UserLinkRepository userLinkRepository,
			UserLinkMapper userLinkMapper) {
        this.softwareCommentsRepository = softwareCommentsRepository;
        this.softwareCommentsMapper = softwareCommentsMapper;
        this.userLinkRepository = userLinkRepository;
        this.userLinkMapper = userLinkMapper;
    }

	@Override
    @Transactional(readOnly = true)
	public Page<SoftwareCommentsDTO> findAllBySoftware(Long id, Pageable pageable) {
		log.debug("Request to get all Software SoftwareComments");
		 pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),Sort.Direction.DESC,"updateTime");
		 return softwareCommentsRepository.findAllBySoftwareId(pageable,id)
				 .map(softwareCommentsMapper::toDto);
	}

	@Override
	public Optional<SoftwareCommentsDTO> createSoftwareComment(SoftwareCommentsDTO softwareCommentsDTO) {
		log.debug("Request to save SoftwareComment : {}", softwareCommentsDTO);
        Optional<UserLinkDTO> userLinkDTO = userLinkRepository.findOneByUserId(
        		Long.parseLong(softwareCommentsDTO.getCreateUser())
        		).map(userLinkMapper::toDto);
        if (!userLinkDTO.isPresent()){
        	throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        softwareCommentsDTO.setUserLinkId(Long.parseLong(softwareCommentsDTO.getCreateUser()));
        softwareCommentsDTO.setTxTitle(softwareCommentsDTO.getTxTitle());
        softwareCommentsDTO.setCreateUser(userLinkDTO.get().getFirstName());
        softwareCommentsDTO.setCreatTime(Times.getInstant());
        softwareCommentsDTO.setUpdateUser(userLinkDTO.get().getFirstName());
        softwareCommentsDTO.setUpdateTime(Times.getInstant());
        SoftwareComments softwareComments = softwareCommentsMapper.toEntity(softwareCommentsDTO);
        softwareComments = softwareCommentsRepository.save(softwareComments);
        return softwareCommentsRepository.findById(softwareComments.getId())
                .map(softwareCommentsMapper::toDto);
	}

 
}
