package com.datalinkedai.process.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.datalinkedai.process.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QualtiyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Qualtiy.class);
        Qualtiy qualtiy1 = new Qualtiy();
        qualtiy1.setId("id1");
        Qualtiy qualtiy2 = new Qualtiy();
        qualtiy2.setId(qualtiy1.getId());
        assertThat(qualtiy1).isEqualTo(qualtiy2);
        qualtiy2.setId("id2");
        assertThat(qualtiy1).isNotEqualTo(qualtiy2);
        qualtiy1.setId(null);
        assertThat(qualtiy1).isNotEqualTo(qualtiy2);
    }
}
