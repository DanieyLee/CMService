package cn.hupig.www.code.cmservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class SoftwareScoreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoftwareScore.class);
        SoftwareScore softwareScore1 = new SoftwareScore();
        softwareScore1.setId(1L);
        SoftwareScore softwareScore2 = new SoftwareScore();
        softwareScore2.setId(softwareScore1.getId());
        assertThat(softwareScore1).isEqualTo(softwareScore2);
        softwareScore2.setId(2L);
        assertThat(softwareScore1).isNotEqualTo(softwareScore2);
        softwareScore1.setId(null);
        assertThat(softwareScore1).isNotEqualTo(softwareScore2);
    }
}
