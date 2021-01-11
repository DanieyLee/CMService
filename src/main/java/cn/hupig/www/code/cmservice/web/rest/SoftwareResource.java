package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.service.SoftwareService;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import cn.hupig.www.code.cmservice.service.dto.SoftwareDTO;

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
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.Software}.
 */
@RestController
@RequestMapping("/api")
public class SoftwareResource {

    private final Logger log = LoggerFactory.getLogger(SoftwareResource.class);

    private static final String ENTITY_NAME = "software";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoftwareService softwareService;

    public SoftwareResource(SoftwareService softwareService) {
        this.softwareService = softwareService;
    }

    /**
     * {@code POST  /software} : Create a new software.
     *
     * @param softwareDTO the softwareDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new softwareDTO, or with status {@code 400 (Bad Request)} if the software has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/software")
    public ResponseEntity<SoftwareDTO> createSoftware(@Valid @RequestBody SoftwareDTO softwareDTO) throws URISyntaxException {
        log.debug("REST request to save Software : {}", softwareDTO);
        if (softwareDTO.getId() != null) {
            throw new BadRequestAlertException("A new software cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SoftwareDTO result = softwareService.save(softwareDTO);
        return ResponseEntity.created(new URI("/api/software/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /software} : Updates an existing software.
     *
     * @param softwareDTO the softwareDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated softwareDTO,
     * or with status {@code 400 (Bad Request)} if the softwareDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the softwareDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/software")
    public ResponseEntity<SoftwareDTO> updateSoftware(@Valid @RequestBody SoftwareDTO softwareDTO) throws URISyntaxException {
        log.debug("REST request to update Software : {}", softwareDTO);
        if (softwareDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SoftwareDTO result = softwareService.save(softwareDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, softwareDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /software} : get all the software.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of software in body.
     */
    @GetMapping("/software")
    public ResponseEntity<List<SoftwareDTO>> getAllSoftware(Pageable pageable) {
        log.debug("REST request to get a page of Software");
        Page<SoftwareDTO> page = softwareService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /software/:id} : get the "id" software.
     *
     * @param id the id of the softwareDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the softwareDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/software/{id}")
    public ResponseEntity<SoftwareDTO> getSoftware(@PathVariable Long id) {
        log.debug("REST request to get Software : {}", id);
        Optional<SoftwareDTO> softwareDTO = softwareService.findOne(id);
        return ResponseUtil.wrapOrNotFound(softwareDTO);
    }

    /**
     * {@code DELETE  /software/:id} : delete the "id" software.
     *
     * @param id the id of the softwareDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/software/{id}")
    public ResponseEntity<Void> deleteSoftware(@PathVariable Long id) {
        log.debug("REST request to delete Software : {}", id);
        softwareService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
