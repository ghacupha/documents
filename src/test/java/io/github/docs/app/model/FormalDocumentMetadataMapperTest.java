package io.github.docs.app.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FormalDocumentMetadataMapperTest {


    private FormalDocumentMetadataMapper formalDocumentMetadataMapper;

    @BeforeEach
    public void setUp() {
        formalDocumentMetadataMapper = new FormalDocumentMetadataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(formalDocumentMetadataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(formalDocumentMetadataMapper.fromId(null)).isNull();
    }

}
