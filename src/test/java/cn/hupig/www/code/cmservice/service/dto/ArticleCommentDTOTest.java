package cn.hupig.www.code.cmservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class ArticleCommentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleCommentDTO.class);
        ArticleCommentDTO articleCommentDTO1 = new ArticleCommentDTO();
        articleCommentDTO1.setId(1L);
        ArticleCommentDTO articleCommentDTO2 = new ArticleCommentDTO();
        assertThat(articleCommentDTO1).isNotEqualTo(articleCommentDTO2);
        articleCommentDTO2.setId(articleCommentDTO1.getId());
        assertThat(articleCommentDTO1).isEqualTo(articleCommentDTO2);
        articleCommentDTO2.setId(2L);
        assertThat(articleCommentDTO1).isNotEqualTo(articleCommentDTO2);
        articleCommentDTO1.setId(null);
        assertThat(articleCommentDTO1).isNotEqualTo(articleCommentDTO2);
    }
}
