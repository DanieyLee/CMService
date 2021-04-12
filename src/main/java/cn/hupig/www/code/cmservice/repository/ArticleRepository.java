package cn.hupig.www.code.cmservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.hupig.www.code.cmservice.domain.Article;

import java.util.Optional;

/**
 * Spring Data  repository for the Article entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	
	Page<Article> findAllByStateTrue(Pageable pageable);
	
	Page<Article> findAllByStateTrueAndArticleTypeId(Pageable pageable, Long articleTypeId);
	
	Optional<Article> findByIdAndStateTrue(Long id);
}
