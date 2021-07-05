package Presenter;

import Gateway.ReadEventFile;
import use_case.EventManager;
import use_case.OrganizerManager;
import use_case.UserManager;

import java.util.ArrayList;
import java.util.List;

public class OrganizerPresenter {

    // This class presents Organizer's menu.

    //TODO (GENERAL): Show all events/ show responsible events/ show other users that chatted with/ show messages/
    //                show options to make schedule/ show options to send messages/ ...

    //TODO (GENERAL): formatting Strings to display/ giving options in some way (numbers maybe?)/ ...


    EventManager eventManager;
    OrganizerManager organizerManager;
    ReadEventFile readEventFile;
    UserManager userManager;

    public OrganizerPresenter(EventManager em, OrganizerManager om, ReadEventFile ref, UserManager um) {
        this.eventManager = em;
        this.organizerManager = om;
        this.readEventFile = ref;
        this.userManager = um;
    }
    /**
     * The very first menu displayed when an organizer logs in.
     */
    public String mainDisplay() {
        String optionOne = "1: Schedule new events";
        String optionTwo = "2: View schedule";
        String optionThree = "3: Manage my events";
        String optionFour = "4: Message";
        String optionFive = "5: Manage rooms";
        String optionSix = "6: See my friends";
        String optionSeven = "7: Add new VIP code";
        String optionEight = "8: Create a new account";
        String optionNine = "9: Logout";
        return optionOne + "\n" + optionTwo + "\n" + optionThree + "\n" + optionFour + "\n" +
                optionFive + "\n" + optionSix + "\n" + optionSeven + "\n" + optionEight + "\n" + optionNine;
    }

    public String vipIdDisplay() { return "Enter the id of the new VIP user:"; }

    public String vipCodeDisplay() { return "Enter the one-time code of the new VIP user:"; }

    public String vipAlreadyExistsDisplay(){ return "This id & code pair is already in use. Please try again.";}
    public String vipAddSuccessfulDisplay(){return "This id & code pair has successfully been added.";};

    /**
     * This method returns all events that are yet to be scheduled, just read from the gateway.
     */
    public String notScheduledEventsDisplay() {
        String instruction = "Choose the event that you wish to organize. Press enter without typing anything to " +
                "go back to the main menu.";
        //readEventFile.read();
        ArrayList<String> newEventNames = readEventFile.getEventNames();
        StringBuilder eventList = new StringBuilder();
        for (int i = 1; i <= newEventNames.size(); i++) {
            String num = Integer.toString(i);
            eventList.append(num).append(": ").append(newEventNames.get(i-1)).append("\n");
        }
        return instruction + "\n" + eventList.toString();
    }

    /**
     * Display for when there is an invalid input of event to organize.
     */
    public String chooseFromNotScheduledEventsDisplay(){
        return "Please choose from the above list of events to organize.";
    }

    /**
     * Display for managing events menu for organizer.
     */
    // Display for OrganizerController.manageEventsControl
    public String manageEventDisplay(){
        String optionOne = "1: See all Attendees & Speaker";
        String optionTwo = "2: Reschedule events";
        String optionThree = "3: Cancel events";
        String optionFour = "4: Add Speaker to an event";
        String optionFive = "5: Remove Speaker from an event";
        String optionSix = "6: Event capacity settings";
        String optionSeven = "7: Logout";
        return optionOne + "\n" + optionTwo + "\n" + optionThree + "\n" + optionFour + "\n" + optionFive
                + "\n" + optionSix + "\n" + optionSeven;
        }

    /**
     * Display for when specific Speaker does not exist.
     */
    public void speakerDNEDisplay(){
        System.out.println("This speaker does not exists.");
    }
    /**
     * Display for when specific Speaker is busy.
     */
    public void speakerBusyDisplay(){
        System.out.println("This speaker is not available at the time.");
    }

    /**
     * Display for when specific speaker has been successfully added to event.
     */
    public void speakerSuccessfullyAddedDisplay(){
        System.out.println("The speaker was successfully added.");
    }


    /**
     * Display for when event has successfully been scheduled.
     */
    public void successMakeEvent(){
        System.out.println("Event has successfully been scheduled.");
    }

    /**
     * Display for when event has failed to be scheduled.
     */
    public void failMakeEvent(){
        System.out.println("Event has failed to be scheduled.");
    }

    /**
     * Display for when event has successfully been cancelled.
     */
    public void successCancelEvent(){
        System.out.println("Event has successfully been cancelled.");
    }

    /**
     * Display for when event has successfully been rescheduled.
     */
    public void successRescheduleEvent(){
        System.out.println("Event has successfully been rescheduled.");
    }

    /**
     * Display for when specific speaker has been successfully removed from event.
     */
    public void successRemoveSpeakerEvent(){ System.out.println("Speaker has successfully been removed from the Event."); }

