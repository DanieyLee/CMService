package cn.hupig.www.code.cmservice.repository;

import cn.hupig.www.code.cmservice.domain.ArticlEnclosure;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ArticlEnclosure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticlEnclosureRepository extends JpaRepository<ArticlEnclosure, Long> {
}
