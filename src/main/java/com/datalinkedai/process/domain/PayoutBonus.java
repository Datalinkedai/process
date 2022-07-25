package com.datalinkedai.process.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Duration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The PayoutBonus entity.\n@author A true hipster
 */
@Schema(description = "The PayoutBonus entity.\n@author A true hipster")
@Document(collection = "payout_bonus")
public class PayoutBonus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("basic_duration")
    private Duration basicDuration;

    @Field("basic_cost")
    private Double basicCost;

    @Field("bonus_duration")
    private Duration bonusDuration;

    @Field("bonus_cost")
    private Double bonusCost;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public PayoutBonus id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Duration getBasicDuration() {
        return this.basicDuration;
    }

    public PayoutBonus basicDuration(Duration basicDuration) {
        this.setBasicDuration(basicDuration);
        return this;
    }

    public void setBasicDuration(Duration basicDuration) {
        this.basicDuration = basicDuration;
    }

    public Double getBasicCost() {
        return this.basicCost;
    }

    public PayoutBonus basicCost(Double basicCost) {
        this.setBasicCost(basicCost);
        return this;
    }

    public void setBasicCost(Double basicCost) {
        this.basicCost = basicCost;
    }

    public Duration getBonusDuration() {
        return this.bonusDuration;
    }

    public PayoutBonus bonusDuration(Duration bonusDuration) {
        this.setBonusDuration(bonusDuration);
        return this;
    }

    public void setBonusDuration(Duration bonusDuration) {
        this.bonusDuration = bonusDuration;
    }

    public Double getBonusCost() {
        return this.bonusCost;
    }

    public PayoutBonus bonusCost(Double bonusCost) {
        this.setBonusCost(bonusCost);
        return this;
    }

    public void setBonusCost(Double bonusCost) {
        this.bonusCost = bonusCost;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PayoutBonus)) {
            return false;
        }
        return id != null && id.equals(((PayoutBonus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PayoutBonus{" +
            "id=" + getId() +
            ", basicDuration='" + getBasicDuration() + "'" +
            ", basicCost=" + getBasicCost() +
            ", bonusDuration='" + getBonusDuration() + "'" +
            ", bonusCost=" + getBonusCost() +
            "}";
    }
}
