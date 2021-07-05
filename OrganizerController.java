package controller;

import Gateway.ReadEventFile;
import Presenter.InitialPresenter;
import Presenter.OrganizerPresenter;
import Presenter.UserPresenter;
import entity.Attendee;
import use_case.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OrganizerController {

    EventManager eventManager;
    MessageManager messageManager;
    OrganizerPresenter organizerPresenter;
    InitialPresenter initialPresenter;
    LogoutController logoutController;
    UserController userController;
    MessageController messageController;
    EventController eventController;
    OrganizerManager organizerManager;
    SpeakerManager speakerManager;
    UserManager userManager;
    ReadEventFile readEventFile;
    UserPresenter userPresenter;
    VipManager vipManager;
    AttendeeManager attendeeManager;

    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public OrganizerController(EventManager em, MessageManager mm, OrganizerPresenter op, InitialPresenter ip,
                               LogoutController lc, MessageController mc, EventController ec, UserManager um, OrganizerManager om,
                               SpeakerManager sm, ReadEventFile ref, UserPresenter up, UserController uc, VipManager vm, AttendeeManager am) {
        eventManager = em;
        messageManager = mm;
        organizerPresenter = op;
        initialPresenter = ip;
        userController = uc;
        logoutController = lc;
        messageController = mc;
        eventController = ec;
        userManager = um;
        organizerManager = om;
        speakerManager = sm;
        readEventFile = ref;
        userPresenter = up;
        vipManager = vm;
        attendeeManager = am;
    }


    /**
     * Displays main menu for Organizer.
     */
    public void displayMain() {
        System.out.println(organizerPresenter.mainDisplay());
    }


    /**
     * Main menu for Organizer, where Organizer can: schedule new events, manage Organizer's events, go to message menu,
     * manage rooms, see Organizer's friends, or logout.
     */
    public boolean mainOrganizerControl() {
        displayMain();
        String choice;
        Scanner in = new Scanner(System.in);
        choice = in.nextLine();
        switch (choice) {
            case "1":
                eventController.makeEvent();
                return mainOrganizerControl();
            case "2":
                userController.seeSchedule();
                return mainOrganizerControl();
            case "3":
                String eventName = userController.chooseEventControl();
                if (eventName == null){
                    System.out.println("Please choose from the given options.");
                    return mainOrganizerControl();
                }
                eventController.manageEventControl(eventName);
                return mainOrganizerControl();
            case "4":
                return messageControl();
            case "5":
                return roomControl();
            case "6":
                String friend = userController.seeMyFriends(userManager.getCurrentUserId());
                if (friend != null) {
                    System.out.println(userPresenter.displayUsersEvents(friend));
                }
                return mainOrganizerControl();
            case "7":
                return createVipCodeControl();
            case "8":
                return accountControl();
            case "9":
                return logoutController.logout();
            default:
                System.out.println(initialPresenter.OutOfChoice());
                return mainOrganizerControl();
        }
    }


    private boolean accountControl() {
        System.out.println(organizerPresenter.accountControlOption());
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        switch(option) {
            case "1":
                createSpeakerControl();
                return mainOrganizerControl();
            case "2":
                createOrganizerControl();
                return mainOrganizerControl();
            case "3":
                createAttendeeControl();
                return mainOrganizerControl();
            case "4":
                createVipControl();
                return mainOrganizerControl();
            case "5":
                System.out.println(organizerPresenter.returnToPreviousMenu());
                return mainOrganizerControl();
            default:
                System.out.println(initialPresenter.OutOfChoice());
                return accountControl();
        }
    }

    /**
     * Method for Organizer to create new Speaker of specific name, ID, and PW.
     */
    public boolean createSpeakerControl() {
        String id;
        String pw;
        String name;

        Scanner in = new Scanner(System.in);

        //Get input String id
        System.out.println(organizerPresenter.createSpeakerIdDisplay());
        id = in.nextLine();

        while (!userManager.isUniqueId(id)) {
            System.out.println(organizerPresenter.newSpeakerIdRequestDisplay());
            id = in.nextLine();
        }

        //Get input String pw
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

    private boolean createOrganizerControl() {
        String id;
        String pw;
        String name;

        Scanner in = new Scanner(System.in);

        //Get input String id
        System.out.println(organizerPresenter.createOrganizerIdDisplay());
        id = in.nextLine();

        while (!userManager.isUniqueId(id)) {
            System.out.println(organizerPresenter.newOrganizerIdRequestDisplay());
            id = in.nextLine();
        }

        //Get input String pw
        System.out.println(organizerPresenter.createOrganizerPwDisplay());
        pw = in.nextLine();

        while (pw.length() < 1) {
            System.out.println(organizerPresenter.newOrganizerPwRequestDisplay());
            pw = in.nextLine();
        }

        System.out.println(organizerPresenter.createOrganizerNameDisplay());
        name = in.nextLine();

        System.out.println(organizerPresenter.createOrganizerSuccessful());
        organizerManager.newOrganizer(name, id, pw);
        return true;
    }

    private boolean createAttendeeControl() {
        String id;
        String pw;
        String name;

        Scanner in = new Scanner(System.in);

        //Get input String id
        System.out.println(organizerPresenter.createAttendeeIdDisplay());
        id = in.nextLine();

        while (!userManager.isUniqueId(id)) {
            System.out.println(organizerPresenter.newAttendeeIdRequestDisplay());
            id = in.nextLine();
        }

        //Get input String pw
        System.out.println(organizerPresenter.createAttendeePwDisplay());
        pw = in.nextLine();

        while (pw.length() < 1) {
            System.out.println(organizerPresenter.newAttendeePwRequestDisplay());
            pw = in.nextLine();
        }

        System.out.println(organizerPresenter.createAttendeeNameDisplay());
        name = in.nextLine();

        System.out.println(organizerPresenter.createAttendeeSuccessful());
        attendeeManager.newAttendee(name, id, pw);
        return true;
    }

    /*
     * Method for Organizer to create new Organizer of specific name, ID, and PW.
     */
    private boolean createVipControl() {
        String id;
        String pw;
        String name;

        Scanner in = new Scanner(System.in);

        //Get input String id
        System.out.println(organizerPresenter.createVipIdDisplay());
        id = in.nextLine();

        while (!userManager.isUniqueId(id)) {
            System.out.println(organizerPresenter.newVipIdRequestDisplay());
            id = in.nextLine();
        }

        //Get input String pw
        System.out.println(organizerPresenter.createVipPwDisplay());
        pw = in.nextLine();

        while (pw.length() < 1) {
            System.out.println(organizerPresenter.newVipPwRequestDisplay());
            pw = in.nextLine();
        }

        System.out.println(organizerPresenter.createVipNameDisplay());
        name = in.nextLine();

        System.out.println(organizerPresenter.createVipSuccessful());
        vipManager.newVip(name, id, pw);
        return true;
    }

    /**
     * This is run when the user chooses "4 (Manage rooms)" in the main menu to manage rooms.
     * Organizer can open new rooms, or modify the maximum capacity for existing rooms.
     */

    public boolean roomControl() {
        System.out.println(organizerPresenter.roomControlOptionDisplay());
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                return openNewRoom();
            case "2":
                return modifyRoomCapacity();
            default:
                initialPresenter.OutOfChoice();
                return roomControl();
        }

    }
    /**
     * Method for Organizer to modify room capacity. Organizers can increase or decrease room capacity, or return
     * to room control menu.
     * @throws InputMismatchException when the input is not from the given integer options.
     */
    private boolean modifyRoomCapacity() {
        System.out.println(organizerPresenter.modifyRoomCapacityInstruction());
        Scanner scanner = new Scanner(System.in);
        try {
            int roomOption = scanner.nextInt();
            if (roomOption < 1 || roomOption > eventManager.getAllRooms().size()) {
                System.out.println(organizerPresenter.notValidOptionRC());
                return modifyRoomCapacity();
            }
            int roomNo = eventManager.getAllRooms().get(roomOption - 1).getRoomNumber();
            System.out.println(organizerPresenter.modifyRoomCapacityActions());
            Scanner scanner2 = new Scanner(System.in);
            String option = scanner2.nextLine();
            switch (option) {
                case "1":
                    return increaseRoomCapacity(roomNo);
                case "2":
                    return decreaseRoomCapacity(roomNo);
                case "3":
                    System.out.println(organizerPresenter.returnToRoomControlMenu());
                    return roomControl();
                default:
                    System.out.println(organizerPresenter.notValidOptionRC());
                    return modifyRoomCapacity();
            }
        } catch (InputMismatchException ime) {
            System.out.println(organizerPresenter.nonIntegerForbidden());
            return modifyRoomCapacity();
        }
    }

    /**
     * Method for Organizer to decrease room capacity. If successful, Organizer will return to main menu. If failed
     * to decrease room capacity, Organizer will have get to retry to decrease room capacity.
     * @param roomNo The room number of the room that the Organizer wants to manage.
     * @throws InputMismatchException when the input is not from the given integer options.
     */
    private boolean decreaseRoomCapacity(int roomNo) {
        int currCap = eventManager.getRoomCapacity(roomNo);
        System.out.println(organizerPresenter.decreaseCapacityBy(currCap));
        try {
            Scanner scanner = new Scanner(System.in);
            int constant = scanner.nextInt();
            if (constant < 0) {
                System.out.println(organizerPresenter.negativeIntegerForbidden());
                return decreaseRoomCapacity(roomNo);
            }
            if (eventManager.modifyRoomCapacity(roomNo, -constant)) {
                int newCap = eventManager.getRoomCapacity(roomNo);
                System.out.println(organizerPresenter.decreaseCapacitySuccessful(newCap));
                return mainOrganizerControl();
            } else {
                System.out.println(organizerPresenter.decreaseCapacityFailed());
                return decreaseRoomCapacity(roomNo);
            }
        } catch (InputMismatchException ime) {
            System.out.println(organizerPresenter.nonIntegerForbidden());
            return decreaseRoomCapacity(roomNo);
        }
    }

    /**
     * Method for Organizer to increase room capacity. If successful, Organizer will return to main menu. If failed
     * to increase room capacity, Organizer will have get to retry to increase room capacity.
     * @param roomNo The room number of the room that the Organizer wants to manage.
     */
    private boolean increaseRoomCapacity(int roomNo) {
        int currCap = eventManager.getRoomCapacity(roomNo);
        System.out.println(organizerPresenter.increaseCapacityBy(currCap));
        try {
            Scanner scanner = new Scanner(System.in);
            int constant = scanner.nextInt();
            if (constant < 0) {
                System.out.println(organizerPresenter.negativeIntegerForbidden());
                return increaseRoomCapacity(roomNo);
            }
            eventManager.modifyRoomCapacity(roomNo, constant);
            int newCap = eventManager.getRoomCapacity(roomNo);
            System.out.println(organizerPresenter.increaseCapacitySuccessful(newCap));
            return mainOrganizerControl();
        } catch (InputMismatchException ime) {
            System.out.println(organizerPresenter.nonIntegerForbidden());
            return increaseRoomCapacity(roomNo);
        }

    }


    /**
     * Method for Organizer to create new Speaker of specific name, ID, and PW.
     */
    /*
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
     */

    /**
     * Create a one-time id&code pair that can be used when a VIP user signs up to the program.
     * @return
     */
    public boolean createVipCodeControl() {
        String id;
        String code;
        boolean successful;
        Scanner in = new Scanner(System.in);
        System.out.println(organizerPresenter.vipIdDisplay());
        id = in.nextLine();
        while(!userManager.isUniqueId(id)){
            System.out.println(initialPresenter.newIdRequestDisplay());
            id = in.nextLine();
        }
        System.out.println(organizerPresenter.vipCodeDisplay());
        code = in.nextLine();
        successful = vipManager.addNewCode(id, code);
        if (successful){
            System.out.println(organizerPresenter.vipAddSuccessfulDisplay());
            return mainOrganizerControl();
        } else {
            System.out.println(organizerPresenter.vipAlreadyExistsDisplay());
            return createVipCodeControl();
        }

    }

    /**
     * Selects from different cases based on user's choice.
     * Choices include displaying all chats, sending private message, sending event message, going back to the main menu, and logging out.
     * @return privateMessage() or messageControl() to go back to the options display / return another method (mainAttendeeControl(), logoutController.logout()) to process selected option.
     */
    public boolean messageControl() {
        System.out.println(organizerPresenter.messageMenuDisplay());
        String choice;
        Scanner sc = new Scanner(System.in);
        choice = sc.nextLine();
        switch (choice) {
            case "1": //Display all chats then send message
                if (!messageController.allChatMessage()) return messageControl();
                return messageControl();
            case "2": //Send private message
                messageController.privateMessage();
                return messageControl();
            case "3": // Send Event Message & Bulk Message.
                messageController.bulkMessage();
                return messageControl();
            case "4":
                messageController.viewArchivedReceiver();
                return messageControl();
            case "5":
                return mainOrganizerControl();
            case "6":
                return logoutController.logout();
            default:
                initialPresenter.OutOfChoice();
                return messageControl();
        }
    }


    /**
     * Method for choosing correct start time with formatter for Organizer.
     * @return correctly formatted start time in String that corresponds to the user input.
     */
/*
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

 */

    /**
     * Method for choosing correct end time with formatter for Organizer.
     * @return correctly formatted end time in String that corresponds to the user input.
     */
/*
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

 */

    /**
     * Method for choosing existing room for Organizer.
     * @return the integer number of the room that corresponds to user input (String).
     */
    /*
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

     */

    /**
     * Method for adding Speaker to event for Organizer. Depending on availability of Speaker, Organizer may or may not be able
     * to add them to event. Organizer will return to main menu after.
     * @param eventName The name of the event that the Organizer wants to manage.
     */
    /*
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
            return mainOrganizerControl();
        }
        organizerPresenter.speakerBusyDisplay();
        return mainOrganizerControl();
    }
     */

    /**
     * Method for removing Speaker from event for Organizer. Organizer will return to main menu after.
     * @param eventName The name of the event that the Organizer wants to manage.
     */
/*
    public boolean removeSpeaker(String eventName) {

        System.out.println(organizerPresenter.displayAllSpeakerOptions(eventName));
        Scanner scanner = new Scanner(System.in);

        System.out.println(organizerPresenter.chooseRemoveSpeakerDisplay());
        int option = scanner.nextInt();

        ArrayList<String> listOfSpeakers = eventManager.getSpeakersOfEvent(eventName);
        if(listOfSpeakers.size() == 0){
            System.out.println(organizerPresenter.noRemoveSpeakerDisplay());
            return mainOrganizerControl();
        }
        if (!(option > 0 && option <= listOfSpeakers.size())){
            System.out.println(organizerPresenter.chooseValidOptionDisplay());
            return removeSpeaker(eventName); // repeat this until user puts in a valid number.
        } else {
            String speaker = listOfSpeakers.get(option-1);
            eventManager.removeSpeaker(speaker, eventManager.eventNamed(eventName));
            speakerManager.removeListTalks(speaker, eventName);
            organizerPresenter.successRemoveSpeakerEvent();
            return mainOrganizerControl();
        }

    }

 */

    /**
     * Method for opening new room for Organizer. If failed to open new room, Organizer will get to retry to open new room.
     * If successful, Organizer will return to main menu after.
     */

    public boolean openNewRoom() {
        System.out.println(organizerPresenter.openNewRoomInstruction());
        try {
            Scanner scanner = new Scanner(System.in);
            int roomNo = scanner.nextInt();
            if (roomNo < 0) {
                System.out.println(organizerPresenter.negativeIntegerForbidden());
                return openNewRoom();
            }
            if (eventManager.openNewRoom(roomNo)) {
                System.out.println(organizerPresenter.openNewRoomSuccessful());
                return mainOrganizerControl();
            } else {
                System.out.println(organizerPresenter.openNewRoomFailed());
                return openNewRoom();
            }
        } catch (InputMismatchException ime) {
            System.out.println(organizerPresenter.nonIntegerForbidden());
            return openNewRoom();
        }
    }


}
