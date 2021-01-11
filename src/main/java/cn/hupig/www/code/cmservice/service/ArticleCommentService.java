package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.ArticleCommentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.ArticleComment}.
 */
public interface ArticleCommentService {

    /**
     * Save a articleComment.
     *
     * @param articleCommentDTO the entity to save.
     * @return the persisted entity.
     */
    ArticleCommentDTO save(ArticleCommentDTO articleCommentDTO);

    /**
     * Get all the articleComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticleCommentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" articleComment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticleCommentDTO> findOne(Long id);

    /**
     * Delete the "id" articleComment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
