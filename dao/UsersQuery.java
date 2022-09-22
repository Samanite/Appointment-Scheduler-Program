package sample.dao;

import javafx.collections.ObservableList;
import sample.model.Data;
import sample.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class contains SQL query information for user objects. */
public class UsersQuery {

    /**This is the select user query method. This gets all the user data from the server.*/
    public static void select() throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preS.executeQuery();

        Data.clearUserList();

        while(resultSet.next()){
            User user = new User();

            user.setUserId(resultSet.getInt("User_ID"));
            user.setUserName(resultSet.getString("User_Name"));
            user.setPassword(resultSet.getString("Password"));

            Data.addUserArrayObject(user);
        }
    }

    /**This is the get user id query method. This returns two lists, one with user id, the other
      with user id and name for viewing purposes.*/
    public static void getUserIdName(ObservableList observableList, ObservableList observableList2) throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preS.executeQuery();

        while(resultSet.next()){
            int userId = resultSet.getInt("User_ID");
            String id = String.valueOf(resultSet.getInt("User_ID"));
            String name = resultSet.getString("User_Name");
            String idAndName = id + " - " + name;

            observableList.add(idAndName);
            observableList2.add(userId);
        }
    }
}
