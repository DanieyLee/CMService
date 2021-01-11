package cn.hupig.www.code.cmservice.service.impl;

import cn.hupig.www.code.cmservice.service.ArticleCommentService;
import cn.hupig.www.code.cmservice.domain.ArticleComment;
import cn.hupig.www.code.cmservice.repository.ArticleCommentRepository;
import cn.hupig.www.code.cmservice.service.dto.ArticleCommentDTO;
import cn.hupig.www.code.cmservice.service.mapper.ArticleCommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ArticleComment}.
 */
@Service
@Transactional
public class ArticleCommentServiceImpl implements ArticleCommentService {

    private final Logger log = LoggerFactory.getLogger(ArticleCommentServiceImpl.class);

    private final ArticleCommentRepository articleCommentRepository;

    private final ArticleCommentMapper articleCommentMapper;

    public ArticleCommentServiceImpl(ArticleCommentRepository articleCommentRepository, ArticleCommentMapper articleCommentMapper) {
        this.articleCommentRepository = articleCommentRepository;
        this.articleCommentMapper = articleCommentMapper;
    }

    @Override
    public ArticleCommentDTO save(ArticleCommentDTO articleCommentDTO) {
        log.debug("Request to save ArticleComment : {}", articleCommentDTO);
        ArticleComment articleComment = articleCommentMapper.toEntity(articleCommentDTO);
        articleComment = articleCommentRepository.save(articleComment);
        return articleCommentMapper.toDto(articleComment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArticleComments");
        return articleCommentRepository.findAll(pageable)
            .map(articleCommentMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleCommentDTO> findOne(Long id) {
        log.debug("Request to get ArticleComment : {}", id);
        return articleCommentRepository.findById(id)
            .map(articleCommentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArticleComment : {}", id);
        articleCommentRepository.deleteById(id);
    }
}
