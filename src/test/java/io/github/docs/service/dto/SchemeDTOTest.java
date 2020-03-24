package io.github.docs.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.docs.web.rest.TestUtil;

public class SchemeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchemeDTO.class);
        SchemeDTO schemeDTO1 = new SchemeDTO();
        schemeDTO1.setId(1L);
        SchemeDTO schemeDTO2 = new SchemeDTO();
        assertThat(schemeDTO1).isNotEqualTo(schemeDTO2);
        schemeDTO2.setId(schemeDTO1.getId());
        assertThat(schemeDTO1).isEqualTo(schemeDTO2);
        schemeDTO2.setId(2L);
        assertThat(schemeDTO1).isNotEqualTo(schemeDTO2);
        schemeDTO1.setId(null);
        assertThat(schemeDTO1).isNotEqualTo(schemeDTO2);
    }
}
