package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import sample.dao.*;
import sample.model.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

/**This class is the controller used for adding appointments. This is a combination of combo boxes
  and text areas.*/
public class AddAppointmentController implements Initializable {
    Stage stage;
    Parent scene;
    private int appId = 0;
    private ObservableList<String> customerIdName = FXCollections.observableArrayList();
    private ObservableList<String> userIdName = FXCollections.observableArrayList();
    private ObservableList<String> contactsIdName = FXCollections.observableArrayList();
    private ObservableList<String> meetingTypes = FXCollections.observableArrayList(Arrays.asList("Consultation", "Follow up", "Discrepancy", "Change request", "Other"));
    private ObservableList<Integer> customerIdList = FXCollections.observableArrayList();
    private ObservableList<Integer> userIdList = FXCollections.observableArrayList();
    private ObservableList<Integer> contactsIdList = FXCollections.observableArrayList();


    @FXML
    private TextArea appDescriptTextArea;

    @FXML
    private ComboBox<String> endTimeBoxH;

    @FXML
    private ComboBox<String> endTimeBoxM;

    @FXML
    private RadioButton endTimeRadioAM;

    @FXML
    private RadioButton endTimeRadioPM;

    @FXML
    private TextField appIDTextField;

    @FXML
    private DatePicker appStartDateField;

    @FXML
    private ComboBox<String> startTimeBoxH;

    @FXML
    private ComboBox<String> startTimeBoxM;

    @FXML
    private RadioButton startTimeRadioAM;

    @FXML
    private RadioButton startTimeRadioPM;

    @FXML
    private ComboBox<String> contactSelection;

    @FXML
    private ComboBox<String> cusIDSelect;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField meetingTitleTextField;

    @FXML
    private ComboBox<String> meetingTypeSelection;

    @FXML
    private ComboBox<String> userIDSelect;

