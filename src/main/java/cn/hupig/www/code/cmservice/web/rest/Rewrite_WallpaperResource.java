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

import cn.hupig.www.code.cmservice.service.WallpaperService;
import cn.hupig.www.code.cmservice.service.dto.WallpaperDTO;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link cn.hupig.www.code.cmservice.domain.Wallpaper}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "002-壁纸操作")
public class Rewrite_WallpaperResource {

    private final Logger log = LoggerFactory.getLogger(Rewrite_WallpaperResource.class);

    private static final String ENTITY_NAME = "wallpaper";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WallpaperService wallpaperService;

    public Rewrite_WallpaperResource(WallpaperService wallpaperService) {
        this.wallpaperService = wallpaperService;
    }

    /**
     * {@code GET  /wallpapers} : get all the wallpapers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wallpapers in body.
     */
    @GetMapping("/public/wallpapers")
    @ApiOperation(value = "查询所有壁纸-无权限-带分页")
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
    @GetMapping("/public/wallpapers/{id}")
    @ApiOperation(value = "查询单张壁纸-无权限")
    public ResponseEntity<WallpaperDTO> getWallpaper(@PathVariable Long id) {
        log.debug("REST request to get Wallpaper : {}", id);
        Optional<WallpaperDTO> wallpaperDTO = wallpaperService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wallpaperDTO);
    }
}
