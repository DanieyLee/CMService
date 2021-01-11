package cn.hupig.www.code.cmservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserLinkMapperTest {

    private UserLinkMapper userLinkMapper;

    @BeforeEach
    public void setUp() {
        userLinkMapper = new UserLinkMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userLinkMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userLinkMapper.fromId(null)).isNull();
    }
}