    /**This is the back to appointment method. This takes the user back to the appointment
      page with no changes made.*/
    @FXML
    void backToAppt(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointmentsPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the save new appointment method. This method implements error checking that all fields
      are filled out, as well as checking that appointment times are issue free.*/
    @FXML
    void saveNewAppt(ActionEvent event) throws IOException, ParseException, SQLException {
        String date = "";
        String startTime = "";
        String endTime = "";
        int cusIndex = 0;
        int userIndex = 0;
        int contactIndex = 0;
        boolean startAmPm = false;
        boolean endAmPm = false;
        Date dateDate = null;

        if(appStartDateField.getValue() !=null) {
            dateDate = Date.valueOf(appStartDateField.getValue());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.format(dateDate);
        }

        for(int i = 0; i < customerIdName.size(); i++){
            if(customerIdName.get(i).equals(cusIDSelect.getValue())){
                cusIndex = i;
            }
        }
        for(int i = 0; i < userIdName.size(); i++){
            if(userIdName.get(i).equals(userIDSelect.getValue())){
                userIndex = i;
            }
        }
        for(int i = 0; i < contactsIdName.size(); i++){
            if(contactsIdName.get(i).equals(contactSelection.getValue())){
                contactIndex = i;
            }
        }

        if(startTimeRadioAM.isSelected()) {
            startAmPm = true;
            if(Integer.parseInt(startTimeBoxH.getValue()) == 12){
                String startDay = "00";
                startTime = " " + startDay + startTimeBoxM.getValue() + ":00";
            }
            else {
                startTime = " " + startTimeBoxH.getValue() + startTimeBoxM.getValue() + ":00";
            }
        }
        else if(startTimeRadioPM.isSelected()){
            startAmPm = true;
            int hour = Integer.parseInt(startTimeBoxH.getValue()) + 12;
            if(hour == 24) {
                hour = 12;
            }
                startTime = " " + hour + startTimeBoxM.getValue() + ":00";
        }

        if(endTimeRadioAM.isSelected()){
            endAmPm = true;
            if(Integer.parseInt(endTimeBoxH.getValue()) == 12){
                String startDay = "00";
                endTime = " " + startDay + endTimeBoxM.getValue() + ":00";
            }
            else {
                endTime = " " + endTimeBoxH.getValue() + endTimeBoxM.getValue() + ":00";
            }
        }
        else if(endTimeRadioPM.isSelected()){
            endAmPm = true;
            int hour = Integer.parseInt(endTimeBoxH.getValue()) + 12;
            if(hour == 24) {
                hour = 12;
            }
                endTime = " " + hour + endTimeBoxM.getValue() + ":00";
        }
        String wholeStartDate = date + startTime;
        String wholeEndDate = date + endTime;

        if((!meetingTitleTextField.getText().isBlank()) && (!appDescriptTextArea.getText().isBlank()) && (!locationTextField.getText().isBlank())
         && meetingTypeSelection.getValue() !=null && cusIDSelect.getValue() !=null && userIDSelect.getValue() !=null
         && contactSelection.getValue() !=null && startTimeBoxH.getValue() != null && startTimeBoxM.getValue() !=null
         && endTimeBoxH.getValue() !=null && endTimeBoxM.getValue() !=null && startAmPm && endAmPm && appStartDateField.getValue() !=null){

            SimpleDateFormat formatForCompare = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date firstTime = formatForCompare.parse(wholeStartDate);
            java.util.Date lastTime = formatForCompare.parse(wholeEndDate);

            if(firstTime.compareTo(lastTime) <0) {
                if(DateTimeManagement.isDuringBusiHours(startTime, endTime)) {

                    boolean itsCool = true;

                    for (int i = 0; i < Data.getAppointmentArrayList().size(); i++) {
                        if (!(lastTime.compareTo(Data.getAppointment(i).getStartDateTime()) <= 0 ||
                                firstTime.compareTo(Data.getAppointment(i).getEndDateTime()) >= 0)
                                && (Data.getAppointment(i).getCustomerId() == customerIdList.get(cusIndex))) {
                            itsCool = false;
                        }
                    }

                    if (itsCool) {
                        String title = meetingTitleTextField.getText();
                        String description = appDescriptTextArea.getText();
                        String location = locationTextField.getText();
                        String type = meetingTypeSelection.getValue();
                        int customerId = customerIdList.get(cusIndex);
                        int userId = userIdList.get(userIndex);
                        int contactId = contactsIdList.get(contactIndex);
                        int appointmentId = appId;
                        AppointmentQuery.insert(appointmentId, title, description, location, type, wholeStartDate, wholeEndDate, customerId, userId, contactId);

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

    /**This is the initialize method. This sets the combo boxes and fills local data lists.*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            CustomerQuery.select();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        contactSelection.setItems(contactsIdName);
        cusIDSelect.setItems(customerIdName);
        userIDSelect.setItems(userIdName);
        meetingTypeSelection.setItems(meetingTypes);

        if(Data.getAppointmentArrayList().isEmpty()){
            appId = 1;
        }
        for(int i = 0; i < Data.getAppointmentArrayList().size(); i++){

            if(appId <= Data.getAppointment(i).getId()){
                appId = Data.getAppointment(i).getId() + 1;
            }
        }
        appIDTextField.setText(String.valueOf(appId));
        startTimeBoxH.setItems(DateTimeManagement.getHours());
        startTimeBoxM.setItems(DateTimeManagement.getMinutes());
        endTimeBoxH.setItems(DateTimeManagement.getHours());
        endTimeBoxM.setItems(DateTimeManagement.getMinutes());

    }

    /**This is the max characters for location method. */
    public void maxCharLocationAction(javafx.scene.input.KeyEvent keyEvent) {
        int max = 50;
        if(locationTextField.getText().length() > max) {
            keyEvent.consume();
            String shortenedLocation = locationTextField.getText().substring(0, max);
            locationTextField.setText(shortenedLocation);
            locationTextField.positionCaret(max);
        }
    }

    /**This is the max characters for title method. */
    public void maxCharTitleAction(javafx.scene.input.KeyEvent keyEvent) {
        int max = 50;
        if(meetingTitleTextField.getText().length() > max) {
            keyEvent.consume();
            String shortenedTitle = meetingTitleTextField.getText().substring(0, max);
            meetingTitleTextField.setText(shortenedTitle);
            meetingTitleTextField.positionCaret(max);
        }
    }

    /**This is the max characters for text area method. */
    public void maxCharTextAreaAction(javafx.scene.input.KeyEvent keyEvent) {
        int max = 50;
        if(appDescriptTextArea.getText().length() > max) {
            keyEvent.consume();
            String shortenedDescription = appDescriptTextArea.getText().substring(0, max);
            appDescriptTextArea.setText(shortenedDescription);
            appDescriptTextArea.positionCaret(max);
        }
    }
}
