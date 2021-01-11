package cn.hupig.www.code.cmservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class UserLinkTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserLink.class);
        UserLink userLink1 = new UserLink();
        userLink1.setId(1L);
        UserLink userLink2 = new UserLink();
        userLink2.setId(userLink1.getId());
        assertThat(userLink1).isEqualTo(userLink2);
        userLink2.setId(2L);
        assertThat(userLink1).isNotEqualTo(userLink2);
        userLink1.setId(null);
        assertThat(userLink1).isNotEqualTo(userLink2);
    }
}
