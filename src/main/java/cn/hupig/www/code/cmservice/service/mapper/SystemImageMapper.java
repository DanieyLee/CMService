package cn.hupig.www.code.cmservice.service.mapper;


import cn.hupig.www.code.cmservice.domain.*;
import cn.hupig.www.code.cmservice.service.dto.SystemImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SystemImage} and its DTO {@link SystemImageDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserLinkMapper.class})
public interface SystemImageMapper extends EntityMapper<SystemImageDTO, SystemImage> {

    @Mapping(source = "userLink.id", target = "userLinkId")
    @Mapping(source = "userLink.firstName", target = "userLinkFirstName")
    SystemImageDTO toDto(SystemImage systemImage);

    @Mapping(source = "userLinkId", target = "userLink")
    SystemImage toEntity(SystemImageDTO systemImageDTO);

    default SystemImage fromId(Long id) {
        if (id == null) {
            return null;
        }
        SystemImage systemImage = new SystemImage();
        systemImage.setId(id);
        return systemImage;
    }
}
