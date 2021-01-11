package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.service.SystemImageService;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import cn.hupig.www.code.cmservice.service.dto.SystemImageDTO;

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
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.SystemImage}.
 */
@RestController
@RequestMapping("/api")
public class SystemImageResource {

    private final Logger log = LoggerFactory.getLogger(SystemImageResource.class);

    private static final String ENTITY_NAME = "systemImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemImageService systemImageService;

    public SystemImageResource(SystemImageService systemImageService) {
        this.systemImageService = systemImageService;
    }

    /**
     * {@code POST  /system-images} : Create a new systemImage.
     *
     * @param systemImageDTO the systemImageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemImageDTO, or with status {@code 400 (Bad Request)} if the systemImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/system-images")
    public ResponseEntity<SystemImageDTO> createSystemImage(@Valid @RequestBody SystemImageDTO systemImageDTO) throws URISyntaxException {
        log.debug("REST request to save SystemImage : {}", systemImageDTO);
        if (systemImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemImageDTO result = systemImageService.save(systemImageDTO);
        return ResponseEntity.created(new URI("/api/system-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /system-images} : Updates an existing systemImage.
     *
     * @param systemImageDTO the systemImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemImageDTO,
     * or with status {@code 400 (Bad Request)} if the systemImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/system-images")
    public ResponseEntity<SystemImageDTO> updateSystemImage(@Valid @RequestBody SystemImageDTO systemImageDTO) throws URISyntaxException {
        log.debug("REST request to update SystemImage : {}", systemImageDTO);
        if (systemImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemImageDTO result = systemImageService.save(systemImageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /system-images} : get all the systemImages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemImages in body.
     */
    @GetMapping("/system-images")
    public ResponseEntity<List<SystemImageDTO>> getAllSystemImages(Pageable pageable) {
        log.debug("REST request to get a page of SystemImages");
        Page<SystemImageDTO> page = systemImageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /system-images/:id} : get the "id" systemImage.
     *
     * @param id the id of the systemImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/system-images/{id}")
    public ResponseEntity<SystemImageDTO> getSystemImage(@PathVariable Long id) {
        log.debug("REST request to get SystemImage : {}", id);
        Optional<SystemImageDTO> systemImageDTO = systemImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemImageDTO);
    }

    /**
     * {@code DELETE  /system-images/:id} : delete the "id" systemImage.
     *
     * @param id the id of the systemImageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/system-images/{id}")
    public ResponseEntity<Void> deleteSystemImage(@PathVariable Long id) {
        log.debug("REST request to delete SystemImage : {}", id);
        systemImageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
