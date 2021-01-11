package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.service.ArticlEnclosureService;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import cn.hupig.www.code.cmservice.service.dto.ArticlEnclosureDTO;

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
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.ArticlEnclosure}.
 */
@RestController
@RequestMapping("/api")
public class ArticlEnclosureResource {

    private final Logger log = LoggerFactory.getLogger(ArticlEnclosureResource.class);

    private static final String ENTITY_NAME = "articlEnclosure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticlEnclosureService articlEnclosureService;

    public ArticlEnclosureResource(ArticlEnclosureService articlEnclosureService) {
        this.articlEnclosureService = articlEnclosureService;
    }

    /**
     * {@code POST  /articl-enclosures} : Create a new articlEnclosure.
     *
     * @param articlEnclosureDTO the articlEnclosureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articlEnclosureDTO, or with status {@code 400 (Bad Request)} if the articlEnclosure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/articl-enclosures")
    public ResponseEntity<ArticlEnclosureDTO> createArticlEnclosure(@RequestBody ArticlEnclosureDTO articlEnclosureDTO) throws URISyntaxException {
        log.debug("REST request to save ArticlEnclosure : {}", articlEnclosureDTO);
        if (articlEnclosureDTO.getId() != null) {
            throw new BadRequestAlertException("A new articlEnclosure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticlEnclosureDTO result = articlEnclosureService.save(articlEnclosureDTO);
        return ResponseEntity.created(new URI("/api/articl-enclosures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /articl-enclosures} : Updates an existing articlEnclosure.
     *
     * @param articlEnclosureDTO the articlEnclosureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articlEnclosureDTO,
     * or with status {@code 400 (Bad Request)} if the articlEnclosureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articlEnclosureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/articl-enclosures")
    public ResponseEntity<ArticlEnclosureDTO> updateArticlEnclosure(@RequestBody ArticlEnclosureDTO articlEnclosureDTO) throws URISyntaxException {
        log.debug("REST request to update ArticlEnclosure : {}", articlEnclosureDTO);
        if (articlEnclosureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArticlEnclosureDTO result = articlEnclosureService.save(articlEnclosureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articlEnclosureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /articl-enclosures} : get all the articlEnclosures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articlEnclosures in body.
     */
    @GetMapping("/articl-enclosures")
    public ResponseEntity<List<ArticlEnclosureDTO>> getAllArticlEnclosures(Pageable pageable) {
        log.debug("REST request to get a page of ArticlEnclosures");
        Page<ArticlEnclosureDTO> page = articlEnclosureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /articl-enclosures/:id} : get the "id" articlEnclosure.
     *
     * @param id the id of the articlEnclosureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articlEnclosureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/articl-enclosures/{id}")
    public ResponseEntity<ArticlEnclosureDTO> getArticlEnclosure(@PathVariable Long id) {
        log.debug("REST request to get ArticlEnclosure : {}", id);
        Optional<ArticlEnclosureDTO> articlEnclosureDTO = articlEnclosureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articlEnclosureDTO);
    }

    /**
     * {@code DELETE  /articl-enclosures/:id} : delete the "id" articlEnclosure.
     *
     * @param id the id of the articlEnclosureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/articl-enclosures/{id}")
    public ResponseEntity<Void> deleteArticlEnclosure(@PathVariable Long id) {
        log.debug("REST request to delete ArticlEnclosure : {}", id);
        articlEnclosureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
