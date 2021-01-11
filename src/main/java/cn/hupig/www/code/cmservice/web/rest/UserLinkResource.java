package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.service.UserLinkService;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import cn.hupig.www.code.cmservice.service.dto.UserLinkDTO;

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
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.UserLink}.
 */
@RestController
@RequestMapping("/api")
public class UserLinkResource {

    private final Logger log = LoggerFactory.getLogger(UserLinkResource.class);

    private static final String ENTITY_NAME = "userLink";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserLinkService userLinkService;

    public UserLinkResource(UserLinkService userLinkService) {
        this.userLinkService = userLinkService;
    }

    /**
     * {@code POST  /user-links} : Create a new userLink.
     *
     * @param userLinkDTO the userLinkDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userLinkDTO, or with status {@code 400 (Bad Request)} if the userLink has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-links")
    public ResponseEntity<UserLinkDTO> createUserLink(@Valid @RequestBody UserLinkDTO userLinkDTO) throws URISyntaxException {
        log.debug("REST request to save UserLink : {}", userLinkDTO);
        if (userLinkDTO.getId() != null) {
            throw new BadRequestAlertException("A new userLink cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserLinkDTO result = userLinkService.save(userLinkDTO);
        return ResponseEntity.created(new URI("/api/user-links/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-links} : Updates an existing userLink.
     *
     * @param userLinkDTO the userLinkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userLinkDTO,
     * or with status {@code 400 (Bad Request)} if the userLinkDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userLinkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-links")
    public ResponseEntity<UserLinkDTO> updateUserLink(@Valid @RequestBody UserLinkDTO userLinkDTO) throws URISyntaxException {
        log.debug("REST request to update UserLink : {}", userLinkDTO);
        if (userLinkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserLinkDTO result = userLinkService.save(userLinkDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userLinkDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-links} : get all the userLinks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userLinks in body.
     */
    @GetMapping("/user-links")
    public ResponseEntity<List<UserLinkDTO>> getAllUserLinks(Pageable pageable) {
        log.debug("REST request to get a page of UserLinks");
        Page<UserLinkDTO> page = userLinkService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-links/:id} : get the "id" userLink.
     *
     * @param id the id of the userLinkDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userLinkDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-links/{id}")
    public ResponseEntity<UserLinkDTO> getUserLink(@PathVariable Long id) {
        log.debug("REST request to get UserLink : {}", id);
        Optional<UserLinkDTO> userLinkDTO = userLinkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userLinkDTO);
    }

    /**
     * {@code DELETE  /user-links/:id} : delete the "id" userLink.
     *
     * @param id the id of the userLinkDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-links/{id}")
    public ResponseEntity<Void> deleteUserLink(@PathVariable Long id) {
        log.debug("REST request to delete UserLink : {}", id);
        userLinkService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
