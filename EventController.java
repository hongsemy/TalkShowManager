package controller;

import Gateway.ReadEventFile;
import Presenter.InitialPresenter;
import Presenter.OrganizerPresenter;
import use_case.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EventController {

    EventManager eventManager;
    OrganizerPresenter organizerPresenter;
    InitialPresenter initialPresenter;
    LogoutController logoutController;
    UserController userController;
    MessageController messageController;
    UserManager userManager;
    SpeakerManager speakerManager;
    ReadEventFile readEventFile;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public EventController(EventManager em, OrganizerPresenter op, InitialPresenter ip,
                               LogoutController lc, MessageController mc, UserManager um,
                           ReadEventFile ref, SpeakerManager sm, UserController uc) {
        eventManager = em;
        organizerPresenter = op;
        initialPresenter = ip;
        userController = uc;
        logoutController = lc;
        messageController = mc;
        userManager = um;
        speakerManager = sm;
        readEventFile = ref;
    }

    /**
     * Menu for managing specific event for Organizer. Organizer can: see all attendees and speakers attending event,
     * reschedule event, cancel event, add speakers to event, remove speakers from event, go back to the Organizer's list
     * of events, or logout.
     * @param eventName The name of the event that the Organizer wants to manage.
     */
    public boolean manageEventControl(String eventName) {
        System.out.println(organizerPresenter.manageEventDisplay());
        String choice2;
        Scanner in = new Scanner(System.in);
        choice2 = in.nextLine();

        switch (choice2) {
            case "1":
                //TODO
                userController.seeAllUsersAndAddFriend(eventName);
                return manageEventControl(eventName);
            case "2":
                return rescheduleEvent(eventName);
            case "3":
                return cancelEvent(eventName);
            case "4":
                return addSpeaker(eventName);
            case "5":
                return removeSpeaker(eventName);
            case "6":
                return setEventCapacityControl(eventName);
            case "7":
                return logoutController.logout();
            default:
                initialPresenter.OutOfChoice();
                return true;
        }
    }
    /**
     * Method for managing the capacity of specific event for Organizer. Organizer can: set the new capacity for the
     * event.
     * @param eventName The name of the event that the Organizer wants to manage.
     */
    private boolean setEventCapacityControl(String eventName) {
        int ogEventCap = eventManager.getEventCapacity(eventName);
        System.out.println(organizerPresenter.setEventCapacityControlDisplay(ogEventCap));
        Scanner scanner = new Scanner(System.in);
        try {
            int eventCap = scanner.nextInt();
            int roomNo = eventManager.eventNamed(eventName).getRoom();
            if (eventManager.possibleCapacity(roomNo, eventCap)) {
                eventManager.setEventCapacity(eventName, eventCap);
                System.out.println(organizerPresenter.setEventCapacitySuccessful(eventCap));
                return true;
            } else {
                System.out.println(organizerPresenter.setEventCapacityFailed());
                return setEventCapacityControl(eventName);
            }
        } catch (InputMismatchException imp) {
            System.out.println(organizerPresenter.inputMismatchDisplay());
            return setEventCapacityControl(eventName);
        }
    }

    /**
     * Method for scheduling new events for Organizer. Organizer will return to main menu after.
     */
    public boolean makeEvent() {
        int eventNameNo;
        String timeFormat;
        int roomNo;
        int maxCapacity;

        Scanner in = new Scanner(System.in);

        //Get input Event name
        System.out.println(organizerPresenter.notScheduledEventsDisplay());
        System.out.println(organizerPresenter.chooseFromNotScheduledEventsDisplay());
        try {
            String option = in.nextLine();
            if (option.equals("")){
                return true;
            }
            eventNameNo = Integer.parseInt(option);


            //Get input Event time in correct format
            String startTimeFormat = chooseCorrectStartTime();
            String endTimeFormat = chooseCorrectEndTime();

            //Get room number to access to the Room object.
            roomNo = chooseExistingRoom();
            int roomCap = eventManager.roomNumbered(roomNo).getCapacity();

            // TODO try catch InputMismatchException
            System.out.println(organizerPresenter.chooseCapacityDisplay(roomCap));
            Scanner scanner = new Scanner(System.in);
            maxCapacity = scanner.nextInt();
            if(!eventManager.possibleCapacity(roomNo, maxCapacity)) {
                System.out.println(organizerPresenter.impossibleCapacityDisplay());
                return makeEvent();
            }

            String eventName = readEventFile.getEventNames().get(eventNameNo - 1);

            if (eventManager.canSchedule(startTimeFormat, roomNo)) {
                userManager.addToEventList(eventName);
                readEventFile.getEventNames().remove(eventNameNo - 1);
                eventManager.makeNewEvents(eventName, startTimeFormat, endTimeFormat, roomNo, maxCapacity, userManager.getCurrentUserId());
                organizerPresenter.successMakeEvent();
                return true;
            }
            organizerPresenter.failMakeEvent();
            return true;
        } catch (InputMismatchException ime) {
            System.out.println(organizerPresenter.inputMismatchDisplay());
            return makeEvent();
        } catch (NumberFormatException nfe) {
            System.out.println(organizerPresenter.inputMismatchDisplay());
            return makeEvent();
        }
    }


    /**
     * Method for cancelling event for Organizer. After cancelling event, a message will be sent to all Attendees enrolled
     * in event, saying the event is cancelled. Organizer will return to main menu after.
     * @param eventName The name of the event that the Organizer wants to manage.
     */

    public boolean cancelEvent(String eventName) {
        String messageContent = eventName + " is cancelled.";

        //Send notification to everyone in the event.
        messageController.sendEventMessage(eventName, "3", messageContent);

        // call eventManager to take the event out of the allEventList
        eventManager.cancelEvent(eventName);

        // call userManager to modify everyone's event list.
        userManager.cancelEvent(eventName);
        organizerPresenter.successCancelEvent();
        return true;
    }

    /**
     * Method for rescheduling event for Organizer. After rescheduling event, a message will be sent to everyone enrolled
     * in event, saying the event is rescheduled. Organizer will return to main menu after.
     * @param eventName The name of the event that the Organizer wants to manage.
     */
    public boolean rescheduleEvent(String eventName) {
        String newStartTime = chooseCorrectStartTime();
        String newEndTime = chooseCorrectEndTime();
        System.out.println(organizerPresenter.chooseRoomEventDisplay());
        int roomNo = chooseExistingRoom();
        int roomCap = eventManager.roomNumbered(roomNo).getCapacity();
        System.out.println(organizerPresenter.chooseCapacityDisplay(roomCap));
        try {
            Scanner scanner = new Scanner(System.in);
            int eventCap = scanner.nextInt();
            if(!eventManager.possibleCapacity(roomNo, eventCap)){
                System.out.println(organizerPresenter.impossibleCapacityDisplay());
                return rescheduleEvent(eventName);
            }
            String announcement = eventName + " is rescheduled to room number " + roomNo + " from "
                    + newStartTime + " to " + newEndTime + ".";

            //Send notification to everyone in the event.
            messageController.sendEventMessage(eventName, "3", announcement);

            // call eventManager to reschedule the event and modify the allEventList
            eventManager.rescheduleEvent(eventName, newStartTime, newEndTime, roomNo, eventCap);

            // call organizerManager to modify everyone's event list.
            organizerPresenter.successRescheduleEvent();
            return true;
        } catch (InputMismatchException ime) {
            System.out.println(organizerPresenter.inputMismatchDisplay());
            return rescheduleEvent(eventName);
        }
    }


    /**
     * Method for adding Speaker to event for Organizer. Depending on availability of Speaker, Organizer may or may not be able
     * to add them to event. Organizer will return to main menu after.
     * @param eventName The name of the event that the Organizer wants to manage.
     */
    public boolean addSpeaker(String eventName) {
        Scanner in = new Scanner(System.in);
        System.out.println(organizerPresenter.chooseAddSpeakerDisplay());
        String speakerName = in.nextLine();
        if (!speakerManager.speakerExists(speakerName)) {
            organizerPresenter.speakerDNEDisplay();
            createSpeakerControl();
        }


        if (userController.speakerTimeAvailable(speakerName, eventName)) {
            eventManager.addSpeaker(speakerName, eventName);
            speakerManager.updateTalkList(speakerName, eventName);
            organizerPresenter.speakerSuccessfullyAddedDisplay();
            return true;
        }
        organizerPresenter.speakerBusyDisplay();
        return true;
    }


    /**
     * Method for Organizer to create new Speaker of specific name, ID, and PW.
     */
    public boolean createSpeakerControl() {
        String id;
        String pw;
        String name;

        Scanner in = new Scanner(System.in);

        System.out.println(organizerPresenter.createSpeakerIdDisplay());
        id = in.nextLine();

        while (!userManager.isUniqueId(id)) {
            System.out.println(organizerPresenter.newSpeakerIdRequestDisplay());
            id = in.nextLine();
        }

        System.out.println(organizerPresenter.createSpeakerPwDisplay());
        pw = in.nextLine();

        while (pw.length() < 1) {
            System.out.println(organizerPresenter.newSpeakerPwRequestDisplay());
            pw = in.nextLine();
        }

        System.out.println(organizerPresenter.createSpeakerNameDisplay());
        name = in.nextLine();

        System.out.println(organizerPresenter.createSpeakerSuccessful());
        speakerManager.newSpeaker(name, id, pw);
        return true;
    }


    /**
     * Method for removing Speaker from event for Organizer. Organizer will return to main menu after.
     * @param eventName The name of the event that the Organizer wants to manage.
     */

    public boolean removeSpeaker(String eventName) {

        System.out.println(organizerPresenter.displayAllSpeakerOptions(eventName));
        Scanner scanner = new Scanner(System.in);

        System.out.println(organizerPresenter.chooseRemoveSpeakerDisplay());
        int option = scanner.nextInt();

        List<String> listOfSpeakers = eventManager.getSpeakersOfEvent(eventName);
        if(listOfSpeakers.size() == 0){
            System.out.println(organizerPresenter.noRemoveSpeakerDisplay());
            return true;
        }
        if (!(option > 0 && option <= listOfSpeakers.size())){
            System.out.println(organizerPresenter.chooseValidOptionDisplay());
            return removeSpeaker(eventName); // repeat this until user puts in a valid number.
        } else {
            String speaker = listOfSpeakers.get(option-1);
            eventManager.removeSpeaker(speaker, eventManager.eventNamed(eventName));
            speakerManager.removeListTalks(speaker, eventName);
            organizerPresenter.successRemoveSpeakerEvent();
            return true;
        }

    }


    /**
     * Method for choosing correct start time with formatter for Organizer.
     * @return correctly formatted start time in String that corresponds to the user input.
     */

    public String chooseCorrectStartTime() {
        Scanner in = new Scanner(System.in);
        System.out.println(organizerPresenter.chooseStartTimeDisplay());
        System.out.println(organizerPresenter.correctTimeFormatDisplay());
        String startTimeString = in.nextLine();
        try {
            LocalDateTime date = LocalDateTime.parse(startTimeString, formatter);
            if (date.isAfter(LocalDateTime.now())) {
                return startTimeString;
            }
            return chooseCorrectStartTime();

        } catch (DateTimeParseException e) {
            System.out.println(organizerPresenter.wrongTimeDisplay());
            return chooseCorrectStartTime();
        }
    }

    /**
     * Method for choosing correct end time with formatter for Organizer.
     * @return correctly formatted end time in String that corresponds to the user input.
     */

    public String chooseCorrectEndTime() {
        Scanner in = new Scanner(System.in);
        System.out.println(organizerPresenter.chooseEndTimeDisplay());
        System.out.println(organizerPresenter.correctTimeFormatDisplay());
        String endTimeString = in.nextLine();
        try {
            LocalDateTime date = LocalDateTime.parse(endTimeString, formatter);
            if (date.isAfter(LocalDateTime.now())) {
                return endTimeString;
            }
            return chooseCorrectEndTime();

        } catch (DateTimeParseException e) {
            System.out.println(organizerPresenter.wrongTimeDisplay());
            return chooseCorrectEndTime();
        }
    }


    /**
     * Method for choosing existing room for Organizer.
     * @return the integer number of the room that corresponds to user input (String).
     */
    public int chooseExistingRoom(){
        int roomNo = -1;
        Scanner in = new Scanner(System.in);
        while (eventManager.thisRoomNo(roomNo) == null) {
            System.out.println(organizerPresenter.chooseRoomDisplay());
            String roomNoStr = in.nextLine();
            roomNo = Integer.parseInt(roomNoStr);
        }
        return roomNo;
    }



}
