package cn.hupig.www.code.cmservice.service.mapper;


import cn.hupig.www.code.cmservice.domain.*;
import cn.hupig.www.code.cmservice.service.dto.ArticleTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArticleType} and its DTO {@link ArticleTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ArticleTypeMapper extends EntityMapper<ArticleTypeDTO, ArticleType> {



    default ArticleType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArticleType articleType = new ArticleType();
        articleType.setId(id);
        return articleType;
    }
}
