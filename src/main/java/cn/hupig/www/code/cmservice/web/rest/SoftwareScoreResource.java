package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.service.SoftwareScoreService;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import cn.hupig.www.code.cmservice.service.dto.SoftwareScoreDTO;

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
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.SoftwareScore}.
 */
@RestController
@RequestMapping("/api")
public class SoftwareScoreResource {

    private final Logger log = LoggerFactory.getLogger(SoftwareScoreResource.class);

    private static final String ENTITY_NAME = "softwareScore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoftwareScoreService softwareScoreService;

    public SoftwareScoreResource(SoftwareScoreService softwareScoreService) {
        this.softwareScoreService = softwareScoreService;
    }

    /**
     * {@code POST  /software-scores} : Create a new softwareScore.
     *
     * @param softwareScoreDTO the softwareScoreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new softwareScoreDTO, or with status {@code 400 (Bad Request)} if the softwareScore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/software-scores")
    public ResponseEntity<SoftwareScoreDTO> createSoftwareScore(@Valid @RequestBody SoftwareScoreDTO softwareScoreDTO) throws URISyntaxException {
        log.debug("REST request to save SoftwareScore : {}", softwareScoreDTO);
        if (softwareScoreDTO.getId() != null) {
            throw new BadRequestAlertException("A new softwareScore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SoftwareScoreDTO result = softwareScoreService.save(softwareScoreDTO);
        return ResponseEntity.created(new URI("/api/software-scores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /software-scores} : Updates an existing softwareScore.
     *
     * @param softwareScoreDTO the softwareScoreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated softwareScoreDTO,
     * or with status {@code 400 (Bad Request)} if the softwareScoreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the softwareScoreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/software-scores")
    public ResponseEntity<SoftwareScoreDTO> updateSoftwareScore(@Valid @RequestBody SoftwareScoreDTO softwareScoreDTO) throws URISyntaxException {
        log.debug("REST request to update SoftwareScore : {}", softwareScoreDTO);
        if (softwareScoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SoftwareScoreDTO result = softwareScoreService.save(softwareScoreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, softwareScoreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /software-scores} : get all the softwareScores.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of softwareScores in body.
     */
    @GetMapping("/software-scores")
    public ResponseEntity<List<SoftwareScoreDTO>> getAllSoftwareScores(Pageable pageable) {
        log.debug("REST request to get a page of SoftwareScores");
        Page<SoftwareScoreDTO> page = softwareScoreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /software-scores/:id} : get the "id" softwareScore.
     *
     * @param id the id of the softwareScoreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the softwareScoreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/software-scores/{id}")
    public ResponseEntity<SoftwareScoreDTO> getSoftwareScore(@PathVariable Long id) {
        log.debug("REST request to get SoftwareScore : {}", id);
        Optional<SoftwareScoreDTO> softwareScoreDTO = softwareScoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(softwareScoreDTO);
    }

    /**
     * {@code DELETE  /software-scores/:id} : delete the "id" softwareScore.
     *
     * @param id the id of the softwareScoreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/software-scores/{id}")
    public ResponseEntity<Void> deleteSoftwareScore(@PathVariable Long id) {
        log.debug("REST request to delete SoftwareScore : {}", id);
        softwareScoreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
