package sample.dao;

import javafx.collections.ObservableList;
import sample.model.Customer;
import sample.model.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class contains SQL query information for customer objects. */
public abstract class CustomerQuery {

    /**This is the insert customer method. This inserts a new customer into the database.*/
    public static int insert(int id, String name, String address, String postalCode, String phone, int DivId) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        preS.setInt(1,id);
        preS.setString(2, name);
        preS.setString(3, address);
        preS.setString(4, postalCode);
        preS.setString(5, phone);
        preS.setInt(6, DivId);
        int rowsAffected = preS.executeUpdate();
        return rowsAffected;
    }

    /**This is the update customer method. This updates a customer in the database.*/
    public static int update(int id, String name, String address, String postalCode, String phone, int DivId) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        preS.setString(1, name);
        preS.setString(2, address);
        preS.setString(3, postalCode);
        preS.setString(4, phone);
        preS.setInt(5, DivId);
        preS.setInt(6,id);
        int rowsAffected = preS.executeUpdate();
        return rowsAffected;
    }

    /**This is the delete customer method. This removes a customer from the database after
      first removing their associated appointments.*/
    public static int delete(int id) throws SQLException {
        String sql1 = "DELETE FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement preS1 = JDBC.connection.prepareStatement(sql1);
        preS1.setInt(1,id);

        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        preS.setInt(1,id);

        int rowsAffected1 = preS1.executeUpdate();
        int rowsAffected = preS.executeUpdate();
        int allRowsAffected = rowsAffected + rowsAffected1;

        return allRowsAffected;
    }

    /**This is the select customer method. This gets all customer information from the database.*/
    public static void select() throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preS.executeQuery();

        Data.clearCustomerList();

        while(resultSet.next()){

            Customer customer = new Customer();

            customer.setId(resultSet.getInt("Customer_ID"));
            customer.setName(resultSet.getString("Customer_Name"));
            customer.setAddress(resultSet.getString("Address"));
            customer.setPostalCode(resultSet.getString("Postal_Code"));
            customer.setPhoneNumber(resultSet.getString("Phone"));
            customer.setDivisionId(resultSet.getInt("Division_ID"));

            Data.addCustomerArrayObject(customer);

        }
    }

    /**This is the get customer id method. This returns two lists, one with customer id,
      another with customer name and id for display purposes.*/
    public static void getCusIdName(ObservableList observableList, ObservableList observableList2) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preS.executeQuery();
        
        while(resultSet.next()){
            int cusid = resultSet.getInt("Customer_ID");
            String id = String.valueOf(resultSet.getInt("Customer_ID"));
            String name = resultSet.getString("Customer_Name");
            String concactIdName = id + " - " + name;
            observableList.add(concactIdName);
            observableList2.add(cusid);
        }
    }
}
