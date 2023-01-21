package sample.model;

public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionId;

    public Customer(int customerId,String customerName,String address,String postalCode,
                    String phoneNumber,int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getAddress() {
        return address;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public int getDivisionId() {
        return divisionId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
