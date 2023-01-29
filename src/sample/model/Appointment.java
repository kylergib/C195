package sample.model;

import java.sql.Timestamp;
/**
 *
 * @author Kyle Gibson
 */
public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private int customerId;
    private int userId;
    private int contactId;

    public Appointment(int id, String title, String description, String location,
                       String type, Timestamp start, Timestamp end, int customerId,
                       int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }
    /**
     * @return appointment id
     */
    public int getId() {
        return this.id;
    }
    /**
     * @return title from appointment
     */
    public String getTitle() {
        return this.title;
    }
    /**
     * @return description of appointment
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * @return lcoation of appointment
     */
    public String getLocation() {
        return this.location;
    }
    /**
     * @return type of appointment
     */
    public String getType() {
        return this.type;
    }
    /**
     * @return start time of appointment
     */
    public Timestamp getStart() {
        return this.start;
    }
    /**
     * @return end time of appointment
     */
    public Timestamp getEnd() {
        return this.end;
    }
    /**
     * @return customer id of appointment
     */
    public int getCustomerId() {
        return this.customerId;
    }
    /**
     * @return user id of appointment
     */
    public int getUserId() {
        return userId;
    }
    /**
     * @return contact id for appointment
     */
    public int getContactId() {
        return this.contactId;
    }
    /**
     * @param id sets the id of appointment
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @param title sets the title of appointment
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @param description sets the description of the appointment
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @param location sets the location of the appointment
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * @param type sets the type of appointment
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * @param start sets the start time of appointment
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }
    /**
     * @param end sets the end time of appointment
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }
    /**
     * @param customerId sets the customer id of appointment
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    /**
     * @param userId sets the user id of the appointment
     */
    public void setUserId(int userId){
        this.userId = userId;
    }
    /**
     * @param contactId sets the contact id of appointment
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

}
