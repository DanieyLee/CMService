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
import cn.hupig.www.code.cmservice.repository.ArticleCommentRepository;
import cn.hupig.www.code.cmservice.repository.UserLinkRepository;
import cn.hupig.www.code.cmservice.service.Rewrite_ArticleCommentService;
import cn.hupig.www.code.cmservice.service.dto.ArticleCommentDTO;
import cn.hupig.www.code.cmservice.service.dto.UserLinkDTO;
import cn.hupig.www.code.cmservice.service.mapper.ArticleCommentMapper;
import cn.hupig.www.code.cmservice.service.mapper.UserLinkMapper;
import cn.hupig.www.code.cmservice.service.utils.Times;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;

/**
 * Service Implementation for managing {@link ArticleComment}.
 */
@Service
@Transactional
public class Rewrite_ArticleCommentServiceImpl implements Rewrite_ArticleCommentService {

    private final Logger log = LoggerFactory.getLogger(Rewrite_ArticleCommentServiceImpl.class);
    
    private static final String ENTITY_NAME = "userLink";

    private final ArticleCommentRepository articleCommentRepository;

    private final ArticleCommentMapper articleCommentMapper;
    
    private final UserLinkRepository userLinkRepository;
    
    private final UserLinkMapper userLinkMapper;

    public Rewrite_ArticleCommentServiceImpl(ArticleCommentRepository articleCommentRepository,
    											ArticleCommentMapper articleCommentMapper,
    											UserLinkRepository userLinkRepository,
    											UserLinkMapper userLinkMapper) {
        this.articleCommentRepository = articleCommentRepository;
        this.articleCommentMapper = articleCommentMapper;
        this.userLinkRepository = userLinkRepository;
        this.userLinkMapper = userLinkMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleCommentDTO> findAllByArticle(Long id, Pageable pageable) {
        log.debug("Request to get all Article ArticleComments");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),Sort.Direction.DESC,"updateTime");
        return articleCommentRepository.findAllByArticleId(pageable,id)
        		.map(articleCommentMapper::toDto);
    }

	@Override
	public Optional<ArticleCommentDTO> createArticleComment(ArticleCommentDTO articleCommentDTO) {
        log.debug("Request to save ArticleComment : {}", articleCommentDTO);
        Optional<UserLinkDTO> userLinkDTO = userLinkRepository.findOneByUserId(
        		Long.parseLong(articleCommentDTO.getCreateUser())
        		).map(userLinkMapper::toDto);
        if (!userLinkDTO.isPresent()){
        	throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        articleCommentDTO.setUserLinkId(Long.parseLong(articleCommentDTO.getCreateUser()));
        articleCommentDTO.setCreateUser(userLinkDTO.get().getFirstName());
        articleCommentDTO.setCreatTime(Times.getInstant());
        articleCommentDTO.setUpdateUser(userLinkDTO.get().getFirstName());
        articleCommentDTO.setUpdateTime(Times.getInstant());
        ArticleComment articleComment = articleCommentMapper.toEntity(articleCommentDTO);
        articleComment = articleCommentRepository.save(articleComment);
        return articleCommentRepository.findById(articleComment.getId())
                .map(articleCommentMapper::toDto);
	}

}
