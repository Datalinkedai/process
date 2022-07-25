package com.datalinkedai.process.domain;

import com.datalinkedai.process.domain.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The Qualtiy entity.\n@author anindya100c
 */
@Schema(description = "The Qualtiy entity.\n@author anindya100c")
@Document(collection = "qualtiy")
public class Qualtiy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("date")
    private LocalDate date;

    @Field("re_work")
    private Boolean reWork;

    @Field("re_work_status")
    private Status reWorkStatus;

    @Field("remarks")
    private String remarks;

    @Field("file_reach_date")
    private Instant fileReachDate;

    @Field("qc_start_date")
    private Instant qcStartDate;

    @Field("qc_end_date")
    private Instant qcEndDate;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Field("result")
    private Double result;

    @Field("re_work_duration")
    private Duration reWorkDuration;

    @DBRef
    @Field("task")
    private Task task;

    @DBRef
    @Field("qcBy")
    private Employee qcBy;

    @DBRef
    @Field("assignedTo")
    private Employee assignedTo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Qualtiy id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Qualtiy date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getReWork() {
        return this.reWork;
    }

    public Qualtiy reWork(Boolean reWork) {
        this.setReWork(reWork);
        return this;
    }

    public void setReWork(Boolean reWork) {
        this.reWork = reWork;
    }

    public Status getReWorkStatus() {
        return this.reWorkStatus;
    }

    public Qualtiy reWorkStatus(Status reWorkStatus) {
        this.setReWorkStatus(reWorkStatus);
        return this;
    }

    public void setReWorkStatus(Status reWorkStatus) {
        this.reWorkStatus = reWorkStatus;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public Qualtiy remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Instant getFileReachDate() {
        return this.fileReachDate;
    }

    public Qualtiy fileReachDate(Instant fileReachDate) {
        this.setFileReachDate(fileReachDate);
        return this;
    }

    public void setFileReachDate(Instant fileReachDate) {
        this.fileReachDate = fileReachDate;
    }

    public Instant getQcStartDate() {
        return this.qcStartDate;
    }

    public Qualtiy qcStartDate(Instant qcStartDate) {
        this.setQcStartDate(qcStartDate);
        return this;
    }

    public void setQcStartDate(Instant qcStartDate) {
        this.qcStartDate = qcStartDate;
    }

    public Instant getQcEndDate() {
        return this.qcEndDate;
    }

    public Qualtiy qcEndDate(Instant qcEndDate) {
        this.setQcEndDate(qcEndDate);
        return this;
    }

    public void setQcEndDate(Instant qcEndDate) {
        this.qcEndDate = qcEndDate;
    }

    public Double getResult() {
        return this.result;
    }

    public Qualtiy result(Double result) {
        this.setResult(result);
        return this;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Duration getReWorkDuration() {
        return this.reWorkDuration;
    }

    public Qualtiy reWorkDuration(Duration reWorkDuration) {
        this.setReWorkDuration(reWorkDuration);
        return this;
    }

    public void setReWorkDuration(Duration reWorkDuration) {
        this.reWorkDuration = reWorkDuration;
    }

    public Task getTask() {
        return this.task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Qualtiy task(Task task) {
        this.setTask(task);
        return this;
    }

    public Employee getQcBy() {
        return this.qcBy;
    }

    public void setQcBy(Employee employee) {
        this.qcBy = employee;
    }

    public Qualtiy qcBy(Employee employee) {
        this.setQcBy(employee);
        return this;
    }

    public Employee getAssignedTo() {
        return this.assignedTo;
    }

    public void setAssignedTo(Employee employee) {
        this.assignedTo = employee;
    }

    public Qualtiy assignedTo(Employee employee) {
        this.setAssignedTo(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Qualtiy)) {
            return false;
        }
        return id != null && id.equals(((Qualtiy) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Qualtiy{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", reWork='" + getReWork() + "'" +
            ", reWorkStatus='" + getReWorkStatus() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", fileReachDate='" + getFileReachDate() + "'" +
            ", qcStartDate='" + getQcStartDate() + "'" +
            ", qcEndDate='" + getQcEndDate() + "'" +
            ", result=" + getResult() +
            ", reWorkDuration='" + getReWorkDuration() + "'" +
            "}";
    }
}
