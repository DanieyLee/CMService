package cn.hupig.www.code.cmservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WallpaperMapperTest {

    private WallpaperMapper wallpaperMapper;

    @BeforeEach
    public void setUp() {
        wallpaperMapper = new WallpaperMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(wallpaperMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(wallpaperMapper.fromId(null)).isNull();
    }
}
