package io.github.docs.app.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionDocumentMetadataMapperTest {

    private TransactionDocumentMetadataMapper transactionDocumentMetadataMapper;

    @BeforeEach
    public void setUp() {
        transactionDocumentMetadataMapper = new TransactionDocumentMetadataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(transactionDocumentMetadataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(transactionDocumentMetadataMapper.fromId(null)).isNull();
    }

}
