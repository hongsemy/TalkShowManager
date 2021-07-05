package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user of the program
 */

public abstract class User implements Serializable {

    private final String name;
    private final String id;
    // password might be have to be stored elsewhere later in the phase.
    private final String pw;

    /**
     * Represents the list of the user's friends.
     */
    List<User> friends;

    //TODO: javadoc
    List<String> eventList;

    /**
     * Represents the list of archived receiver's id in string.
     */
    List<String> archivedId;

    /**
     * Creates a User with the specified name, id, and password.
     * @param name The name of the user.
     * @param id The id of the user.
     * @param pw The password of the user.
     */
    // Constructor is called when subclasses are instantiated, although user itself cannot be instantiated.
    public User(String name, String id, String pw){
        this.name = name;
        this.id = id;
        this.pw = pw;
        this.friends = new ArrayList<>();
        this.eventList = new ArrayList<>();
        this.archivedId = new ArrayList<>();
    }

    /**
     * Gets the name of this User.
     * @return A string representing the user's name.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets the id of this User.
     * @return A string representing the user's id.
     */
    public String getId(){
        return this.id;
    }

    /**
     * Gets the password of this User.
     * @return A string representing the user's password.
     */
    public String getPw(){
        return this.pw;
    }


    /**
     * Gets the list of Users that this User has added as a friend.
     * @return An ArrayList of user objects representing the user's friends.
     */
    public List<User> getFriends() {
        return this.friends;
    }

    /**
     * Adds the specified user object as a friend of this User.
     * @param user The user to be added as this User's friend.
     */
    public void addToFriends(User user){
        friends.add(user);
    }

    /**
     * Removes the specified user object from the list of this User's friends.
     * @param user The User to be removed from the list of this User's friends.
     */
    public void removeFromFriends(User user){
        friends.remove(user);
    }


    /**
     * Gets the list of events that this Attendee has signed up for.
     * @return An ArrayList of Strings representing the events that this Attendee has signed up for.
     */
    public List<String> getEventList(){
        return this.eventList;
    }

    /**
     * Add the specified event to the list of events that this Attendee has signed up for.
     * @param event A String representing the name of the event that the Attendee has newly signed up for.
     */
    public void addToEvents(String event){
        eventList.add(event);
    }

    /**
     * Remove the specified event from the list of events that this Attendee has signed up for.
     * @param event A String representing the name of the event that the Attendee is no longer attending.
     */
    public void removeFromEvents(String event){
        eventList.remove(event);
    }

    /**
     * gets the list of id that this user selected to archive
     * @return an arraylist of strings representing the receiver ids that was archived
     */
    public List<String> getArchiveList() {return this.archivedId;}

    /**
     * Add an id of a recipient to the list of archived ids that user selected.
     * @param archive A string representing the id of the archived user.
     */
    public void addToArchived(String archive){ archivedId.add(archive);}


}
