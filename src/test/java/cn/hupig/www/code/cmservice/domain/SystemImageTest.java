package cn.hupig.www.code.cmservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class SystemImageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemImage.class);
        SystemImage systemImage1 = new SystemImage();
        systemImage1.setId(1L);
        SystemImage systemImage2 = new SystemImage();
        systemImage2.setId(systemImage1.getId());
        assertThat(systemImage1).isEqualTo(systemImage2);
        systemImage2.setId(2L);
        assertThat(systemImage1).isNotEqualTo(systemImage2);
        systemImage1.setId(null);
        assertThat(systemImage1).isNotEqualTo(systemImage2);
    }
}
