package com.datalinkedai.process.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.datalinkedai.process.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayoutBonusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayoutBonus.class);
        PayoutBonus payoutBonus1 = new PayoutBonus();
        payoutBonus1.setId("id1");
        PayoutBonus payoutBonus2 = new PayoutBonus();
        payoutBonus2.setId(payoutBonus1.getId());
        assertThat(payoutBonus1).isEqualTo(payoutBonus2);
        payoutBonus2.setId("id2");
        assertThat(payoutBonus1).isNotEqualTo(payoutBonus2);
        payoutBonus1.setId(null);
        assertThat(payoutBonus1).isNotEqualTo(payoutBonus2);
    }
}
