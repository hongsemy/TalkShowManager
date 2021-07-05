package use_case;

import entity.Organizer;

import java.io.Serializable;

public class OrganizerManager implements Serializable{
    /**
     * Represents the UserManager that manages this Organizer
     */
    UserManager userManager;
    /**
     * Represents the OrganizerManager that manages this Organizer.
     */
    public OrganizerManager(UserManager um){
        userManager = um;
    }

    /**
     * Instantiate a new Organizer with given name, id, and pw. (This will be called by Gateway).
     */
    public boolean newOrganizer(String name, String id, String pw){
        Organizer organizer = new Organizer(name, id, pw);
        userManager.addUserList(organizer);
        return true;
    }




}
