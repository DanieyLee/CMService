package cn.hupig.www.code.cmservice.web.rest;

import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hupig.www.code.cmservice.security.AuthoritiesConstants;
import cn.hupig.www.code.cmservice.service.Rewrite_UserLinkService;
import cn.hupig.www.code.cmservice.service.dto.UserLinkDTO;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.UserLink}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "008-用户扩展信息管理")
public class Rewrite_UserLinkResource {

    private final Logger log = LoggerFactory.getLogger(Rewrite_UserLinkResource.class);

    private static final String ENTITY_NAME = "userLink";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Rewrite_UserLinkService rewrite_UserLinkService;

    public Rewrite_UserLinkResource(Rewrite_UserLinkService rewrite_UserLinkService) {
        this.rewrite_UserLinkService = rewrite_UserLinkService;
    }

    /**
     * {@code GET  /user-links/:id} : get the "id" userLink.
     *
     * @param id the id of the userLinkDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userLinkDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-links/user-id/{id}")
    @ApiOperation(value = "取出用户关联信息-根据用户id")
    public ResponseEntity<UserLinkDTO> getUserLink(@PathVariable Long id) {
        log.debug("REST request to get UserLink : {}", id);
        Optional<UserLinkDTO> userLinkDTO = rewrite_UserLinkService.findByUser(id);
        return ResponseUtil.wrapOrNotFound(userLinkDTO);
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
    @PutMapping("/user-links/update")
    @ApiOperation(value = "管理员，后台修改用户扩展信息")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<UserLinkDTO> updateUserLink(@Valid @RequestBody UserLinkDTO userLinkDTO) throws URISyntaxException {
        log.debug("REST request to update UserLink : {}", userLinkDTO);
        if (userLinkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserLinkDTO result = rewrite_UserLinkService.save(userLinkDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userLinkDTO.getId().toString()))
            .body(result);
    }

}
