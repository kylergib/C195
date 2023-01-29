package sample.model;
/**
 *
 * @author Kyle Gibson
 */
public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;

    public Customer(int customerId,String customerName,String address,String postalCode,
                    String phoneNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }
    /**
     * @return customer id
     */
    public int getCustomerId() {
        return customerId;
    }
    /**
     * @return name of customer
     */
    public String getCustomerName() {
        return customerName;
    }
    /**
     * @return address of customer
     */
    public String getAddress() {
        return address;
    }
    /**
     * @return postal code of customer
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * @return phone number of customer
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * @param customerId sets the customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    /**
     * @param customerName sets the name of customer
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**
     * @param address sets the address of the customer
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * @param postalCode sets the postal code of the customer
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * @param phoneNumber sets the phone number of the customer
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
