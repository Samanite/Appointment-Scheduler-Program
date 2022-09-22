package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import sample.dao.*;
import sample.model.Appointment;
import sample.model.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

/**This class is the controller for the modify and delete Appointments page. */
public class ModifyDeleteAppointmentController implements Initializable {

    Stage stage;
    Parent scene;
    private int cusIndex, userIndex, contactIndex, meetingIndex, startHourIndex, endHourIndex, startMinuteIndex, endMinuteIndex;
    private String startHour, startMinute, endHour, endMinute;
    private boolean pm1, pm2;
    private Appointment selectedAppointment;
    private ObservableList<String> customerIdName = FXCollections.observableArrayList();
    private ObservableList<String> userIdName = FXCollections.observableArrayList();
    private ObservableList<String> contactsIdName = FXCollections.observableArrayList();
    private ObservableList<String> meetingTypes = FXCollections.observableArrayList(Arrays.asList("Consultation", "Follow up", "Discrepancy", "Change request", "Other"));
    private ObservableList<Integer> customerIdList = FXCollections.observableArrayList();
    private ObservableList<Integer> userIdList = FXCollections.observableArrayList();
    private ObservableList<Integer> contactsIdList = FXCollections.observableArrayList();

    /**This is the fill selected appointment method. This gets the information about a selected
      appointment from the appointment controller.*/
    public void fillSelectedAppointment(Appointment appointment) {
        selectedAppointment = appointment;
        int aptId = appointment.getId();



        modDelApptIDField.setText(String.valueOf(selectedAppointment.getId()));
        modDelApptDescptFieldArea.setText(selectedAppointment.getDescription());

        for(int i = 0; i < Data.getCustomerArrayList().size(); i++){
            if(selectedAppointment.getCustomerId() == Data.getCustomer(i).getId()){
                cusIndex = i;
            }
        }
        modDelApptCusIDSelect.getSelectionModel().select(cusIndex);

        for(int i = 0; i < Data.getUsersArrayList().size(); i++){
            if(Data.getUser(i).getUserId() == selectedAppointment.getUserId()){
                userIndex = i;
            }
        }
        modDelApptUserIDSelect.getSelectionModel().select(userIndex);

        for(int i = 0; i < Data.getContactsArrayList().size(); i++){
            if(Data.getContact(i).getId() == selectedAppointment.getContactId()){
                contactIndex = i;
            }
        }
        modDelApptContactSelect.getSelectionModel().select(contactIndex);

        modDelApptLocationField.setText(selectedAppointment.getLocation());
        modDelApptMeetingTitleField.setText(selectedAppointment.getTitle());
        modDelApptStartDate.setValue(DateTimeManagement.dateToLocalDate(selectedAppointment.getStartDateTime()));

        for(int i = 0; i < meetingTypes.size(); i++){
            if(meetingTypes.get(i).equals(selectedAppointment.getType())){
                meetingIndex = i;
            }
        }
        modDelApptMeetingTypeSelect.getSelectionModel().select(meetingIndex);

        startHour = DateTimeManagement.dateToHour(selectedAppointment.getStartDateTime());
        startMinute = DateTimeManagement.dateToMinute(selectedAppointment.getStartDateTime());
        endHour = DateTimeManagement.dateToHour(selectedAppointment.getEndDateTime());
        endMinute = DateTimeManagement.dateToMinute(selectedAppointment.getEndDateTime());
        pm1 = DateTimeManagement.amFalsepmTrue(selectedAppointment.getStartDateTime());
        pm2 = DateTimeManagement.amFalsepmTrue(selectedAppointment.getEndDateTime());

        for(int i = 0; i <DateTimeManagement.getHours().size(); i ++) {
            if(Integer.parseInt(startHour) == Integer.parseInt(DateTimeManagement.getHourObject(i))){
                startHourIndex = i;
            }
        }
        for(int i = 0; i <DateTimeManagement.getHours().size(); i ++) {
            if(Integer.parseInt(endHour) == Integer.parseInt(DateTimeManagement.getHourObject(i))){
                endHourIndex = i;

            }
        }

        modDelApptStartTimeSelectH.getSelectionModel().select(startHourIndex);
        modDelApptEndTimeSelectH.getSelectionModel().select(endHourIndex);

        for(int i = 0; i < DateTimeManagement.getMinutesAsIntegers().size(); i++){
            if(Integer.parseInt(startMinute) == DateTimeManagement.getMinuteAsIntObject(i)){
                startMinuteIndex = i;
            }
        }
        for(int i = 0; i < DateTimeManagement.getMinutesAsIntegers().size(); i++){
            if(Integer.parseInt(endMinute) == DateTimeManagement.getMinuteAsIntObject(i)){
                endMinuteIndex = i;
            }
        }
        startTimeSelectM.getSelectionModel().select(startMinuteIndex);
        endTimeSelectM.getSelectionModel().select(endMinuteIndex);

        if(pm1){ pmStartRB.setSelected(true);}
        else { amStartRB.setSelected(true);}

        if(pm2){ pmEndRb.setSelected(true);}
        else { amEndRB.setSelected(true);}

    }

