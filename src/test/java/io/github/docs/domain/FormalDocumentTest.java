package io.github.docs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.docs.web.rest.TestUtil;

public class FormalDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormalDocument.class);
        FormalDocument formalDocument1 = new FormalDocument();
        formalDocument1.setId(1L);
        FormalDocument formalDocument2 = new FormalDocument();
        formalDocument2.setId(formalDocument1.getId());
        assertThat(formalDocument1).isEqualTo(formalDocument2);
        formalDocument2.setId(2L);
        assertThat(formalDocument1).isNotEqualTo(formalDocument2);
        formalDocument1.setId(null);
        assertThat(formalDocument1).isNotEqualTo(formalDocument2);
    }
}
