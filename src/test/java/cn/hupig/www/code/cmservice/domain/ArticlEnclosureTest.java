package cn.hupig.www.code.cmservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class ArticlEnclosureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticlEnclosure.class);
        ArticlEnclosure articlEnclosure1 = new ArticlEnclosure();
        articlEnclosure1.setId(1L);
        ArticlEnclosure articlEnclosure2 = new ArticlEnclosure();
        articlEnclosure2.setId(articlEnclosure1.getId());
        assertThat(articlEnclosure1).isEqualTo(articlEnclosure2);
        articlEnclosure2.setId(2L);
        assertThat(articlEnclosure1).isNotEqualTo(articlEnclosure2);
        articlEnclosure1.setId(null);
        assertThat(articlEnclosure1).isNotEqualTo(articlEnclosure2);
    }
}