    @FXML
    private RadioButton amEndRB;

    @FXML
    private RadioButton amStartRB;

    @FXML
    private RadioButton pmEndRb;

    @FXML
    private RadioButton pmStartRB;

    @FXML
    private ComboBox<String> endTimeSelectM;

    @FXML
    private ComboBox<String> startTimeSelectM;

    @FXML
    private ComboBox<String> modDelApptContactSelect;

    @FXML
    private ComboBox<String> modDelApptCusIDSelect;

    @FXML
    private TextArea modDelApptDescptFieldArea;

    @FXML
    private ComboBox<String> modDelApptEndTimeSelectH;

    @FXML
    private TextField modDelApptIDField;

    @FXML
    private TextField modDelApptLocationField;

    @FXML
    private TextField modDelApptMeetingTitleField;

    @FXML
    private ComboBox<String> modDelApptMeetingTypeSelect;

    @FXML
    private DatePicker modDelApptStartDate;

    @FXML
    private ComboBox<String> modDelApptStartTimeSelectH;

    @FXML
    private ComboBox<String> modDelApptUserIDSelect;

    /**This is the back-to-appointments method. Takes the user back to the appointment page with no
      changes. */
    @FXML
    void backToAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointmentsPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the delete-appointment method. Deletes an appointment.*/
    @FXML
    void deleteAppointment(ActionEvent event) throws IOException {

        int rowAffected = 0;
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment");
            alert.setContentText("Are you sure you want to delete this appointment? " + " ID: " + selectedAppointment.getId() + " | Type: " + selectedAppointment.getType());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                rowAffected = AppointmentQuery.delete(selectedAppointment.getId());
            }
            if (rowAffected > 0) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Appointment Deleted");
                alert1.setContentText("Appointment ID: " + selectedAppointment.getId() + " | Type: " + selectedAppointment.getType() + "\n" + " has been removed from the records");
                alert1.showAndWait();

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/appointmentsPage.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing Deleted");
            alert.setContentText("The appointment was not deleted");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**This is the save-appointment method. Saves any changes to an appointment.*/
    @FXML
    void saveAppointment(ActionEvent event) throws IOException, SQLException, ParseException {

        String date = "";
        String startTime = "";
        String endTime = "";
        int cusIndex = 0;
        int userIndex = 0;
        int contactIndex = 0;

        if(modDelApptStartDate.getValue() !=null) {
            Date dateDate = Date.valueOf(modDelApptStartDate.getValue());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.format(dateDate);
        }

        for(int i = 0; i < customerIdName.size(); i++){
            if(customerIdName.get(i).equals(modDelApptCusIDSelect.getValue())){
                cusIndex = i;
            }
        }
        for(int i = 0; i < userIdName.size(); i++){
            if(userIdName.get(i).equals(modDelApptUserIDSelect.getValue())){
                userIndex = i;
            }
        }
        for(int i = 0; i < contactsIdName.size(); i++){
            if(contactsIdName.get(i).equals(modDelApptContactSelect.getValue())){
                contactIndex = i;
            }
        }

        if(amStartRB.isSelected()) {
            if(Integer.parseInt(modDelApptStartTimeSelectH.getValue()) == 12){
                String startDay = "00";
                startTime = " " + startDay + startTimeSelectM.getValue() + ":00";
            }
            else {
                startTime = " " + modDelApptStartTimeSelectH.getValue() + startTimeSelectM.getValue() + ":00";
            }
        }
        else if(pmStartRB.isSelected()){

            int hour = Integer.parseInt(modDelApptStartTimeSelectH.getValue()) + 12;
            if(hour == 24) {
                hour = 12;
            }
            startTime = " " + hour + startTimeSelectM.getValue() + ":00";
        }

        if(amEndRB.isSelected()){
            if(Integer.parseInt(modDelApptEndTimeSelectH.getValue()) == 12){
                String startDay = "00";
                endTime = " " + startDay + endTimeSelectM.getValue() + ":00";
            }
            else {
                endTime = " " + modDelApptEndTimeSelectH.getValue() + endTimeSelectM.getValue() + ":00";
            }
        }
        else if(pmEndRb.isSelected()){
            int hour = Integer.parseInt(modDelApptEndTimeSelectH.getValue()) + 12;
            if(hour == 24) {
                hour = 12;
            }
            endTime = " " + hour + endTimeSelectM.getValue() + ":00";
        }

        String wholeStartDate = date + startTime;
        String wholeEndDate = date + endTime;

        if(modDelApptStartDate.getValue() !=null && (!modDelApptMeetingTitleField.getText().isBlank())
         && (!modDelApptDescptFieldArea.getText().isBlank()) && (!modDelApptLocationField.getText().isBlank())) {

            SimpleDateFormat formatForCompare = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date firstTime = formatForCompare.parse(wholeStartDate);
            java.util.Date lastTime = formatForCompare.parse(wholeEndDate);

            if(firstTime.compareTo(lastTime) <0) {
                if(DateTimeManagement.isDuringBusiHours(startTime, endTime)) {

                    boolean itsCool = true;

                    for (int i = 0; i < Data.getAppointmentArrayList().size(); i++) {
                        if(Data.getAppointment(i).getId() != selectedAppointment.getId()) {
                            if (!(lastTime.compareTo(Data.getAppointment(i).getStartDateTime()) <= 0 ||
                                    firstTime.compareTo(Data.getAppointment(i).getEndDateTime()) >= 0)
                                    && (Data.getAppointment(i).getCustomerId() == customerIdList.get(cusIndex))) {
                                itsCool = false;
                            }
                        }
                    }

                    if (itsCool) {
                        String title = modDelApptMeetingTitleField.getText();
                        String description = modDelApptDescptFieldArea.getText();
                        String location = modDelApptLocationField.getText();
                        String type = modDelApptMeetingTypeSelect.getValue();
                        int customerId = customerIdList.get(cusIndex);
                        int userId = userIdList.get(userIndex);
                        int contactId = contactsIdList.get(contactIndex);
                        AppointmentQuery.update(title, description, location, type, wholeStartDate, wholeEndDate, customerId, userId, contactId, selectedAppointment.getId());


                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/appointmentsPage.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("DATE/TIME ERROR");
                        alert.setContentText("Meeting time conflicts with previous customer appointment");
                        alert.showAndWait();
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("DATE/TIME ERROR");
                    alert.setContentText("Meeting time is not during business hours");
                    alert.showAndWait();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("DATE/TIME ERROR");
                alert.setContentText("Meeting end time conflicts with start time");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Information");
            alert.setContentText("Please fill out all the required areas");
            alert.showAndWait();
        }
    }

    /**This is the max-character method for location. */
    public void maxCharLocationAction(KeyEvent keyEvent) {
        int max = 50;
        if (modDelApptLocationField.getText().length() > max) {
            keyEvent.consume();
            String shortenedLocation = modDelApptLocationField.getText().substring(0, max);
            modDelApptLocationField.setText(shortenedLocation);
            modDelApptLocationField.positionCaret(max);
        }
    }

    /**This is the max-character method for Title. */
    public void maxCharTitleAction(KeyEvent keyEvent) {
        int max = 50;
        if (modDelApptMeetingTitleField.getText().length() > max) {
            keyEvent.consume();
            String shortenedTitle = modDelApptMeetingTitleField.getText().substring(0, max);
            modDelApptMeetingTitleField.setText(shortenedTitle);
            modDelApptMeetingTitleField.positionCaret(max);
        }
    }

    /**This is the max-character method for Text Area. */
    public void maxCharTextAreaAction(KeyEvent keyEvent) {
        int max = 50;
        if (modDelApptDescptFieldArea.getText().length() > max) {
            keyEvent.consume();
            String shortenedDescription = modDelApptDescptFieldArea.getText().substring(0, max);
            modDelApptDescptFieldArea.setText(shortenedDescription);
            modDelApptDescptFieldArea.positionCaret(max);
        }
    }

    /**This is the initialize method. This sets local data lists and combo boxes.*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            CustomerQuery.getCusIdName(customerIdName, customerIdList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            UsersQuery.getUserIdName(userIdName, userIdList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ContactsQuery.getContactsIdName(contactsIdName, contactsIdList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        modDelApptContactSelect.setItems(contactsIdName);
        modDelApptCusIDSelect.setItems(customerIdName);
        modDelApptUserIDSelect.setItems(userIdName);
        modDelApptMeetingTypeSelect.setItems(meetingTypes);

        modDelApptStartTimeSelectH.setItems(DateTimeManagement.getHours());
        startTimeSelectM.setItems(DateTimeManagement.getMinutes());
        modDelApptEndTimeSelectH.setItems(DateTimeManagement.getHours());
        endTimeSelectM.setItems(DateTimeManagement.getMinutes());

    }
}
