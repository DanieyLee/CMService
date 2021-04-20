package cn.hupig.www.code.cmservice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.hupig.www.code.cmservice.service.dto.ArticleDTO;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.Article}.
 */
public interface Rewrite_ArticleService {

    /**
     * Get all the articles by type.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticleDTO> findAllState(Long id, Pageable pageable);
    
    /**
     * Get top10 the articles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticleDTO> findTopState();

    /**
     * Get the "id" article.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticleDTO> findOneState(Long id);
    
    /**
     * like "id" article.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticleDTO> findOneLikeAndState(Long id);

    /**
     * Save a article.
     *
     * @param articleDTO the entity to save.
     * @return the persisted entity.
     */
    ArticleDTO createArticle(ArticleDTO articleDTO);
    
    /**
     * Save a article.
     *
     * @param articleDTO the entity to save.
     * @return the persisted entity.
     */
    ArticleDTO updateArticle(ArticleDTO articleDTO);
    
}
