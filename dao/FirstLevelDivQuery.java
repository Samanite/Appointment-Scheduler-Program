package sample.dao;

import sample.model.Data;
import sample.model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class contains the SQL query information for first level divisions. */
public class FirstLevelDivQuery {

    /**This is the select query method. This gets the first level division data from the server.*/
    public static void select() throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preS.executeQuery();

        Data.clearFirstLevList();

        while (resultSet.next()) {

            FirstLevelDivision divisionObject = new FirstLevelDivision();

            divisionObject.setId(resultSet.getInt("Division_ID"));
            divisionObject.setDivision(resultSet.getString("Division"));
            divisionObject.setCountryId(resultSet.getInt("Country_ID"));

            Data.addFirstLevObject(divisionObject);
        }
    }
}
