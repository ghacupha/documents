package io.github.docs.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.docs.web.rest.TestUtil;

public class FormalDocumentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormalDocumentDTO.class);
        FormalDocumentDTO formalDocumentDTO1 = new FormalDocumentDTO();
        formalDocumentDTO1.setId(1L);
        FormalDocumentDTO formalDocumentDTO2 = new FormalDocumentDTO();
        assertThat(formalDocumentDTO1).isNotEqualTo(formalDocumentDTO2);
        formalDocumentDTO2.setId(formalDocumentDTO1.getId());
        assertThat(formalDocumentDTO1).isEqualTo(formalDocumentDTO2);
        formalDocumentDTO2.setId(2L);
        assertThat(formalDocumentDTO1).isNotEqualTo(formalDocumentDTO2);
        formalDocumentDTO1.setId(null);
        assertThat(formalDocumentDTO1).isNotEqualTo(formalDocumentDTO2);
    }
}
