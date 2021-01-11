package cn.hupig.www.code.cmservice.service.mapper;


import cn.hupig.www.code.cmservice.domain.*;
import cn.hupig.www.code.cmservice.service.dto.ArticleEnclosureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArticleEnclosure} and its DTO {@link ArticleEnclosureDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArticleMapper.class})
public interface ArticleEnclosureMapper extends EntityMapper<ArticleEnclosureDTO, ArticleEnclosure> {

    @Mapping(source = "article.id", target = "articleId")
    @Mapping(source = "article.title", target = "articleTitle")
    ArticleEnclosureDTO toDto(ArticleEnclosure articleEnclosure);

    @Mapping(source = "articleId", target = "article")
    ArticleEnclosure toEntity(ArticleEnclosureDTO articleEnclosureDTO);

    default ArticleEnclosure fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArticleEnclosure articleEnclosure = new ArticleEnclosure();
        articleEnclosure.setId(id);
        return articleEnclosure;
    }
}
