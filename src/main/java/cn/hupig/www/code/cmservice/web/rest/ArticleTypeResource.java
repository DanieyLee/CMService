package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.service.ArticleTypeService;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import cn.hupig.www.code.cmservice.service.dto.ArticleTypeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.ArticleType}.
 */
@RestController
@RequestMapping("/api")
public class ArticleTypeResource {

    private final Logger log = LoggerFactory.getLogger(ArticleTypeResource.class);

    private static final String ENTITY_NAME = "articleType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleTypeService articleTypeService;

    public ArticleTypeResource(ArticleTypeService articleTypeService) {
        this.articleTypeService = articleTypeService;
    }

    /**
     * {@code POST  /article-types} : Create a new articleType.
     *
     * @param articleTypeDTO the articleTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleTypeDTO, or with status {@code 400 (Bad Request)} if the articleType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/article-types")
    public ResponseEntity<ArticleTypeDTO> createArticleType(@Valid @RequestBody ArticleTypeDTO articleTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ArticleType : {}", articleTypeDTO);
        if (articleTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new articleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
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
    @PutMapping("/article-types")
    public ResponseEntity<ArticleTypeDTO> updateArticleType(@Valid @RequestBody ArticleTypeDTO articleTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ArticleType : {}", articleTypeDTO);
        if (articleTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArticleTypeDTO result = articleTypeService.save(articleTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /article-types} : get all the articleTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articleTypes in body.
     */
    @GetMapping("/article-types")
    public ResponseEntity<List<ArticleTypeDTO>> getAllArticleTypes(Pageable pageable) {
        log.debug("REST request to get a page of ArticleTypes");
        Page<ArticleTypeDTO> page = articleTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /article-types/:id} : get the "id" articleType.
     *
     * @param id the id of the articleTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/article-types/{id}")
    public ResponseEntity<ArticleTypeDTO> getArticleType(@PathVariable Long id) {
        log.debug("REST request to get ArticleType : {}", id);
        Optional<ArticleTypeDTO> articleTypeDTO = articleTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleTypeDTO);
    }

    /**
     * {@code DELETE  /article-types/:id} : delete the "id" articleType.
     *
     * @param id the id of the articleTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/article-types/{id}")
    public ResponseEntity<Void> deleteArticleType(@PathVariable Long id) {
        log.debug("REST request to delete ArticleType : {}", id);
        articleTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