    /**
     * Display for getting Organizer input of Speaker ID to create new Speaker.
     */
    public String createSpeakerIdDisplay(){
        return "Enter the new speaker's ID: " ;
    }

    /**
     * Display for getting Organizer input of Speaker PW to create new Speaker.
     */
    public String createSpeakerPwDisplay(){
        return "Enter the new speaker's Password: ";
    }

    /**
     * Display for when Organizer input of Speaker ID to create new Speaker already exists.
     */
    public String newSpeakerIdRequestDisplay(){
        return "This ID already exists. Enter another ID for the new speaker: ";
    }

    /**
     * Display for when Organizer input of Speaker PW is less than 1 character long.
     */
    public String newSpeakerPwRequestDisplay(){
        return "Password has to be at least length of 1";
    }

    /**
     * Display for getting Organizer input of Speaker name to create new Speaker.
     */
    public String createSpeakerNameDisplay(){
        return "Enter the new speaker's name: ";
    }

    /**
     * Display for when specific speaker has been successfully created.
     */
    public String createSpeakerSuccessful(){
        return "Sign Up successful!";
    }

    /**
     * Display of all options for Speaker for specific event.
     * @param eventName The name of the event.
     */
    public String displayAllSpeakerOptions(String eventName) {
        List<String> listOfSpeakers = eventManager.getSpeakersOfEvent(eventName);
        StringBuilder options = new StringBuilder();
        for (int i = 1; i <= listOfSpeakers.size(); i++ ) {
            String thisOpt = i + ": " + listOfSpeakers.get(i-1) +"\n";
            options.append(thisOpt);
        }
        return options.toString();
    }


