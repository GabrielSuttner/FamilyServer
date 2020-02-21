package Model;

import java.util.UUID;

public class Event {
    private String EventID;
    private String AssociatedUsername;
    private String PersonID;
    private double Latitude;
    private double Longitude;
    private String Country;
    private String City;
    private String EventType;
    private int Year;

    /**
     *
     * @param associatedUsername
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public Event(String associatedUsername, String personID, double latitude, double longitude, String country, String city, String eventType, int year) {
        this.EventID = UUID.randomUUID().toString();
        this.AssociatedUsername = associatedUsername;
        this.PersonID = personID;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.Country = country;
        this.City = city;
        this.EventType = eventType;
        this.Year = year;
    }

    public String getEventID() {
        return EventID;
    }

    public String getAssociatedUsername() {
        return AssociatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        AssociatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String personID) {
        PersonID = personID;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(int latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(int longitude) {
        Longitude = longitude;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        EventType = eventType;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }
}
