package cn.hupig.www.code.cmservice.web.rest;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hupig.www.code.cmservice.security.AuthoritiesConstants;
import cn.hupig.www.code.cmservice.service.Rewrite_ArticleEnclosureService;
import cn.hupig.www.code.cmservice.service.dto.ArticleEnclosureDTO;
import cn.hupig.www.code.cmservice.web.rest.vm.FileAndNameVM;
import io.github.jhipster.web.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.ArticleEnclosure}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "011-文章附件管理")
public class Rewrite_ArticleEnclosureResource {

    private final Logger log = LoggerFactory.getLogger(Rewrite_ArticleEnclosureResource.class);

    private static final String ENTITY_NAME = "articleEnclosure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Rewrite_ArticleEnclosureService rewrite_ArticleEnclosureService;

    public Rewrite_ArticleEnclosureResource(Rewrite_ArticleEnclosureService rewrite_ArticleEnclosureService) {
        this.rewrite_ArticleEnclosureService = rewrite_ArticleEnclosureService;
    }

    /**
     * {@code POST  /article-enclosures} : Create a new ArticleEnclosure.
     *
     * @param ArticleEnclosureDTO the ArticleEnclosureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ArticleEnclosureDTO, or with status {@code 400 (Bad Request)} if the ArticleEnclosure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/article-enclosures/create")
    @ApiOperation(value = "创建新的文章附件-管理员")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ArticleEnclosureDTO> createArticleEnclosure(@RequestBody FileAndNameVM fileAndNameVM) throws URISyntaxException {
        log.debug("REST request to save ArticleEnclosure : {}", fileAndNameVM);
        ArticleEnclosureDTO result = rewrite_ArticleEnclosureService.createArticleEnclosure(fileAndNameVM);
        return ResponseEntity.created(new URI("/api/article-enclosures/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }
}
