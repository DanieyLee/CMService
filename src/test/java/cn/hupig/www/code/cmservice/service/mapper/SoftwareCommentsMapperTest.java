package cn.hupig.www.code.cmservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SoftwareCommentsMapperTest {

    private SoftwareCommentsMapper softwareCommentsMapper;

    @BeforeEach
    public void setUp() {
        softwareCommentsMapper = new SoftwareCommentsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(softwareCommentsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(softwareCommentsMapper.fromId(null)).isNull();
    }
}
