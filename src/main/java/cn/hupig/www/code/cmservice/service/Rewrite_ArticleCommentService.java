package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.ArticleCommentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

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

}
