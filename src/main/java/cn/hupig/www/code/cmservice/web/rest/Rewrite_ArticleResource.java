package cn.hupig.www.code.cmservice.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cn.hupig.www.code.cmservice.service.Rewrite_ArticleService;
import cn.hupig.www.code.cmservice.service.dto.ArticleDTO;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.Article}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "000-分享文章操作")
public class Rewrite_ArticleResource {

    private final Logger log = LoggerFactory.getLogger(Rewrite_ArticleResource.class);

    private static final String ENTITY_NAME = "article";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Rewrite_ArticleService rewrite_ArticleService;

    public Rewrite_ArticleResource(Rewrite_ArticleService rewrite_ArticleService) {
        this.rewrite_ArticleService = rewrite_ArticleService;
    }

    /**
     * {@code GET  /articles} : get all the articles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articles in body.
     */
    @GetMapping("/public/articles")
    @ApiOperation(value = "查询所有文章-无权限-带分页-只查上架的")
    public ResponseEntity<List<ArticleDTO>> getAllArticles(Pageable pageable) {
        log.debug("REST request to get a page of Articles");
        Page<ArticleDTO> page = rewrite_ArticleService.findAllState(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code GET  /articles} : get top8 the articles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articles in body.
     */
    @GetMapping("/public/articles/top")
    @ApiOperation(value = "查询8篇文章-无权限-只查上架的")
    public ResponseEntity<List<ArticleDTO>> getTopArticles() {
        log.debug("REST request to get a page of Articles");
        Page<ArticleDTO> page = rewrite_ArticleService.findTopState();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code GET  /articles/:id} : get the "id" article.
     *
     * @param id the id of the articleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public/articles/{id}")
    @ApiOperation(value = "查询单个文章-无权限")
    public ResponseEntity<ArticleDTO> getArticle(@PathVariable Long id) {
        log.debug("REST request to get Article : {}", id);
        Optional<ArticleDTO> articleDTO = rewrite_ArticleService.findOneState(id);
        return ResponseUtil.wrapOrNotFound(articleDTO);
    }

}
