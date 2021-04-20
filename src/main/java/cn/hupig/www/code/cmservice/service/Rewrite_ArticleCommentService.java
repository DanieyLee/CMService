package cn.hupig.www.code.cmservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.hupig.www.code.cmservice.service.dto.ArticleCommentDTO;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.ArticleComment}.
 */
public interface Rewrite_ArticleCommentService {

    /**
     * Get all the articleComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticleCommentDTO> findAllByArticle(Long id, Pageable pageable);

    /**
     * Create a articleComment.
     *
     * @param articleCommentDTO the entity to create.
     * @return the persisted entity.
     */
    Page<ArticleCommentDTO> createArticleComment(ArticleCommentDTO articleCommentDTO);
    
}
