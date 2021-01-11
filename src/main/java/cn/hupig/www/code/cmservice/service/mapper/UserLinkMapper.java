package cn.hupig.www.code.cmservice.service.mapper;


import cn.hupig.www.code.cmservice.domain.*;
import cn.hupig.www.code.cmservice.service.dto.UserLinkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserLink} and its DTO {@link UserLinkDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserLinkMapper extends EntityMapper<UserLinkDTO, UserLink> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    UserLinkDTO toDto(UserLink userLink);

    @Mapping(source = "userId", target = "user")
    UserLink toEntity(UserLinkDTO userLinkDTO);

    default UserLink fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserLink userLink = new UserLink();
        userLink.setId(id);
        return userLink;
    }
}
