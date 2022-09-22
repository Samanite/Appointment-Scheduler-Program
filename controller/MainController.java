package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.dao.*;
import sample.model.Appointment;
import sample.model.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**This class is the controller for the main menu. */
public class MainController implements Initializable{
Stage stage;
Parent scene;
private ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
LocalDateTime localDateTime = LocalDateTime.now();
Date currentYrMoDay = DateTimeManagement.localDateTimeToDate(localDateTime);


    @FXML
    private TableColumn<Appointment, Integer> cusTableCol;

    @FXML
    private TableColumn<Appointment, Timestamp> timeTableCol;

    @FXML
    private TableColumn<Appointment, String> titleTabCol;

    @FXML
    private TableView<Appointment> upcomingAppTable;

    public MainController() throws ParseException {
    }

    /**This is the appointment-details method. This takes the user to view a selected appointments details.*/
    @FXML
    void appDetailsAction(ActionEvent event) throws IOException {
        if(!upcomingAppTable.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/viewAppointmentDetails.fxml"));
            loader.load();

            ViewAppointmentDetailsController controller = loader.getController();
            controller.fillSelectedAppointment(upcomingAppTable.getSelectionModel().getSelectedItem());
            controller.fillSceneTracker(2);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NOTHING SELECTED");
            alert.setContentText("No appointment has been selected");
            alert.showAndWait();
        }
    }

    @FXML
    private Button mainMenuAppButton;

    @FXML
    private Button mainMenuCusButton;

    /**This is the to-appointments method. Takes the user to the appointments page.*/
    @FXML
    void toAppointments(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointmentsPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the to-customers method. Takes the user to the customers page.*/
    @FXML
    void toCustomers(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/customersPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the initialize method. Fills local data, and gives a notice of upcoming appointments.*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            CustomerQuery.select();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            AppointmentQuery.select();
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            UsersQuery.select();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ContactsQuery.select();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            CountrysQuery.select();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            FirstLevelDivQuery.select();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < Data.getAppointmentArrayList().size(); i++){
            //if later than yesterday
            if(Data.getAppointmentArrayList().get(i).getStartDateTime().compareTo(currentYrMoDay) > 0) {
                long difference = Math.abs(Data.getAppointmentArrayList().get(i).getStartDateTime().getTime() - currentYrMoDay.getTime());
                long diffDays = TimeUnit.HOURS.convert(difference, TimeUnit.MILLISECONDS);

                if (diffDays < 25) {
                    filteredAppointments.add(Data.getAppointmentArrayList().get(i));
                }
            }
        }
        //Create filter for Appointments in next 24hrs
        //filteredAppointments = Data.getAppointmentArrayList();
        Collections.sort(filteredAppointments);

        upcomingAppTable.setItems(filteredAppointments);

        cusTableCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        timeTableCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        titleTabCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        String upcommingAppts = "";
        StringBuilder stringBuilder = new StringBuilder();
        boolean isNotice = false;

        for(int i = 0; i < Data.getAppointmentArrayList().size(); i++){
            //if within next 15 min
            if(Data.getAppointmentArrayList().get(i).getStartDateTime().compareTo(currentYrMoDay) > 0) {
                long difference = Math.abs(Data.getAppointmentArrayList().get(i).getStartDateTime().getTime() - currentYrMoDay.getTime());
                long diffMin = TimeUnit.MINUTES.convert(difference, TimeUnit.MILLISECONDS);

                if (diffMin < 16) {
                    stringBuilder.append(Appointment.appointmentNoticeStringDetails(Data.getAppointmentArrayList().get(i)));
                    isNotice = true;
                }
            }
        }
        upcommingAppts = stringBuilder.toString();

        if(!Data.getLoggedIn()) {
            if (isNotice) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("UPCOMING APPOINTMENT(S)");
                alert.setContentText(upcommingAppts);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("UPCOMING APPOINTMENT(S)");
                alert.setContentText("There are no appointments scheduled in the next 15 minutes");
                alert.showAndWait();
            }
            Data.setLoggedIn();
        }
    }
}
