package cn.hupig.www.code.cmservice.service.mapper;


import cn.hupig.www.code.cmservice.domain.*;
import cn.hupig.www.code.cmservice.service.dto.SoftwareDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Software} and its DTO {@link SoftwareDTO}.
 */
@Mapper(componentModel = "spring", uses = {SoftwareTypeMapper.class, UserLinkMapper.class})
public interface SoftwareMapper extends EntityMapper<SoftwareDTO, Software> {

    @Mapping(source = "softwareType.id", target = "softwareTypeId")
    @Mapping(source = "softwareType.type", target = "softwareTypeType")
    @Mapping(source = "userLink.id", target = "userLinkId")
    @Mapping(source = "userLink.firstName", target = "userLinkFirstName")
    SoftwareDTO toDto(Software software);

    @Mapping(source = "softwareTypeId", target = "softwareType")
    @Mapping(source = "userLinkId", target = "userLink")
    Software toEntity(SoftwareDTO softwareDTO);

    default Software fromId(Long id) {
        if (id == null) {
            return null;
        }
        Software software = new Software();
        software.setId(id);
        return software;
    }
}
