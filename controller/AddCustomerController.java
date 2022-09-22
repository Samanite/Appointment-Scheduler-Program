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
import sample.model.Data;
import sample.model.FirstLevelDivision;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**This is the controller class for adding customers. This is a combination of combo boxes and
  text areas.*/
public class AddCustomerController implements Initializable {

    Stage stage;
    Parent scene;
    private int x;
    private ObservableList<String> countryNames = FXCollections.observableArrayList();
    private ObservableList<String> divisionNames = FXCollections.observableArrayList();
    private ObservableList<String> divisionNames2 = FXCollections.observableArrayList();
    private ObservableList<String> divisionNames3 = FXCollections.observableArrayList();

    /**This is the country select method. This fills the first level division combo box
      depending on which country is selected.*/
    @FXML
    void countrySelectAction(ActionEvent event) {
        if(addCusCountrySelect.getValue().equalsIgnoreCase("U.S")){
            addCusStateSelect.setItems(divisionNames);
        }
        else if(addCusCountrySelect.getValue().equalsIgnoreCase("UK")){
            addCusStateSelect.setItems(divisionNames2);
        }
        else if(addCusCountrySelect.getValue().equalsIgnoreCase("Canada")){
            addCusStateSelect.setItems(divisionNames3);
        }
    }

    @FXML
    private TextField addCusAddressField;

    @FXML
    private ComboBox<String> addCusCountrySelect;

    @FXML
    private TextField addCusIDField;

    @FXML
    private TextField addCusNameField;

    @FXML
    private TextField addCusPhoneField;

    @FXML
    private TextField addCusPosCodeField;

    @FXML
    private ComboBox<String> addCusStateSelect;

    /**This is the max characters for phone method. */
    public void phoneMaxCharAction(KeyEvent keyEvent) {
        int max = 50;
        if(addCusPhoneField.getText().length() > max) {
            keyEvent.consume();
            String shortenedPhone = addCusPhoneField.getText().substring(0, max);
            addCusPhoneField.setText(shortenedPhone);
            addCusPhoneField.positionCaret(max);
        }
    }

    /**This is the max characters for postal code method. */
    public void postalCodeMaxCharAction(KeyEvent keyEvent) {
        int max = 50;
        if(addCusPosCodeField.getText().length() > max) {
            keyEvent.consume();
            String shortenedPostal = addCusPosCodeField.getText().substring(0, max);
            addCusPosCodeField.setText(shortenedPostal);
            addCusPosCodeField.positionCaret(max);
        }
    }

    /**This is the max characters for name method. */
    public void nameMaxCharAction(KeyEvent keyEvent) {
        int max = 50;
        if(addCusNameField.getText().length() > max) {
            keyEvent.consume();
            String shortenedName = addCusNameField.getText().substring(0, max);
            addCusNameField.setText(shortenedName);
            addCusNameField.positionCaret(max);
        }
    }

    /**This is the max characters for address method. */
    public void addressMaxCharAction(KeyEvent keyEvent) {
        int max = 100;
        if(addCusAddressField.getText().length() > max) {
            keyEvent.consume();
            String shortenedAddress = addCusAddressField.getText().substring(0, max);
            addCusAddressField.setText(shortenedAddress);
            addCusAddressField.positionCaret(max);
        }
    }

    /**This is the add customer method. This does basic error checking to make sure all fields are
      filled out. */
    @FXML
    void addNewCustomer(ActionEvent event) throws IOException, SQLException {

        if( (!addCusAddressField.getText().isBlank()) && (!addCusNameField.getText().isBlank()) && (!addCusPosCodeField.getText().isBlank())
         && (!addCusPhoneField.getText().isBlank()) && addCusCountrySelect.getValue() !=null && addCusStateSelect.getValue() !=null) {
            int id = Integer.parseInt(addCusIDField.getText());
            String name = addCusNameField.getText();
            String address = addCusAddressField.getText();
            String postalCode = addCusPosCodeField.getText();
            String phone = addCusPhoneField.getText();
            String firstDivision = addCusStateSelect.getValue();
            int divId = Data.getDivisionId(firstDivision);

            CustomerQuery.insert(id, name, address, postalCode, phone, divId);

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

    /**This is the back to customers method. This returns the user to the customer page with
      no changes made.*/
    @FXML
    void backToCustomers(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/customersPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the Initialize method. This fills the local data lists and combo boxes.*/
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

        for (int i = 0; i< Data.getCountryArrayList().size(); i++ ){
            Country country;
            country = Data.getCountry(i);
            countryNames.add(country.getCountry());
        }
        for (int i = 0; i< Data.getFirstLevelDivArrayList().size(); i++) {
            FirstLevelDivision division;
            division = Data.getFirstLevelDivision(i);
            if (division.getCountryId() == 1) {
                divisionNames.add(division.getDivision());
                }
            else if (division.getCountryId() == 2) {
                divisionNames2.add(division.getDivision());
                }
            else if (division.getCountryId() == 3) {
                divisionNames3.add(division.getDivision());
                }
        }

        if(Data.getCustomerArrayList().isEmpty()){
            x = 1;
        }
        for(int i=0; i<Data.getCustomerArrayList().size(); i++) {
            if (x <= Data.getCustomer(i).getId()) {
                x = Data.getCustomer(i).getId() + 1;
            }
        }

        addCusIDField.setText(String.valueOf(x));
        addCusCountrySelect.setItems(countryNames);

    }
}
