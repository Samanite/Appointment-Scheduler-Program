package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import sample.dao.AppointmentQuery;
import sample.dao.CustomerQuery;
import sample.dao.DateTimeManagement;
import sample.model.Appointment;
import sample.model.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**This class is the controller for Appointments (with Lambda). Appointments are viewed on a table with some
  interactive options.*/
public class AppointmentsController implements Initializable {

Stage stage;
Parent scene;
LocalDateTime localDateTime = LocalDateTime.now();

Date currentYrMoDay = DateTimeManagement.localDateTimeToYrMoDayDate(localDateTime);
Date currentYrMo = DateTimeManagement.localDateTimeToYrMo(localDateTime);

ObservableList<Appointment> thisMonthsAppts = FXCollections.observableArrayList();
ObservableList<Appointment> thisWeeksAppts = FXCollections.observableArrayList();

    @FXML
    private RadioButton monthlyRB;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private RadioButton allRB;

    @FXML
    private TableColumn<Appointment, Integer> appContactCol;

    @FXML
    private TableColumn<Appointment, Integer> appCusIdCol;

    @FXML
    private TableColumn<Appointment, String> appDiscptCol;

    @FXML
    private TableColumn<Appointment, String> appEndCol;

    @FXML
    private TableColumn<Appointment, Integer> appIdCol;

    @FXML
    private TableColumn<Appointment, String> appLocationCol;

    @FXML
    private TableColumn<Appointment, String> appStartCol;

    @FXML
    private TableColumn<Appointment, String> appTitleCol;

    @FXML
    private TableColumn<Appointment, String> appTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> appUserIdCol;

    public AppointmentsController() throws ParseException {
    }

    /**This is the to-appointment-by-contact method. This takes the user to a contact select screen.*/
    @FXML
    void toApptByContact(ActionEvent event) throws IOException {
        stage = (Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/contactSelect.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the View All Appointments RB method. Shows all appointments in the table view.*/
    @FXML
    void rbAllAction(ActionEvent event) {
        if(allRB.isSelected()){
            appointmentTable.setItems(Data.getAppointmentArrayList());
            appIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            appDiscptCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            appLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            appStartCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            appEndCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            appCusIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            appUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
            appContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        }
    }

    /**This is the View This Months Appointments RB method. Shows all Monthly appointments in the table view.*/
    @FXML
    void rbMonthAction(ActionEvent event) {

        if(monthlyRB.isSelected()){
            appointmentTable.setItems(thisMonthsAppts);
            appIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            appDiscptCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            appLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            appStartCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            appEndCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            appCusIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            appUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
            appContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        }

    }

    /**This is the View this Weeks Appointments RB method. Shows all appointments over the next seven days in the table view.*/
    @FXML
    void rbWeekAction(ActionEvent event) {
        appointmentTable.setItems(thisWeeksAppts);
        appIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDiscptCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        appCusIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    }

    /**This is the to-add-appointment method. Takes the user to the add appointment page.*/
    @FXML
    void toAddAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/addAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the to-modify-delete-appointment method. Takes the user to the modify delete appointment page.*/
    @FXML
    void toModDeleteAppointments(ActionEvent event) throws IOException {
        if(!appointmentTable.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/modifyDeleteAppointment.fxml"));
            loader.load();

            ModifyDeleteAppointmentController controller = loader.getController();
            controller.fillSelectedAppointment(appointmentTable.getSelectionModel().getSelectedItem());

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

    /**This is the to-appointment-details method. Opens the appointment details page showing information
      about the highlighted appointment.*/
    @FXML
    void toAppointmentDetails(ActionEvent event) throws IOException {
        if(!appointmentTable.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/viewAppointmentDetails.fxml"));
            loader.load();

            ViewAppointmentDetailsController controller = loader.getController();
            controller.fillSelectedAppointment(appointmentTable.getSelectionModel().getSelectedItem());
            controller.fillSceneTracker(1);

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

    /**This is the to-customers method. Takes the user to the customers page.*/
    @FXML
    void toCustomers(ActionEvent event) throws IOException {
        stage = (Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/customersPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the to-main-menu method. Takes the user back to the main menu page.*/
    @FXML
    void toMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the to-total-appointments method. Takes the user to the total appointments report page.*/
    @FXML
    void toTotalAppointments(ActionEvent event) throws IOException {
        stage = (Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/totalAppointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the initialize method (with lambda expressions). Sets the local data and table.
      Two Lambda expressions are used in setting local data. By using a "forEach" loop when adding
      data to the monthly and weekly appointment list, the loops are cleaner looking and instead of
      "Data.getAppointmentArrayList.get(i)"  we only need "i". */
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
        Data.sortApptByDate();

        //loop using Lambda expression
        Data.getAppointmentArrayList().forEach(i -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            if(simpleDateFormat.format(i.getStartDateTime()).equals(simpleDateFormat.format(currentYrMo))) {
                thisMonthsAppts.add(i);
            }
        });

        //Loop using Lambda expression
        Data.getAppointmentArrayList().forEach( i -> {
            if (i.getStartDateTime().compareTo(currentYrMoDay) > 0) {
                long difference = Math.abs(i.getStartDateTime().getTime() - currentYrMoDay.getTime());
                long diffDays = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
                if (diffDays < 8) {
                    thisWeeksAppts.add(i);
                }
            }
        });

        appointmentTable.setItems(Data.getAppointmentArrayList());

        appIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDiscptCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        appCusIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));

        allRB.setSelected(true);
    }
}
