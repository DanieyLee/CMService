package cn.hupig.www.code.cmservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class WallpaperDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WallpaperDTO.class);
        WallpaperDTO wallpaperDTO1 = new WallpaperDTO();
        wallpaperDTO1.setId(1L);
        WallpaperDTO wallpaperDTO2 = new WallpaperDTO();
        assertThat(wallpaperDTO1).isNotEqualTo(wallpaperDTO2);
        wallpaperDTO2.setId(wallpaperDTO1.getId());
        assertThat(wallpaperDTO1).isEqualTo(wallpaperDTO2);
        wallpaperDTO2.setId(2L);
        assertThat(wallpaperDTO1).isNotEqualTo(wallpaperDTO2);
        wallpaperDTO1.setId(null);
        assertThat(wallpaperDTO1).isNotEqualTo(wallpaperDTO2);
    }
}
