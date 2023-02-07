package sample.model;

public class Contact {
    private int contactId;
    private String contactName;

    public Contact(int contactId,String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
    }
    /**
     * @return contact id
     */
    public int getContactId() {
        return contactId;
    }
    /**
     * @return name of contact
     */
    public String getContactName() {
        return contactName;
    }
    /**
     * @param contactId sets the contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    /**
     * @param contactName sets the name of contact
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}
