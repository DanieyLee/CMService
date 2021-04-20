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
import cn.hupig.www.code.cmservice.service.Rewrite_KeyBoxService;
import cn.hupig.www.code.cmservice.service.Rewrite_UserLinkService;
import cn.hupig.www.code.cmservice.service.UserService;
import cn.hupig.www.code.cmservice.service.dto.KeyBoxDTO;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.KeyBox}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "003-密码盒子")
public class Rewrite_KeyBoxResource {

    private final Logger log = LoggerFactory.getLogger(Rewrite_KeyBoxResource.class);

    private static final String ENTITY_NAME = "keyBox";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Rewrite_KeyBoxService rewrite_KeyBoxService;
    
    private final UserService userService;
    
    private final Rewrite_UserLinkService rewrite_UserLinkService;

    public Rewrite_KeyBoxResource(Rewrite_KeyBoxService rewrite_KeyBoxService, UserService userService, Rewrite_UserLinkService rewrite_UserLinkService) {
        this.rewrite_KeyBoxService = rewrite_KeyBoxService;
        this.userService = userService;
        this.rewrite_UserLinkService = rewrite_UserLinkService;
    }

    /**
     * {@code GET  /key-boxes} : get all the keyBoxes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of keyBoxes in body.
     */
    @GetMapping("/my-key-boxes")
    @ApiOperation(value = "查询我的所有密码-带分页")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<KeyBoxDTO>> getAllKeyBoxes(Pageable pageable) {
        log.debug("REST request to get a page of KeyBoxes");
        Long userId = userService.getUserWithAuthorities().get().getId();
        Long userLinkId = rewrite_UserLinkService.findByUser(userId).get().getId();
        Page<KeyBoxDTO> page = rewrite_KeyBoxService.findMyAll(pageable, userLinkId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code GET  /key-boxes/:id} : get the "id" keyBox.
     *
     * @param id the id of the keyBoxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keyBoxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/key-boxes/show-hide/{id}")
    @ApiOperation(value = "显示、隐藏指定id的密码")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<KeyBoxDTO> getKeyBox(@PathVariable Long id) {
        log.debug("REST request to get KeyBox : {}", id);
        Optional<KeyBoxDTO> keyBoxDTO = rewrite_KeyBoxService.findOneShowHideAndState(id);
        return ResponseUtil.wrapOrNotFound(keyBoxDTO);
    }
    
    /**
     * {@code DELETE  /key-boxes/:id} : delete the "id" keyBox.
     *
     * @param id the id of the keyBoxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/key-boxes/delete/{id}")
    @ApiOperation(value = "删除指定id的密码")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> deleteKeyBox(@PathVariable Long id) {
        log.debug("REST request to delete KeyBox : {}", id);
        Long userId = userService.getUserWithAuthorities().get().getId();
        Long userLinkId = rewrite_UserLinkService.findByUser(userId).get().getId();
        rewrite_KeyBoxService.delete(id, userLinkId);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * {@code POST  /key-boxes} : Create a new keyBox.
     *
     * @param keyBoxDTO the keyBoxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new keyBoxDTO, or with status {@code 400 (Bad Request)} if the keyBox has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/key-boxes/create")
    @ApiOperation(value = "创建新的密码")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<KeyBoxDTO> createKeyBox(@RequestBody KeyBoxDTO keyBoxDTO) throws URISyntaxException {
        log.debug("REST request to save KeyBox : {}", keyBoxDTO);
        Long userId = userService.getUserWithAuthorities().get().getId();
        Long userLinkId = rewrite_UserLinkService.findByUser(userId).get().getId();
        if (keyBoxDTO.getId() != null) {
            throw new BadRequestAlertException("A new keyBox cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KeyBoxDTO result = rewrite_KeyBoxService.createKeyBox(keyBoxDTO, userLinkId);
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
    @PutMapping("/key-boxes/update")
    @ApiOperation(value = "修改指定id的密码")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<KeyBoxDTO> updateKeyBox(@RequestBody KeyBoxDTO keyBoxDTO) throws URISyntaxException {
        log.debug("REST request to update KeyBox : {}", keyBoxDTO);
        Long userId = userService.getUserWithAuthorities().get().getId();
        Long userLinkId = rewrite_UserLinkService.findByUser(userId).get().getId();
        if (keyBoxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KeyBoxDTO result = rewrite_KeyBoxService.updateKeyBox(keyBoxDTO, userLinkId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, keyBoxDTO.getId().toString()))
            .body(result);
    }
}
