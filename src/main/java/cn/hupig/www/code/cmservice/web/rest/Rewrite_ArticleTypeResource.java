package cn.hupig.www.code.cmservice.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cn.hupig.www.code.cmservice.service.ArticleTypeService;
import cn.hupig.www.code.cmservice.service.UserService;
import cn.hupig.www.code.cmservice.service.dto.ArticleTypeDTO;
import cn.hupig.www.code.cmservice.service.utils.Times;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
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
    
    private final UserService userService;

    public Rewrite_ArticleTypeResource(ArticleTypeService articleTypeService, UserService userService) {
        this.articleTypeService = articleTypeService;
        this.userService = userService;
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
    
    /**
     * {@code POST  /article-types} : Create a new articleType.
     *
     * @param articleTypeDTO the articleTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleTypeDTO, or with status {@code 400 (Bad Request)} if the articleType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/article-types/create")
    @ApiOperation(value = "创建文章类型-自动获取时间")
    public ResponseEntity<ArticleTypeDTO> createArticleType(@Valid @RequestBody ArticleTypeDTO articleTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ArticleType : {}", articleTypeDTO);
        if (articleTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new articleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        String userName = userService.getUserWithAuthorities().get().getFirstName();
        articleTypeDTO.setCreateUser(userName);
        articleTypeDTO.setCreatTime(Times.getInstant());
        articleTypeDTO.setUpdateUser(userName);
        articleTypeDTO.setUpdateTime(Times.getInstant());
        ArticleTypeDTO result = articleTypeService.save(articleTypeDTO);
        return ResponseEntity.created(new URI("/api/article-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /article-types} : Updates an existing articleType.
     *
     * @param articleTypeDTO the articleTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleTypeDTO,
     * or with status {@code 400 (Bad Request)} if the articleTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articleTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/article-types/update")
    @ApiOperation(value = "修改文章类型-自动获取时间")
    public ResponseEntity<ArticleTypeDTO> updateArticleType(@Valid @RequestBody ArticleTypeDTO articleTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ArticleType : {}", articleTypeDTO);
        if (articleTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        String userName = userService.getUserWithAuthorities().get().getFirstName();
        articleTypeDTO.setUpdateUser(userName);
        articleTypeDTO.setUpdateTime(Times.getInstant());
        ArticleTypeDTO result = articleTypeService.save(articleTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleTypeDTO.getId().toString()))
            .body(result);
    }
}
