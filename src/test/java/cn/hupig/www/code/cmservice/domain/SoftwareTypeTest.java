package cn.hupig.www.code.cmservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class SoftwareTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoftwareType.class);
        SoftwareType softwareType1 = new SoftwareType();
        softwareType1.setId(1L);
        SoftwareType softwareType2 = new SoftwareType();
        softwareType2.setId(softwareType1.getId());
        assertThat(softwareType1).isEqualTo(softwareType2);
        softwareType2.setId(2L);
        assertThat(softwareType1).isNotEqualTo(softwareType2);
        softwareType1.setId(null);
        assertThat(softwareType1).isNotEqualTo(softwareType2);
    }
}
