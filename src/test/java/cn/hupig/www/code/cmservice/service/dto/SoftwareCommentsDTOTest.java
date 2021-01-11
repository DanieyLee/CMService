package cn.hupig.www.code.cmservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class SoftwareCommentsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoftwareCommentsDTO.class);
        SoftwareCommentsDTO softwareCommentsDTO1 = new SoftwareCommentsDTO();
        softwareCommentsDTO1.setId(1L);
        SoftwareCommentsDTO softwareCommentsDTO2 = new SoftwareCommentsDTO();
        assertThat(softwareCommentsDTO1).isNotEqualTo(softwareCommentsDTO2);
        softwareCommentsDTO2.setId(softwareCommentsDTO1.getId());
        assertThat(softwareCommentsDTO1).isEqualTo(softwareCommentsDTO2);
        softwareCommentsDTO2.setId(2L);
        assertThat(softwareCommentsDTO1).isNotEqualTo(softwareCommentsDTO2);
        softwareCommentsDTO1.setId(null);
        assertThat(softwareCommentsDTO1).isNotEqualTo(softwareCommentsDTO2);
    }
}
