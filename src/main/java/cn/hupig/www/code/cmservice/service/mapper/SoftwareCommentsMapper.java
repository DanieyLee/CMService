package cn.hupig.www.code.cmservice.service.mapper;


import cn.hupig.www.code.cmservice.domain.*;
import cn.hupig.www.code.cmservice.service.dto.SoftwareCommentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SoftwareComments} and its DTO {@link SoftwareCommentsDTO}.
 */
@Mapper(componentModel = "spring", uses = {SoftwareMapper.class, UserLinkMapper.class})
public interface SoftwareCommentsMapper extends EntityMapper<SoftwareCommentsDTO, SoftwareComments> {

    @Mapping(source = "software.id", target = "softwareId")
    @Mapping(source = "software.name", target = "softwareName")
    @Mapping(source = "userLink.id", target = "userLinkId")
    @Mapping(source = "userLink.firstName", target = "userLinkFirstName")
    SoftwareCommentsDTO toDto(SoftwareComments softwareComments);

    @Mapping(source = "softwareId", target = "software")
    @Mapping(source = "userLinkId", target = "userLink")
    SoftwareComments toEntity(SoftwareCommentsDTO softwareCommentsDTO);

    default SoftwareComments fromId(Long id) {
        if (id == null) {
            return null;
        }
        SoftwareComments softwareComments = new SoftwareComments();
        softwareComments.setId(id);
        return softwareComments;
    }
}
