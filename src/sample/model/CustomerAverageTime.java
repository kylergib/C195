package sample.model;
/**
 *
 * @author Kyle Gibson
 */
public class CustomerAverageTime {
    private int customerId;
    private long averageTime;
    private int numberOfAppointments;

    public CustomerAverageTime(int customerId,long averageTime, int numberOfAppointments) {
        this.averageTime = averageTime;
        this.customerId = customerId;
        this.numberOfAppointments = numberOfAppointments;
    }
    /**
     * @return customer id
     */
    public int getCustomerId() {
        return customerId;
    }
    /**
     * @return average time customer has an appointment for
     */
    public long getAverageTime() {
        return averageTime;
    }
    /**
     * @return total number of appointment a customer has
     */
    public int getNumberOfAppointments() {
        return numberOfAppointments;
    }
}
