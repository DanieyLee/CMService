package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.ArticleTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.ArticleType}.
 */
public interface ArticleTypeService {

    /**
     * Save a articleType.
     *
     * @param articleTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ArticleTypeDTO save(ArticleTypeDTO articleTypeDTO);

    /**
     * Get all the articleTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticleTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" articleType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticleTypeDTO> findOne(Long id);

    /**
     * Delete the "id" articleType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
