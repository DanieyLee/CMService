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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cn.hupig.www.code.cmservice.service.SoftwareService;
import cn.hupig.www.code.cmservice.service.dto.SoftwareDTO;
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

    private final SoftwareService softwareService;

    public Rewrite_SoftwareResource(SoftwareService softwareService) {
        this.softwareService = softwareService;
    }

    /**
     * {@code GET  /software} : get all the software.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of software in body.
     */
    @GetMapping("/public/software")
    @ApiOperation(value = "查询所有软件-无权限-带分页")
    public ResponseEntity<List<SoftwareDTO>> getAllSoftware(Pageable pageable) {
        log.debug("REST request to get a page of Software");
        Page<SoftwareDTO> page = softwareService.findAll(pageable);
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
        Optional<SoftwareDTO> softwareDTO = softwareService.findOne(id);
        return ResponseUtil.wrapOrNotFound(softwareDTO);
    }

}
