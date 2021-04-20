package cn.hupig.www.code.cmservice.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cn.hupig.www.code.cmservice.domain.User;
import cn.hupig.www.code.cmservice.security.AuthoritiesConstants;
import cn.hupig.www.code.cmservice.service.Rewrite_SoftwareService;
import cn.hupig.www.code.cmservice.service.UserService;
import cn.hupig.www.code.cmservice.service.dto.SoftwareDTO;
import cn.hupig.www.code.cmservice.service.utils.Times;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.Software}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "001-软件操作")
public class Rewrite_SoftwareResource {

    private final Logger log = LoggerFactory.getLogger(Rewrite_SoftwareResource.class);

    private static final String ENTITY_NAME = "software";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private final UserService userService;

    private final Rewrite_SoftwareService rewrite_SoftwareService;

    public Rewrite_SoftwareResource(Rewrite_SoftwareService rewrite_SoftwareService, UserService userService) {
        this.rewrite_SoftwareService = rewrite_SoftwareService;
        this.userService = userService;
    }
    
    /**
     * {@code GET  /public/software/type/{id}} : get all the software.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of software in body.
     */
    @GetMapping("/public/software/type/{id}")
    @ApiOperation(value = "查询所有软件-无权限-带分页-带类型id")
    public ResponseEntity<List<SoftwareDTO>> getAllSoftwareByType(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of Software By : {}", id);
        Page<SoftwareDTO> page = rewrite_SoftwareService.findAllState(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code GET  /software} : get top12 the software.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of software in body.
     */
    @GetMapping("/public/software/top")
    @ApiOperation(value = "查询12个软件-无权限")
    public ResponseEntity<List<SoftwareDTO>> getSoftware() {
        log.debug("REST request to get a page of Software");
        Page<SoftwareDTO> page = rewrite_SoftwareService.findTopState();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code GET  /software/:id} : get the "id" software.
     *
     * @param id the id of the softwareDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the softwareDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public/software/{id}")
    @ApiOperation(value = "查询单个软件-无权限")
    public ResponseEntity<SoftwareDTO> getSoftware(@PathVariable Long id) {
        log.debug("REST request to get Software : {}", id);
        Optional<SoftwareDTO> softwareDTO = rewrite_SoftwareService.findOneState(id);
        return ResponseUtil.wrapOrNotFound(softwareDTO);
    }
    
    /**
     * {@code GET  /software/:id} : like software.
     *
     * @param id the id of the softwareDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the softwareDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public/software/like/{id}")
    @ApiOperation(value = "喜欢软件-无权限")
    public ResponseEntity<SoftwareDTO> updateSoftware(@PathVariable Long id) {
        log.debug("REST request to update Software : {}", id);
        if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<SoftwareDTO> softwareDTO = rewrite_SoftwareService.findOneLikeAndState(id);
        return ResponseUtil.wrapOrNotFound(softwareDTO);
    }
    
    /**
     * {@code GET  /software/:id} : download software.
     *
     * @param id the id of the softwareDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the softwareDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public/software/download/{id}")
    @ApiOperation(value = "下载软件-无权限")
    public ResponseEntity<SoftwareDTO> downloadSoftware(@PathVariable Long id) {
        log.debug("REST request to download Software : {}", id);
        if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<SoftwareDTO> softwareDTO = rewrite_SoftwareService.findOneDownloadAndState(id);
        return ResponseUtil.wrapOrNotFound(softwareDTO);
    }
    
    /**
     * {@code POST  /software} : Create a new software.
     *
     * @param softwareDTO the softwareDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new softwareDTO, or with status {@code 400 (Bad Request)} if the software has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/software/create")
    @ApiOperation(value = "管理员-发布新的软件")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<SoftwareDTO> createSoftware(@Valid @RequestBody SoftwareDTO softwareDTO) throws URISyntaxException {
        log.debug("REST request to save Software : {}", softwareDTO);
        if (softwareDTO.getId() != null) {
            throw new BadRequestAlertException("A new software cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user = userService.getUserWithAuthorities().get();
        softwareDTO.setCreateUser(user.getFirstName());
        softwareDTO.setCreatTime(Times.getInstant());
        softwareDTO.setUpdateUser(user.getFirstName());
        softwareDTO.setUpdateTime(Times.getInstant());
        softwareDTO.setUserLinkId(user.getId()); // 临时储存userId，实现方法内处理成userlinkId
        SoftwareDTO result = rewrite_SoftwareService.createSoftware(softwareDTO);
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
    @PutMapping("/software/update")
    @ApiOperation(value = "管理员-修改软件")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<SoftwareDTO> updateSoftware(@Valid @RequestBody SoftwareDTO softwareDTO) throws URISyntaxException {
        log.debug("REST request to update Software : {}", softwareDTO);
        if (softwareDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        String userName = userService.getUserWithAuthorities().get().getFirstName();
        softwareDTO.setUpdateUser(userName);
        softwareDTO.setUpdateTime(Times.getInstant());
        SoftwareDTO result = rewrite_SoftwareService.updateSoftware(softwareDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, softwareDTO.getId().toString()))
            .body(result);
    }
}
