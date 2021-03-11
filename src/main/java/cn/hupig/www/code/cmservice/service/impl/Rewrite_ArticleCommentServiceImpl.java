package cn.hupig.www.code.cmservice.service.impl;

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
import cn.hupig.www.code.cmservice.service.Rewrite_ArticleCommentService;
import cn.hupig.www.code.cmservice.service.dto.ArticleCommentDTO;
import cn.hupig.www.code.cmservice.service.mapper.ArticleCommentMapper;

/**
 * Service Implementation for managing {@link ArticleComment}.
 */
@Service
@Transactional
public class Rewrite_ArticleCommentServiceImpl implements Rewrite_ArticleCommentService {

    private final Logger log = LoggerFactory.getLogger(Rewrite_ArticleCommentServiceImpl.class);

    private final ArticleCommentRepository articleCommentRepository;

    private final ArticleCommentMapper articleCommentMapper;

    public Rewrite_ArticleCommentServiceImpl(ArticleCommentRepository articleCommentRepository, ArticleCommentMapper articleCommentMapper) {
        this.articleCommentRepository = articleCommentRepository;
        this.articleCommentMapper = articleCommentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleCommentDTO> findAllByArticle(Long id, Pageable pageable) {
        log.debug("Request to get all Article ArticleComments");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),Sort.Direction.DESC,"updateTime");
        return articleCommentRepository.findAllByArticleId(pageable,id)
        		.map(articleCommentMapper::toDto);
    }

}
