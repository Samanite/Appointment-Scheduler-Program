package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

/**This class manages Date and Time within the application. Most Date and time is managed within this class,
  but some time conversion is done in the Appointment query methods.*/
public class DateTimeManagement {

    //Variables for setting appointment times
    private static ObservableList<String> hours = FXCollections.observableArrayList(Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));
    private static ObservableList<String> minutes = FXCollections.observableArrayList(Arrays.asList(":00", ":15", ":30", ":45"));
    private static ObservableList<Integer> minutesAsIntegers = FXCollections.observableArrayList(Arrays.asList(0, 15, 30, 45));

    //get time lists
    public static ObservableList<String> getHours() {
        return hours;
    }
    public static ObservableList<String> getMinutes() {
        return minutes;
    }
    public static ObservableList<Integer> getMinutesAsIntegers() {return minutesAsIntegers; }

    //get object from lists
    public static String getHourObject(int index) { return hours.get(index); }
    public static String getMinuteObject(int index) {return minutes.get(index); }
    public static Integer getMinuteAsIntObject(int index) {return minutesAsIntegers.get(index); }

    /**This is the date to a local date conversion method. */
    public static LocalDate dateToLocalDate(Date date){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();

        return localDate;
    }

    /**This is the local-date-time to Date without time method.*/
    public static Date localDateTimeToYrMoDayDate(LocalDateTime localDateTime) throws ParseException {
        Date date;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateHolder = "";
        dateHolder = dateTimeFormatter.format(localDateTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.parse(dateHolder);
        return date;
    }

    /**This is the local-date-time to Date (yyyy-MM only) method. */
    public static Date localDateTimeToYrMo(LocalDateTime localDateTime) throws ParseException {
        Date date;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String dateHolder = "";
        dateHolder = dateTimeFormatter.format(localDateTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        date = simpleDateFormat.parse(dateHolder);
        return date;
    }

    /**This is the local-date-time to Date conversion method. */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) throws ParseException {
        Date date;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateHolder = "";
        dateHolder = dateTimeFormatter.format(localDateTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = simpleDateFormat.parse(dateHolder);
        return date;
    }

    /**This is the 24hr to 12 hr method. This returns a String containing the hour. */
    public static String dateToHour(Date date){
        String hours = "";
        int h = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        hours = simpleDateFormat.format(date);

        if(Integer.parseInt(hours) > 12){
            h = Integer.parseInt(hours) - 12;
            hours = String.valueOf(h);
        }

        return hours;
    }

    /**This is the date to minutes method. This returns the minutes as a string.*/
    public static String dateToMinute(Date date){
        String minutes = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm");
        minutes = simpleDateFormat.format(date);
        //System.out.println(minutes);

        return minutes;
    }


    /**This is the am pm method. This method returns true if time is pm.*/
    public static boolean amFalsepmTrue(Date date){
        boolean pm = false;
        String hours = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        hours = simpleDateFormat.format(date);
        if(Integer.parseInt(hours) > 11){
            pm = true;
        }

        return pm;
    }

    /**This is the during business hours method. This converts local time to eastern standard
      time and checks if the appointment is during business hours.*/
    public static boolean isDuringBusiHours (String dateStart, String dateEnd) throws ParseException {
        boolean doorsOpen = false;

        DateFormat localTime = new SimpleDateFormat("HH:mm:ss");
        localTime.setTimeZone(TimeZone.getDefault());
        DateFormat est = new SimpleDateFormat("HH:mm:ss");
        est.setTimeZone(TimeZone.getTimeZone("EST"));

        Date startLocal = localTime.parse(dateStart);
        Date endLocal = localTime.parse(dateEnd);

        String startEst = est.format(startLocal);
        Date startEstDate = est.parse(startEst);
        String endEst = est.format(endLocal);
        Date endEstDate = est.parse(endEst);

        Date startBusiness = est.parse("08:00:00");
        Date endBusiness = est.parse("22:00:00");

        if(startBusiness.compareTo(startEstDate) <=0 && endBusiness.compareTo(endEstDate) >=0){
            doorsOpen = true;
        }

        return doorsOpen;
    }

    /**This is the get universal coordinated date and time method. This method gets the Date
      and returns the current UTC date and time.*/
    public static Date getUtcDateTime() throws ParseException {
        Date date = new Date();

        DateFormat localTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localTime.setTimeZone(TimeZone.getDefault());
        DateFormat utc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        utc.setTimeZone(TimeZone.getTimeZone("UTC"));

        String localStringDate = localTime.format(date);
        Date localDate = localTime.parse(localStringDate);

        String utcNowString = utc.format(localDate);
        Date utcNow = utc.parse(utcNowString);

        return utcNow;
    }

}
