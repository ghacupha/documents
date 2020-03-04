package io.github.docs.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionDocumentMapperTest {

    private TransactionDocumentMapper transactionDocumentMapper;

    @BeforeEach
    public void setUp() {
        transactionDocumentMapper = new TransactionDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(transactionDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(transactionDocumentMapper.fromId(null)).isNull();
    }
}
