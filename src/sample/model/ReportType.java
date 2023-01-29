package sample.model;
/**
 *
 * @author Kyle Gibson
 */
public class ReportType {
    private String type;
    private int month;
    private int year;
    private int total;

    public ReportType(String type, int month, int year, int total) {
        this.type = type;
        this.month = month;
        this.year = year;
        this.total = total;
    }
    /**
     * @return type of appointment
     */
    public String getType() {
        return this.type;
    }
    /**
     * @return month of appointment
     */
    public int getMonth() {
        return this.month;
    }
    /**
     * @return year of appointment
     */
    public int getYear() {
        return this.year;
    }
    /**
     * @return total number of appointments
     */
    public int getTotal() {
        return this.total;
    }
    /**
     * @param total sets the total number of appointments
     */
    public void setTotal(int total) {
        this.total = total;
    }

}
