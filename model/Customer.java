package sample.model;

/**This class defines a Customer object. A customer has an ID, Name, Address, Postal Code, Phone, and Division ID.*/
public class Customer {

    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionId;

    /**This is the default constructor. */
    public Customer(){
    }

    /**This is the constructor with all the arguments. This sets all the fields of a Customer.*/
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }

    /**Gets division id.
     @return divisionId*/
    public int getDivisionId() {
        return divisionId;
    }

    /**Sets division id.
     @param divisionId */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**Gets id.
     @return id*/
    public int getId() {
        return id;
    }

    /**Sets id.
     @param id */
    public void setId(int id) {
        this.id = id;
    }

    /**Gets name.
     @return name*/
    public String getName() {
        return name;
    }

    /**Sets name.
     @param name */
    public void setName(String name) {
        this.name = name;
    }

    /**Gets address.
     @return address */
    public String getAddress() { return address; }

    /**Sets address.
     @param address */
    public void setAddress(String address) {
        this.address = address;
    }

    /**Gets postal code.
     @return postalCode*/
    public String getPostalCode() {
        return postalCode;
    }

    /**Sets postal code.
     @param postalCode */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**Gets phone number.
     @return phone number*/
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**Sets phone number.
     @param phoneNumber*/
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
