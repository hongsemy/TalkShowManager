package entity;


import java.io.Serializable;

/**
 * Represents a speaker of the conference. A speaker is a user of the program.
 */
public class Speaker extends User implements Serializable {

    /**
     * Creates a Speaker with the specified name, id, and password.
     * @param name A string representing the name of this Speaker.
     * @param id A String representing the id of this Speaker.
     * @param pw A String representing the password of this Speaker.
     */
    public Speaker(String name, String id, String pw) {
        super(name, id, pw);
    }
}

