package sample.model;

/**This class defines a Contact object. Contacts are simple ID, Name, and Email. */
public class Contacts {
    int id;
    String name;
    String email;

    /**This is the default constructor. */
    public Contacts() {}

    /**This is the constructor with all arguments. This sets all three fields.*/
    public Contacts(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
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

    /**Gets email.
     @return email*/
    public String getEmail() {
        return email;
    }

    /**Sets email.
     @param email*/
    public void setEmail(String email) {
        this.email = email;
    }
}
