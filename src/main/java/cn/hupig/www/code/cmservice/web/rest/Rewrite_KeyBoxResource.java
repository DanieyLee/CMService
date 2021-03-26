package cn.hupig.www.code.cmservice.web.rest;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cn.hupig.www.code.cmservice.security.AuthoritiesConstants;
import cn.hupig.www.code.cmservice.service.Rewrite_KeyBoxService;
import cn.hupig.www.code.cmservice.service.Rewrite_UserLinkService;
import cn.hupig.www.code.cmservice.service.UserService;
import cn.hupig.www.code.cmservice.service.dto.KeyBoxDTO;
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
    public ResponseEntity<KeyBoxDTO> getKeyBox(@PathVariable Long id) {
        log.debug("REST request to get KeyBox : {}", id);
        Optional<KeyBoxDTO> keyBoxDTO = rewrite_KeyBoxService.findOneShowHideAndState(id);
        return ResponseUtil.wrapOrNotFound(keyBoxDTO);
    }
}
