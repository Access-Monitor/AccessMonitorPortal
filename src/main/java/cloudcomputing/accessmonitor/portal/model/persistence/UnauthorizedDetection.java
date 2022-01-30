package cloudcomputing.accessmonitor.portal.model.persistence;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.commons.codec.binary.Base64;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Container(containerName = "UnauthorizedMembers")
public class UnauthorizedDetection {

    @Id
    private String id;
    @PartitionKey
    private String faceId;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime detectionTime;
    private byte[] blobContent;
    private long detectionTimestamp;
    private boolean notified;
    private String base64Image;


    public UnauthorizedDetection(String id, String faceId, LocalDateTime detectionTime, byte[] blobContent) {
        this.id = id;
        this.faceId = faceId;
        this.detectionTime = detectionTime;
        this.blobContent = blobContent;
        this.notified = false;
        this.base64Image = Base64.encodeBase64String(blobContent);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public LocalDateTime getDetectionTime() {
        return detectionTime;
    }

    public void setDetectionTime(LocalDateTime detectionTime) {
        this.detectionTime = detectionTime;
    }

    public byte[] getBlobContent() {
        return blobContent;
    }

    public void setBlobContent(byte[] blobContent) {
        this.blobContent = blobContent;
    }

    public long getDetectionTimestamp() {
        return detectionTimestamp;
    }

    public void setDetectionTimestamp(long detectionTimestamp) {
        this.detectionTimestamp = detectionTimestamp;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }
}
