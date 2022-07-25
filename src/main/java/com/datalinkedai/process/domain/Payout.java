package com.datalinkedai.process.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Duration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The Payout entity.\n@author anindya100c
 */
@Schema(description = "The Payout entity.\n@author anindya100c")
@Document(collection = "payout")
public class Payout implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("total_duration")
    private Duration totalDuration;

    @Field("cost_per_duration")
    private Double costPerDuration;

    @Field("total_cost")
    private Double totalCost;

    @Field("bonus_payout")
    private Double bonusPayout;

    @Field("cost_per_duration_bonus")
    private Double costPerDurationBonus;

    @Field("total_duration_bonus")
    private Duration totalDurationBonus;

    @Field("total_cost_bonus")
    private Double totalCostBonus;

    @Field("pf_amount")
    private Double pfAmount;

    @Field("esi_amount")
    private Double esiAmount;

    @DBRef
    @Field("employee")
    private Employee employee;

    @DBRef
    @Field("authorisedBy")
    private Employee authorisedBy;

    @DBRef
    @Field("client")
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Payout id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Duration getTotalDuration() {
        return this.totalDuration;
    }

    public Payout totalDuration(Duration totalDuration) {
        this.setTotalDuration(totalDuration);
        return this;
    }

    public void setTotalDuration(Duration totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Double getCostPerDuration() {
        return this.costPerDuration;
    }

    public Payout costPerDuration(Double costPerDuration) {
        this.setCostPerDuration(costPerDuration);
        return this;
    }

    public void setCostPerDuration(Double costPerDuration) {
        this.costPerDuration = costPerDuration;
    }

    public Double getTotalCost() {
        return this.totalCost;
    }

    public Payout totalCost(Double totalCost) {
        this.setTotalCost(totalCost);
        return this;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getBonusPayout() {
        return this.bonusPayout;
    }

    public Payout bonusPayout(Double bonusPayout) {
        this.setBonusPayout(bonusPayout);
        return this;
    }

    public void setBonusPayout(Double bonusPayout) {
        this.bonusPayout = bonusPayout;
    }

    public Double getCostPerDurationBonus() {
        return this.costPerDurationBonus;
    }

    public Payout costPerDurationBonus(Double costPerDurationBonus) {
        this.setCostPerDurationBonus(costPerDurationBonus);
        return this;
    }

    public void setCostPerDurationBonus(Double costPerDurationBonus) {
        this.costPerDurationBonus = costPerDurationBonus;
    }

    public Duration getTotalDurationBonus() {
        return this.totalDurationBonus;
    }

    public Payout totalDurationBonus(Duration totalDurationBonus) {
        this.setTotalDurationBonus(totalDurationBonus);
        return this;
    }

    public void setTotalDurationBonus(Duration totalDurationBonus) {
        this.totalDurationBonus = totalDurationBonus;
    }

    public Double getTotalCostBonus() {
        return this.totalCostBonus;
    }

    public Payout totalCostBonus(Double totalCostBonus) {
        this.setTotalCostBonus(totalCostBonus);
        return this;
    }

    public void setTotalCostBonus(Double totalCostBonus) {
        this.totalCostBonus = totalCostBonus;
    }

    public Double getPfAmount() {
        return this.pfAmount;
    }

    public Payout pfAmount(Double pfAmount) {
        this.setPfAmount(pfAmount);
        return this;
    }

    public void setPfAmount(Double pfAmount) {
        this.pfAmount = pfAmount;
    }

    public Double getEsiAmount() {
        return this.esiAmount;
    }

    public Payout esiAmount(Double esiAmount) {
        this.setEsiAmount(esiAmount);
        return this;
    }

    public void setEsiAmount(Double esiAmount) {
        this.esiAmount = esiAmount;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Payout employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    public Employee getAuthorisedBy() {
        return this.authorisedBy;
    }

    public void setAuthorisedBy(Employee employee) {
        this.authorisedBy = employee;
    }

    public Payout authorisedBy(Employee employee) {
        this.setAuthorisedBy(employee);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Payout client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payout)) {
            return false;
        }
        return id != null && id.equals(((Payout) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payout{" +
            "id=" + getId() +
            ", totalDuration='" + getTotalDuration() + "'" +
            ", costPerDuration=" + getCostPerDuration() +
            ", totalCost=" + getTotalCost() +
            ", bonusPayout=" + getBonusPayout() +
            ", costPerDurationBonus=" + getCostPerDurationBonus() +
            ", totalDurationBonus='" + getTotalDurationBonus() + "'" +
            ", totalCostBonus=" + getTotalCostBonus() +
            ", pfAmount=" + getPfAmount() +
            ", esiAmount=" + getEsiAmount() +
            "}";
    }
}
