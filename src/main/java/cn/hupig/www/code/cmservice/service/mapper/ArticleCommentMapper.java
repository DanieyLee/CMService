package cn.hupig.www.code.cmservice.service.mapper;


import cn.hupig.www.code.cmservice.domain.*;
import cn.hupig.www.code.cmservice.service.dto.ArticleCommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArticleComment} and its DTO {@link ArticleCommentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArticleMapper.class, UserLinkMapper.class})
public interface ArticleCommentMapper extends EntityMapper<ArticleCommentDTO, ArticleComment> {

    @Mapping(source = "article.id", target = "articleId")
    @Mapping(source = "article.title", target = "articleTitle")
    @Mapping(source = "userLink.id", target = "userLinkId")
    @Mapping(source = "userLink.firstName", target = "userLinkFirstName")
    ArticleCommentDTO toDto(ArticleComment articleComment);

    @Mapping(source = "articleId", target = "article")
    @Mapping(source = "userLinkId", target = "userLink")
    ArticleComment toEntity(ArticleCommentDTO articleCommentDTO);

    default ArticleComment fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArticleComment articleComment = new ArticleComment();
        articleComment.setId(id);
        return articleComment;
    }
}
