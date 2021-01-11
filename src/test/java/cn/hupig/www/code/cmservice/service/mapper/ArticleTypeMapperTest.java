package cn.hupig.www.code.cmservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ArticleTypeMapperTest {

    private ArticleTypeMapper articleTypeMapper;

    @BeforeEach
    public void setUp() {
        articleTypeMapper = new ArticleTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(articleTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(articleTypeMapper.fromId(null)).isNull();
    }
}
