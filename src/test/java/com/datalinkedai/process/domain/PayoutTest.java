package com.datalinkedai.process.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.datalinkedai.process.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayoutTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payout.class);
        Payout payout1 = new Payout();
        payout1.setId("id1");
        Payout payout2 = new Payout();
        payout2.setId(payout1.getId());
        assertThat(payout1).isEqualTo(payout2);
        payout2.setId("id2");
        assertThat(payout1).isNotEqualTo(payout2);
        payout1.setId(null);
        assertThat(payout1).isNotEqualTo(payout2);
    }
}
