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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cn.hupig.www.code.cmservice.service.Rewrite_ArticleCommentService;
import cn.hupig.www.code.cmservice.service.dto.ArticleCommentDTO;
import io.github.jhipster.web.util.PaginationUtil;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.ArticleComment}.
 */
@RestController
@RequestMapping("/api")
public class Rewrite_ArticleCommentResource {

    private final Logger log = LoggerFactory.getLogger(Rewrite_ArticleCommentResource.class);

    private static final String ENTITY_NAME = "articleComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Rewrite_ArticleCommentService rewrite_ArticleCommentService;

    public Rewrite_ArticleCommentResource(Rewrite_ArticleCommentService rewrite_ArticleCommentService) {
        this.rewrite_ArticleCommentService = rewrite_ArticleCommentService;
    }

//    /**
//     * {@code POST  /article-comments} : Create a new articleComment.
//     *
//     * @param articleCommentDTO the articleCommentDTO to create.
//     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleCommentDTO, or with status {@code 400 (Bad Request)} if the articleComment has already an ID.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PostMapping("/article-comments")
//    public ResponseEntity<ArticleCommentDTO> createArticleComment(@RequestBody ArticleCommentDTO articleCommentDTO) throws URISyntaxException {
//        log.debug("REST request to save ArticleComment : {}", articleCommentDTO);
//        if (articleCommentDTO.getId() != null) {
//            throw new BadRequestAlertException("A new articleComment cannot already have an ID", ENTITY_NAME, "idexists");
//        }
//        ArticleCommentDTO result = articleCommentService.save(articleCommentDTO);
//        return ResponseEntity.created(new URI("/api/article-comments/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }

    /**
     * {@code GET  /article-comments/article-id} : get all the id articleComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articleComments in body.
     */
    @GetMapping("/public/article-comments/{id}")
    public ResponseEntity<List<ArticleCommentDTO>> getAllArticleComments(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of ArticleComments");
        Page<ArticleCommentDTO> page = rewrite_ArticleCommentService.findAllByArticle(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
