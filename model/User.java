package sample.model;

/**This class defines a user object. A user has an id, username, and password.*/
public class User {

    int userId;
    String userName;
    String password;

    /**This is the default user constructor. */
    public User(){}

    /**This is the user constructor with all parameters. This sets all the values.*/
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**Gets user id.
     @return userId*/
    public int getUserId() {
        return userId;
    }

    /**Sets user id.
     @param userId */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**Gets username.
     @return userName*/
    public String getUserName() {
        return userName;
    }

    /**Sets username.
     @param userName */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**Gets password.
     @return password*/
    public String getPassword() {
        return password;
    }

    /**Sets password.
     @param password*/
    public void setPassword(String password) {
        this.password = password;
    }
}
