package cn.hupig.www.code.cmservice.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cn.hupig.www.code.cmservice.security.AuthoritiesConstants;
import cn.hupig.www.code.cmservice.service.SoftwareTypeService;
import cn.hupig.www.code.cmservice.service.UserService;
import cn.hupig.www.code.cmservice.service.dto.SoftwareTypeDTO;
import cn.hupig.www.code.cmservice.service.utils.Times;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.SoftwareType}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "009-软件类型管理")
public class Rewrite_SoftwareTypeResource {

    private final Logger log = LoggerFactory.getLogger(Rewrite_SoftwareTypeResource.class);

    private static final String ENTITY_NAME = "softwareType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoftwareTypeService softwareTypeService;
    
    private final UserService userService;

    public Rewrite_SoftwareTypeResource(SoftwareTypeService softwareTypeService, UserService userService) {
        this.softwareTypeService = softwareTypeService;
        this.userService = userService;
    }

    /**
     * {@code GET  /software-types} : get all the softwareTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of softwareTypes in body.
     */
    @GetMapping("/public/software-types")
    @ApiOperation(value = "查询所有软件类型-无权限")
    public ResponseEntity<List<SoftwareTypeDTO>> getAllSoftwareTypes(Pageable pageable) {
        log.debug("REST request to get a page of SoftwareTypes");
        Page<SoftwareTypeDTO> page = softwareTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code POST  /software-types} : Create a new softwareType.
     *
     * @param softwareTypeDTO the softwareTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new softwareTypeDTO, or with status {@code 400 (Bad Request)} if the softwareType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/software-types/create")
    @ApiOperation(value = "创建软件类型-自动获取时间")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<SoftwareTypeDTO> createSoftwareType(@Valid @RequestBody SoftwareTypeDTO softwareTypeDTO) throws URISyntaxException {
        log.debug("REST request to save SoftwareType : {}", softwareTypeDTO);
        if (softwareTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new softwareType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        String userName = userService.getUserWithAuthorities().get().getFirstName();
        softwareTypeDTO.setCreateUser(userName);
        softwareTypeDTO.setCreatTime(Times.getInstant());
        softwareTypeDTO.setUpdateUser(userName);
        softwareTypeDTO.setUpdateTime(Times.getInstant());
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
    @PutMapping("/software-types/update")
    @ApiOperation(value = "修改软件类型-自动获取时间")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<SoftwareTypeDTO> updateSoftwareType(@Valid @RequestBody SoftwareTypeDTO softwareTypeDTO) throws URISyntaxException {
        log.debug("REST request to update SoftwareType : {}", softwareTypeDTO);
        if (softwareTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        String userName = userService.getUserWithAuthorities().get().getFirstName();
        softwareTypeDTO.setUpdateUser(userName);
        softwareTypeDTO.setUpdateTime(Times.getInstant());
        SoftwareTypeDTO result = softwareTypeService.save(softwareTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, softwareTypeDTO.getId().toString()))
            .body(result);
    }
}
