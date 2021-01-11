package cn.hupig.www.code.cmservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class ArticleTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleType.class);
        ArticleType articleType1 = new ArticleType();
        articleType1.setId(1L);
        ArticleType articleType2 = new ArticleType();
        articleType2.setId(articleType1.getId());
        assertThat(articleType1).isEqualTo(articleType2);
        articleType2.setId(2L);
        assertThat(articleType1).isNotEqualTo(articleType2);
        articleType1.setId(null);
        assertThat(articleType1).isNotEqualTo(articleType2);
    }
}
