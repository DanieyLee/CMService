package cn.hupig.www.code.cmservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class SystemImageDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemImageDTO.class);
        SystemImageDTO systemImageDTO1 = new SystemImageDTO();
        systemImageDTO1.setId(1L);
        SystemImageDTO systemImageDTO2 = new SystemImageDTO();
        assertThat(systemImageDTO1).isNotEqualTo(systemImageDTO2);
        systemImageDTO2.setId(systemImageDTO1.getId());
        assertThat(systemImageDTO1).isEqualTo(systemImageDTO2);
        systemImageDTO2.setId(2L);
        assertThat(systemImageDTO1).isNotEqualTo(systemImageDTO2);
        systemImageDTO1.setId(null);
        assertThat(systemImageDTO1).isNotEqualTo(systemImageDTO2);
    }
}
