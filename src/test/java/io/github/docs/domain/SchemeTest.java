package io.github.docs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.docs.web.rest.TestUtil;

public class SchemeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scheme.class);
        Scheme scheme1 = new Scheme();
        scheme1.setId(1L);
        Scheme scheme2 = new Scheme();
        scheme2.setId(scheme1.getId());
        assertThat(scheme1).isEqualTo(scheme2);
        scheme2.setId(2L);
        assertThat(scheme1).isNotEqualTo(scheme2);
        scheme1.setId(null);
        assertThat(scheme1).isNotEqualTo(scheme2);
    }
}
