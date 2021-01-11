package cn.hupig.www.code.cmservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class KeyBoxDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyBoxDTO.class);
        KeyBoxDTO keyBoxDTO1 = new KeyBoxDTO();
        keyBoxDTO1.setId(1L);
        KeyBoxDTO keyBoxDTO2 = new KeyBoxDTO();
        assertThat(keyBoxDTO1).isNotEqualTo(keyBoxDTO2);
        keyBoxDTO2.setId(keyBoxDTO1.getId());
        assertThat(keyBoxDTO1).isEqualTo(keyBoxDTO2);
        keyBoxDTO2.setId(2L);
        assertThat(keyBoxDTO1).isNotEqualTo(keyBoxDTO2);
        keyBoxDTO1.setId(null);
        assertThat(keyBoxDTO1).isNotEqualTo(keyBoxDTO2);
    }
}
