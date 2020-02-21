package Model;

import java.util.UUID;

/**
 *
 */
public class Person {
    private String PersonID;
    private String Username;
    private String FirstName;
    private String LastName;
    private String Gender;
    private String FatherID;
    private String MotherID;
    private String SpouseID;

    /**
     *
     * @param userName
     * @param firstName
     * @param lastName
     * @param gender
     */
    public Person(String userName, String firstName, String lastName, String gender){
        PersonID = UUID.randomUUID().toString();
        this.Username = userName;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Gender = gender;
    }

    /**
     *
     * @param personId
     * @param userName
     * @param firstName
     * @param lastName
     * @param gender
     */
    public Person(String personId, String userName, String firstName, String lastName, String gender) {
        this.PersonID = personId;
        this.Username = userName;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Gender = gender;
    }

    /**
     * @return
     */
        public String getPersonID() {
        return PersonID;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return Username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) { Username = username; }

    /**
     *
     * @return
     */
    public String getFirstName() {
        return FirstName;
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        LastName = lastName;
    }

    /**
     *
     * @return
     */
    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getFatherID() {
        return FatherID;
    }

    public void setFatherID(String fatherID) {
        FatherID = fatherID;
    }

    public String getMotherID() {
        return MotherID;
    }

    public void setMotherID(String motherID) {
        MotherID = motherID;
    }

    public String getSpouseID() {
        return SpouseID;
    }

    public void setSpouseID(String spouseID) {
        SpouseID = spouseID;
    }

}
