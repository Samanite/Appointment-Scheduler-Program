package sample.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.dao.AppointmentQuery;
import sample.dao.CustomerQuery;
import sample.dao.JDBC;

import java.sql.SQLException;

/**This class creates an application that tracks customers and appointments.*/
public class Main extends Application {

    public Main() throws SQLException {
    }

    @Override
    public void init()
    {
        System.out.println("Loading Application...");
    }

    /**This is the Start method. This method opens the application to the log-in form.*/
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        primaryStage.setTitle("Appointment Scheduler");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    /**This is the Stop method. */
    @Override
    public void stop()
    {
        System.out.println("Application Terminated");
    }

    /**This is the Main method. This makes the JDBC connection.*/
    public static void main(String[] args) {
        JDBC.makeConnection();
        launch(args);
    }

}
