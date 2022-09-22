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
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sample.dao.ContactsQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**This class is the controller for selecting a contact to view appointments. */
public class ContactSelectController implements Initializable {

    Stage stage;
    Parent scene;
    private ObservableList<String> contactsIdName = FXCollections.observableArrayList();
    private ObservableList<Integer> contactsIdList = FXCollections.observableArrayList();


    @FXML
    private ComboBox<String> contactComboBox;


    /**This is the to-show-associated-appointments method. This transfers the selected contact
      and takes the user to the appointment report.*/
    public void toShowAllAppts(ActionEvent actionEvent) throws IOException {
        int contactIndex = 0;
        if(!contactComboBox.getSelectionModel().isEmpty()) {
            for(int i = 0; i < contactsIdName.size(); i ++){
                if(contactsIdName.get(i).equals(contactComboBox.getValue())){
                    contactIndex = i;
                }
            }


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/totalContactAppointments.fxml"));
            loader.load();

            TotalApptByContactController controller = loader.getController();
            controller.fillContactId(contactsIdList.get(contactIndex));

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NOTHING SELECTED");
            alert.setContentText("No contact has been selected");
            alert.showAndWait();
        }
    }

    /**This is the back-to-appointments method. This takes the user back to the appointments page.*/
    public void backToAppointments(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointmentsPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the initialize method. This sets the contact selection box.*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            ContactsQuery.getContactsIdName(contactsIdName, contactsIdList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        contactComboBox.setItems(contactsIdName);
    }
}
