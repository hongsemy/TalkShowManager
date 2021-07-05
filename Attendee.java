package entity;

import java.io.Serializable;

/**
 * Represents an attendee of the conference. An attendee is a user of the program.
 */
public class Attendee extends User implements Serializable {

    /**
     * Creates a new Attendee with the specified name, id, and password.
     * @param name The name of this Attendee.
     * @param id The id of this Attendee.
     * @param pw The password of this Attendee.
     */
    public Attendee(String name, String id, String pw){
        super(name, id, pw);
    }

    /**
     * Evaluate whether or not this Attendee has signed up for the specified event.
     * @param event A String representing the name of the event that is being evaluated
     * @return A boolean value, where a return value of True shows that this Attendee has signed up for the specified event.
     */
    public boolean isInAttendingEvents(String event) {
        return getEventList().contains(event);
    }
}
