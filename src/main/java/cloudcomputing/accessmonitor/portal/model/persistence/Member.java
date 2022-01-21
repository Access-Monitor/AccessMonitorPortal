package cloudcomputing.accessmonitor.portal.model.persistence;

public class Member {

    private String id;
    private String personId;
    private String role;
    private String phoneNumber;

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

    public String getRoles() {
        return role;
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

    public Member(String email, String personId, String role, String phoneNumber) {
        this.id = email;
        this.personId = personId;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }







}
