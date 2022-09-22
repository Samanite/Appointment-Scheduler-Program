package sample.dao;

import sample.model.Appointment;
import sample.model.Data;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

/**This class contains SQL query information for Appointment objects.*/
public abstract class AppointmentQuery {

    /**This is the Insert Appointment Query method. This adds an appointment to the server, and converts the
     users local date/time to universal coordinated time "UTC"*/
    public static int insert(int appointmentId, String title, String description, String location, String type, String start, String end, int customer_id, int user_id, int contact_id) throws SQLException, ParseException {

        DateFormat localTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localTime.setTimeZone(TimeZone.getDefault());

        java.util.Date localStart = localTime.parse(start);
        java.util.Date localEnd = localTime.parse(end);

        DateFormat utcTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        utcTime.setTimeZone(TimeZone.getTimeZone("UTC"));

        String utcStart = utcTime.format(localStart);
        String utcEnd = utcTime.format(localEnd);

        String sql = "INSERT INTO APPOINTMENTS (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        preS.setInt(1,appointmentId);
        preS.setString(2, title);
        preS.setString(3, description);
        preS.setString(4, location);
        preS.setString(5, type);
        preS.setString(6, utcStart);
        preS.setString(7, utcEnd);
        preS.setInt(8, customer_id);
        preS.setInt(9, user_id);
        preS.setInt(10, contact_id);

        int rowsAffected = preS.executeUpdate();
        return rowsAffected;
    }

    /**This is the Update Appointment Query method. This modifies an existing appointment on the server. Local time and
      universal coordinated time are converted as necessary.*/
    public static int update(String title, String description, String location, String type, String start, String end, int customer_id, int user_id, int contact_id, int appointment_id) throws SQLException, ParseException {

        DateFormat localTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localTime.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));

        java.util.Date localStart = localTime.parse(start);
        java.util.Date localEnd = localTime.parse(end);

        DateFormat utcTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        utcTime.setTimeZone(TimeZone.getTimeZone("UTC"));

        String utcStart = utcTime.format(localStart);
        String utcEnd = utcTime.format(localEnd);

        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        preS.setString(1, title);
        preS.setString(2, description);
        preS.setString(3, location);
        preS.setString(4, type);
        preS.setString(5, utcStart);
        preS.setString(6, utcEnd);
        preS.setInt(7, customer_id);
        preS.setInt(8, user_id);
        preS.setInt(9, contact_id);
        preS.setInt(10, appointment_id);

        int rowsAffected = preS.executeUpdate();
        return rowsAffected;
    }

    /** This is the Delete Appointment Query method. This deletes an appointment with a
     matching appointment id*/
    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        preS.setInt(1,id);

        int rowsAffected = preS.executeUpdate();
        return rowsAffected;
    }

    /** This is the Select query method. This gets the appointments saved in the database and
      saves them locally according to local time zone.*/
    public static void select() throws SQLException, ParseException {
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement preS = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preS.executeQuery();

        Data.clearAppointmentList();

        while(resultSet.next()){

            Appointment appointmentObject = new Appointment();
            String start;
            String end;

            appointmentObject.setId(resultSet.getInt("Appointment_ID"));
            appointmentObject.setTitle(resultSet.getString("Title"));
            appointmentObject.setDescription(resultSet.getString("Description"));
            appointmentObject.setLocation(resultSet.getString("Location"));
            appointmentObject.setType(resultSet.getString("Type"));
            start = resultSet.getString("Start");
            end = resultSet.getString("End");
            appointmentObject.setCustomerId(resultSet.getInt("Customer_ID"));
            appointmentObject.setUserId(resultSet.getInt("User_ID"));
            appointmentObject.setContactId(resultSet.getInt("Contact_ID"));


            DateFormat localTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            localTime.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));

            DateFormat utcTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            utcTime.setTimeZone(TimeZone.getTimeZone("UTC"));

            java.util.Date utcStart = utcTime.parse(start);
            java.util.Date utcEnd = utcTime.parse(end);

            String stringStart = localTime.format(utcStart);
            String stringEnd = localTime.format(utcEnd);

            java.util.Date localS = localTime.parse(stringStart);
            java.util.Date localE = localTime.parse(stringEnd);

            appointmentObject.setStartDateTime(localS);
            appointmentObject.setEndDateTime(localE);

            Data.addAppointmentArrayObject(appointmentObject);

        }
    }

}
