package cloudcomputing.accessmonitor.portal.model.persistence;

public class Member {

    private String id;
    private String personId;
    private String role;
    private String phoneNumber;
    private String firstName;
    private String lastName;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Member(String id, String personId, String role, String phoneNumber, String firstName, String lastName) {
        this.id = id;
        this.personId = personId;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
