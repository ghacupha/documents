package io.github.docs.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.docs.web.rest.TestUtil;

public class TransactionDocumentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionDocumentDTO.class);
        TransactionDocumentDTO transactionDocumentDTO1 = new TransactionDocumentDTO();
        transactionDocumentDTO1.setId(1L);
        TransactionDocumentDTO transactionDocumentDTO2 = new TransactionDocumentDTO();
        assertThat(transactionDocumentDTO1).isNotEqualTo(transactionDocumentDTO2);
        transactionDocumentDTO2.setId(transactionDocumentDTO1.getId());
        assertThat(transactionDocumentDTO1).isEqualTo(transactionDocumentDTO2);
        transactionDocumentDTO2.setId(2L);
        assertThat(transactionDocumentDTO1).isNotEqualTo(transactionDocumentDTO2);
        transactionDocumentDTO1.setId(null);
        assertThat(transactionDocumentDTO1).isNotEqualTo(transactionDocumentDTO2);
    }
}
