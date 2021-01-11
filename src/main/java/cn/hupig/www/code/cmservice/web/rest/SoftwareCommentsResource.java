package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.service.SoftwareCommentsService;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import cn.hupig.www.code.cmservice.service.dto.SoftwareCommentsDTO;

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
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.SoftwareComments}.
 */
@RestController
@RequestMapping("/api")
public class SoftwareCommentsResource {

    private final Logger log = LoggerFactory.getLogger(SoftwareCommentsResource.class);

    private static final String ENTITY_NAME = "softwareComments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoftwareCommentsService softwareCommentsService;

    public SoftwareCommentsResource(SoftwareCommentsService softwareCommentsService) {
        this.softwareCommentsService = softwareCommentsService;
    }

    /**
     * {@code POST  /software-comments} : Create a new softwareComments.
     *
     * @param softwareCommentsDTO the softwareCommentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new softwareCommentsDTO, or with status {@code 400 (Bad Request)} if the softwareComments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/software-comments")
    public ResponseEntity<SoftwareCommentsDTO> createSoftwareComments(@Valid @RequestBody SoftwareCommentsDTO softwareCommentsDTO) throws URISyntaxException {
        log.debug("REST request to save SoftwareComments : {}", softwareCommentsDTO);
        if (softwareCommentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new softwareComments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SoftwareCommentsDTO result = softwareCommentsService.save(softwareCommentsDTO);
        return ResponseEntity.created(new URI("/api/software-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /software-comments} : Updates an existing softwareComments.
     *
     * @param softwareCommentsDTO the softwareCommentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated softwareCommentsDTO,
     * or with status {@code 400 (Bad Request)} if the softwareCommentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the softwareCommentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/software-comments")
    public ResponseEntity<SoftwareCommentsDTO> updateSoftwareComments(@Valid @RequestBody SoftwareCommentsDTO softwareCommentsDTO) throws URISyntaxException {
        log.debug("REST request to update SoftwareComments : {}", softwareCommentsDTO);
        if (softwareCommentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SoftwareCommentsDTO result = softwareCommentsService.save(softwareCommentsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, softwareCommentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /software-comments} : get all the softwareComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of softwareComments in body.
     */
    @GetMapping("/software-comments")
    public ResponseEntity<List<SoftwareCommentsDTO>> getAllSoftwareComments(Pageable pageable) {
        log.debug("REST request to get a page of SoftwareComments");
        Page<SoftwareCommentsDTO> page = softwareCommentsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /software-comments/:id} : get the "id" softwareComments.
     *
     * @param id the id of the softwareCommentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the softwareCommentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/software-comments/{id}")
    public ResponseEntity<SoftwareCommentsDTO> getSoftwareComments(@PathVariable Long id) {
        log.debug("REST request to get SoftwareComments : {}", id);
        Optional<SoftwareCommentsDTO> softwareCommentsDTO = softwareCommentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(softwareCommentsDTO);
    }

    /**
     * {@code DELETE  /software-comments/:id} : delete the "id" softwareComments.
     *
     * @param id the id of the softwareCommentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/software-comments/{id}")
    public ResponseEntity<Void> deleteSoftwareComments(@PathVariable Long id) {
        log.debug("REST request to delete SoftwareComments : {}", id);
        softwareCommentsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
