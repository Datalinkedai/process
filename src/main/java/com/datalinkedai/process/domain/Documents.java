package com.datalinkedai.process.domain;

import com.datalinkedai.process.domain.enumeration.DocumentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Documents.
 */
@Document(collection = "documents")
public class Documents implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("document_type")
    private DocumentType documentType;

    @Field("document")
    private byte[] document;

    @Field("document_content_type")
    private String documentContentType;

    @Field("document_link")
    private String documentLink;

    @Field("document_expiry")
    private LocalDate documentExpiry;

    @Field("verified")
    private Boolean verified;

    @DBRef
    @Field("fromCandidate")
    private Employee fromCandidate;

    @DBRef
    @Field("verifiedBy")
    private Employee verifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Documents id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public Documents documentType(DocumentType documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public byte[] getDocument() {
        return this.document;
    }

    public Documents document(byte[] document) {
        this.setDocument(document);
        return this;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getDocumentContentType() {
        return this.documentContentType;
    }

    public Documents documentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
        return this;
    }

    public void setDocumentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
    }

    public String getDocumentLink() {
        return this.documentLink;
    }

    public Documents documentLink(String documentLink) {
        this.setDocumentLink(documentLink);
        return this;
    }

    public void setDocumentLink(String documentLink) {
        this.documentLink = documentLink;
    }

    public LocalDate getDocumentExpiry() {
        return this.documentExpiry;
    }

    public Documents documentExpiry(LocalDate documentExpiry) {
        this.setDocumentExpiry(documentExpiry);
        return this;
    }

    public void setDocumentExpiry(LocalDate documentExpiry) {
        this.documentExpiry = documentExpiry;
    }

    public Boolean getVerified() {
        return this.verified;
    }

    public Documents verified(Boolean verified) {
        this.setVerified(verified);
        return this;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Employee getFromCandidate() {
        return this.fromCandidate;
    }

    public void setFromCandidate(Employee employee) {
        this.fromCandidate = employee;
    }

    public Documents fromCandidate(Employee employee) {
        this.setFromCandidate(employee);
        return this;
    }

    public Employee getVerifiedBy() {
        return this.verifiedBy;
    }

    public void setVerifiedBy(Employee employee) {
        this.verifiedBy = employee;
    }

    public Documents verifiedBy(Employee employee) {
        this.setVerifiedBy(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documents)) {
            return false;
        }
        return id != null && id.equals(((Documents) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Documents{" +
            "id=" + getId() +
            ", documentType='" + getDocumentType() + "'" +
            ", document='" + getDocument() + "'" +
            ", documentContentType='" + getDocumentContentType() + "'" +
            ", documentLink='" + getDocumentLink() + "'" +
            ", documentExpiry='" + getDocumentExpiry() + "'" +
            ", verified='" + getVerified() + "'" +
            "}";
    }
}
