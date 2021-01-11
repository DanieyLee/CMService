package cn.hupig.www.code.cmservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class UserLinkDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserLinkDTO.class);
        UserLinkDTO userLinkDTO1 = new UserLinkDTO();
        userLinkDTO1.setId(1L);
        UserLinkDTO userLinkDTO2 = new UserLinkDTO();
        assertThat(userLinkDTO1).isNotEqualTo(userLinkDTO2);
        userLinkDTO2.setId(userLinkDTO1.getId());
        assertThat(userLinkDTO1).isEqualTo(userLinkDTO2);
        userLinkDTO2.setId(2L);
        assertThat(userLinkDTO1).isNotEqualTo(userLinkDTO2);
        userLinkDTO1.setId(null);
        assertThat(userLinkDTO1).isNotEqualTo(userLinkDTO2);
    }
}
