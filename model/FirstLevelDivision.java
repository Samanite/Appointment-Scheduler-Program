package sample.model;

/**This class defines a first level division object. A first level division holds an id, division name, and country id.*/
public class FirstLevelDivision {
    int id;
    String division;
    int countryId;

    /**This is the default constructor. */
    public FirstLevelDivision(){
    }

    /**This is the constructor with all arguments. This sets all fields.*/
    public FirstLevelDivision(int id, String division, int divId) {
        this.id = id;
        this.division = division;
        this.countryId = divId;
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

    /**Gets division.
     @return division*/
    public String getDivision() {
        return division;
    }

    /**Sets division.
     @param division */
    public void setDivision(String division) {
        this.division = division;
    }

    /**Gets country id.
     @return country id*/
    public int getCountryId() {
        return countryId;
    }

    /**Sets country id.
     @param id */
    public void setCountryId(int id) {
        this.countryId = id;
    }
}
