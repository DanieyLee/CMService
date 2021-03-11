package cn.hupig.www.code.cmservice.repository;

import cn.hupig.www.code.cmservice.domain.Article;
import cn.hupig.www.code.cmservice.domain.ArticleComment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ArticleComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
	Page<ArticleComment> findAllByArticleId(Pageable pageable, Long articleId);
}
