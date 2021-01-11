package cn.hupig.www.code.cmservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SystemImageMapperTest {

    private SystemImageMapper systemImageMapper;

    @BeforeEach
    public void setUp() {
        systemImageMapper = new SystemImageMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(systemImageMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(systemImageMapper.fromId(null)).isNull();
    }
}
