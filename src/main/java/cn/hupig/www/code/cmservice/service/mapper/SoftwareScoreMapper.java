package cn.hupig.www.code.cmservice.service.mapper;


import cn.hupig.www.code.cmservice.domain.*;
import cn.hupig.www.code.cmservice.service.dto.SoftwareScoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SoftwareScore} and its DTO {@link SoftwareScoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {SoftwareMapper.class, UserLinkMapper.class})
public interface SoftwareScoreMapper extends EntityMapper<SoftwareScoreDTO, SoftwareScore> {

    @Mapping(source = "software.id", target = "softwareId")
    @Mapping(source = "software.name", target = "softwareName")
    @Mapping(source = "userLink.id", target = "userLinkId")
    @Mapping(source = "userLink.firstName", target = "userLinkFirstName")
    SoftwareScoreDTO toDto(SoftwareScore softwareScore);

    @Mapping(source = "softwareId", target = "software")
    @Mapping(source = "userLinkId", target = "userLink")
    SoftwareScore toEntity(SoftwareScoreDTO softwareScoreDTO);

    default SoftwareScore fromId(Long id) {
        if (id == null) {
            return null;
        }
        SoftwareScore softwareScore = new SoftwareScore();
        softwareScore.setId(id);
        return softwareScore;
    }
}
