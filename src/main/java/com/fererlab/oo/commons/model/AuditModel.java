package com.fererlab.oo.commons.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@MappedSuperclass
public abstract class AuditModel<M extends BaseModel, PK> extends BaseModel<M, PK> {

    private static final long serialVersionUID = -3595786370597466692L;

    @Column(name = "AUM_CREATED_BY")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AUM_CREATION_DATE")
    private Date creationDate;

    @Column(name = "AUM_UPDATED_BY")
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AUM_UPDATE_DATE")
    private Date updateDate;

    @Column(name = "AUM_DELETED_BY")
    private String deletedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AUM_DELETED_DATE")
    private Date deleteDate;

    @Column(name = "AUM_IS_DELETED")
    private boolean deleted = false;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "AuditModel{" +
                "createdBy='" + createdBy + '\'' +
                ", creationDate=" + creationDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updateDate=" + updateDate +
                ", deletedBy='" + deletedBy + '\'' +
                ", deleteDate=" + deleteDate +
                ", deleted=" + deleted +
                ", Parent=" + super.toString() +
                '}';
    }
}
