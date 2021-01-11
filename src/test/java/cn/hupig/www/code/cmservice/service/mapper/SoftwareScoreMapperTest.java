package cn.hupig.www.code.cmservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SoftwareScoreMapperTest {

    private SoftwareScoreMapper softwareScoreMapper;

    @BeforeEach
    public void setUp() {
        softwareScoreMapper = new SoftwareScoreMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(softwareScoreMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(softwareScoreMapper.fromId(null)).isNull();
    }
}
