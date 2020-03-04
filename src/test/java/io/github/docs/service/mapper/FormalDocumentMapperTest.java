package io.github.docs.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FormalDocumentMapperTest {

    private FormalDocumentMapper formalDocumentMapper;

    @BeforeEach
    public void setUp() {
        formalDocumentMapper = new FormalDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(formalDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(formalDocumentMapper.fromId(null)).isNull();
    }
}
