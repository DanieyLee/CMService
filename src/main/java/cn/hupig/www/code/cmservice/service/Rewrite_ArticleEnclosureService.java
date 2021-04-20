package cn.hupig.www.code.cmservice.service;

import cn.hupig.www.code.cmservice.service.dto.ArticleEnclosureDTO;
import cn.hupig.www.code.cmservice.web.rest.vm.FileAndNameVM;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.ArticleEnclosure}.
 */
public interface Rewrite_ArticleEnclosureService {

    /**
     * create a ArticleEnclosure.
     *
     * @param ArticleEnclosureDTO the entity to save.
     * @return the persisted entity.
     */
	ArticleEnclosureDTO createArticleEnclosure(FileAndNameVM fileAndNameVM);

}
