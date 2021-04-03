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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cn.hupig.www.code.cmservice.security.AuthoritiesConstants;
import cn.hupig.www.code.cmservice.service.Rewrite_WallpaperService;
import cn.hupig.www.code.cmservice.service.UserService;
import cn.hupig.www.code.cmservice.service.dto.WallpaperDTO;
import cn.hupig.www.code.cmservice.service.utils.Times;
import cn.hupig.www.code.cmservice.web.rest.errors.BadRequestAlertException;
import cn.hupig.www.code.cmservice.web.rest.vm.ImageAndWallpaperVM;
import io.github.jhipster.web.util.HeaderUtil;
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
    
    private final UserService userService;

    public Rewrite_WallpaperResource(Rewrite_WallpaperService rewrite_WallpaperService, UserService userService) {
        this.rewrite_WallpaperService = rewrite_WallpaperService;
        this.userService = userService;
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
	
    /**
     * {@code PUT  /wallpapers} : Updates an existing wallpaper.
     *
     * @param wallpaperDTO the wallpaperDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wallpaperDTO,
     * or with status {@code 400 (Bad Request)} if the wallpaperDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wallpaperDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wallpapers/update")
    @ApiOperation(value = "管理员-更新壁纸的信息")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WallpaperDTO> updateWallpaper(@Valid @RequestBody WallpaperDTO wallpaperDTO) throws URISyntaxException {
        log.debug("REST request to update Wallpaper : {}", wallpaperDTO);
        if (wallpaperDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        String userName = userService.getUserWithAuthorities().get().getFirstName();
        wallpaperDTO.setUpdateUser(userName);
        wallpaperDTO.setUpdateTime(Times.getInstant());
        WallpaperDTO result = rewrite_WallpaperService.UpdateWallpaper(wallpaperDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wallpaperDTO.getId().toString()))
            .body(result);
    }
    
    /**
     * {@code DELETE  /wallpapers/:id} : delete the "id" wallpaper.
     *
     * @param id the id of the wallpaperDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wallpapers/delete/{id}")
    @ApiOperation(value = "管理员-删除壁纸和上传的壁纸文件")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteWallpaper(@PathVariable Long id) {
        log.debug("REST request to delete Wallpaper : {}", id);
        rewrite_WallpaperService.deleteWallpaper(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * {@code POST  /wallpapers} : Create a new wallpaper.
     *
     * @param wallpaperDTO the wallpaperDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wallpaperDTO, or with status {@code 400 (Bad Request)} if the wallpaper has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wallpapers/create")
    @ApiOperation(value = "管理员-创建并上传新壁纸")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WallpaperDTO> createWallpaper(@Valid @RequestBody ImageAndWallpaperVM imageAndWallpaperVM) throws URISyntaxException {
        log.debug("REST request to save Wallpaper : {}", imageAndWallpaperVM);
        if (imageAndWallpaperVM.getId() != null) {
            throw new BadRequestAlertException("A new wallpaper cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WallpaperDTO result = rewrite_WallpaperService.createWallpaper(imageAndWallpaperVM);
        return ResponseEntity.created(new URI("/api/wallpapers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
