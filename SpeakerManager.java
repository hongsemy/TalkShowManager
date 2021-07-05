package use_case;

import entity.Speaker;
import entity.User;

import java.io.Serializable;

public class SpeakerManager implements Serializable{
    /**
     * Represents the UserManager that manages this Organizer
     */
    UserManager userManager ;

    /**
     * Represents the SpeakerManager that manages this Speaker.
     */
    public SpeakerManager(UserManager um){
        userManager = um;
    }


    /**
     * Evaluates whether or not the specified speaker already exists.
     * @param name The name of the Speaker that is being evaluated.
     * @return A boolean value. Returns true if the specified Speaker exists.
     */

    public boolean speakerExists(String name){
        for (User user: userManager.userList){
            if (user.getName().equals(name) && user instanceof Speaker){
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a new speaker with the specified name, id, and password.
     * @param name A String representing the name of the new Speaker.
     * @param id A String representing the id of the new Speaker.
     * @param pw A String representing the password of the new Speaker.
     * @return A boolean value -- This is set to be always true in this version.
     */
    public boolean newSpeaker(String name, String id, String pw){
        Speaker speaker = new Speaker(name, id, pw);
        userManager.userList.add(speaker);
        return true;
    }

    /**
     * Gets the speaker with the specified name.
     * @param name A String representing the name of the target Speaker.
     * @return A Speaker object whose name is specified.
     */
    public Speaker speakerNamed(String name){
        for (User user: userManager.userList){
            if (user.getName().equals(name) && user instanceof Speaker){
                return (Speaker) user;
            }
        }
        return null;
    }

    /**
     * Add the specified event to the talk list of the specified Speaker.
     * @param speakerName A String representing the name of the Speaker.
     * @param event A String representing the name of the Event.
     * @return A boolean value -- This is set to be always true in this version.
     */
    // Update speaker's talk list.
    public boolean updateTalkList(String speakerName, String event){
        Speaker speaker = speakerNamed(speakerName);
        userManager.addToUserEventList(event, speaker);
        return true;
    }

    /**
     * Remove the specified event from the talk list of the specified Speaker.
     * @param speakerName A String representing the name of the Speaker.
     * @param eventName A String representing the name of the Event.
     * @return A boolean value -- This is set to be always true in this version.
     */
    public boolean removeListTalks(String speakerName, String eventName){
        Speaker speaker = speakerNamed(speakerName);
        userManager.removeFromUserEventList(eventName, speaker);
        return true;
    }






}
