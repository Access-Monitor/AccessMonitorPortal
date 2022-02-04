package cloudcomputing.accessmonitor.portal.model.persistence;

import cloudcomputing.accessmonitor.portal.service.login.AuthorizedAccessesService;
import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.commons.codec.binary.Base64;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

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
    private long detectionTimestamp;
    private boolean notified;

    @Transient
    private String base64Image;


    public UnauthorizedDetection(String id, String faceId, LocalDateTime detectionTime) {
        this.id = id;
        this.faceId = faceId;
        this.detectionTime = detectionTime;
        this.notified = false;

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

    public long getDetectionTimestamp() {
        return detectionTimestamp;
    }

    public void setDetectionTimestamp(long detectionTimestamp) {
        this.detectionTimestamp = detectionTimestamp;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public String getBase64Image() {
        return new AuthorizedAccessesService().getImageFromBlobContent("accessmonitorblob","" , this.id);
    }

}
