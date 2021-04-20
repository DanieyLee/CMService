package cn.hupig.www.code.cmservice.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cn.hupig.www.code.cmservice.service.ArticleCommentService;
import cn.hupig.www.code.cmservice.service.dto.ArticleCommentDTO;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.ArticleComment}.
 */
@RestController
@RequestMapping("/api")
public class ArticleCommentResource {

    private final Logger log = LoggerFactory.getLogger(ArticleCommentResource.class);

    private static final String ENTITY_NAME = "articleComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleCommentService articleCommentService;

    public ArticleCommentResource(ArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
    }

    /**
     * {@code POST  /article-comments} : Create a new articleComment.
     *
     * @param articleCommentDTO the articleCommentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleCommentDTO, or with status {@code 400 (Bad Request)} if the articleComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/article-comments")
    public ResponseEntity<ArticleCommentDTO> createArticleComment(@RequestBody ArticleCommentDTO articleCommentDTO) throws URISyntaxException {
        log.debug("REST request to save ArticleComment : {}", articleCommentDTO);
        if (articleCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new articleComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticleCommentDTO result = articleCommentService.save(articleCommentDTO);
        return ResponseEntity.created(new URI("/api/article-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /article-comments} : Updates an existing articleComment.
     *
     * @param articleCommentDTO the articleCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleCommentDTO,
     * or with status {@code 400 (Bad Request)} if the articleCommentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articleCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/article-comments")
    public ResponseEntity<ArticleCommentDTO> updateArticleComment(@RequestBody ArticleCommentDTO articleCommentDTO) throws URISyntaxException {
        log.debug("REST request to update ArticleComment : {}", articleCommentDTO);
        if (articleCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArticleCommentDTO result = articleCommentService.save(articleCommentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /article-comments} : get all the articleComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articleComments in body.
     */
    @GetMapping("/article-comments")
    public ResponseEntity<List<ArticleCommentDTO>> getAllArticleComments(Pageable pageable) {
        log.debug("REST request to get a page of ArticleComments");
        Page<ArticleCommentDTO> page = articleCommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /article-comments/:id} : get the "id" articleComment.
     *
     * @param id the id of the articleCommentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleCommentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/article-comments/{id}")
    public ResponseEntity<ArticleCommentDTO> getArticleComment(@PathVariable Long id) {
        log.debug("REST request to get ArticleComment : {}", id);
        Optional<ArticleCommentDTO> articleCommentDTO = articleCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleCommentDTO);
    }

    /**
     * {@code DELETE  /article-comments/:id} : delete the "id" articleComment.
     *
     * @param id the id of the articleCommentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/article-comments/{id}")
    public ResponseEntity<Void> deleteArticleComment(@PathVariable Long id) {
        log.debug("REST request to delete ArticleComment : {}", id);
        articleCommentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
