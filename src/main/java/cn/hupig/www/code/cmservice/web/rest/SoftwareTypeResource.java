package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.service.SoftwareTypeService;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import cn.hupig.www.code.cmservice.service.dto.SoftwareTypeDTO;

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
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.SoftwareType}.
 */
@RestController
@RequestMapping("/api")
public class SoftwareTypeResource {

    private final Logger log = LoggerFactory.getLogger(SoftwareTypeResource.class);

    private static final String ENTITY_NAME = "softwareType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoftwareTypeService softwareTypeService;

    public SoftwareTypeResource(SoftwareTypeService softwareTypeService) {
        this.softwareTypeService = softwareTypeService;
    }

    /**
     * {@code POST  /software-types} : Create a new softwareType.
     *
     * @param softwareTypeDTO the softwareTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new softwareTypeDTO, or with status {@code 400 (Bad Request)} if the softwareType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/software-types")
    public ResponseEntity<SoftwareTypeDTO> createSoftwareType(@Valid @RequestBody SoftwareTypeDTO softwareTypeDTO) throws URISyntaxException {
        log.debug("REST request to save SoftwareType : {}", softwareTypeDTO);
        if (softwareTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new softwareType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SoftwareTypeDTO result = softwareTypeService.save(softwareTypeDTO);
        return ResponseEntity.created(new URI("/api/software-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /software-types} : Updates an existing softwareType.
     *
     * @param softwareTypeDTO the softwareTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated softwareTypeDTO,
     * or with status {@code 400 (Bad Request)} if the softwareTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the softwareTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/software-types")
    public ResponseEntity<SoftwareTypeDTO> updateSoftwareType(@Valid @RequestBody SoftwareTypeDTO softwareTypeDTO) throws URISyntaxException {
        log.debug("REST request to update SoftwareType : {}", softwareTypeDTO);
        if (softwareTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SoftwareTypeDTO result = softwareTypeService.save(softwareTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, softwareTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /software-types} : get all the softwareTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of softwareTypes in body.
     */
    @GetMapping("/software-types")
    public ResponseEntity<List<SoftwareTypeDTO>> getAllSoftwareTypes(Pageable pageable) {
        log.debug("REST request to get a page of SoftwareTypes");
        Page<SoftwareTypeDTO> page = softwareTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /software-types/:id} : get the "id" softwareType.
     *
     * @param id the id of the softwareTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the softwareTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/software-types/{id}")
    public ResponseEntity<SoftwareTypeDTO> getSoftwareType(@PathVariable Long id) {
        log.debug("REST request to get SoftwareType : {}", id);
        Optional<SoftwareTypeDTO> softwareTypeDTO = softwareTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(softwareTypeDTO);
    }

    /**
     * {@code DELETE  /software-types/:id} : delete the "id" softwareType.
     *
     * @param id the id of the softwareTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/software-types/{id}")
    public ResponseEntity<Void> deleteSoftwareType(@PathVariable Long id) {
        log.debug("REST request to delete SoftwareType : {}", id);
        softwareTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
