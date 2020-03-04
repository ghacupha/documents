package io.github.docs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.docs.web.rest.TestUtil;

public class TransactionDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionDocument.class);
        TransactionDocument transactionDocument1 = new TransactionDocument();
        transactionDocument1.setId(1L);
        TransactionDocument transactionDocument2 = new TransactionDocument();
        transactionDocument2.setId(transactionDocument1.getId());
        assertThat(transactionDocument1).isEqualTo(transactionDocument2);
        transactionDocument2.setId(2L);
        assertThat(transactionDocument1).isNotEqualTo(transactionDocument2);
        transactionDocument1.setId(null);
        assertThat(transactionDocument1).isNotEqualTo(transactionDocument2);
    }
}
