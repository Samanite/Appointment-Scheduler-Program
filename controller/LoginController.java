package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.model.TextLoginFile;
import sample.dao.UsersQuery;
import sample.model.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**This class is the controller for the log-in screen. */
public class LoginController implements Initializable {
Stage stage;
Parent scene;
private ObservableList<String> userIdName = FXCollections.observableArrayList();
private ObservableList<Integer> userIdList = FXCollections.observableArrayList();
private ZoneId zone = ZoneId.systemDefault();

Locale locale = Locale.getDefault();
String lqng = locale.toLanguageTag();
int langKey = 1;

    @FXML
    private Button loginButton;

    @FXML
    private Label zoneNameLable;

    @FXML
    private Label nameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label zoneIdLabel;

    @FXML
    private ComboBox<String> userComboBox;

    @FXML
    private PasswordField passwordTextField;

    /**This is the to-main-menu method. This checks that the user id and password are correct, either
      sending the user to the main menu or giving an error message.*/
    @FXML
    void toMainMenu(ActionEvent event) throws IOException {
        int index = 0;
        String password = "";

        if(!passwordTextField.getText().isEmpty()) {
            password = passwordTextField.getText();

            boolean match = false;

            for (int i = 0; i < userIdList.size(); i++) {
                if (userComboBox.getValue().equals(userIdName.get(i))) {
                    index = i;
                }
            }

            if (Data.getUser(index).getPassword().equals(password)) {
                match = true;
            } else {
                match = false;
            }


            if (match == true) {


                try {
                    TextLoginFile.newFileRecord(userIdName.get(index) + " | Successful");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.centerOnScreen();
                stage.show();
            }
            else if (langKey == 1){

                try {
                    TextLoginFile.newFileRecord(userIdName.get(index) + " | Unsuccessful");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INCORRECT PASSWORD");
                alert.setContentText("The password does not match");
                alert.showAndWait();
            }
            else if(langKey == 2){
                try {
                    TextLoginFile.newFileRecord(userIdName.get(index) + " | Unsuccessful");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("MOT DE PASSE INCORRECT");
                alert.setContentText("Le mot de passe ne correspond pas");
                alert.showAndWait();
            }
        }
        else if (langKey == 1){
            try {
                TextLoginFile.newFileRecord(userIdName.get(index) + " | Unsuccessful");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("MISSING INFORMATION");
            alert.setContentText("No Password has been entered");
            alert.showAndWait();
        }
        else if(langKey == 2){
            try {
                TextLoginFile.newFileRecord(userIdName.get(index) + " | Unsuccessful");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INFORMATION MANQUANTE");
            alert.setContentText("Aucun mot de passe n'a été saisi");
            alert.showAndWait();
        }

    }

    /**This is the initialize method. This fills local data, sets the combo box, sets the zone id, and
      makes changes based on the users language setting.*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            UsersQuery.select();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            UsersQuery.getUserIdName(userIdName, userIdList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userComboBox.setItems(userIdName);
        userComboBox.getSelectionModel().selectFirst();

        zoneNameLable.setText(String.valueOf(zone));

        if(lqng.contains("fr")){
            titleLabel.setText("Utilisateur en ligne");
            nameLabel.setText("Nom d'utilisateur");
            passwordLabel.setText("Mot de passe");
            zoneIdLabel.setText("Identifiant de la zone:");
            loginButton.setText("Soumettre");
            langKey = 2;
        }

    }

}
