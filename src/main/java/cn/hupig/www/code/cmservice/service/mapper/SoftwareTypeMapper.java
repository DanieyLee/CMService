package cn.hupig.www.code.cmservice.service.mapper;


import cn.hupig.www.code.cmservice.domain.*;
import cn.hupig.www.code.cmservice.service.dto.SoftwareTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SoftwareType} and its DTO {@link SoftwareTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SoftwareTypeMapper extends EntityMapper<SoftwareTypeDTO, SoftwareType> {



    default SoftwareType fromId(Long id) {
        if (id == null) {
            return null;
        }
        SoftwareType softwareType = new SoftwareType();
        softwareType.setId(id);
        return softwareType;
    }
}
