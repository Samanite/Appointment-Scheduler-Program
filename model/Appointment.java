package sample.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**This class defines an Appointment object. Appointments are created using Java Util Date,
  integers and strings. This class implements comparable for sorting purposes.*/
public class Appointment implements Comparable<Appointment>{

    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private Date startDateTime;
    private Date endDateTime;
    private int customerId;
    private int userId;
    private int contactId;

    /**This is the Appointment default constructor. */
    public Appointment(){
    }

    /**This is the Appointment constructor with all arguments. This sets all the fields of an Appointment object.*/
    public Appointment(int id, String title, String description, String location, String type, java.sql.Timestamp startDateTime, java.sql.Timestamp endDateTime, int customerId, int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }
    /**Gets id.
     @return id*/
    public int getId() {
        return id;
    }

    /**Set id.
     @param id*/
    public void setId(int id) {
        this.id = id;
    }

    /**Gets title.
     @return title */
    public String getTitle() {
        return title;
    }

    /**Sets title.
     @param title*/
    public void setTitle(String title) {
        this.title = title;
    }

    /**Gets description.
     @return description */
    public String getDescription() {
        return description;
    }

    /**Sets description.
     @param description*/
    public void setDescription(String description) {
        this.description = description;
    }

    /**Gets location.
     @return location*/
    public String getLocation() {
        return location;
    }

    /**Sets location.
     @param location */
    public void setLocation(String location) {
        this.location = location;
    }

    /**Gets type.
     @return type*/
    public String getType() {
        return type;
    }

    /**Sets type
     @param type */
    public void setType(String type) {
        this.type = type;
    }

    /**Gets start date/time
     @return startDateTime*/
    public Date getStartDateTime() {
        return startDateTime;
    }

    /**Sets start date/time
     @param startDateTime */
    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**Gets end date/time
     @return endDateTime*/
    public Date getEndDateTime() {
        return endDateTime;
    }

    /**Sets end date/time
     @param endDateTime*/
    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**Gets customer id
     @return customer id*/
    public int getCustomerId() {
        return customerId;
    }

    /**Sets customer id
     @param customerId */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**Gets user id
     @return user id*/
    public int getUserId() {
        return userId;
    }

    /**Sets user id
     @param userId*/
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**Gets contact id
     @return contact id*/
    public int getContactId() {
        return contactId;
    }

    /**Sets contact id
     @param contactId */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**This is the Appointment Details method. This method returns an Appointment in an organized String for display purposes.*/
    public static String stringAppointmentDetails(Appointment appointment){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM hh:mm a");
        String details =("ID: " + appointment.getId() + " | " + simpleDateFormat.format(appointment.getStartDateTime())
                + " - " + simpleDateFormat.format(appointment.getEndDateTime()) + " |  Title: " + appointment.getTitle()
                + " | " + appointment.getType() + " | Location: " + appointment.getLocation()
                + " | user ID: " + appointment.getUserId() + " | contact ID: "
                + appointment.getContactId() + "\n" + "     Description: "
                + appointment.getDescription() + "\n");
        return details;
    }

    /**This is the Appointment Notice Details method. This returns a simplified Appointment details String for display purposes.*/
    public static String appointmentNoticeStringDetails (Appointment appointment){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM hh:mm a");
        String details =("ID: " + appointment.getId() + " | " + simpleDateFormat.format(appointment.getStartDateTime())
                + " - " + simpleDateFormat.format(appointment.getEndDateTime()) + "\n");
        return details;
    }

    /**This is the Compare-To method. This allows Appointments to be sorted by their Date value.*/
    @Override
    public int compareTo(Appointment appoint) {
        int lastDate = this.startDateTime.compareTo(appoint.startDateTime);
        return lastDate;
    }
}
