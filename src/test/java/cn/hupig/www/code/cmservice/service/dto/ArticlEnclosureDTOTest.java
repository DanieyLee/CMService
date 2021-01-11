package cn.hupig.www.code.cmservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.hupig.www.code.cmservice.web.rest.TestUtil;

public class ArticlEnclosureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticlEnclosureDTO.class);
        ArticlEnclosureDTO articlEnclosureDTO1 = new ArticlEnclosureDTO();
        articlEnclosureDTO1.setId(1L);
        ArticlEnclosureDTO articlEnclosureDTO2 = new ArticlEnclosureDTO();
        assertThat(articlEnclosureDTO1).isNotEqualTo(articlEnclosureDTO2);
        articlEnclosureDTO2.setId(articlEnclosureDTO1.getId());
        assertThat(articlEnclosureDTO1).isEqualTo(articlEnclosureDTO2);
        articlEnclosureDTO2.setId(2L);
        assertThat(articlEnclosureDTO1).isNotEqualTo(articlEnclosureDTO2);
        articlEnclosureDTO1.setId(null);
        assertThat(articlEnclosureDTO1).isNotEqualTo(articlEnclosureDTO2);
    }
}
