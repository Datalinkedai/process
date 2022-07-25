package com.datalinkedai.process.domain;

import com.datalinkedai.process.domain.enumeration.HealthyTask;
import com.datalinkedai.process.domain.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The Entity Task.\n@author anindya100c
 */
@Schema(description = "The Entity Task.\n@author anindya100c")
@Document(collection = "task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("total_no_of_work")
    private Integer totalNoOfWork;

    @Field("start_date")
    private Instant startDate;

    @Field("end_date")
    private Instant endDate;

    @Field("health")
    private HealthyTask health;

    @Field("status")
    private Status status;

    @Field("work_name")
    private String workName;

    @Field("remarks")
    private String remarks;

    @Field("total_duration")
    private Duration totalDuration;

    @Field("done_duration")
    private Duration doneDuration;

    @Field("verified")
    private Boolean verified;

    @Field("free_text_1")
    private String freeText1;

    @Field("free_text_2")
    private String freeText2;

    @Field("free_text_3")
    private String freeText3;

    @Field("free_text_4")
    private String freeText4;

    @Field("free_text_5")
    private String freeText5;

    @Field("task_assigned_date")
    private Instant taskAssignedDate;

    @Field("task_completed_date")
    private Instant taskCompletedDate;

    @DBRef
    @Field("assignedTo")
    private Employee assignedTo;

    @DBRef
    @Field("assignedBy")
    private Employee assignedBy;

    @DBRef
    @Field("verifiedBy")
    private Employee verifiedBy;

    @DBRef
    @Field("documentsTask")
    @JsonIgnoreProperties(value = { "fromCandidate", "verifiedBy" }, allowSetters = true)
    private Documents documentsTask;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Task id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotalNoOfWork() {
        return this.totalNoOfWork;
    }

    public Task totalNoOfWork(Integer totalNoOfWork) {
        this.setTotalNoOfWork(totalNoOfWork);
        return this;
    }

    public void setTotalNoOfWork(Integer totalNoOfWork) {
        this.totalNoOfWork = totalNoOfWork;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Task startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Task endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public HealthyTask getHealth() {
        return this.health;
    }

    public Task health(HealthyTask health) {
        this.setHealth(health);
        return this;
    }

    public void setHealth(HealthyTask health) {
        this.health = health;
    }

    public Status getStatus() {
        return this.status;
    }

    public Task status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getWorkName() {
        return this.workName;
    }

    public Task workName(String workName) {
        this.setWorkName(workName);
        return this;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public Task remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Duration getTotalDuration() {
        return this.totalDuration;
    }

    public Task totalDuration(Duration totalDuration) {
        this.setTotalDuration(totalDuration);
        return this;
    }

    public void setTotalDuration(Duration totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Duration getDoneDuration() {
        return this.doneDuration;
    }

    public Task doneDuration(Duration doneDuration) {
        this.setDoneDuration(doneDuration);
        return this;
    }

    public void setDoneDuration(Duration doneDuration) {
        this.doneDuration = doneDuration;
    }

    public Boolean getVerified() {
        return this.verified;
    }

    public Task verified(Boolean verified) {
        this.setVerified(verified);
        return this;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getFreeText1() {
        return this.freeText1;
    }

    public Task freeText1(String freeText1) {
        this.setFreeText1(freeText1);
        return this;
    }

    public void setFreeText1(String freeText1) {
        this.freeText1 = freeText1;
    }

    public String getFreeText2() {
        return this.freeText2;
    }

    public Task freeText2(String freeText2) {
        this.setFreeText2(freeText2);
        return this;
    }

    public void setFreeText2(String freeText2) {
        this.freeText2 = freeText2;
    }

    public String getFreeText3() {
        return this.freeText3;
    }

    public Task freeText3(String freeText3) {
        this.setFreeText3(freeText3);
        return this;
    }

    public void setFreeText3(String freeText3) {
        this.freeText3 = freeText3;
    }

    public String getFreeText4() {
        return this.freeText4;
    }

    public Task freeText4(String freeText4) {
        this.setFreeText4(freeText4);
        return this;
    }

    public void setFreeText4(String freeText4) {
        this.freeText4 = freeText4;
    }

    public String getFreeText5() {
        return this.freeText5;
    }

    public Task freeText5(String freeText5) {
        this.setFreeText5(freeText5);
        return this;
    }

    public void setFreeText5(String freeText5) {
        this.freeText5 = freeText5;
    }

    public Instant getTaskAssignedDate() {
        return this.taskAssignedDate;
    }

    public Task taskAssignedDate(Instant taskAssignedDate) {
        this.setTaskAssignedDate(taskAssignedDate);
        return this;
    }

    public void setTaskAssignedDate(Instant taskAssignedDate) {
        this.taskAssignedDate = taskAssignedDate;
    }

    public Instant getTaskCompletedDate() {
        return this.taskCompletedDate;
    }

    public Task taskCompletedDate(Instant taskCompletedDate) {
        this.setTaskCompletedDate(taskCompletedDate);
        return this;
    }

    public void setTaskCompletedDate(Instant taskCompletedDate) {
        this.taskCompletedDate = taskCompletedDate;
    }

    public Employee getAssignedTo() {
        return this.assignedTo;
    }

    public void setAssignedTo(Employee employee) {
        this.assignedTo = employee;
    }

    public Task assignedTo(Employee employee) {
        this.setAssignedTo(employee);
        return this;
    }

    public Employee getAssignedBy() {
        return this.assignedBy;
    }

    public void setAssignedBy(Employee employee) {
        this.assignedBy = employee;
    }

    public Task assignedBy(Employee employee) {
        this.setAssignedBy(employee);
        return this;
    }

    public Employee getVerifiedBy() {
        return this.verifiedBy;
    }

    public void setVerifiedBy(Employee employee) {
        this.verifiedBy = employee;
    }

    public Task verifiedBy(Employee employee) {
        this.setVerifiedBy(employee);
        return this;
    }

    public Documents getDocumentsTask() {
        return this.documentsTask;
    }

    public void setDocumentsTask(Documents documents) {
        this.documentsTask = documents;
    }

    public Task documentsTask(Documents documents) {
        this.setDocumentsTask(documents);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", totalNoOfWork=" + getTotalNoOfWork() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", health='" + getHealth() + "'" +
            ", status='" + getStatus() + "'" +
            ", workName='" + getWorkName() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", totalDuration='" + getTotalDuration() + "'" +
            ", doneDuration='" + getDoneDuration() + "'" +
            ", verified='" + getVerified() + "'" +
            ", freeText1='" + getFreeText1() + "'" +
            ", freeText2='" + getFreeText2() + "'" +
            ", freeText3='" + getFreeText3() + "'" +
            ", freeText4='" + getFreeText4() + "'" +
            ", freeText5='" + getFreeText5() + "'" +
            ", taskAssignedDate='" + getTaskAssignedDate() + "'" +
            ", taskCompletedDate='" + getTaskCompletedDate() + "'" +
            "}";
    }
}
