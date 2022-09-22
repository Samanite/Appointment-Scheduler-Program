package sample.model;

/**This class defines a Country object. A country object is an ID and Country Name.*/
public class Country {
    int id;
    String country;

    /**This is the default constructor. */
    public Country(){
    }

    /**This is the constructor with all arguments. This sets all the fields.*/
    public Country(int id, String country) {
        this.id = id;
        this.country = country;
    }

    /**Gets id.
     @return id*/
    public int getId() {
        return id;
    }

    /**Sets id.
     @param id*/
    public void setId(int id) {
        this.id = id;
    }

    /**Gets country.
     @return country*/
    public String getCountry() {
        return country;
    }

    /**Sets country.
     @param country */
    public void setCountry(String country) {
        this.country = country;
    }
}
