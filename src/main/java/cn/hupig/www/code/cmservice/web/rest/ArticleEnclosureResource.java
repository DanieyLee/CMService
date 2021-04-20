package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.service.ArticleEnclosureService;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import cn.hupig.www.code.cmservice.service.dto.ArticleEnclosureDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.ArticleEnclosure}.
 */
@RestController
@RequestMapping("/api")
public class ArticleEnclosureResource {

    private final Logger log = LoggerFactory.getLogger(ArticleEnclosureResource.class);

    private static final String ENTITY_NAME = "articleEnclosure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleEnclosureService articleEnclosureService;

    public ArticleEnclosureResource(ArticleEnclosureService articleEnclosureService) {
        this.articleEnclosureService = articleEnclosureService;
    }

    /**
     * {@code POST  /article-enclosures} : Create a new ArticleEnclosure.
     *
     * @param ArticleEnclosureDTO the ArticleEnclosureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ArticleEnclosureDTO, or with status {@code 400 (Bad Request)} if the ArticleEnclosure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/article-enclosures")
    public ResponseEntity<ArticleEnclosureDTO> createArticleEnclosure(@RequestBody ArticleEnclosureDTO articleEnclosureDTO) throws URISyntaxException {
        log.debug("REST request to save ArticleEnclosure : {}", articleEnclosureDTO);
        if (articleEnclosureDTO.getId() != null) {
            throw new BadRequestAlertException("A new ArticleEnclosure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticleEnclosureDTO result = articleEnclosureService.save(articleEnclosureDTO);
        return ResponseEntity.created(new URI("/api/article-enclosures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /article-enclosures} : Updates an existing ArticleEnclosure.
     *
     * @param ArticleEnclosureDTO the ArticleEnclosureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ArticleEnclosureDTO,
     * or with status {@code 400 (Bad Request)} if the ArticleEnclosureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ArticleEnclosureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/article-enclosures")
    public ResponseEntity<ArticleEnclosureDTO> updateArticleEnclosure(@RequestBody ArticleEnclosureDTO articleEnclosureDTO) throws URISyntaxException {
        log.debug("REST request to update ArticleEnclosure : {}", articleEnclosureDTO);
        if (articleEnclosureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArticleEnclosureDTO result = articleEnclosureService.save(articleEnclosureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleEnclosureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /article-enclosures} : get all the ArticleEnclosures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ArticleEnclosures in body.
     */
    @GetMapping("/article-enclosures")
    public ResponseEntity<List<ArticleEnclosureDTO>> getAllArticleEnclosures(Pageable pageable) {
        log.debug("REST request to get a page of ArticleEnclosures");
        Page<ArticleEnclosureDTO> page = articleEnclosureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /article-enclosures/:id} : get the "id" ArticleEnclosure.
     *
     * @param id the id of the ArticleEnclosureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ArticleEnclosureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/article-enclosures/{id}")
    public ResponseEntity<ArticleEnclosureDTO> getArticleEnclosure(@PathVariable Long id) {
        log.debug("REST request to get ArticleEnclosure : {}", id);
        Optional<ArticleEnclosureDTO> articleEnclosureDTO = articleEnclosureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleEnclosureDTO);
    }

    /**
     * {@code DELETE  /article-enclosures/:id} : delete the "id" ArticleEnclosure.
     *
     * @param id the id of the ArticleEnclosureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/article-enclosures/{id}")
    public ResponseEntity<Void> deleteArticleEnclosure(@PathVariable Long id) {
        log.debug("REST request to delete ArticleEnclosure : {}", id);
        articleEnclosureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
