package cloudcomputing.accessmonitor.portal.model.persistence;

import cloudcomputing.accessmonitor.portal.service.login.AuthorizedAccessesService;
import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

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
    private String name;
    private String filename;

    @Transient
    private String base64Image;

    public AuthorizedDetection(String id, String personId, LocalDateTime detectionTime, long detectionTimestamp, String name ,String filename) {
        this.id = id;
        this.personId = personId;
        this.detectionTime = detectionTime;
        this.detectionTimestamp = detectionTimestamp;
        this.name = name;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getBase64Image() {
        return new AuthorizedAccessesService().getImageFromBlobContent( "accessmonitorblob" ,"" , this.filename);
    }



}
