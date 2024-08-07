package experimental.dynamic_steps.entity;

import java.time.LocalDateTime;

public class CommonRawDataFormat {
    private Integer recordId;
    private String sourceSystem;
    private LocalDateTime dateReceived;
    private String appdbStatusCode;
    private String filenetStatusCode;
    private Integer appdbRetryCount;
    private Integer filenetRetryCount;
    private Integer batchStatus;
    private Integer recordCount;
    private Integer successCount;
    private Integer failureCount;
    private Integer duplicateCount;
    private String filenetGuid;

    public CommonRawDataFormat() {
    }

    public CommonRawDataFormat(Integer recordId, String sourceSystem, LocalDateTime dateReceived,
            String appdbStatusCode,
            String filenetStatusCode,
            Integer appdbRetryCount, Integer filenetRetryCount) {
        this.recordId = recordId;
        this.sourceSystem = sourceSystem;
        this.dateReceived = dateReceived;
        this.appdbStatusCode = appdbStatusCode;
        this.filenetStatusCode = filenetStatusCode;
        this.appdbRetryCount = appdbRetryCount;
        this.filenetRetryCount = filenetRetryCount;
    }

    public CommonRawDataFormat(Integer recordId, String sourceSystem, LocalDateTime dateReceived, Integer batchStatus,
            Integer recordCount,
            Integer successCount, Integer failureCount, Integer duplicateCount, String filenetGuid) {
        this.recordId = recordId;
        this.sourceSystem = sourceSystem;
        this.dateReceived = dateReceived;
        this.batchStatus = batchStatus;
        this.recordCount = recordCount;
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.duplicateCount = duplicateCount;
        this.filenetGuid = filenetGuid;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public LocalDateTime getDateReceived() {
        return dateReceived;
    }

    public String getAppdbStatusCode() {
        return appdbStatusCode;
    }

    public String getFilenetStatusCode() {
        return filenetStatusCode;
    }

    public Integer getAppdbRetryCount() {
        return appdbRetryCount;
    }

    public Integer getFilenetRetryCount() {
        return filenetRetryCount;
    }

    public Integer getBatchStatus() {
        return batchStatus;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    public Integer getDuplicateCount() {
        return duplicateCount;
    }

    public String getFilenetGuid() {
        return filenetGuid;
    }
}
