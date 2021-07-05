package Presenter;

import use_case.*;

import java.util.ArrayList;
import java.util.List;

public class UserPresenter {

    EventManager eventManager;
    UserManager userManager;
    OrganizerManager organizerManager;
    AttendeeManager attendeeManager;
    SpeakerManager speakerManager;
    MessageManager messageManager;

    public UserPresenter(EventManager em, UserManager um, OrganizerManager om, AttendeeManager am, SpeakerManager sm, MessageManager mm){
        eventManager = em;
        userManager = um;
        organizerManager = om;
        attendeeManager = am;
        speakerManager = sm;
        messageManager = mm;
    }

    /**
     * Display all users in an event.
     * @param eventName a string event name to show all users in.
     */
    public String displayAllUserInEvent(String eventName){
        List<String> al = eventManager.getAttendeesOfEvent(eventName);
        List<String> sl = eventManager.getSpeakersOfEvent(eventName);
        StringBuilder format = new StringBuilder();
        format.append("<organizer>").append("\n");
        format.append(eventManager.getOrganizer(eventName)).append("\n \n");
        format.append("<speakers>").append("\n");
        for (String speaker: sl){
            format.append(speaker).append("\n");
        }
        format.append("<attendees>").append("\n");
        for (String attendee: al){
            format.append(attendee).append("\n");
        }
        return format.toString();
    }

    /**
     * Display "who to add as a friend.".
     */
    public String whoToAddDisplay(){
        return "Choose who to add as a friend (id)";
    }

    /**
     * Display that a user is successfully added as a friend.
     */
    public String successfullyAddedFriend(String name){
        return name + " is successfully added as your friend!";
    }

    /**
     * Display "Who would you like to see?".
     */
    public String whoToSeeDisplay(){
        return "Who would you like to see?";
    }

    /**
     * Display "(Press enter to proceed without adding anyone)".
     */
    public String proceedNoAddDisplay(){
        return "(Press enter to proceed without adding anyone)";
    }

    /**
     * Display "(Press enter to go back to main menu)".
     */
    public String proceedEnterDisplay(){
        return "(Press enter to go back to main menu)";
    }

    /**
     * Tell the user that they cannot add themself as a friend.
     */
    public String cannotAddSelfDisplay(){
        return "Cannot add yourself as a friend!";
    }

    /**
     * Display all friends to a user.
     * @param user id of the user who wants to display one's friends.
     */
    public String displayFriends(String user){
        StringBuilder returnString = new StringBuilder();
        //returnString.append("Who would you like to see? \n");
        List<String> friendList = userManager.getFriends(user);
        if (friendList.isEmpty()) {
            return "There is no friends to display.";
        }
        for(int i = 0; i < friendList.size(); i ++){
            returnString.append(i+1).append(": ").append(friendList.get(i)).append("\n");
        }
        return returnString.toString();
    }

    /**
     * Show all enrolled/managing/talking events of the current user.
     */
    public String showMyEvents(){
        List<String> events = userManager.getCurrentUserEvents() ;
        StringBuilder format = new StringBuilder();
        for(int i = 1; i <= events.size(); i++){
            format.append(i).append(": ").append(events.get(i-1)).append("\n");
        }
        return format.toString();
    }

    /**
     * Show all enrolled/managing/talking events of a user.
     * @param user id of a user to show related events of.
     */
    public String displayUsersEvents(String user){
        List<String> list;
        list = userManager.getUserEvents(user);
        StringBuilder format = new StringBuilder();
        for(int i = 1; i <= list.size(); i++){
            format.append(i).append(": ").append(list.get(i-1)).append("\n");
        }
        return format.toString();
    }

