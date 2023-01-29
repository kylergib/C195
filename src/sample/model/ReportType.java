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
    public String getType() {
        return this.type;
    }
    public int getMonth() {
        return this.month;
    }
    public int getYear() {
        return this.year;
    }
    public int getTotal() {
        return this.total;
    }
    public void setTotal(int total) {
        this.total = total;
    }

}
