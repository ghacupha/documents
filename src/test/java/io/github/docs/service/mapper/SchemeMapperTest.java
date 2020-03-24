package io.github.docs.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SchemeMapperTest {

    private SchemeMapper schemeMapper;

    @BeforeEach
    public void setUp() {
        schemeMapper = new SchemeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(schemeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(schemeMapper.fromId(null)).isNull();
    }
}
