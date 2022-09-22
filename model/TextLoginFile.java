package sample.model;

import sample.dao.DateTimeManagement;
import java.io.*;
import java.text.ParseException;
import java.util.Date;

/**This class outputs log-in details to a text document. */
public class TextLoginFile {

    /**This is the method that creates and appends the text document.
     @param string */
    public static void newFileRecord(String string) throws IOException, ParseException {
        Date date = new Date();

        BufferedWriter bw = new BufferedWriter(
                new FileWriter("login_activity.txt", true));

        bw.write("UTC TIME: " + DateTimeManagement.getUtcDateTime() + " | Log-in User: "
                + string + "\n");

        bw.close();
    }

}
