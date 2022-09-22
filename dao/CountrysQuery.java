package sample.dao;

import sample.model.Country;
import sample.model.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class contains SQL query information for country objects. */
public class CountrysQuery {

    /**This is the select country method. This gets all countries from the database.*/
    public static void select() throws SQLException {
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preS.executeQuery();

        Data.clearCountryList();

        while (resultSet.next()) {

            Country countryObject = new Country();

            countryObject.setId(resultSet.getInt("Country_ID"));
            countryObject.setCountry(resultSet.getString("Country"));
            Data.addCountryArrayObject(countryObject);

        }
    }
}
