package cn.hupig.www.code.cmservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class ArticleEnclosureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleEnclosureDTO.class);
        ArticleEnclosureDTO articleEnclosureDTO1 = new ArticleEnclosureDTO();
        articleEnclosureDTO1.setId(1L);
        ArticleEnclosureDTO articleEnclosureDTO2 = new ArticleEnclosureDTO();
        assertThat(articleEnclosureDTO1).isNotEqualTo(articleEnclosureDTO2);
        articleEnclosureDTO2.setId(articleEnclosureDTO1.getId());
        assertThat(articleEnclosureDTO1).isEqualTo(articleEnclosureDTO2);
        articleEnclosureDTO2.setId(2L);
        assertThat(articleEnclosureDTO1).isNotEqualTo(articleEnclosureDTO2);
        articleEnclosureDTO1.setId(null);
        assertThat(articleEnclosureDTO1).isNotEqualTo(articleEnclosureDTO2);
    }
}
