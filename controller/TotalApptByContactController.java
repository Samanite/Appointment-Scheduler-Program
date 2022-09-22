package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.model.Appointment;
import sample.model.Data;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.ResourceBundle;

/**This class is the controller for the view-total-appointments-for-contact page. */
public class TotalApptByContactController implements Initializable {

    Stage stage;
    Parent scene;
    int contactId;
    ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

    /**This is the fill-contact method. This gets the contact id and fills the report with all
      of the related appointments sorted by date and type. */
    public void fillContactId(int indexId) {
        contactId = indexId;

        for (int i = 0; i < Data.getAppointmentArrayList().size(); i++) {
            if (contactId == Data.getAppointment(i).getContactId()) {
                filteredAppointments.add(Data.getAppointment(i));
                //System.out.println("Adding appointment: " + i);
            }
        }

        Collections.sort(filteredAppointments);
        String report = "";
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder typeOne = new StringBuilder();
        StringBuilder typeTwo = new StringBuilder();
        StringBuilder typeThree = new StringBuilder();
        StringBuilder typeFour = new StringBuilder();
        StringBuilder typeFive = new StringBuilder();
        String types = "";

        //Print top page information
        stringBuilder.append("        Contact Appointments Report- For Contact ID: " + indexId + "\n" + "\n");

        SimpleDateFormat yearMonthDate = new SimpleDateFormat("yyyy-MMMM");

        //Print all information
        for (int i = 0; i < filteredAppointments.size(); i++) {
            int amountPerMonth = 0;
            typeOne.setLength(0);
            typeTwo.setLength(0);
            typeThree.setLength(0);
            typeFour.setLength(0);
            typeFive.setLength(0);

            //Append type title for each type
            typeOne.append("\n" + "Meeting Type: Consultation" + "\n" + "\n");
            typeTwo.append("\n" + "Meeting Type: Follow Up" + "\n" + "\n");
            typeThree.append("\n" + "Meeting Type: Discrepancy" + "\n" + "\n");
            typeFour.append("\n" + "Meeting Type: Change Request" + "\n" + "\n");
            typeFive.append("\n" + "Meeting Type: Other" + "\n" + "\n");

            //For each new month (int i is index) print Year and month name Title
            stringBuilder.append(yearMonthDate.format(filteredAppointments.get(i).getStartDateTime()) + "\n");
            //Print out first element because we know it's there
            //here we need to separate types before we tag on anything else, tag on types to type Strings, then tag those onto main string later
            types = filteredAppointments.get(i).getType();

            switch (types) {
                case "Consultation":
                    typeOne.append(Appointment.stringAppointmentDetails(filteredAppointments.get(i)));
                    break;
                case "Follow up":
                    typeTwo.append(Appointment.stringAppointmentDetails(filteredAppointments.get(i)));
                    break;
                case "Discrepancy":
                    typeThree.append(Appointment.stringAppointmentDetails(filteredAppointments.get(i)));
                    break;
                case "Change request":
                    typeFour.append(Appointment.stringAppointmentDetails(filteredAppointments.get(i)));
                    break;
                case "Other":
                    typeFive.append(Appointment.stringAppointmentDetails(filteredAppointments.get(i)));
                    break;
                default:
                    System.out.println("Didn't register : " + Appointment.stringAppointmentDetails(filteredAppointments.get(i)));
            }
            //stringBuilder.append(Appointment.stringAppointmentDetails(filteredAppointments.get(i)));
            amountPerMonth++;
            //If the next element isn't empty
            if ((i + 1) < filteredAppointments.size()) {
                int index = i;
                //Loop starting with next element, if it has same year/month print
                for (int x = i + 1; x < filteredAppointments.size(); x++) {
                    if ((yearMonthDate.format(filteredAppointments.get(x).getStartDateTime())).equals(yearMonthDate.format(filteredAppointments.get(i).getStartDateTime()))) {

                        types = filteredAppointments.get(x).getType();
                        switch (types) {
                            case "Consultation":
                                typeOne.append(Appointment.stringAppointmentDetails(filteredAppointments.get(x)));
                                break;
                            case "Follow up":
                                typeTwo.append(Appointment.stringAppointmentDetails(filteredAppointments.get(x)));
                                break;
                            case "Discrepancy":
                                typeThree.append(Appointment.stringAppointmentDetails(filteredAppointments.get(x)));
                                break;
                            case "Change request":
                                typeFour.append(Appointment.stringAppointmentDetails(filteredAppointments.get(x)));
                                break;
                            case "Other":
                                typeFive.append(Appointment.stringAppointmentDetails(filteredAppointments.get(x)));
                                break;
                            default:
                                System.out.println("Didn't register : " + Appointment.stringAppointmentDetails(filteredAppointments.get(x)));
                        }

                        //stringBuilder.append(Appointment.stringAppointmentDetails(filteredAppointments.get(x)));
                        amountPerMonth++;
                        index++;
                    }
                }
                //After for loop is finished set previous for loop after last matching index
                i = index;
            }
            //After loop is finished set all five types to main string
            stringBuilder.append(typeOne.toString() + typeTwo.toString() + typeThree.toString() + typeFour.toString() + typeFive.toString());

            stringBuilder.append("\n" + "(Total appointments for month = " + amountPerMonth + ")" + "\n" + "\n");
            amountPerMonth = 0;
        }

        report = stringBuilder.toString();
        contactTextArea.setText(report);
    }

    @FXML
    private TextArea contactTextArea;

    /**This is the back-to-appointments method. Takes the user back to the appointments page. */
    @FXML
    void backToAppointments(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointmentsPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the initialize method. Empty.*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
