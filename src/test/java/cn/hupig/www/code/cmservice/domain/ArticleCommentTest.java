package cn.hupig.www.code.cmservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class ArticleCommentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleComment.class);
        ArticleComment articleComment1 = new ArticleComment();
        articleComment1.setId(1L);
        ArticleComment articleComment2 = new ArticleComment();
        articleComment2.setId(articleComment1.getId());
        assertThat(articleComment1).isEqualTo(articleComment2);
        articleComment2.setId(2L);
        assertThat(articleComment1).isNotEqualTo(articleComment2);
        articleComment1.setId(null);
        assertThat(articleComment1).isNotEqualTo(articleComment2);
    }
}
