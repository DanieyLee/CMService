package cn.hupig.www.code.cmservice.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cn.hupig.www.code.cmservice.service.ArticleTypeService;
import cn.hupig.www.code.cmservice.service.dto.ArticleTypeDTO;
import io.github.jhipster.web.util.PaginationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.ArticleType}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "006-文章类型管理")
public class Rewrite_ArticleTypeResource {

    private final Logger log = LoggerFactory.getLogger(Rewrite_ArticleTypeResource.class);

    private static final String ENTITY_NAME = "articleType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleTypeService articleTypeService;

    public Rewrite_ArticleTypeResource(ArticleTypeService articleTypeService) {
        this.articleTypeService = articleTypeService;
    }

    /**
     * {@code GET  /article-types} : get all the articleTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articleTypes in body.
     */
    @GetMapping("/public/article-types")
    @ApiOperation(value = "查询所有文章类型-无权限")
    public ResponseEntity<List<ArticleTypeDTO>> getAllArticleTypes(Pageable pageable) {
        log.debug("REST request to get a page of ArticleTypes");
        Page<ArticleTypeDTO> page = articleTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
