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

import cn.hupig.www.code.cmservice.service.Rewrite_WallpaperService;
import cn.hupig.www.code.cmservice.service.dto.WallpaperDTO;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
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

    private final Rewrite_WallpaperService rewrite_WallpaperService;

    public Rewrite_WallpaperResource(Rewrite_WallpaperService rewrite_WallpaperService) {
        this.rewrite_WallpaperService = rewrite_WallpaperService;
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
        Page<WallpaperDTO> page = rewrite_WallpaperService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code GET  /wallpapers} : get top4 the wallpapers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wallpapers in body.
     */
    @GetMapping("/public/wallpapers/top")
    @ApiOperation(value = "查询4张壁纸-无权限-带分页")
    public ResponseEntity<List<WallpaperDTO>> getTopWallpapers() {
        log.debug("REST request to get a page of Wallpapers");
        Page<WallpaperDTO> page = rewrite_WallpaperService.findTop();
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
        Optional<WallpaperDTO> wallpaperDTO = rewrite_WallpaperService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wallpaperDTO);
    }
    
    /**
	 * {@code GET  /wallpapers/:id} : like wallpapers.
	 *
	 * @param id the id of the WallpaperDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the WallpaperDTO, or with status {@code 404 (Not Found)}.
	 */
    @GetMapping("/public/wallpapers/like/{id}")
    @ApiOperation(value = "喜欢壁纸-无权限")
    public ResponseEntity<WallpaperDTO> updateWallpaper(@PathVariable Long id) {
        log.debug("REST request to update Wallpaper : {}", id);
        if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<WallpaperDTO> wallpaperDTO = rewrite_WallpaperService.findOneLikeAndState(id);
        return ResponseUtil.wrapOrNotFound(wallpaperDTO);
    }
    
    /**
     * {@code GET  /wallpapers} : Take out a piece of wallpaper.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wallpapers in body.
     */
	@GetMapping("/public/wallpapers/near/{id}&{near}")
    @ApiOperation(value = "查询1张相近的壁纸-无权限")
    public ResponseEntity<WallpaperDTO> getNearWallpaper(@PathVariable Long id,@PathVariable Boolean near) {
        log.debug("REST request to get a Wallpaper");
        Optional<WallpaperDTO> wallpaperDTO = rewrite_WallpaperService.findOneNearWallpaperAndState(id, near);
        return ResponseUtil.wrapOrNotFound(wallpaperDTO);
    }
}
