package cn.hupig.www.code.cmservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class SoftwareTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoftwareTypeDTO.class);
        SoftwareTypeDTO softwareTypeDTO1 = new SoftwareTypeDTO();
        softwareTypeDTO1.setId(1L);
        SoftwareTypeDTO softwareTypeDTO2 = new SoftwareTypeDTO();
        assertThat(softwareTypeDTO1).isNotEqualTo(softwareTypeDTO2);
        softwareTypeDTO2.setId(softwareTypeDTO1.getId());
        assertThat(softwareTypeDTO1).isEqualTo(softwareTypeDTO2);
        softwareTypeDTO2.setId(2L);
        assertThat(softwareTypeDTO1).isNotEqualTo(softwareTypeDTO2);
        softwareTypeDTO1.setId(null);
        assertThat(softwareTypeDTO1).isNotEqualTo(softwareTypeDTO2);
    }
}
