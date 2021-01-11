package cn.hupig.www.code.cmservice.web.rest;

import cn.hupig.www.code.cmservice.service.WallpaperService;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import cn.hupig.www.code.cmservice.service.dto.WallpaperDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.Wallpaper}.
 */
@RestController
@RequestMapping("/api")
public class WallpaperResource {

    private final Logger log = LoggerFactory.getLogger(WallpaperResource.class);

    private static final String ENTITY_NAME = "wallpaper";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WallpaperService wallpaperService;

    public WallpaperResource(WallpaperService wallpaperService) {
        this.wallpaperService = wallpaperService;
    }

    /**
     * {@code POST  /wallpapers} : Create a new wallpaper.
     *
     * @param wallpaperDTO the wallpaperDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wallpaperDTO, or with status {@code 400 (Bad Request)} if the wallpaper has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wallpapers")
    public ResponseEntity<WallpaperDTO> createWallpaper(@Valid @RequestBody WallpaperDTO wallpaperDTO) throws URISyntaxException {
        log.debug("REST request to save Wallpaper : {}", wallpaperDTO);
        if (wallpaperDTO.getId() != null) {
            throw new BadRequestAlertException("A new wallpaper cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WallpaperDTO result = wallpaperService.save(wallpaperDTO);
        return ResponseEntity.created(new URI("/api/wallpapers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wallpapers} : Updates an existing wallpaper.
     *
     * @param wallpaperDTO the wallpaperDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wallpaperDTO,
     * or with status {@code 400 (Bad Request)} if the wallpaperDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wallpaperDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wallpapers")
    public ResponseEntity<WallpaperDTO> updateWallpaper(@Valid @RequestBody WallpaperDTO wallpaperDTO) throws URISyntaxException {
        log.debug("REST request to update Wallpaper : {}", wallpaperDTO);
        if (wallpaperDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WallpaperDTO result = wallpaperService.save(wallpaperDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wallpaperDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wallpapers} : get all the wallpapers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wallpapers in body.
     */
    @GetMapping("/wallpapers")
    public ResponseEntity<List<WallpaperDTO>> getAllWallpapers(Pageable pageable) {
        log.debug("REST request to get a page of Wallpapers");
        Page<WallpaperDTO> page = wallpaperService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wallpapers/:id} : get the "id" wallpaper.
     *
     * @param id the id of the wallpaperDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wallpaperDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wallpapers/{id}")
    public ResponseEntity<WallpaperDTO> getWallpaper(@PathVariable Long id) {
        log.debug("REST request to get Wallpaper : {}", id);
        Optional<WallpaperDTO> wallpaperDTO = wallpaperService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wallpaperDTO);
    }

    /**
     * {@code DELETE  /wallpapers/:id} : delete the "id" wallpaper.
     *
     * @param id the id of the wallpaperDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wallpapers/{id}")
    public ResponseEntity<Void> deleteWallpaper(@PathVariable Long id) {
        log.debug("REST request to delete Wallpaper : {}", id);
        wallpaperService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
