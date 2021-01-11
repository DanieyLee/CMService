package cn.hupig.www.code.cmservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class SoftwareCommentsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoftwareComments.class);
        SoftwareComments softwareComments1 = new SoftwareComments();
        softwareComments1.setId(1L);
        SoftwareComments softwareComments2 = new SoftwareComments();
        softwareComments2.setId(softwareComments1.getId());
        assertThat(softwareComments1).isEqualTo(softwareComments2);
        softwareComments2.setId(2L);
        assertThat(softwareComments1).isNotEqualTo(softwareComments2);
        softwareComments1.setId(null);
        assertThat(softwareComments1).isNotEqualTo(softwareComments2);
    }
}
