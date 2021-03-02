package cn.hupig.www.code.cmservice.web.rest;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hupig.www.code.cmservice.service.Rewrite_PhoneService;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import cn.hupig.www.code.cmservice.web.rest.utils.PhoneFormatCheckUtils;
import io.github.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.Phone}.
 */
@RestController
@RequestMapping("/api")
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
    public ResponseEntity<Void> sendCode(@RequestBody String phoneNumber) throws URISyntaxException {
        log.debug("REST request to save Phone : {}", phoneNumber);
        phoneNumber = phoneNumber.substring(phoneNumber.indexOf(":") + 2).substring(0, phoneNumber.length() - phoneNumber.indexOf(":") - 4);
        if (!PhoneFormatCheckUtils.isPhoneLegal(phoneNumber)) {
            throw new BadRequestAlertException("Send code information to phone", ENTITY_NAME, "idexists");
        }
        rewrite_PhoneService.sendCode(phoneNumber);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, phoneNumber)).build();
    }

}
