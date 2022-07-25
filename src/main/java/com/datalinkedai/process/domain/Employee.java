package com.datalinkedai.process.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The Entity Employee.\n@author anindya100c
 */
@Schema(description = "The Entity Employee.\n@author anindya100c")
@Document(collection = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("date_of_joining")
    private Instant dateOfJoining;

    @Pattern(regexp = "^[0-9]{3,8}$")
    @Field("employee_id")
    private String employeeId;

    @Field("account_id")
    private String accountId;

    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$")
    @Field("ifsc_code")
    private String ifscCode;

    @Field("bank_name")
    private String bankName;

    @Field("bank_holder_name")
    private String bankHolderName;

    @Field("pf_number")
    private String pfNumber;

    @Field("esi_number")
    private String esiNumber;

    @DBRef
    @Field("userName")
    private Candidate userName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Employee id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDateOfJoining() {
        return this.dateOfJoining;
    }

    public Employee dateOfJoining(Instant dateOfJoining) {
        this.setDateOfJoining(dateOfJoining);
        return this;
    }

    public void setDateOfJoining(Instant dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public Employee employeeId(String employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public Employee accountId(String accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getIfscCode() {
        return this.ifscCode;
    }

    public Employee ifscCode(String ifscCode) {
        this.setIfscCode(ifscCode);
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankName() {
        return this.bankName;
    }

    public Employee bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankHolderName() {
        return this.bankHolderName;
    }

    public Employee bankHolderName(String bankHolderName) {
        this.setBankHolderName(bankHolderName);
        return this;
    }

    public void setBankHolderName(String bankHolderName) {
        this.bankHolderName = bankHolderName;
    }

    public String getPfNumber() {
        return this.pfNumber;
    }

    public Employee pfNumber(String pfNumber) {
        this.setPfNumber(pfNumber);
        return this;
    }

    public void setPfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
    }

    public String getEsiNumber() {
        return this.esiNumber;
    }

    public Employee esiNumber(String esiNumber) {
        this.setEsiNumber(esiNumber);
        return this;
    }

    public void setEsiNumber(String esiNumber) {
        this.esiNumber = esiNumber;
    }

    public Candidate getUserName() {
        return this.userName;
    }

    public void setUserName(Candidate candidate) {
        this.userName = candidate;
    }

    public Employee userName(Candidate candidate) {
        this.setUserName(candidate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", dateOfJoining='" + getDateOfJoining() + "'" +
            ", employeeId='" + getEmployeeId() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", bankHolderName='" + getBankHolderName() + "'" +
            ", pfNumber='" + getPfNumber() + "'" +
            ", esiNumber='" + getEsiNumber() + "'" +
            "}";
    }
}
