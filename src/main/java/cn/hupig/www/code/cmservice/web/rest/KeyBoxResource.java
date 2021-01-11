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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cn.hupig.www.code.cmservice.security.AuthoritiesConstants;
import cn.hupig.www.code.cmservice.service.KeyBoxService;
import cn.hupig.www.code.cmservice.service.dto.KeyBoxDTO;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.KeyBox}.
 */
@RestController
@RequestMapping("/api")
public class KeyBoxResource {

    private final Logger log = LoggerFactory.getLogger(KeyBoxResource.class);

    private static final String ENTITY_NAME = "keyBox";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KeyBoxService keyBoxService;

    public KeyBoxResource(KeyBoxService keyBoxService) {
        this.keyBoxService = keyBoxService;
    }

    /**
     * {@code POST  /key-boxes} : Create a new keyBox.
     *
     * @param keyBoxDTO the keyBoxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new keyBoxDTO, or with status {@code 400 (Bad Request)} if the keyBox has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/key-boxes")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<KeyBoxDTO> createKeyBox(@RequestBody KeyBoxDTO keyBoxDTO) throws URISyntaxException {
        log.debug("REST request to save KeyBox : {}", keyBoxDTO);
        if (keyBoxDTO.getId() != null) {
            throw new BadRequestAlertException("A new keyBox cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KeyBoxDTO result = keyBoxService.save(keyBoxDTO);
        return ResponseEntity.created(new URI("/api/key-boxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /key-boxes} : Updates an existing keyBox.
     *
     * @param keyBoxDTO the keyBoxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keyBoxDTO,
     * or with status {@code 400 (Bad Request)} if the keyBoxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the keyBoxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/key-boxes")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<KeyBoxDTO> updateKeyBox(@RequestBody KeyBoxDTO keyBoxDTO) throws URISyntaxException {
        log.debug("REST request to update KeyBox : {}", keyBoxDTO);
        if (keyBoxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KeyBoxDTO result = keyBoxService.save(keyBoxDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, keyBoxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /key-boxes} : get all the keyBoxes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of keyBoxes in body.
     */
    @GetMapping("/key-boxes")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<KeyBoxDTO>> getAllKeyBoxes(Pageable pageable) {
        log.debug("REST request to get a page of KeyBoxes");
        Page<KeyBoxDTO> page = keyBoxService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /key-boxes/:id} : get the "id" keyBox.
     *
     * @param id the id of the keyBoxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keyBoxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/key-boxes/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<KeyBoxDTO> getKeyBox(@PathVariable Long id) {
        log.debug("REST request to get KeyBox : {}", id);
        Optional<KeyBoxDTO> keyBoxDTO = keyBoxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(keyBoxDTO);
    }

    /**
     * {@code DELETE  /key-boxes/:id} : delete the "id" keyBox.
     *
     * @param id the id of the keyBoxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/key-boxes/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteKeyBox(@PathVariable Long id) {
        log.debug("REST request to delete KeyBox : {}", id);
        keyBoxService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
