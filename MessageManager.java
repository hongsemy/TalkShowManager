package use_case;

import entity.Message;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class MessageManager implements Serializable {
    /**
     * Represents a list of all events happening at this conference.
     */
    private List<Message> allMessageList = new ArrayList<>();

    /**
     * Gets the chat room with the specified sender and receiver.
     * @param senderId A String representing the id of the sender of the target chat room.
     * @param receiverId A String representing the id of the receiver of the target chat room.
     */
    public Message findAChatRoom(String senderId, String receiverId){
        for(Message message: allMessageList){
            if(message.getUsers().contains(senderId) && message.getUsers().contains(receiverId)){
                return message;
            }
        }
        Message message = new Message(senderId, receiverId, allMessageList.size());
        allMessageList.add(message);
        return message;
    }

    /**
     * Gets the list of all users that the specified user has ever messaged.
     * @param user A String representing the name of the specified user.
     * @return A list of Strings representing the names of all users the specified user has ever messaged.
     */
    public List<String> getEveryOpponent(String user) {
        List<String> allOpponent = new ArrayList<>();
        for (Message message : allMessageList) {
            if (message.getUsers().get(0).equals(user)) {
                if (getMessageContent(user, message.getUsers().get(1)).size() > 0) {
                    allOpponent.add(message.getUsers().get(1));
                }

            }
            if (message.getUsers().get(1).equals(user)) {
                if (getMessageContent(user, message.getUsers().get(1)).size() > 0) {
                    allOpponent.add(message.getUsers().get(0));
                }
            }
        }
        return allOpponent;
    }

    /**
     * Gets the list of all contents shared in the chat room of the specified users.
     * @param userId1 A String representing the id of one user in the chat room.
     * @param userId2 A String representing the id of the other user in the chat room.
     * @return A list of Strings, each representing the contents of each Message shared in this chat room.
     */
    public List<String> getMessageContent(String userId1, String userId2) {
            return findAChatRoom(userId1, userId2).getMessageContents();
        }

    /**
     * Gets the list of all times at which the messages in the chat room of the specified users have been sent.
     * @param userId1 A String representing the id of one user in the chat room.
     * @param userId2 A String representing the id of the other user in the chat room.
     * @return A list of LocalDateTime objects, each representing the time at which each Message has been shared in this chat room.
     */
    public List<LocalDateTime> getMessageTime(String userId1, String userId2) {
        return findAChatRoom(userId1, userId2).getMessageTime();
    }

    /**
     * Gets the consecutive list of all senders of the messages in the chat room of the specified users.
     * @param userId1 A String representing the id of one user in the chat room.
     * @param userId2 A String representing the id of the other user in the chat room.
     * @return A list of Strings, each representing the id of the user who sent each Message in this chat room.
     */
    public List<String> getMessageFrom(String userId1, String userId2) {
        return findAChatRoom(userId1, userId2).getMessageFrom();
    }



    /**
     * Sends a private message saying the specified contents, from the specified sender to the specified receiver.
     * @param senderId A String representing the id of the user who is sending this private message.
     * @param receiverId A String representing the id of the user who is receiving this private message.
     * @param sentence A String representing the contents of this private message.
     */
    public void sendPrivateMessage(String senderId, String receiverId, String sentence){
        Message message = findAChatRoom(senderId, receiverId);

        LocalDateTime time = LocalDateTime.now();

        // update attributes in the message object.
        message.addMessageContents(sentence);
        message.addMessageTime(time);
        message.addMessageFrom(senderId);
        message.newUnreadMsg();
    }


    /**
     * Send an event message saying the specified announcement, from the specified sender to the specified list of receivers.
     * @param sender A String representing the id of the user who is sending this event message.
     * @param listPeople A list of Strings representing the ids of the users who are receiving this event message.
     * @param announcement A String representing the contents of this event message.
     */
    public void sendEventMessage(String sender, List<String> listPeople, String announcement){
        // Use sendPrivateMessage method and iterate over a for loop to send to everyone.
        for (String listPerson : listPeople) {
            sendPrivateMessage(sender, listPerson, announcement);
        }
    }

    /**
     * Gets the number of unread messages sent from the opponent in a chat.
     * @param currentId User that is logged in.
     * @param opponentId The opposing user in the chat.
     * @return Integer, the number of messages not read
     */
    public int getNumUnread(String currentId, String opponentId){
        Message message = findAChatRoom(currentId, opponentId);
        if (currentId.equals(message.getLastSenderId())) return 0;
        return message.getNumUnread();
    }

    /**
     * Mark all unread message as raed iff there was a new message from the opponent.
     * @param currentId User that is logged in.
     * @param opponentId The opposing user in the chat.
     */
    public void read(String currentId, String opponentId){
        Message message = findAChatRoom(currentId, opponentId);
        if (getNumUnread(currentId,opponentId)>0){
            message.readAll();
        }
    }

    /**
     * Replaces the content of the message with a new content in a chat at an index iff the message is sent by currentId.
     * @param currentId the user Id of currently logged in user
     * @param opponentId the Id of opponent in a chat
     * @param index index of message that is to be modified
     * @param newContent new string that will replace old content
     * @return Boolean true iff message content is successfully changed
     */
    public boolean changeContent(String currentId, String opponentId, int index, String newContent) {
        List<String> messageContent = getMessageContent(currentId, opponentId);
        List<String> messageFrom = getMessageFrom(currentId,opponentId);
        if (!messageFrom.get(index).equals(currentId)) return false;
        messageContent.set(index, newContent);
        return true;
    }

    /**
     * Serialize all messages.
     */
    public void serialize() {
        try {
            FileOutputStream fos = new FileOutputStream("phase2/ser_files/Message.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(allMessageList);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    /**
     * Deserialize all messages from "Message.ser" file.
     */
    public boolean deserialize(){

        ArrayList<Message> messages;
        try {
            FileInputStream fis = new FileInputStream("phase2/ser_files/Message.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            messages = (ArrayList<Message>) ois.readObject();

            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return false;
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return false;
        }

        allMessageList = messages;

        return true;

    }


}
