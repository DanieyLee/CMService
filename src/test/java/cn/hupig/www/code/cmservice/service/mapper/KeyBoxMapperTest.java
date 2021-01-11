package cn.hupig.www.code.cmservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class KeyBoxMapperTest {

    private KeyBoxMapper keyBoxMapper;

    @BeforeEach
    public void setUp() {
        keyBoxMapper = new KeyBoxMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(keyBoxMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(keyBoxMapper.fromId(null)).isNull();
    }
}
