package cn.hupig.www.code.cmservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class KeyBoxTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyBox.class);
        KeyBox keyBox1 = new KeyBox();
        keyBox1.setId(1L);
        KeyBox keyBox2 = new KeyBox();
        keyBox2.setId(keyBox1.getId());
        assertThat(keyBox1).isEqualTo(keyBox2);
        keyBox2.setId(2L);
        assertThat(keyBox1).isNotEqualTo(keyBox2);
        keyBox1.setId(null);
        assertThat(keyBox1).isNotEqualTo(keyBox2);
    }
}
