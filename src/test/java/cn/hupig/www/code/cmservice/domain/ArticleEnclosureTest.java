package cn.hupig.www.code.cmservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class ArticleEnclosureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleEnclosure.class);
        ArticleEnclosure articleEnclosure1 = new ArticleEnclosure();
        articleEnclosure1.setId(1L);
        ArticleEnclosure articleEnclosure2 = new ArticleEnclosure();
        articleEnclosure2.setId(articleEnclosure1.getId());
        assertThat(articleEnclosure1).isEqualTo(articleEnclosure2);
        articleEnclosure2.setId(2L);
        assertThat(articleEnclosure1).isNotEqualTo(articleEnclosure2);
        articleEnclosure1.setId(null);
        assertThat(articleEnclosure1).isNotEqualTo(articleEnclosure2);
    }
}
