package com.contact_view.util.mongo;

import org.apache.commons.lang3.builder.ToStringBuilder;
public abstract class AbstractMongoEntity {

    private Long creationTime;
    private String createdBy;
    private Long lastUpdated;
    private String lastUpdatedBy; // For tracking last updated by

    public AbstractMongoEntity() {
        super();
    }

    public AbstractMongoEntity(Long creationTime, String createdBy, Long lastUpdated, String lastUpdatedBy) {
        this.creationTime = creationTime;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public abstract Object getId();

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    public abstract void setEntityDefaultProperties(String callingUserId);


    public static class Constants {

        public static final String ID = "id";
        public static final String _ID = "_id";
        public static final String CREATION_TIME = "creationTime";
        public static final String LAST_UPDATED = "lastUpdated";
        public static final String LAST_UPDATED_BY = "lastUpdatedBy";
        public static final String CREATED_BY = "createdBy";
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
