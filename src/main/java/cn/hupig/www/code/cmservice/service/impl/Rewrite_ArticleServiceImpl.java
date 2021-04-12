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

import cn.hupig.www.code.cmservice.domain.Article;
import cn.hupig.www.code.cmservice.repository.ArticleRepository;
import cn.hupig.www.code.cmservice.repository.UserLinkRepository;
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
    
    private final UserLinkRepository userLinkRepository;

    private final ArticleMapper articleMapper;

    public Rewrite_ArticleServiceImpl(ArticleRepository articleRepository, UserLinkRepository userLinkRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.userLinkRepository = userLinkRepository;
        this.articleMapper = articleMapper;
    }
    
	@Override
	@Transactional(readOnly = true)
	public Page<ArticleDTO> findAllState(Long id, Pageable pageable) {
		log.debug("Request to get all Articles by type");
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),Sort.Direction.DESC,"updateTime");
		return (id == 0 ? articleRepository.findAllByStateTrue(pageable):
			articleRepository.findAllByStateTrueAndArticleTypeId(pageable, id)).map(articleMapper::toDto);
	}
	
    @Override
    @Transactional(readOnly = true)
    public Page<ArticleDTO> findTopState() {
        log.debug("Request to get all Articles");
        Pageable pageable = PageRequest.of(0, 8,Sort.Direction.DESC,"updateTime");
        return articleRepository.findAllByStateTrue(pageable)
            .map(articleMapper::toDto);
    }

    @Override
    public Optional<ArticleDTO> findOneState(Long id) {
        log.debug("Request to get Article : {}", id);
        return articleRepository.findByIdAndStateTrue(id)
        	.map(article -> {
        		article.setViews(article.getViews() + 1);
        		return articleRepository.save(article);
        	})
            .map(articleMapper::toDto);
    }

	@Override
	public Optional<ArticleDTO> findOneLikeAndState(Long id) {
        log.debug("Request to get Article : {}", id);
        return articleRepository.findByIdAndStateTrue(id)
        	.map(article -> {
        		article.setLikeNumber(article.getLikeNumber() + 1);
        		return articleRepository.save(article);
        	})
            .map(articleMapper::toDto);
	}

	@Override
	public ArticleDTO createArticle(ArticleDTO articleDTO) {
		log.debug("Request to save Article : {}", articleDTO);
		articleDTO.setViews(0L);
		articleDTO.setLikeNumber(0L);
		articleDTO.setUserLinkId(userLinkRepository.findOneByUserId(articleDTO.getUserLinkId()).get().getId());		
        Article article = articleMapper.toEntity(articleDTO);
        article = articleRepository.save(article);
        return articleMapper.toDto(article);
	}

	@Override
	public ArticleDTO updateArticle(ArticleDTO articleDTO) {
		log.debug("Request to save Article : {}", articleDTO);
        Article article = articleMapper.toEntity(articleDTO);
        article = articleRepository.save(article);
        return articleMapper.toDto(article);
	}

}
