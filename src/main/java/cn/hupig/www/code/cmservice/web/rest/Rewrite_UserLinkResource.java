package cn.hupig.www.code.cmservice.web.rest;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hupig.www.code.cmservice.service.Rewrite_UserLinkService;
import cn.hupig.www.code.cmservice.service.dto.UserLinkDTO;
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

}
