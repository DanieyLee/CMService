package cn.hupig.www.code.cmservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class SoftwareScoreDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoftwareScoreDTO.class);
        SoftwareScoreDTO softwareScoreDTO1 = new SoftwareScoreDTO();
        softwareScoreDTO1.setId(1L);
        SoftwareScoreDTO softwareScoreDTO2 = new SoftwareScoreDTO();
        assertThat(softwareScoreDTO1).isNotEqualTo(softwareScoreDTO2);
        softwareScoreDTO2.setId(softwareScoreDTO1.getId());
        assertThat(softwareScoreDTO1).isEqualTo(softwareScoreDTO2);
        softwareScoreDTO2.setId(2L);
        assertThat(softwareScoreDTO1).isNotEqualTo(softwareScoreDTO2);
        softwareScoreDTO1.setId(null);
        assertThat(softwareScoreDTO1).isNotEqualTo(softwareScoreDTO2);
    }
}
