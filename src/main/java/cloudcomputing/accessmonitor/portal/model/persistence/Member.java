package cloudcomputing.accessmonitor.portal.model.persistence;

import cloudcomputing.accessmonitor.portal.service.login.AuthorizedAccessesService;
import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Objects;

import static cloudcomputing.accessmonitor.portal.constants.BlobConstants.BLOB_MEMBERS_CONTAINER_NAME;

@Container(containerName = "AuthorizedMembers")
public class Member {

    @Id
    @PartitionKey
    private String id;
    private String personId;
    private String role;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String fileName;

    @Transient
    private String base64Image;

    public String getRole() { return role; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

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

    public void setRole(String roles) {
        this.role = role;
    }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBase64Image() {
        return new AuthorizedAccessesService().getImageFromBlobContent(BLOB_MEMBERS_CONTAINER_NAME,"./images/" , this.fileName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Member(String id, String personId, String role, String phoneNumber, String firstName, String lastName, String fileName) {
        this.id = id;
        this.personId = personId;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fileName = fileName;
    }

}
