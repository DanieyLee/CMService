package cn.hupig.www.code.cmservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class ArticleTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleTypeDTO.class);
        ArticleTypeDTO articleTypeDTO1 = new ArticleTypeDTO();
        articleTypeDTO1.setId(1L);
        ArticleTypeDTO articleTypeDTO2 = new ArticleTypeDTO();
        assertThat(articleTypeDTO1).isNotEqualTo(articleTypeDTO2);
        articleTypeDTO2.setId(articleTypeDTO1.getId());
        assertThat(articleTypeDTO1).isEqualTo(articleTypeDTO2);
        articleTypeDTO2.setId(2L);
        assertThat(articleTypeDTO1).isNotEqualTo(articleTypeDTO2);
        articleTypeDTO1.setId(null);
        assertThat(articleTypeDTO1).isNotEqualTo(articleTypeDTO2);
    }
}
