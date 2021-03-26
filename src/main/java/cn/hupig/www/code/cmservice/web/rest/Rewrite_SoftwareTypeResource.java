package cn.hupig.www.code.cmservice.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cn.hupig.www.code.cmservice.service.SoftwareTypeService;
import cn.hupig.www.code.cmservice.service.dto.SoftwareTypeDTO;
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

    public Rewrite_SoftwareTypeResource(SoftwareTypeService softwareTypeService) {
        this.softwareTypeService = softwareTypeService;
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
}
