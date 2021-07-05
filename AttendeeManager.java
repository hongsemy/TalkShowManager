package use_case;

import entity.Attendee;

import java.io.Serializable;

public class AttendeeManager implements Serializable{

//    private Attendee currentAttendee;
    /**
     * Represents the UserManager that manages this Attendee
     */
    UserManager userManager;

    /**
     * Represents the AttendeeManager that manages this Attendee.
     */
    public AttendeeManager(UserManager um) {
        userManager = um;
    }

    /**
     * Creates a new Attendee with the specified name, id, password, and adds new Attendee to userList.
     * @param name The name of this Attendee.
     * @param id The id of this Attendee.
     * @param pw The password of this Attendee.
     */

    public boolean newAttendee(String name, String id, String pw) {
        Attendee attendee = new Attendee(name, id, pw);
        userManager.userList.add(attendee);
        return true;
    }

    /**
     * Checks to see if Attendee is enrolled in specified event.
     * @param event The name of the event.
     */
    public boolean isEnrolled(String event) {
        Attendee attendee = (Attendee) userManager.userIded(userManager.getCurrentUserId());
        return attendee.isInAttendingEvents(event);
    }





}