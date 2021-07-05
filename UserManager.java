package use_case;

import entity.Attendee;
import entity.Organizer;
import entity.Speaker;
import entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements Serializable {

    /**
     * Represents the list of all Users in this program.
     */
    List<User> userList;
    /**
     * Represents the User who is currently logged in to this program.
     */
    private User currentUser;

    /**
     * Creates a new UserManager.
     */
    public UserManager(){
        this.userList = new ArrayList<>();
    }
    // getter for userList

    /**
     * Gets the list of all Users in this program.
     * @return A list of User objects.
     */
    public List<User> getUserList(){
        return userList;
    }

    /**
     * Adds the specified user to the program.
     * @param user An user object.
     */
    public void addUserList(User user){

        userList.add(user);
    }

    /**
     * Sets the current User to the specified User.
     * @param id A String representing the id of the specified User.
     * @return A boolean value -- This is set to be always true in this version.
     */
    public boolean setCurrentUser(String id) {
        this.currentUser = userIded(id);
        return true;
    }

    /**
     * Gets the id of the current USer.
     * @return A String representing the id of the current User.
     */
    public String getCurrentUserId() {
        if(currentUser == null){
            return "";
        }
        return currentUser.getId();
    }

    public String getCurrentUserName(){
        return currentUser.getName();
    }

    /**
     * Cancels an Event and updates all users' event lists.
     * @param event A String representing the name of the Event that is being cancelled.
     * @return A boolean value -- This is always set to be true in this version.
     */
    // Update everyone's list
    public boolean cancelEvent(String event){
        for(User user: userList){
            if (user.getEventList().contains(event)){
                user.removeFromEvents(event);
            }
        }
        return true;
    }

    /**
     * Gets the list of all Events that the specified User is involved in.
     * @param user A String representing the name of the specified User.
     * @return A list of Strings representing the names of the Events that the specified User is involved in.
     */
    public List<String> getUserEvents(String user){
        for (User u: userList){
            if(u.getId().equals(user)){
                return u.getEventList();
            }
        }
        return null;
    }


    /**
     * Gets the list of Events that the current User is involved in.
     * @return A list of Strings representing the names of Events that the current User is involved in.
     */
    public List<String> getCurrentUserEvents() {
        return currentUser.getEventList();
    }



    /**
     * Adds the specified Event to the list of all Events.
     * @param event A String representing the name of the newly added Event.
     * @return A boolean value -- This is set to be always true in this version.
     */
    public boolean addToEventList(String event) {
        currentUser.addToEvents(event);
        return true;
    }

    /**
     * Adds the specified User to the specified Event.
     * @param event A String representing the name of the Event.
     * @param user A User object.
     * @return A boolean value -- This is set to be always true in this version.
     */
    public boolean addToUserEventList(String event, User user){
        user.addToEvents(event);
        return true;
    }

    /**
     * Removes the specified Event from the list of the Events that the specified User is involved in.
     * @param event A String representing the name of the Event.
     * @param user A User object.
     * @return A boolean value -- This is set to be always true in this version.
     */
    public boolean removeFromUserEventList(String event, User user){
        user.removeFromEvents(event);
        return true;
    }

    /**
     * Removes the specified Event from the list of all Events
     * @param event A String representing the name of the Event.
     */
    public boolean removeFromEventList(String event) {
        currentUser.removeFromEvents(event);
        return true;
    }


    /**
     * Evaluates whether or not the specified user id is unique.
     * @param s A String representing the id that is being evaluated.
     * @return A boolean value. Returns true when the specified id is unique.
     */
    // Check if a new user's input ID is unique.
    // FIXME: used by all managers
    public boolean isUniqueId(String s){
        for (User user : userList) {
            if (user.getId().equals(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Whether or not the specified id and password are a valid authentication set to the program.
     * @param id A String representing the id.
     * @param pw A String representing the password.
     * @return A boolean value. Returns true when the id+password set is successfully authenticated.
     */
    public boolean authenticate(String id, String pw){
        for (User user: userList){
            if(user.getId().equals(id) && user.getPw().equals(pw)){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of friends of the specified user.
     * @param user A String representing the User's name.
     * @return A list of Strings representing the names of the specified User's friends.
     */
    public List<String> getFriends(String user) {
        User object = userIded(user);
        List<String> newList = new ArrayList<>();
        List<User> list = object.getFriends();
        for (User u : list) {
            newList.add(u.getId());
        }
        return newList;
    }


    /**
     * Remove a user from a user's friend list.
     * @param option Integer choice representing who to remove from the friend list.
     * @param user a string representing a user's id who wants to modify the friend list.
     */
    public boolean removeFriend(int option, String user) {
        if (option < 1 || option >= getFriends(user).size()) {
            return false;
        } else {
            User wishToRemove = userIded(getFriends(user).get(option-1));
            userIded(user).removeFromFriends(wishToRemove);
            return true;
        }
    }

    /**
     * Convert from user object to user name.
     * @param user User object who the program wants to know the name of.
     * @return a string name of the user.
     */
    private String userNameIs(User user){

        return user.getName();
    }


    /**
     * Get the type of the user by knowing one's id.
     * @param userId a string representing user's ID who we want to know the type of.
     * @return a string representing the type of the user.
     */
    public String getUserType(String userId){
        User user = userIded(userId);
        if (user instanceof Organizer) {
            return "organizer";
        } if (user instanceof Attendee) {
            return "attendee";
        } else {return "speaker";}
    }


    /**
     * Get the object of the user by knowing one's id.
     * @param userId a string representing user's ID who we want to know the type of.
     * @return a User object that corresponds to the string ID.
     */
    public User userIded(String userId){
        for(User user: userList){
            if(user.getId().equals(userId)){
                return user;
            }
        }
        return null;
    }


    /**
     * Get the ID of the user by knowing one's name.
     * @param userName a string representing user's name who we want to know the ID of.
     * @return a string representing the ID of the user.
     */
    public String userNameToId(String userName){
        for(User user: userList){
            if(user.getName().equals(userName)){
                return user.getId();
            }
        }
        return null;
    }


    /**
     * Check if a user exists or not by knowing his/her ID.
     * @param friendId a string representing user's ID who we want to know if they exist.
     * @return true iff the user with friendId exists in the system.
     */
    public boolean isExistingUser(String friendId) {
        User friend = userIded(friendId);
        for(User user: userList){
            if (user == friend) {
                return true;
            }
        }
        return false;
    }


    /**
     * Get the name of the user by knowing one's ID.
     * @param userId a string representing user's ID who we want to know the name of.
     * @return a string representing the name of the user.
     */
    public String userIdToName(String userId){

        return userNameIs(userIded(userId));
    }


    /**
     * Add a user as a friend.
     * @param userId a string representing user's ID who wants to add someone as a friend.
     * @param friendId a string representing friend's ID who the user wants to add as a friend.
     * @return true iff a user with that friendId exists and it not yet friended.
     */
    public boolean addFriend(String userId, String friendId){
        User user = userIded(userId);
        User newFriend = userIded(friendId);
        if (!user.getFriends().contains(newFriend) && isExistingUser(friendId)){
            user.addToFriends(newFriend);
            return true;
        }
        return false;
    }


    /**
     * Check if the currentUser is a speaker or not.
     * @return true iff the currentUser is a speaker.
     */
    public boolean isCurrentUserSpeaker() {return currentUser instanceof Speaker;}

    /**
     * gets the list of id that the current user selected to archive
     * @return an arraylist of strings representing the receiver ids that was archived by the current user
     */
    public List<String> getArchivedList() {return currentUser.getArchiveList();}

    /**
     * Add an id of a recipient to the list of archived ids that the current user selected.
     * @param archive A string representing the id of the archived user archived by the current user.
     */
    public boolean addToArchived (String archive){
        currentUser.addToArchived(archive);
        return true;
    }




    /**
     * Serialize all users.
     */
    public void serialize() {
        try {
            FileOutputStream fos = new FileOutputStream("phase2/ser_files/User.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(getUserList());
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    /**
     * Deserialize all users from "User.ser" file.
     */
    public void deserialize(){

        ArrayList<User> users = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("phase2/ser_files/User.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            users = (ArrayList<User>) ois.readObject();

            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
        }

        for (User user: users) {
            System.out.println(user);
        }

        for (User user: users){
            addUserList(user);
        }
    }





}
