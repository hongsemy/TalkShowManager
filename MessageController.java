package controller;

import Presenter.InitialPresenter;
import Presenter.UserPresenter;
import entity.Message;
import use_case.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// If you correctly implement this, you should NOT import any entity.

public class MessageController {

    UserController userController;
    EventManager eventManager;
    UserManager userManager;
    MessageManager messageManager;
    AttendeeManager attendeeManager;
    UserPresenter userPresenter;
    InitialPresenter initialPresenter;
    SpeakerManager speakerManager;
    OrganizerManager organizerManager;

    public MessageController(EventManager em, UserManager um, MessageManager mm, AttendeeManager am,
                             UserPresenter up, InitialPresenter ip, SpeakerManager sm, OrganizerManager om, UserController uc) {
        userController = uc;
        eventManager = em;
        userManager = um;
        messageManager = mm;
        attendeeManager = am;
        userPresenter = up;
        initialPresenter = ip;
        speakerManager = sm;
        organizerManager = om;
    }


    /**
     * sending private message to a chosen receiver.
     *
     * @param receiverId a string representing receiver's id.
     * @return Return True if message successfully sent, Return False if user cancelled sending
     */
    public boolean sendPrivateMessage(String receiverId) {
        String senderId = userManager.getCurrentUserId();
        Scanner sc = new Scanner(System.in);
        if (receiverId.isEmpty()) {
            System.out.println(userPresenter.messageDisplay("messageNotSent"));
            return false;
        }
//        if (!userController.isValidUserId(receiverId)) return sendPrivateMessage();
        System.out.print(userPresenter.messageDisplay("messageContent"));
        String messageContent = sc.nextLine();
        if (messageContent.isEmpty()) {
            System.out.println(userPresenter.messageDisplay("messageNotSent"));
            return false;
        }
        messageManager.sendPrivateMessage(senderId, receiverId, messageContent);
        System.out.println(userPresenter.messageDisplay("messageSent"));
        return true;
    }

    /**
     * sending private message by getting receiver id input from the user.
     *
     * @return a boolean value by calling sendPrivateMessage() method.
     */
    // OverLoad
    public boolean sendPrivateMessage() {
        Scanner sc = new Scanner(System.in);
        System.out.print(userPresenter.messageDisplay("receiverId"));
        String receiverId = sc.nextLine();
        return sendPrivateMessage(receiverId);
    }


    /**
     * Send event message to users related to an event.
     *
     * @param eventName      a string representing an event selected by the user.
     * @param receiverType   a string representing the type of user to send messages to.
     * @param messageContent a string representing the content of the message.
     */
    public void sendEventMessage(String eventName, String receiverType, String messageContent) {
        String senderId = userManager.getCurrentUserId();
        switch (receiverType) {
            case "1":
                messageManager.sendEventMessage(senderId, eventManager.getAttendeesOfEvent(eventName), messageContent);
                break;
            case "2":
                messageManager.sendEventMessage(senderId, eventManager.getSpeakersOfEvent(eventName), messageContent);
                break;
            case "3":
                messageManager.sendEventMessage(senderId, eventManager.getAttendeesOfEvent(eventName), messageContent);
                messageManager.sendEventMessage(senderId, eventManager.getSpeakersOfEvent(eventName), messageContent);
                break;
        }
        System.out.println(eventName + ": " + userPresenter.messageDisplay("messageSent"));
    }

    /**
     * send bulk announcement to attendees, speaker, or both.
     *
     * @param eventNameList an ArrayList representing the name of an event that
     *                      require announcement message to be sent.
     * @param receiverType  a string representing the type of user to send messages to.
     * @return a boolean value "true"
     */
    public boolean sendBulkMessage(List<String> eventNameList, String receiverType) {
        Scanner sc = new Scanner(System.in);
        if (receiverType.isEmpty()) {
            System.out.println(userPresenter.messageDisplay("messageNotSent"));
            return false;
        }
        if (!receiverType.matches("[123]")) {
            System.out.println(userPresenter.messageDisplay("invalidReceiverType"));
            return sendBulkMessage(eventNameList);
        }
        System.out.print(userPresenter.messageDisplay("messageContent"));
        String messageContent = sc.nextLine();
        if (messageContent.isEmpty()) {
            System.out.println(userPresenter.messageDisplay("messageNotSent"));
            return false;
        }
        for (String eventName : eventNameList) {
            sendEventMessage(eventName, receiverType, messageContent);
        }
        System.out.println(userPresenter.messageDisplay("messageSent"));
        return true;
    }

