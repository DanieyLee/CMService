package cn.hupig.www.code.cmservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SoftwareTypeMapperTest {

    private SoftwareTypeMapper softwareTypeMapper;

    @BeforeEach
    public void setUp() {
        softwareTypeMapper = new SoftwareTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(softwareTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(softwareTypeMapper.fromId(null)).isNull();
    }
}
