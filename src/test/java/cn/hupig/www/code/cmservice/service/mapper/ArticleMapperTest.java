package cn.hupig.www.code.cmservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ArticleMapperTest {

    private ArticleMapper articleMapper;

    @BeforeEach
    public void setUp() {
        articleMapper = new ArticleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(articleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(articleMapper.fromId(null)).isNull();
    }
}