    /**
     * send bulk announcement to attendees, speaker, or both.
     *
     * @param eventNameList an ArrayList representing the name of an event that require announcement message to be sent.
     * @return a boolean value "true"
     */
    // OverLoad
    public boolean sendBulkMessage(List<String> eventNameList) {
        Scanner sc = new Scanner(System.in);
        System.out.println(userPresenter.messageDisplay("receiverType"));
        String receiverType = sc.nextLine();
        return sendBulkMessage(eventNameList, receiverType);
    }

    /**
     * display all existing chats of the user and send message successfully.
     *
     * @return False if input is empty or no chat is found. True if message is sent
     */
    public boolean allChatMessage() {
        Scanner sc = new Scanner(System.in);
        String currentUserId = userManager.getCurrentUserId();

        List<String> chatList = chatListRemoveArchived(currentUserId);
        if (chatList.isEmpty()) {
            System.out.println(userPresenter.messageDisplay("noChat"));
            return false;
        }
        System.out.println(userPresenter.chatListDisplay(chatList));
        System.out.print(userPresenter.messageDisplay("userChoice"));
        String receiverId = sc.nextLine();
        if (receiverId.isEmpty()) return false;
        if (!chatList.contains(receiverId)) {
            System.out.println(userPresenter.messageDisplay("invalidUserId"));
            return allChatMessage();
        }
        System.out.println(userPresenter.displayAllMessage(currentUserId, receiverId)); //Display chat content
        messageManager.read(currentUserId, receiverId); // Once user enters a chat, all new messages are marked as read.
        System.out.println(userPresenter.messageDisplay("allChatOptions"));
        String choice;
        Scanner in = new Scanner(System.in);
        choice = in.nextLine();
        switch (choice) {
            case "1":
                return sendPrivateMessage((receiverId));
            case "2":
                return deleteLastMessage(currentUserId, receiverId);
            case "3":
                return archiveChat(receiverId);
        }
        return true;
    }

    // Fixme: shouldn't be able to delete opponent's message if last sender is opponent
    // Todo: Kevin(decrement unread message count by 1)

    /**
     * delete last sent message to receiver
     * @param currentUserId a string representing current user's id
     * @param receiverId a string representing current user's message recipient id
     * @return a boolean value true
     */
    public boolean deleteLastMessage(String currentUserId, String receiverId) {
        List<String> content = messageManager.getMessageContent(currentUserId, receiverId);
        List<String> messageFrom = messageManager.getMessageFrom(currentUserId, receiverId);
        for(int i = content.size() - 1; i > 0; i--) {
            if (messageFrom.get(i).equals(currentUserId)) {
                return deleteMessage(receiverId, i);
            }
        }
        System.out.println(userPresenter.messageDisplay("deleteFailed"));
        return false;
    }

    /**
     * Deletes the message at an index, prints whether if successfully deleted.
     * @param receiverId Id of the opponent
     * @param index Integer that corresponds to the position of the message that is to be deleted.
     * @return True iff message is deleted.
     */
    public boolean deleteMessage(String receiverId, Integer index) {
        String messageDeleted = userPresenter.messageDisplay("deletedMessage");
        if (!messageManager.changeContent(userManager.getCurrentUserId(), receiverId, index, messageDeleted)) {
            System.out.println(userPresenter.messageDisplay("deleteFailed"));
            return false;
        }
        System.out.println(userPresenter.messageDisplay("deleted"));
        return true;
        }



    /**
     * archive selected receiver's chat by adding it to a new array list of string type
     * @param receiverId a string representing a receiver's id being archived
     * @return a boolean value true
     */
    public boolean archiveChat(String receiverId){
        userManager.addToArchived(receiverId);
        System.out.println(userPresenter.messageDisplay("archived"));
        return true;
    }

