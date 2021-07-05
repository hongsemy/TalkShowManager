package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a message stream shared between two Users of this program.
 */
public class Message implements Serializable {

    // Save the original sender as "user1" and the original receiver as "user2".
    private final String user1Id;
    private final String user2Id;
    private final int id;
    private int numUnread;

    // Save the content, time sent, and sender of each message in different ArrayList.
    // When we want to access to specific information of a message, use index to find it in these ArrayLists.
    /**
     * Represents the list of the the contents of each message in this Message stream.
     * The time and sender of each content in the list is identified by the corresponding location in messageTime and messageFrom.
     */
    List<String> messageContents;
    /**
     * Represents the list of the times at which each message was sent in this Message stream.
     */
    List<LocalDateTime> messageTime;
    /**
     * Represents the list of the senders of each message in this Message stream.
     */
    List<String> messageFrom;

    // A new object instantiated when a user sends a message for the first time to another user.
    // If there already is a Message object between two users, DO NOT instantiate a new object.
    // The constructor DOES NOT automatically save the first message. You have to add in Use Case.

    /**
     * Creates a new Message with the specified sender's id, receiver's id, and the id of itself.
     * @param senderId A String representing the id of the User who sent this Message.
     * @param receiverId A String representing the id of the User who is receiving this Message.
     * @param id A String representing the id of this Message.
     */
    public Message(String senderId, String receiverId, int id){
        this.user1Id = senderId;
        this.user2Id = receiverId;
        this.id = id;

        this.messageContents = new ArrayList<>();
        this.messageTime = new ArrayList<>();
        this.messageFrom= new ArrayList<>();
    }

    // getter for two users as an arraylist, user1 and user2 respectively.

    /**
     * Gets the list of Users who are involved in this message stream.
     * @return A list of Strings representing the ids of the users involved in this Message.
     */
    public List<String> getUsers(){
        List<String> userList = new ArrayList<>(2);
        userList.add(user1Id);
        userList.add(user2Id);
        return userList;
    }

    // getter for messageContent

    /**
     * Gets the contents of this message stream.
     * @return A list of Strings representing the contents of each message sent in this message stream.
     */
    public List<String> getMessageContents(){
        return messageContents;
    }

    // getter for messageTime

    /**
     * Gets the times of this message stream.
     * @return A list of LocalDateTime items representing the times at which each message in this message stream was sent.
     */
    public List<LocalDateTime> getMessageTime(){
        return messageTime;
    }

    // getter for messageFrom

    /**
     * Gets the senders of this message stream.
     * @return A list of Strings representing the sender's id of each message sent in this message stream.
     */
    public List<String> getMessageFrom(){
        return messageFrom;
    }

    /**
     * Gets the id of this message stream.
     * @return A String representing the id of this message stream.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Adds a new content to this message stream.
     * @param message A String representing the contents of the new message sent in this message stream.
     */
    public void addMessageContents(String message){
        messageContents.add(message);
    }

    /**
     * Adds a new time to this message stream.
     * @param time A LocalDateTime item representing the time at which the new message was sent in this message stream.
     */
    public void addMessageTime(LocalDateTime time){
        messageTime.add(time);
    }

    /**
     * Adds a new sender to this message stream.
     * @param user A String representing the id of the person who sent the new message in this message stream.
     */
    public void addMessageFrom(String user){
        messageFrom.add(user);
    }

    /**
     * get Id of the user that last sent a message
     * @return String UserId
     */
    public String getLastSenderId(){
        return messageFrom.get(messageFrom.size() - 1);
    }

    /**
     * get number of new messages
     * @return Integer number of new messages added to chat
     */
    public int getNumUnread() {
        return numUnread;
    }

    /**
     * increment numUnread by 1
     */
    public void newUnreadMsg(){
        numUnread += 1;
    }

    /**
     * set the newMsgCount to 0
     */
    public void readAll(){
        numUnread = 0;
    }
}
