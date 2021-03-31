package cn.hupig.www.code.cmservice.web.rest;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cn.hupig.www.code.cmservice.service.Rewrite_SoftwareCommentsService;
import cn.hupig.www.code.cmservice.service.dto.SoftwareCommentsDTO;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.SoftwareComments}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "010-软件评论管理")
public class Rewrite_SoftwareCommentsResource {

    private final Logger log = LoggerFactory.getLogger(Rewrite_SoftwareCommentsResource.class);

    private static final String ENTITY_NAME = "softwareComments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Rewrite_SoftwareCommentsService rewrite_SoftwareCommentsService;

    public Rewrite_SoftwareCommentsResource(Rewrite_SoftwareCommentsService rewrite_SoftwareCommentsService) {
        this.rewrite_SoftwareCommentsService = rewrite_SoftwareCommentsService;
    }

    /**
     * {@code POST  /software-comments} : Create a new softwareComments.
     *
     * @param softwareCommentsDTO the softwareCommentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new softwareCommentsDTO, or with status {@code 400 (Bad Request)} if the softwareComments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/software-comments/reply")
    @ApiOperation(value = "创建新的软件回复信息-软件id必传")
    public ResponseEntity<List<SoftwareCommentsDTO>> createSoftwareComments(@RequestBody SoftwareCommentsDTO softwareCommentsDTO) throws URISyntaxException {
        log.debug("REST request to save SoftwareComments : {}", softwareCommentsDTO);
        if (softwareCommentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new softwareComments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Page<SoftwareCommentsDTO> page = rewrite_SoftwareCommentsService.createSoftwareComment(softwareCommentsDTO);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /software-comments/:id} : get the "id" softwareComments.
     *
     * @param id the id of the softwareCommentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the softwareCommentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public/software-comments/{id}")
    @ApiOperation(value = "取出所有软件评论-当前这个id的软件")
    public ResponseEntity<List<SoftwareCommentsDTO>> getAllSoftwareComments(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of SoftwareComments");
        Page<SoftwareCommentsDTO> page = rewrite_SoftwareCommentsService.findAllBySoftware(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
