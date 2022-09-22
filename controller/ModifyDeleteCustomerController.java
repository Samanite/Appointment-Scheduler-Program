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
import sample.dao.CountrysQuery;
import sample.dao.CustomerQuery;
import sample.dao.FirstLevelDivQuery;
import sample.model.Country;
import sample.model.Customer;
import sample.model.Data;
import sample.model.FirstLevelDivision;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**This class is the controller for the modify and delete customer page. */
public class ModifyDeleteCustomerController implements Initializable {
    Stage stage;
    Parent scene;
    private Customer selectedCustomer;
    private ObservableList<String> countryNames = FXCollections.observableArrayList();
    private ObservableList<String> divisionNames = FXCollections.observableArrayList();
    private ObservableList<String> divisionNames2 = FXCollections.observableArrayList();
    private ObservableList<String> divisionNames3 = FXCollections.observableArrayList();

    /**This is the fill-customer method. Gets the customer information from the customer controller.*/
    public void fillSelectedCustomer(Customer customer){
        selectedCustomer = customer;

        int countryId = Data.getCountryIdFromDivId(customer.getDivisionId());

        String cusDivName = Data.getDivNameFromId(customer.getDivisionId());
        modDelCusIDField.setText(Integer.toString(selectedCustomer.getId()));
        modDelCusNameField.setText(selectedCustomer.getName());
        modDelCusAddressField.setText(selectedCustomer.getAddress());
        modDelCusPostalField.setText(selectedCustomer.getPostalCode());
        modDelCusPhoneField.setText(selectedCustomer.getPhoneNumber());

        modDelCusCountrySelect.setItems(countryNames);

        //Depending on Country ID set country & Division combos, finding and using index
        if(countryId == 1){
            modDelCusCountrySelect.getSelectionModel().select(0);
            modDelCusStateSelect.setItems(divisionNames);
            for(int i = 0; i < divisionNames.size(); i++){
                if(divisionNames.get(i).equals(cusDivName)){
                    modDelCusStateSelect.getSelectionModel().select(i);
                }
            }

        }
        else if(countryId == 2){
            modDelCusCountrySelect.getSelectionModel().select(1);
            modDelCusStateSelect.setItems(divisionNames2);
            for(int i = 0; i < divisionNames2.size(); i++){
                if(divisionNames2.get(i).equals(cusDivName)){
                    modDelCusStateSelect.getSelectionModel().select(i);
                }
            }
        }
        else if(countryId == 3){
            modDelCusCountrySelect.getSelectionModel().select(2);
            modDelCusStateSelect.setItems(divisionNames3);
            for(int i = 0; i < divisionNames3.size(); i++){
                if(divisionNames3.get(i).equals(cusDivName)){
                    modDelCusStateSelect.getSelectionModel().select(i);
                }
            }
        }

    }
    @FXML
    private TextField modDelCusAddressField;

    @FXML
    private ComboBox<String> modDelCusCountrySelect;

    @FXML
    private TextField modDelCusIDField;

    @FXML
    private TextField modDelCusNameField;

    @FXML
    private TextField modDelCusPhoneField;

    @FXML
    private TextField modDelCusPostalField;

    @FXML
    private ComboBox<String> modDelCusStateSelect;

    /**This is the select-country method. This fills the first level division combo box depending
      on Country selection.*/
    @FXML
    void selectCountryAction(ActionEvent event) {
        if(modDelCusCountrySelect.getValue().equalsIgnoreCase("U.S")){
            modDelCusStateSelect.setItems(divisionNames);
        }
        else if(modDelCusCountrySelect.getValue().equalsIgnoreCase("UK")){
            modDelCusStateSelect.setItems(divisionNames2);
        }
        else if(modDelCusCountrySelect.getValue().equalsIgnoreCase("Canada")){
            modDelCusStateSelect.setItems(divisionNames3);
        }
    }

    @FXML
    void selectDivisionAction(ActionEvent event) {

    }

