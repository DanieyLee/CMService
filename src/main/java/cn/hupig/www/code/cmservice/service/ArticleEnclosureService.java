package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.ArticleEnclosureDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.ArticleEnclosure}.
 */
public interface ArticleEnclosureService {

    /**
     * Save a ArticleEnclosure.
     *
     * @param ArticleEnclosureDTO the entity to save.
     * @return the persisted entity.
     */
    ArticleEnclosureDTO save(ArticleEnclosureDTO articleEnclosureDTO);

    /**
     * Get all the ArticleEnclosures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticleEnclosureDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ArticleEnclosure.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticleEnclosureDTO> findOne(Long id);

    /**
     * Delete the "id" ArticleEnclosure.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
