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
    public int getId() {
        return this.id;
    }
    public String getTitle() {
        return this.title;
    }
    public String getDescription() {
        return this.description;
    }
    public String getLocation() {
        return this.location;
    }
    public String getType() {
        return this.type;
    }
    public Timestamp getStart() {
        return this.start;
    }
    public Timestamp getEnd() {
        return this.end;
    }
    public int getCustomerId() {
        return this.customerId;
    }
    public int getUserId() {
        return userId;
    }
    public int getContactId() {
        return this.contactId;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setStart(Timestamp start) {
        this.start = start;
    }
    public void setEnd(Timestamp end) {
        this.end = end;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

}
