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
import sample.dao.ContactsQuery;
import sample.dao.CustomerQuery;
import sample.dao.UsersQuery;
import sample.model.Appointment;
import sample.model.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**This class is the controller for the view-appointment-details page. */
public class ViewAppointmentDetailsController implements Initializable {
    Stage stage;
    Parent scene;
    private Appointment selectedAppointment;
    private int sceneNumber = 0;
    private ObservableList<String> customerIdName = FXCollections.observableArrayList();
    private ObservableList<String> userIdName = FXCollections.observableArrayList();
    private ObservableList<String> contactsIdName = FXCollections.observableArrayList();
    private ObservableList<Integer> customerIdList = FXCollections.observableArrayList();
    private ObservableList<Integer> userIdList = FXCollections.observableArrayList();
    private ObservableList<Integer> contactsIdList = FXCollections.observableArrayList();

    /**This is the fill-scene-tracker method. This tracks which controller is using this page.*/
    public void fillSceneTracker(int integer){
        sceneNumber = integer;
    }

    /**This is the fill-selected-appointment method. Gets the data from another controller and
      displays it for the user. */
    public void fillSelectedAppointment(Appointment appointment) {
        selectedAppointment = appointment;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        String selectedContactIDName = "";
        String selectedUserIDName = "";
        String selectedCusIDName = "";
        for(int i = 0; i < contactsIdName.size(); i++){
            if(selectedAppointment.getContactId() == Data.getContact(i).getId()){
                selectedContactIDName = contactsIdName.get(i);
            }
        }
        for(int i = 0; i < userIdName.size(); i++){
            if(selectedAppointment.getUserId() == Data.getUser(i).getUserId()){
                selectedUserIDName = userIdName.get(i);
            }
        }
        for(int i = 0; i < customerIdName.size(); i++){
            if(selectedAppointment.getCustomerId() == Data.getCustomer(i).getId()){
                selectedCusIDName = customerIdName.get(i);
            }
        }

        viewApptIDField.setText(String.valueOf(selectedAppointment.getId()));
        viewApptContactField.setText(selectedContactIDName);
        viewApptCusIDField.setText(selectedCusIDName);
        viewApptDescptFieldArea.setText(selectedAppointment.getDescription());
        viewApptLocationField.setText(selectedAppointment.getLocation());
        viewApptMeetingTitleField.setText(selectedAppointment.getTitle());
        viewApptStartDateTimeField.setText(simpleDateFormat.format(selectedAppointment.getStartDateTime()));
        viewApptEndDateTimeField.setText(simpleDateFormat.format(selectedAppointment.getEndDateTime()));
        viewApptMeetingTypeField.setText(selectedAppointment.getType());
        viewApptUserIDField.setText(selectedUserIDName);
    }

    @FXML
    private TextField viewApptContactField;

    @FXML
    private TextField viewApptCusIDField;

    @FXML
    private TextArea viewApptDescptFieldArea;

    @FXML
    private TextField viewApptEndDateTimeField;

    @FXML
    private TextField viewApptIDField;

    @FXML
    private TextField viewApptLocationField;

    @FXML
    private TextField viewApptMeetingTitleField;

    @FXML
    private TextField viewApptMeetingTypeField;

    @FXML
    private TextField viewApptStartDateTimeField;


    @FXML
    private TextField viewApptUserIDField;


    /**This is the back-to method. Sends the user back to the controller they came from. */
    @FXML
    void backToAppt(ActionEvent event) throws IOException {
        if(sceneNumber == 1) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/appointmentsPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        if(sceneNumber == 2){
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**This is the initialize method. Fills the local data to use for display purposes.*/
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

    }

}
