package Presenter;

import use_case.AttendeeManager;
import use_case.EventManager;

import java.util.ArrayList;
import java.util.List;

public class AttendeePresenter {

    // This class presents Attendee's menu.

    //TODO (GENERAL): Show all events/ show attending events/ show other users that chatted with/ show messages/
    //                show options to add or cancel an event/ show options to send messages/ ...

    //TODO (GENERAL): formatting Strings to display/ giving options in some way (numbers maybe?)/ ...

    AttendeeManager attendeeManager;
    EventManager eventManager;

    public AttendeePresenter(AttendeeManager am, EventManager em){
        attendeeManager = am;
        eventManager = em;
    }

    /**
     * Main menu displayed when an attendee log in to the system.
     * @return a string representing five different options in each line.
     */
    public String mainDisplay(){
        String optionOne = "1: Browse events";
        String optionTwo = "2: View schedule";
        String optionThree = "3: Manage my events";
        String optionFour = "4: Message";
        String optionFive = "5: See my friends";
        String optionSix = "6: Logout";
        return optionOne + "\n" + optionTwo + "\n" + optionThree + "\n" + optionFour +
                "\n" + optionFive + "\n" + optionSix;
    }

    /**
     * When the attendee clicks "browse events," all responsible events are displayed.
     */
    public String allEventsDisplay() {
        String instruction = "Choose the event that you wish to manage. Press enter without typing anything to go back to the main menu.";
        List<String> eventNames = eventManager.getAllEventNames();
        StringBuilder eventList = new StringBuilder();
        for (int i = 1; i <= eventNames.size(); i++) {
            String num = Integer.toString(i);
            eventList.append(num).append(": ").append(eventNames.get(i-1)).append("\n");
        }
        return instruction + "\n" + eventList.toString();
    }

    /**
     * When the attendee clicks a specific event, if they are enrolled in the event, all options to manage event are displayed.
     * @return a string representing five different options in each line.
     */
    public String myEventDisplay(){
        String optionOne = "1: Cancel your enrolment";
        String optionTwo = "2: See who is attending";
        String optionThree = "3: Download my ticket as pdf";
        String optionFour = "4: Go back to previous menu";
        String optionFive = "5: Go back to main menu";
        String optionSix = "6: Logout";

        return optionOne + "\n" + optionTwo + "\n" + optionThree + "\n" + optionFour + "\n" + optionFive + "\n" + optionSix;
    }

    /**
     * When the attendee clicks a specific event, if they are not enrolled in the event, all options to manage event are displayed.
     * @return a string representing three different options in each line.
     */
    public String newEventDisplay(){
        String optionOne = "1: Enrol in this event";
        String optionTwo = "2: Go back to main menu";
        String optionThree = "3: Logout";

        return optionOne + "\n" + optionTwo + "\n" + optionThree;
    }

    /**
     * Displays successful cancellation of enrollment of event.
     */
    public String cancelledEventDisplay () {
        return "You are no longer enrolled in this event";
    }
    /**
     * Displays successful enrollment of event.
     */
    public String enrolledEventDisplay () {
        return "You are now enrolled in this event";
    }

    /**
     * Display for AttendeeController.messageControl
     * Display messaging options
     * @return a string representing four different options in each line.
     */
    public String messageMenuDisplay(){
        String optionOne = "1: See all Chats";
        String optionTwo = "2: Send a new private message";
        String optionThree = "3: View Archived receiver";
        String optionFour = "4: Go back to main menu";
        String optionFive = "5: Logout";

        return optionOne + "\n" + optionTwo + "\n" + optionThree + "\n" + optionFour + "\n" + optionFive;
    }

    /**
     * Display options for option2 of messageMenuDisplay (sending private message)
     * @return a string representing options in each line.
     */
    public String privateMessageDisplay(){
        String optionOne = "1: Lookup by receiver ID";
        String optionTwo = "2: Lookup by events";
        String optionThree = "3: Go back to message menu";
        return optionOne + "\n" + optionTwo + "\n" + optionThree;
    }

    /**
     * Displays list of friends when Attendee has no friends.
     */
    public String noFriendsDisplay(){ return "Empty friend's list, returning to main menu"; }
    /**
     * Display for when returning to main menu.
     */
    public String toMainMenuDisplay(){ return "Returning to main menu"; }

    /**
     * display a sentence indicating that message has been sent.
     * @return a string "Message sent".
     */
    public String messageSent(){ return "Message sent"; }

    public String friendEventListView() {return "Which event would you like to view from your friend's list?"; }

    /**
     * Display all chats for AttendeeController.displayAllChats
     * @param allChatList array list from messageManager.getEveryOpponent
     * @return a string of receivers that attendee is messaging
     */
    public String allChatsDisplay(ArrayList<String> allChatList){
        StringBuilder chats = new StringBuilder();
        for (int i = 0; i < allChatList.size(); i++) {
            String num = Integer.toString(i);
            chats.append(num).append(": ").append(allChatList.get(i)).append("\n");
        }
        return chats.toString();
    }

    /**
     * Display for input required in AttendeeController.privateMessage
     * @param choice a string representing which line to be printed in a display for input.
     * @return a string "messageDisplay: Invalid Input Error" representing that input cannot be processed(invalid).
     */
    public String sendPrivateDisplay(String choice){
        if (choice.equals("receiverId")) return "Receiver's ID: ";
        if (choice.equals("messageContent")) return "Type your message: ";
        if (choice.equals ("eventName")) return "Enter event number: ";
        return "messageDisplay: Invalid Input Error";
    }

    /**
     * Display a sentence indicating that room is full
     */
    public String roomFullDisplay(){
        return "The room is full.";
    }


    /**
     * display a sentence indicating that an event selected by the user does not exist.
     * @return a string "Selected event(s) does not exist."
     */
    public String noEvent(){
        return "Selected event(s) does not exist.";
    }



}


