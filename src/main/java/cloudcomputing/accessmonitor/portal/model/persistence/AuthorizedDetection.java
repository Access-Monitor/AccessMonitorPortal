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

@Container(containerName = "DetectionAudit")
public class AuthorizedDetection {

    @Id
    String id;
    @PartitionKey
    String personId;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime detectionTime;
    private long detectionTimestamp;
    private byte[] blobContent;
    private String base64Image;

    public AuthorizedDetection(String id, String personId, LocalDateTime detectionTime, long detectionTimestamp, byte[] blobContent) {
        this.id = id;
        this.personId = personId;
        this.detectionTime = detectionTime;
        this.detectionTimestamp = detectionTimestamp;
        this.blobContent = blobContent;
        this.base64Image = Base64.encodeBase64String(blobContent);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
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

    public byte[] getBlobContent() {
        return blobContent;
    }

    public void setBlobContent(byte[] blobContent) {
        this.blobContent = blobContent;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}
