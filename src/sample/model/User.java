package sample.model;

public class User {
    private String userName;
    private int userId;
    private String password;

    public User(int userId, String userName, String password) {
        this.userName = userName;
        this.userId = userId;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }
    public int getUserId() {
        return userId;
    }
    public String getPassword() {
        return password;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