    /**
     * display string message indicating what users should input for each steps and instruct users.
     * @param choice a string representing which line to be returned.
     * @return a string that instruct users on messaging processes.
     */
    public String messageDisplay(String choice){
        if (choice.equals("receiverId")) return "Recipient ID: ";
        if (choice.equals("userChoice")) return "Whose chat do you want to see?: ";
        if (choice.equals("invalidUserId")) return "Invalid User Id";
        if (choice.equals("messageContent")) return "Message: ";
        if (choice.equals("messageSent")) return "Message sent";
        if (choice.equals("bulkMessageSent")) return "Bulk Message complete";
        if (choice.equals("messageNotSent")) return "Message not sent";
        if (choice.equals("receiverType")) return "Select Receiver Type: 1. Attendee, 2. Speaker, 3. Both";
        if (choice.equals("invalidReceiverType")) return "Invalid Receiver Type";
        if (choice.equals("eventNameList")) return "Select Event(s) separated by spaces (e.g '1' or '1 3 15'): ";
        if (choice.equals ("eventName")) return "Enter event number: ";
        if (choice.equals ("invalidEventChoice")) return "Selected event(s) does not exist.";
        if (choice.equals ("noChat")) return "No chats found.";
        if (choice.equals ("archived")) return "Chat archived successfully.";
        if (choice.equals ("privateMessageDisplay")) return
                "1. Message by Receiver ID\n" +
                "2. Search for User by Event\n" +
                "3. Go back to message menu";
        if (choice.equals ("allChatOptions")) return
                "1: Enter new chat\n" +
                        "2: Delete last sent message\n" +
                        "3: Archive chat";
        if (choice.equals ("deleted")) return "Message has been deleted";
        if (choice.equals("deletedMessage")) return "[deleted message]";
        if (choice.equals("deleteFailed")) return "Opponent's message cannot be deleted.";
        return "messageDisplay: Invalid Input Error";
    }


    /**
     * display all messages of the current user.
     * @param currentUserId a string representing current user's id.
     * @param opponentId a string representing opponent's id.
     * @return a string that lists all messages sent to an opponent.
     */
    public String displayAllMessage(String currentUserId, String opponentId) {
//        Need to get all Message.messageContent. Then print tab if message.messageFrom.equals(currentUserId)
        List<String> messageFrom = messageManager.getMessageFrom(currentUserId, opponentId);
        List<String> messageContent = messageManager.getMessageContent(currentUserId, opponentId);
        StringBuilder chatView = new StringBuilder();
        for(int i = 0; i < messageContent.size(); i++){
            if (!messageFrom.get(i).equals(opponentId)) {
                chatView.append("    ");
            }
            chatView.append(messageContent.get(i));
            chatView.append("\n");
        }
        return chatView.toString();
    }

    /**
     * Display list of opponent id of current user.
     * @param chatList a string type list that represents list of id
     * @return a list of string representing id.
     */
    public List<String> chatListDisplay(List<String> chatList){
        String currentId = userManager.getCurrentUserId();
        List<String> newChatList = new ArrayList<>();
        for (String userId : chatList) {
            int numUnread = messageManager.getNumUnread(currentId,userId);
            if (numUnread == 0) {
                newChatList.add(userId);
            } else {
                String s = String.format("%s(%s)", userId, messageManager.getNumUnread(currentId, userId));
                newChatList.add(s);
            }
        }
        return newChatList;
    }

    /**
     * Display "Non-Integer input received. Returning to my friend menu. ".
     */
    public String nonIntegerInputDisplay() {
        return "Non-Integer input received. Returning to my friend menu. ";
    }

    public String chooseEventInstruction(){
        StringBuilder instruction = new StringBuilder();
        instruction.append("Please choose from the following options which event you would like to manage. \n");
        instruction.append("(Type 0 to go to the previous menu.)");
        return instruction.toString();
    }

    public String noEventToShow() {
        return "No event to show. \n";
    }

    public String seeScheduleDisplay(){
        String optionOne = "1: View schedule by day";
        String optionTwo = "2: See my upcoming events schedule";
        String optionThree = "3: View schedule by speaker";
        String optionFour = "4: Go back to the main menu";
        String optionFive = "5: Logout";

        return optionOne + "\n" + optionTwo + "\n" + optionThree + "\n" + optionFour + "\n" + optionFive;
    }
}
