package cn.hupig.www.code.cmservice.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hupig.www.code.cmservice.domain.Article;
import cn.hupig.www.code.cmservice.repository.ArticleRepository;
import cn.hupig.www.code.cmservice.service.Rewrite_ArticleService;
import cn.hupig.www.code.cmservice.service.dto.ArticleDTO;
import cn.hupig.www.code.cmservice.service.mapper.ArticleMapper;

/**
 * Service Implementation for managing {@link Article}.
 */
@Service
@Transactional
public class Rewrite_ArticleServiceImpl implements Rewrite_ArticleService {

    private final Logger log = LoggerFactory.getLogger(Rewrite_ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    public Rewrite_ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleDTO> findAllState(Pageable pageable) {
        log.debug("Request to get all Articles");
        return articleRepository.findAllByState(pageable, true)
            .map(articleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleDTO> findOneState(Long id) {
        log.debug("Request to get Article : {}", id);
        return articleRepository.findByIdAndState(id, true)
            .map(articleMapper::toDto);
    }

}
