package sample.dao;

import javafx.collections.ObservableList;
import sample.model.Contacts;
import sample.model.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class contains SQL query information for Contact objects. */
public class ContactsQuery {

    /**This is the Select contact query method. This gets a list of contacts from the SQL server.*/
    public static void select() throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preS.executeQuery();

        Data.clearContactsList();

        while(resultSet.next()){
            Contacts contact = new Contacts();

            contact.setId(resultSet.getInt("Contact_ID"));
            contact.setName(resultSet.getString("Contact_Name"));
            contact.setEmail(resultSet.getString("Email"));

            Data.addContactArrayObject(contact);
        }
    }

    /**This is the contact ID query method. This generates two lists, one with only id, another with
      id and name for viewing purposes.*/
    public static void getContactsIdName(ObservableList observableList, ObservableList observableList2) throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preS.executeQuery();

        while(resultSet.next()){
            int contactId = resultSet.getInt("Contact_ID");
            String id = Integer.toString(resultSet.getInt("Contact_ID"));
            String name = resultSet.getString("Contact_Name");
            String idAndName = id + " - " + name;
            observableList.add(idAndName);
            observableList2.add(contactId);
        }
    }
}
