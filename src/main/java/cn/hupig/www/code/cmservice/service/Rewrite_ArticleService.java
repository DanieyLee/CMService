package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.ArticleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.Article}.
 */
public interface Rewrite_ArticleService {

    /**
     * Get all the articles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticleDTO> findAllState(Pageable pageable);

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

}
