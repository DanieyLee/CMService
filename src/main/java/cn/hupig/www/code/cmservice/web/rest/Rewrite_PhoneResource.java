package cn.hupig.www.code.cmservice.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cn.hupig.www.code.cmservice.security.AuthoritiesConstants;
import cn.hupig.www.code.cmservice.service.Rewrite_PhoneService;
import cn.hupig.www.code.cmservice.service.dto.PhoneDTO;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import cn.hupig.www.code.cmservice.web.rest.utils.PhoneFormatCheckUtils;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.Phone}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "007-手机信息管理")
public class Rewrite_PhoneResource {

    private final Logger log = LoggerFactory.getLogger(Rewrite_PhoneResource.class);

    private static final String ENTITY_NAME = "phone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Rewrite_PhoneService rewrite_PhoneService;

    public Rewrite_PhoneResource(Rewrite_PhoneService rewrite_PhoneService) {
        this.rewrite_PhoneService = rewrite_PhoneService;
    }

    /**
     * {@code POST  /phones} : Send code information to phone.
     *
     * @param phoneDTO the phoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phoneDTO, or with status {@code 400 (Bad Request)} if the phone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/public/send/code")
    @ApiOperation(value = "发送手机验证码-24小时内只能发一条-24小时有效")
    public ResponseEntity<Void> sendCode(@RequestBody String phoneNumber) throws URISyntaxException {
        log.debug("REST request to save Phone : {}", phoneNumber);
        phoneNumber = phoneNumber.substring(phoneNumber.indexOf(":") + 2).substring(0, phoneNumber.length() - phoneNumber.indexOf(":") - 4);
        if (!PhoneFormatCheckUtils.isPhoneLegal(phoneNumber)) {
            throw new BadRequestAlertException("Send code information to phone", ENTITY_NAME, "idexists");
        }
        rewrite_PhoneService.sendCode(phoneNumber);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, phoneNumber)).build();
    }
    
    /**
     * {@code GET  /phones} : get all the phones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phones in body.
     */
    @GetMapping("/phones/all")
    @ApiOperation(value = "获取所有短信验证码，管理员专用")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<PhoneDTO>> getAllPhones(Pageable pageable) {
        log.debug("REST request to get a page of Phones");
        Page<PhoneDTO> page = rewrite_PhoneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
