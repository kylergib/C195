package sample.model;
/**
 *
 * @author Kyle Gibson
 */
public class User {
    private String userName;
    private int userId;
    private String password;

    public User(int userId, String userName, String password) {
        this.userName = userName;
        this.userId = userId;
        this.password = password;
    }
    /**
     * @return user name
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @return user id
     */
    public int getUserId() {
        return userId;
    }
    /**
     * @return password of user
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param userName sets username of user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * @param userId sets user id of user
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**
     * @param password sets password of user
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
