package cn.hupig.www.code.cmservice.service.mapper;


import cn.hupig.www.code.cmservice.domain.*;
import cn.hupig.www.code.cmservice.service.dto.ArticlEnclosureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArticlEnclosure} and its DTO {@link ArticlEnclosureDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArticleMapper.class})
public interface ArticlEnclosureMapper extends EntityMapper<ArticlEnclosureDTO, ArticlEnclosure> {

    @Mapping(source = "article.id", target = "articleId")
    @Mapping(source = "article.title", target = "articleTitle")
    ArticlEnclosureDTO toDto(ArticlEnclosure articlEnclosure);

    @Mapping(source = "articleId", target = "article")
    ArticlEnclosure toEntity(ArticlEnclosureDTO articlEnclosureDTO);

    default ArticlEnclosure fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArticlEnclosure articlEnclosure = new ArticlEnclosure();
        articlEnclosure.setId(id);
        return articlEnclosure;
    }
}