    /**
     * Display for OrganizerController.messageControl
     * Display messaging options
     * @return a string representing five different options in each line.
     */
    public String messageMenuDisplay(){
        String optionOne = "1: See all Chats";
        String optionTwo = "2: Send a new private message";
        String optionThree = "3: Send event message";
        String optionFour = "4: View Archived receiver";
        String optionFive = "5: Go back to the main menu";
        String optionSix = "6: Logout";
        return optionOne + "\n" + optionTwo + "\n" + optionThree + "\n" + optionFour + "\n" + optionFive + "\n" + optionSix;
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
     * display a sentence indicating that message has been sent.
     * @return a string "Message sent".
     */
    public String messageSent(){ return "Message sent"; }


    /**
     * Display all chats for OrganizerController.displayAllChats
     * @param allChatList array list from messageManager.getEveryOpponent
     * @return a string of attendees and speakers that organizer is messaging
     */
    public String allChatsDisplay(ArrayList<String> allChatList){
//        number each item
//        return String
        StringBuilder chats = new StringBuilder();
        for (int i = 1; i <= allChatList.size(); i++) {
            String num = Integer.toString(i);
            chats.append(num).append(": ").append(allChatList.get(i)).append("\n");
        }
        return chats.toString();
    }

    /**
     * Display for input required in organizerController.privateMessage
     * @param choice a string representing which line to be printed in a display for input.
     * @return a string "messageDisplay: Invalid Input Error" representing that input cannot be processed(invalid).
     */
    public String messageDisplay(String choice){
        if (choice.equals("receiverId")) return "Recipient ID: ";
        if (choice.equals("messageContent")) return "Message: ";
        if (choice.equals("receiverType")) return "Select Receiver Type: 1. Attendee, 2. Speaker, 3. Both";
        if (choice.equals("eventNameList")) return "Select Event(s) separated by spaces (e.g '1' or '1 3 15'): ";
        if (choice.equals ("eventName")) return "Enter event number: ";
        return "messageDisplay: Invalid Input Error";
        }

    /**
     * Display for getting Organizer input of choosing a specific room for event.
     */
    public String chooseRoomEventDisplay(){
        return "Choose where to hold the event.";
    }
    /**
     * Display for getting Organizer input of choosing a specific Speaker to add for event.
     */
    public String chooseAddSpeakerDisplay(){
        return "Please type in the name of the Speaker you want to add.";
    }
    /**
     * Display for getting Organizer input of choosing a specific Speaker to remove from event.
     */
    public String chooseRemoveSpeakerDisplay(){
        return "Please type in the option number you want to remove.";
    }
    /**
     * Display for when there is no Speaker to remove from event.
     */
    public String noRemoveSpeakerDisplay(){
        return "There is no speaker to remove.";
    }
    /**
     * Display for getting valid Organizer input after invalid input
     */
    public String chooseValidOptionDisplay(){
        return "Please enter a valid option.";
    }
    /**
     * display a sentence indicating that an event selected by the user does not exist.
     * @return a string "Selected event(s) does not exist."
     */
    public String noEvent(){
        return "Selected event(s) does not exist.";
    }

    /**
     * Display for getting Organizer input of choosing a specific start time for event.
     */
    public String chooseStartTimeDisplay(){
        return "Enter event start time: ";
    }

    /**
     * Display for getting Organizer input of choosing a specific end time for event.
     */
    public String chooseEndTimeDisplay(){
        return "Enter event end time: ";
    }

    /**
     * Display for getting Organizer input of choosing a specific time for event when previous input was formatted incorrectly.
     */
    public String wrongTimeDisplay(){
        return "Enter correctly formatted time: ";
    }

    /**
     * Display for correct time format.
     */
    public String correctTimeFormatDisplay(){
        return "Time format: yyyy-MM-dd HH:mm";
    }

    /**
     * Display for getting Organizer input of choosing a room.
     */
    public String chooseRoomDisplay(){
        List<String> lstRooms = eventManager.getAllRoomsInString();
        StringBuilder returnedString = new StringBuilder();
        for(int i = 0; i < lstRooms.size(); i++){
            returnedString.append(lstRooms.get(i)).append("\n");
        }
        return "Enter the number of the room to assign:\n" + returnedString.toString();
    }

    /**
     * Display for room control menu.
     */
    public String roomControlOptionDisplay() {
        return "1: Open a new room\n2: Modify the room capacity";
    }

    /**
     * Display for when new room has successfully been opened.
     */
    public String openNewRoomSuccessful() {
        return "Room is opened successfully. Returning to main menu.";
    }

    /**
     * Display for when new room has failed to be opened.
     */
    public String openNewRoomFailed() {
        return "Room is already existing. Please try other room number.";
    }

    /**
     * Display for getting Organizer input of choosing a room number for new room to open.
     */
    public String openNewRoomInstruction() {
        return "Please enter a room number to open.";
    }

    /**
     * Display for getting Organizer input of choosing a room number to modify capacity of.
     */
    public String modifyRoomCapacityInstruction() {
        StringBuilder inst = new StringBuilder("Please enter a room number to modify the capacity\n");
        List<String> allRooms = eventManager.getAllRoomsInString();
        for (int i = 0; i < allRooms.size(); i ++) {
            inst.append(i+1).append(": ").append("Room ").append(allRooms.get(i)).append("\n");
        }
        return inst.substring(0, inst.length());
    }

    /**
     * Display for menu of modifying room capacity.
     */
    public String modifyRoomCapacityActions() {
        String intro = "Select the action to do";
        String option1 = "1: Increase the capacity";
        String option2 = "2: Decrease the capacity";
        String option3 = "3: Return to room control menu";
        return intro + "\n" + option1 + "\n" + option2 + "\n" + option3;
    }

    /**
     * Display for when returning to room control menu.
     */
    public String returnToRoomControlMenu() {
        return "Returning to room control menu";
    }

    /**
     * Display for when input of Organizer for specific room capacity menu option is invalid.
     */
    public String notValidOptionRC() {
        return "Please enter a valid option";
    }

    /**
     * Display for getting input of Organizer for magnitude to decrease room capacity by.
     * @param currCap Size to decrease room capacity by.
     */
    public String decreaseCapacityBy(int currCap) {
        return "Please enter the number to decrease by (Current Capacity: " + currCap + ")";
    }

    /**
     * Display for when room capacity decrease was successful
     * @param newCap Size of new room capacity.
     */
    public String decreaseCapacitySuccessful(int newCap) {
        return "Room capacity successfully updated: " + newCap +"\n" + "Returning to main menu";
    }

    /**
     * Display for when room capacity decrease was unsuccessful.
     */
    public String decreaseCapacityFailed() {
        return "Please enter a valid number (Modified capacity less than 0)";
    }

    /**
     * Display for getting input of Organizer for magnitude to increase room capacity by.
     * @param currCap Size to increase room capacity by.
     */
    public String increaseCapacityBy(int currCap) {
        return "Please enter the number to increase by (Current Capacity: " + currCap + ")";
    }

    /**
     * Display for when room capacity increase was successful
     * @param newCap Size of new room capacity.
     */
    public String increaseCapacitySuccessful(int newCap) {
        return "Room capacity successfully updated: " + newCap + "\n" + "Returning to main menu";
    }

    /**
     * Display for when input is negative, which is forbidden.
     */
    public String negativeIntegerForbidden() {
        return "Negative input is forbidden. Please enter a valid input.";
    }

    /**
     * Display for when input is non-integer, which is forbidden.
     */
    public String nonIntegerForbidden() {
        return "Non-Integer input is forbidden. Please enter a valid input.";
    }

    /**
     * Display for menu of creating a new account.
     */
    public String accountControlOption() {
        String description = "Choose an user type to create an account";
        String optionOne = "1: Speaker";
        String optionTwo = "2: Organizer";
        String optionThree = "3: Attendee";
        String optionFour = "4: VIP";
        String optionFive = "5: Return to main menu";
        return description + "\n" + optionOne + "\n" + optionTwo + "\n" + optionThree + "\n" + optionFour + "\n" + optionFive;

    }
    /**
     * Display for returning to previous menu.
     */
    public String returnToPreviousMenu() {
        return "Returning to previous menu";
    }
    /**
     * Display for getting Organizer input of Organizer ID to create new Organizer.
     */
    public String createOrganizerIdDisplay() {
        return "Enter the new Organizer's ID: " ;
    }
    /**
     * Display for when Organizer input of Organizer ID to create new Organizer already exists.
     */
    public String newOrganizerIdRequestDisplay() {
        return "This ID already exists. Enter another ID for the new organizer: ";
    }
    /**
     * Display for getting Organizer input of Organizer PW to create new Organizer.
     */
    public String createOrganizerPwDisplay() {
        return "Enter the new organizer's Password: ";
    }
    /**
     * Display for when Organizer input of Organizer PW is less than 1 character long.
     */
    public String newOrganizerPwRequestDisplay() {
        return "Password has to be at least length of 1";
    }
    /**
     * Display for getting Organizer input of Organizer name to create new Speaker.
     */
    public String createOrganizerNameDisplay() {
        return "Enter the new organizer's name: ";
    }
    /**
     * Display for when specific Organizer has been successfully created.
     */
    public String createOrganizerSuccessful() {
        return "Sign Up successful!";
    }
    /**
     * Display for getting Organizer input of Attendee ID to create new Attendee.
     */
    public String createAttendeeIdDisplay() {
        return "Enter the new attendee's ID: " ;
    }
    /**
     * Display for when Organizer input of Attendee ID to create new Attendee already exists.
     */
    public String newAttendeeIdRequestDisplay() {
        return "This ID already exists. Enter another ID for the new attendee: ";
    }
    /**
     * Display for getting Organizer input of Attendee PW to create new Attendee.
     */
    public String createAttendeePwDisplay() {
        return "Enter the new attendee's Password: ";
    }
    /**
     * Display for when Organizer input of Attendee PW is less than 1 character long.
     */
    public String newAttendeePwRequestDisplay() {
        return "Password has to be at least length of 1";
    }
    /**
     * Display for getting Organizer input of Attendee name to create new Speaker.
     */
    public String createAttendeeNameDisplay() {
        return "Enter the new attendee's name: ";
    }
    /**
     * Display for when specific Attendee has been successfully created.
     */
    public String createAttendeeSuccessful() {
        return "Sign Up successful!";
    }
    /**
     * Display for event capacity setting instruction.
     */
    public String chooseCapacityDisplay(int roomCap) { return "Please enter the event capacity. Room Capacity: "
            + Integer.toString(roomCap) ;
    }
    /**
     * Display for when received capacity does not fit the room capacity.
     */
    public String impossibleCapacityDisplay() { return "Received capacity is less than the room capacity." +
            " Please try again";
    }
    /**
     * Display for event capacity modify instruction.
     */
    public String setEventCapacityControlDisplay(int eventCap) {
        return "Enter the new capacity for this event. Current Capacity: " + Integer.toString(eventCap);
    }
    /**
     * Display for successful modify of event capacity.
     */
    public String setEventCapacitySuccessful(int eventCap) {
        return "Event capacity successfully modified. New Capacity: " + Integer.toString(eventCap);
    }
    /**
     * Display for event capacity modify failed.
     */
    public String setEventCapacityFailed() {
        String imp = impossibleCapacityDisplay();
        return imp + "\nreturning to the event capacity setting menu";
    }
    /**
     * Display for getting Organizer input of VIP ID to create new VIP.
     */
    public String createVipIdDisplay() { return "Enter the new VIP's ID: " ; }
    /**
     * Display for when Organizer input of VIP ID to create new VIP already exists.
     */
    public String newVipIdRequestDisplay() { return "This ID already exists. Enter another ID for the new VIP: ";
    }
    /**
     * Display for getting Organizer input of VIP PW to create new VIP.
     */
    public String createVipPwDisplay() { return "Enter the new VIP's Password: ";
    }
    /**
     * Display for when Organizer input of VIP PW is less than 1 character long.
     */
    public String newVipPwRequestDisplay() { return "Password has to be at least length of 1";
    }
    /**
     * Display for getting Organizer input of VIP name to create new VIP.
     */
    public String createVipNameDisplay() { return "Enter the new VIP's name: ";
    }
    /**
     * Display for when specific VIP has been successfully created.
     */
    public String createVipSuccessful() { return "Sign Up successful!";
    }

    public String inputMismatchDisplay() { return "Input must be an integer. Please try again.";
    }
}
