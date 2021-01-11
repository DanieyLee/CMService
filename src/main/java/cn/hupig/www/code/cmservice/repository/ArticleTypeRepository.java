package cn.hupig.www.code.cmservice.repository;

import cn.hupig.www.code.cmservice.domain.ArticleType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ArticleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleTypeRepository extends JpaRepository<ArticleType, Long> {
}