    /**
     * remove archived id from every opponent list
     * @param currentUserId a string representing current user's id
     * @return an array list with string type representing list of opponent id excluding archived ids.
     */
    public List<String> chatListRemoveArchived(String currentUserId){
        List<String> chatList = messageManager.getEveryOpponent(currentUserId);
        List<String> archivedList = userManager.getArchivedList();
        if (archivedList.size() > 1){
            chatList.removeIf(id -> id.equals(archivedList.get(archivedList.size()-1)));
        }
        if (archivedList.size() == 1){
            chatList.removeIf(id -> id.equals(archivedList.get(0)));
        }
        return chatList;

    }

    /**
     * view list of archived ids when "view archived receiver" option is selected from messagecontrol() and unarchive chat when new message is sent
     * @return a method viewArchivedReceiver() to go back to the beginning when receiver choice is invalid and a method sendPrivateMessage() to send message to unarchive.
     */
    public boolean viewArchivedReceiver() {
        Scanner sc = new Scanner(System.in);
        List<String> archivedList = userManager.getArchivedList();
        System.out.println(userPresenter.chatListDisplay(archivedList));
        System.out.print(userPresenter.messageDisplay("userChoice"));
        String receiverId = sc.nextLine();
        if (receiverId.isEmpty()) return false;
        if (!archivedList.contains(receiverId)) {
            System.out.println(userPresenter.messageDisplay("invalidUserId"));
            return viewArchivedReceiver();
        }
        archivedList.remove(receiverId);
        return sendPrivateMessage((receiverId));

    }



    /**
     * Send private message by providing two choices for a user to select from. (lookup by receiver Id or by events)
     *
     * @return Return True to bo back to messageControl, False to repeat privateMessage
     */
    public boolean privateMessage() {
        System.out.println(userPresenter.messageDisplay("privateMessageDisplay"));
        String choice;
        Scanner sc = new Scanner(System.in);
        choice = sc.nextLine();
        switch (choice) {
            case "1": //Message by receiver id
                sendPrivateMessage();
                return privateMessage();
            case "2": //Message by Searching UserId
                messageBySearch();
                return privateMessage();
            case "3": // Go back to messageControl
                return false;
            default:
                System.out.println(initialPresenter.OutOfChoice());
                return privateMessage();
        }
    }

    /**
     * send message by searching the event name and display list of users in the event.
     *
     * @return a boolean value false if no choice of event was inputted and return messageBySearch method again when list of users of an event is found.
     */
    public boolean messageBySearch() {
        Scanner sc = new Scanner(System.in);
        System.out.println(userPresenter.showMyEvents());
        System.out.print(userPresenter.messageDisplay("eventName"));
        String eventChoice = sc.nextLine();
        if (eventChoice.isEmpty()) return false;
        if (!userController.isValidEventChoice(eventChoice)) return messageBySearch();
        String eventName = userController.convertEventChoiceToName(eventChoice);
        System.out.println(userPresenter.displayAllUserInEvent(eventName));
        if (!sendPrivateMessage()) return messageBySearch();
        return true;
    }

    /**
     * Receives multiple eventChoices, checks if choices are valid. Speaker can send bulkMessage to Attendee only, Organizer can send to both Attenddee or Speakers
     *
     * @return a boolean value true if bulk message is sent otherwise, false. If event choice is not valid recurse it self.
     */
    public boolean bulkMessage() {
        Scanner sc = new Scanner(System.in);
        System.out.println(userPresenter.showMyEvents());
        System.out.println(userPresenter.messageDisplay("eventNameList"));
        String eventBulkChoice = sc.nextLine();
        if (eventBulkChoice.isEmpty()) {
            System.out.println(userPresenter.messageDisplay("messageNotSent"));
            return false;
        }
        List<String> eventNameList = new ArrayList<>();
        for (String eventChoice : eventBulkChoice.split(" ")) {
            if (!userController.isValidEventChoice(eventChoice)) return bulkMessage();
            eventNameList.add(userController.convertEventChoiceToName(eventChoice));
        }
        if (userManager.isCurrentUserSpeaker()) {
            sendBulkMessage(eventNameList, "1");
        } else {
            sendBulkMessage(eventNameList);
        }
        System.out.println(userPresenter.messageDisplay("bulkMessageSent"));
        return true;
    }


}
