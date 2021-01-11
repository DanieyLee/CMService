package cn.hupig.www.code.cmservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ArticlEnclosureMapperTest {

    private ArticlEnclosureMapper articlEnclosureMapper;

    @BeforeEach
    public void setUp() {
        articlEnclosureMapper = new ArticlEnclosureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(articlEnclosureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(articlEnclosureMapper.fromId(null)).isNull();
    }
}
