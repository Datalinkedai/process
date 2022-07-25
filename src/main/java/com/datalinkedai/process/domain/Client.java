package com.datalinkedai.process.domain;

import com.datalinkedai.process.domain.enumeration.PayoutStructure;
import com.datalinkedai.process.domain.enumeration.Status;
import com.datalinkedai.process.domain.enumeration.TaskType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Client.
 */
@Document(collection = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("address")
    private String address;

    @Field("description")
    private String description;

    @Field("business_start_date")
    private LocalDate businessStartDate;

    @Field("business_end_date")
    private LocalDate businessEndDate;

    @Field("payout")
    private PayoutStructure payout;

    @Field("status")
    private Status status;

    @Field("remarks")
    private String remarks;

    @Field("task_name")
    private String taskName;

    @Field("task_type")
    private TaskType taskType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Client id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Client name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Client address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return this.description;
    }

    public Client description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBusinessStartDate() {
        return this.businessStartDate;
    }

    public Client businessStartDate(LocalDate businessStartDate) {
        this.setBusinessStartDate(businessStartDate);
        return this;
    }

    public void setBusinessStartDate(LocalDate businessStartDate) {
        this.businessStartDate = businessStartDate;
    }

    public LocalDate getBusinessEndDate() {
        return this.businessEndDate;
    }

    public Client businessEndDate(LocalDate businessEndDate) {
        this.setBusinessEndDate(businessEndDate);
        return this;
    }

    public void setBusinessEndDate(LocalDate businessEndDate) {
        this.businessEndDate = businessEndDate;
    }

    public PayoutStructure getPayout() {
        return this.payout;
    }

    public Client payout(PayoutStructure payout) {
        this.setPayout(payout);
        return this;
    }

    public void setPayout(PayoutStructure payout) {
        this.payout = payout;
    }

    public Status getStatus() {
        return this.status;
    }

    public Client status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public Client remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public Client taskName(String taskName) {
        this.setTaskName(taskName);
        return this;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public TaskType getTaskType() {
        return this.taskType;
    }

    public Client taskType(TaskType taskType) {
        this.setTaskType(taskType);
        return this;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", description='" + getDescription() + "'" +
            ", businessStartDate='" + getBusinessStartDate() + "'" +
            ", businessEndDate='" + getBusinessEndDate() + "'" +
            ", payout='" + getPayout() + "'" +
            ", status='" + getStatus() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", taskName='" + getTaskName() + "'" +
            ", taskType='" + getTaskType() + "'" +
            "}";
    }
}
