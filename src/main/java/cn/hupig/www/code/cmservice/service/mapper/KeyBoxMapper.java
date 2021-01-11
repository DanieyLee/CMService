package cn.hupig.www.code.cmservice.service.mapper;


import cn.hupig.www.code.cmservice.domain.*;
import cn.hupig.www.code.cmservice.service.dto.KeyBoxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link KeyBox} and its DTO {@link KeyBoxDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserLinkMapper.class})
public interface KeyBoxMapper extends EntityMapper<KeyBoxDTO, KeyBox> {

    @Mapping(source = "userLink.id", target = "userLinkId")
    @Mapping(source = "userLink.firstName", target = "userLinkFirstName")
    KeyBoxDTO toDto(KeyBox keyBox);

    @Mapping(source = "userLinkId", target = "userLink")
    KeyBox toEntity(KeyBoxDTO keyBoxDTO);

    default KeyBox fromId(Long id) {
        if (id == null) {
            return null;
        }
        KeyBox keyBox = new KeyBox();
        keyBox.setId(id);
        return keyBox;
    }
}
