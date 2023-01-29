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

    public int getCustomerId() {
        return customerId;
    }
    public long getAverageTime() {
        return averageTime;
    }
    public int getNumberOfAppointments() {
        return numberOfAppointments;
    }
}