    /**This is the back-to-customers method. Takes the user back to the customers page with no changes. */
    @FXML
    void backToCustomers(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/customersPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the delete customer method. Deletes the customer. */
    @FXML
    void deleteCustomer(ActionEvent event) throws IOException, SQLException {

        int rowAffected = 0;
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setContentText("Are you sure you want to delete  " + selectedCustomer.getName() + " and any associated appointments?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                rowAffected = CustomerQuery.delete(selectedCustomer.getId());
            }
            if (rowAffected > 0) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Customer Deleted");
                alert1.setContentText(selectedCustomer.getName() + " has been removed from the records");
                alert1.showAndWait();

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/customersPage.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nothing Deleted");
                alert.setContentText("The customer was not deleted");
                alert.showAndWait();
            }
    }

    /**This is the save custome method. Saves any changes to the customer. */
    @FXML
    void saveCustomerModification(ActionEvent event) throws IOException, SQLException {

        if((!modDelCusAddressField.getText().isBlank()) && (!modDelCusNameField.getText().isBlank()) && (!modDelCusPostalField.getText().isBlank())
         && (!modDelCusPhoneField.getText().isBlank())) {
            int id = Integer.parseInt(modDelCusIDField.getText());
            String name = modDelCusNameField.getText();
            String address = modDelCusAddressField.getText();
            String postalCode = modDelCusPostalField.getText();
            String phone = modDelCusPhoneField.getText();
            String firstDivision = modDelCusStateSelect.getValue();
            int divId = Data.getDivisionId(firstDivision);

            CustomerQuery.update(id, name, address, postalCode, phone, divId);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/customersPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Information");
            alert.setContentText("Please fill out all the required areas");
            alert.showAndWait();
        }
    }

    /**This is the initialize method. This sets the local data. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            CountrysQuery.select();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            FirstLevelDivQuery.select();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < Data.getCountryArrayList().size(); i++) {
            Country country;
            country = Data.getCountry(i);
            countryNames.add(country.getCountry());
            // System.out.println("Country added "+ i);
        }
        for (int i = 0; i < Data.getFirstLevelDivArrayList().size(); i++) {
            FirstLevelDivision division;
            division = Data.getFirstLevelDivision(i);
            if (division.getCountryId() == 1) {
                divisionNames.add(division.getDivision());
                // System.out.println("First Division added " + i);
            } else if (division.getCountryId() == 2) {
                divisionNames2.add(division.getDivision());
            } else if (division.getCountryId() == 3) {
                divisionNames3.add(division.getDivision());
            }
        }
    }

    /**This is the max-character method for phone. */
    public void phoneMaxCharAction(KeyEvent keyEvent) {
        int max = 50;
        if(modDelCusPhoneField.getText().length() > max) {
            keyEvent.consume();
            String shortenedPhone = modDelCusPhoneField.getText().substring(0, max);
            modDelCusPhoneField.setText(shortenedPhone);
            modDelCusPhoneField.positionCaret(max);
        }
    }

    /**This is the max-character method for postal code. */
    public void postalMaxCharAction(KeyEvent keyEvent) {
        int max = 50;
        if(modDelCusPostalField.getText().length() > max) {
            keyEvent.consume();
            String shortenedPostal = modDelCusPostalField.getText().substring(0, max);
            modDelCusPostalField.setText(shortenedPostal);
            modDelCusPostalField.positionCaret(max);
        }
    }

    /**This is the max-character method for name. */
    public void nameMaxCharAction(KeyEvent keyEvent) {
        int max = 50;
        if(modDelCusNameField.getText().length() > max) {
            keyEvent.consume();
            String shortenedName = modDelCusNameField.getText().substring(0, max);
            modDelCusNameField.setText(shortenedName);
            modDelCusNameField.positionCaret(max);
        }
    }

    /**This is the max-character method for address. */
    public void addressMaxCharAction(KeyEvent keyEvent) {
        int max = 100;
        if(modDelCusAddressField.getText().length() > max) {
            keyEvent.consume();
            String shortenedAddress = modDelCusAddressField.getText().substring(0, max);
            modDelCusAddressField.setText(shortenedAddress);
            modDelCusAddressField.positionCaret(max);
        }
    }
}
