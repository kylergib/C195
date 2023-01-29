package sample.Utilities;
/**
 *
 * @author Kyle Gibson
 */

/**
 * Lambda interface that  calculates time from 12-hour to 24-hour format.
 * This improves my code by easily being able to go from 12-hour format in the add appointment form to a 24-hour format for the database.
 */

public interface CalculateTime {
    String getTime(String time);
}
