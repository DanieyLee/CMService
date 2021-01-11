package cn.hupig.www.code.cmservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class WallpaperTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wallpaper.class);
        Wallpaper wallpaper1 = new Wallpaper();
        wallpaper1.setId(1L);
        Wallpaper wallpaper2 = new Wallpaper();
        wallpaper2.setId(wallpaper1.getId());
        assertThat(wallpaper1).isEqualTo(wallpaper2);
        wallpaper2.setId(2L);
        assertThat(wallpaper1).isNotEqualTo(wallpaper2);
        wallpaper1.setId(null);
        assertThat(wallpaper1).isNotEqualTo(wallpaper2);
    }
}
