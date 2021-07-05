package Presenter;

import use_case.EventManager;
import use_case.SpeakerManager;


public class SpeakerPresenter {

    // This class presents Speaker's menu.

    //TODO (GENERAL): Show attending talks/ show other users that chatted with/ show messages/
    //                show options to add or cancel an event/ show options to send messages/...

    //TODO (GENERAL): formatting Strings to display/ giving options in some way (numbers maybe?)/ ...
    SpeakerManager speakerManager;
    EventManager eventManager;


    public SpeakerPresenter(SpeakerManager sm, EventManager em){
        speakerManager = sm;
        eventManager = em;
    }

    /**
     * Display for SpeakerController.mainSpeakerControl, Display all options Speaker can take
     * @return a string representing four different options in each line.
     */
    public String mainDisplay(){
        String optionOne = "1: My talks";
        String optionTwo = "2: View schedule";
        String optionThree = "3: Message";
        String optionFour = "4: My Friends";
        String optionFive = "5: Logout";
        return optionOne + "\n" + optionTwo + "\n" + optionThree + "\n" + optionFour + "\n" + optionFive;


    }

    /**
     * Display for SpeakerController.messageControl, Display messaging options
     * @return a string representing five different options in each line.
     */
    public String messageDisplay(){
        String optionOne = "1: All chats";
        String optionTwo = "2: Send a new message to an attendee";
        String optionThree = "3: Send a new message to all attendees";
        String optionFour = "4: View Archived receiver";
        String optionFive = "5: Go back to the main menu";
        String optionSix = "6: Logout";
        return optionOne + "\n" + optionTwo + "\n" + optionThree + "\n" + optionFour + "\n" + optionFive + "\n" + optionSix;
    }

    /**
     * Display for input required in SpeakerController.privateMessage
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
     * display a sentence indicating that an event selected by the user does not exist.
     * @return a string "Selected event(s) does not exist."
     */
    public String noEvent(){
        return "Selected event(s) does not exist.";
    }

    /**
     * display a sentence indicating that message has been sent.
     * @return a string "Message sent".
     */
    public String messageSent(){ return "Message sent"; }

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

    public String sendEventDisplay(String choice){
        if (choice.equals("eventList")) return "Enter event(s): ";
        if (choice.equals("messageContent")) return "Type your message: ";
        return "messageDisplay: Invalid Input Error";
    }

    public String manageTalkDisplay(){
        String optionOne = "1: See all Users in the event";
        String optionTwo = "2: Message all attendees coming to listen to this talk";
        String optionThree = "3: Download my ticket as pdf";
        String optionFour = "4: Go back to talk list";
        String optionFive = "5: Logout";

        return optionOne + "\n" + optionTwo + "\n" + optionThree + "\n" + optionFour + "\n" + optionFive;
    }

    public String talkMessageDisplay(){
        return "Type in the announcement to be sent to the attendees of this talk. To go back to your talk " +
                "list, press Enter without typing anything.";
    }

    public String announcementSentDisplay(){
        return "Your announcement has been sent. Enter any key to go back to your talk list.";
    }


    /**
     * Display for SpeakerController.friendControl, Display friend managing options
     * @return a string representing four different options in each line.
     */
    public String friendControlInstruction() {

        String optionOne = "1: Display friends list";
        String optionTwo = "2: Add a new friend";
        String optionThree = "3: Remove friend";
        String optionFour = "4: Return to Main menu";

        return optionOne + "\n" + optionTwo + "\n" + optionThree + "\n" + optionFour;
    }

    /**
     * Display for non option inputs, Display a message that the input is not valid and notify the user.
     * @return a string "Input is not valid. Please enter a valid option."
     */
    public String notAnOptionDisplay() {

        return "Input is not valid. Please enter a valid option. ";
    }

    /**
     * Display for returning to main menu, Display a message that the program is returning to the main menu.
     * @return a string "Returning to the main menu".
     */
    public String returningToMainDisplay() {

        return "Returning to the main menu";
    }
    /**
     * Display for deleting friend instruction, Display a message that shows the user how to proceed.
     * @return a string "Please enter an option from the friends list to delete. Enter non-integer input to return
     * to my friend menu."
     */
    public String deleteFriendInstruction() {
        return "Please enter an option from the friends list to delete. Enter non-integer input to return to my friend menu ";
    }
    /**
     * Display for successful friend deletion, Display a message the friend with given id is successfully removed.
     * @return a string "Friend Successfully deleted."
     */
    public String deleteFriendSuccessful() {
        return "Friend Successfully deleted.";
    }

    /**
     * Display for failed friend deletion, Display a message the friend with given id is failed to be removed.
     * @return a string "Deletion Failed. Please try a valid input. "
     */
    public String deleteFriendFailed() {
        return "Deletion Failed. Please try a valid input. ";
    }

    /**
     * Display for non-integer input, Display a message that non-integer input is received.
     * @return a string "Non-Integer input received. Returning to my friend menu. "
     */
    public String nonIntegerInputDisplay() {
        return "Non-Integer input received. Returning to my friend menu. ";
    }

    /**
     * Display for adding friend instruction, Display a message that shows the user how to proceed.
     * @return a string "Please enter an ID of the user to add. Enter '-return-' to return to my friend menu "
     */
    public String addFriendInstruction() {
        return "Please enter an ID of the user to add. Enter '-return-' to return to my friend menu ";
    }

    /**
     * Display for successful friend addition, Display a message the friend with given id is successfully added.
     * @return a string "User successfully added to the friends list. "
     */
    public String addFriendSuccessful() {
        return "User successfully added to the friends list. ";
    }

    /**
     * Display for failed friend addition, Display a message the friend with given id is failed to be added.
     * @return a string "User with given ID does not exist or user already added to the friends list. \n
     * Please check you input and try again. ";
     */
    public String addFriendFailed() {
        return "User with given ID does not exist or user already added to the friends list. \n" +
                "Please check you input and try again. ";
    }

    /**
     * Display for ' -return- ' input, Display a message the prgram will return to the previous menu.
     * @return a string "Input '-return-' received. Returning to my friend menu"
     */
    public String returningToFriendControlDisplay() {
        return "Input '-return-' received. Returning to my friend menu";
    }
}
