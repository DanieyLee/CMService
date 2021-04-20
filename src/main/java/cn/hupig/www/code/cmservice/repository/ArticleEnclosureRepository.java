package cn.hupig.www.code.cmservice.repository;

import cn.hupig.www.code.cmservice.domain.ArticleEnclosure;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ArticleEnclosure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleEnclosureRepository extends JpaRepository<ArticleEnclosure, Long> {
}
