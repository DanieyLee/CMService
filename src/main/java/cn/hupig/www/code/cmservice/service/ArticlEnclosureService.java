package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.ArticlEnclosureDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.ArticlEnclosure}.
 */
public interface ArticlEnclosureService {

    /**
     * Save a articlEnclosure.
     *
     * @param articlEnclosureDTO the entity to save.
     * @return the persisted entity.
     */
    ArticlEnclosureDTO save(ArticlEnclosureDTO articlEnclosureDTO);

    /**
     * Get all the articlEnclosures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticlEnclosureDTO> findAll(Pageable pageable);


    /**
     * Get the "id" articlEnclosure.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticlEnclosureDTO> findOne(Long id);

    /**
     * Delete the "id" articlEnclosure.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
