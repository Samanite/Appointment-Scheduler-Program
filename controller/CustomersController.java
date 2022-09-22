package sample.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.dao.CustomerQuery;
import sample.model.Appointment;
import sample.model.Customer;
import sample.model.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/**This class is the controller for the customers page (with Lambda). */
public class CustomersController implements Initializable {

Stage stage;
Parent scene;
private ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, Timestamp> appDateCol;

    @FXML
    private TableColumn<Appointment, Integer> appIdCol;

    @FXML
    private TableColumn<Appointment, String> appTitleCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, Integer> customerDivIdCol;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerPostCodeCol;

    @FXML
    private TableView<Customer> customerTable;

    /**This is the show-customer-report method. This sends the user to the view total customer
      appointments page.*/
    @FXML
    void showCustomerReport(ActionEvent event) throws IOException {

        if(!customerTable.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/totalAppointmentsByCus.fxml"));
            loader.load();

            TotalApptByCusController controller = loader.getController();
            controller.fillCusAppointments(customerTable.getSelectionModel().getSelectedItem().getId());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NOTHING SELECTED");
            alert.setContentText("No customer has been selected");
            alert.showAndWait();
        }
    }

    /**This is the back-to-main method. This sends the user back to the main menu.*/
    @FXML
    void backToMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the to-add-customer method. This sends the user to the add customer page.*/
    @FXML
    void toAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/addCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the to-appointments method. Sends the user to the appointment page.*/
    @FXML
    void toAppointments(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointmentsPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the to-modify-delete-customer method. Sends the user to the modify/delete customer page.*/
    @FXML
    void toModDelCustomer(ActionEvent event) throws IOException {
        if(!customerTable.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/modifyDeleteCustomer.fxml"));
            loader.load();

            ModifyDeleteCustomerController controller = loader.getController();
            controller.fillSelectedCustomer(customerTable.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NOTHING SELECTED");
            alert.setContentText("No customer has been selected");
            alert.showAndWait();
        }
    }

    /**This is the initialize method (with lambda expression). This sets the table information on the page.
       The listener for the table uses a lambda expression, this is a simple way to fill the associated
       appointments table when a customer is highlighted in the customer table.*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            CustomerQuery.select();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerTable.setItems(Data.getCustomerArrayList());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));//address
        customerPostCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));//postalCode
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));//phoneNumber
        customerDivIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));//divisionId

        appointmentTable.setItems(filteredAppointments);
        appIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDateCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));

        //Lambda expression listener
        customerTable.getSelectionModel().selectedItemProperty().addListener(observable -> {
            if(!customerTable.getSelectionModel().isEmpty()){
                filteredAppointments.clear();
                int id = customerTable.getSelectionModel().getSelectedItem().getId();
                Data.getAppointmentByCusId(id, filteredAppointments);
            }
        });
    }

}
