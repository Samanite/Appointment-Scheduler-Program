package sample.model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.Collections;

/**This class holds the shared data for the program. This includes data lists and a few methods for comparing them.*/
public class Data {

//Data array lists
    private static ObservableList<Customer> customerArrayList = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentArrayList = FXCollections.observableArrayList();
    private static ObservableList<User> usersArrayList = FXCollections.observableArrayList();
    private static ObservableList<Contacts> contactsArrayList = FXCollections.observableArrayList();
    private static ObservableList<Country> countryArrayList = FXCollections.observableArrayList();
    private static  ObservableList<FirstLevelDivision> firstLevelDivArrayList = FXCollections.observableArrayList();
    private static boolean loggedIn = false;

    /**This is the sort appointment by date method. Sorts the appointments by date. */
    public static void sortApptByDate(){
        Collections.sort(appointmentArrayList);
    }

    /**Clear the country list method. */
    public static void clearCountryList(){
        countryArrayList.clear();
    }
    /**Clear the first level division list method. */
    public static void clearFirstLevList(){
        firstLevelDivArrayList.clear();
    }
    /**Clear the customer list method. */
    public static void clearCustomerList(){
        customerArrayList.clear();
    }
    /**Clear the appointment list method. */
    public static void clearAppointmentList(){
        appointmentArrayList.clear();
    }
    /**Clear the user list method. */
    public static void clearUserList(){
        usersArrayList.clear();
    }
    /**Clear the contact list method. */
    public static void clearContactsList(){
        contactsArrayList.clear();
    }

    /**Add to the customer list.
     @param customer */
    public static void addCustomerArrayObject(Customer customer){ customerArrayList.add(customer); }
    /**Add to the appointment list.
     @param appointment */
    public static void addAppointmentArrayObject(Appointment appointment){ appointmentArrayList.add(appointment); }
    /**Add to the user list.
     @param user */
    public static void addUserArrayObject(User user){ usersArrayList.add(user); }
    /**Add to the contact list.
     @param contact */
    public static void addContactArrayObject(Contacts contact){ contactsArrayList.add(contact); }
    /**Add to the country list.
     @param country */
    public static void addCountryArrayObject(Country country){countryArrayList.add(country);}
    /**Add to the first level division list.
     @param level */
    public static void addFirstLevObject(FirstLevelDivision level){firstLevelDivArrayList.add(level);}

    /**Gets whole customer list.
     @return customerArrayList*/
    public static ObservableList<Customer> getCustomerArrayList() { return customerArrayList; }
    /**Gets whole appointment list.
     @return appointmentArrayList*/
    public static ObservableList<Appointment> getAppointmentArrayList() { return appointmentArrayList; }
    /**Gets whole users list.
     @return usersArrayList*/
    public static ObservableList<User> getUsersArrayList() { return usersArrayList; }
    /**Gets whole contacts list.
     @return contactsArrayList*/
    public static ObservableList<Contacts> getContactsArrayList() { return contactsArrayList; }
    /**Gets whole country list.
     @return countryArrayList*/
    public static ObservableList<Country> getCountryArrayList() {return countryArrayList; }
    /**Gets whole first level division list.
     @return firstLevelDivisionArrayList*/
    public static ObservableList<FirstLevelDivision> getFirstLevelDivArrayList() {return firstLevelDivArrayList; }

    /**Gets first level division object.
     @param index
     @return firstLevelDivArrayList.get(i)*/
    public static FirstLevelDivision getFirstLevelDivision (int index){
        return firstLevelDivArrayList.get(index);
    }
    /**Gets country object.
     @param index
     @return countryArrayList.get(i)*/
    public static Country getCountry(int index){
        return countryArrayList.get(index);
    }
    /**Gets appointment object.
     @param index
     @return appointmentArrayList.get(i)*/
    public static Appointment getAppointment(int index){
        return appointmentArrayList.get(index);
    }
    /**Gets customer object.
     @param index
     @return customerArrayList.get(i)*/
    public static Customer getCustomer(int index){
        return customerArrayList.get(index);
    }
    /**Gets user object.
     @param index
     @return usersArrayList.get(i)*/
    public static User getUser(int index) { return usersArrayList.get(index); }
    /**Gets contact object.
     @param index
     @return contactsArrayList.get(i)*/
    public static Contacts getContact(int index) {return contactsArrayList.get(index); }

    /**Sets customer object.
     @param customer
     @param index */
    public static void setCustomerObject(int index, Customer customer){ customerArrayList.set(index, customer); }
    /**Sets appointment object.
     @param appointment
     @param index */
    public static void setAppointmentObject(int index, Appointment appointment){ appointmentArrayList.set(index, appointment); }
    /**Sets user object.
     @param user
     @param index */
    public static void setUserObject(int index, User user) { usersArrayList.set(index, user); }
    /**Sets contact object.
     @param contact
     @param index */
    public static void setContactsObject(int index, Contacts contact) { contactsArrayList.set(index, contact); }

    /**Deletes a customer object.
     @param index */
    public static void deleteCustomerObject(int index){ customerArrayList.remove(index); }
    /**Deletes a appointment object.
     @param index */
    public static void deleteAppointmentObject(int index){ appointmentArrayList.remove(index); }
    /**Deletes a user object.
     @param index */
    public static void deleteUserObject(int index) {usersArrayList.remove(index); }
    /**Deletes a contact object.
     @param index */
    public static void deleteContactsObject(int index) {contactsArrayList.remove(index); }

    /**This method returns a division id from a customer name. */
    public static int getDivisionId(String name) {
        int x = 0;
        for(int i = 0; i < getFirstLevelDivArrayList().size(); i++){
            if(name.equals(getFirstLevelDivision(i).getDivision())){
                x = getFirstLevelDivision(i).getId();
            }
        }
        return x;
    }

    /**This method returns a Country id from a division id. */
    public static int getCountryIdFromDivId(int id) {
        int x =0;
        for(int i = 0; i < getFirstLevelDivArrayList().size(); i++){
            if(id == getFirstLevelDivision(i).getId()){
                x = getFirstLevelDivision(i).getCountryId();
            }
        }
        return x;
    }

    /**This method returns a division name from a division id. */
    public static String getDivNameFromId(int id) {
        String x = "";
        for(int i = 0; i < getFirstLevelDivArrayList().size(); i++) {
            if(id == getFirstLevelDivision(i).getId()){
                x = getFirstLevelDivision(i).getDivision();
            }
        }
        return x;
    }

    /**This method returns an Appointment from a Customer id. */
    public static void getAppointmentByCusId(int id, ObservableList filteredAppointments) {
        for(Appointment appointment: appointmentArrayList) {
            if(appointment.getCustomerId() == id){
                filteredAppointments.add(appointment);
            }
        }
    }

    /**Sets user log-in status. */
    public static void setLoggedIn(){ loggedIn = true; }
    /**Gets user logged-in status.
     @return loggedIn*/
    public static boolean getLoggedIn(){return loggedIn; }
}

