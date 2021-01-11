package cn.hupig.www.code.cmservice.service.mapper;


import cn.hupig.www.code.cmservice.domain.*;
import cn.hupig.www.code.cmservice.service.dto.WallpaperDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Wallpaper} and its DTO {@link WallpaperDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserLinkMapper.class})
public interface WallpaperMapper extends EntityMapper<WallpaperDTO, Wallpaper> {

    @Mapping(source = "userLink.id", target = "userLinkId")
    @Mapping(source = "userLink.firstName", target = "userLinkFirstName")
    WallpaperDTO toDto(Wallpaper wallpaper);

    @Mapping(source = "userLinkId", target = "userLink")
    Wallpaper toEntity(WallpaperDTO wallpaperDTO);

    default Wallpaper fromId(Long id) {
        if (id == null) {
            return null;
        }
        Wallpaper wallpaper = new Wallpaper();
        wallpaper.setId(id);
        return wallpaper;
    }
}
