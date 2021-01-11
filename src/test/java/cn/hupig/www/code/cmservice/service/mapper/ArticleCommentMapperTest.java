package cn.hupig.www.code.cmservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ArticleCommentMapperTest {

    private ArticleCommentMapper articleCommentMapper;

    @BeforeEach
    public void setUp() {
        articleCommentMapper = new ArticleCommentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(articleCommentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(articleCommentMapper.fromId(null)).isNull();
    }
}
