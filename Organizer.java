package entity;

import java.io.Serializable;

/**
 * Represents an organizer of the conference. An organizer is a user of the program.
 */
public class Organizer extends User implements Serializable {


    /**
     * Creates a new Organizer with the specified name, id, and password.
     * @param name The name of this Organizer.
     * @param id The id of this Organizer.
     * @param pw The password of this Organizer.
     */
    public Organizer(String name, String id, String pw){
        super(name, id, pw);
    }

}
