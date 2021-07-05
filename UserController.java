package controller;

import Presenter.InitialPresenter;
import Presenter.UserPresenter;
import controller.DisplayScheduleStrategy.DisplayMyScheduleController;
import controller.DisplayScheduleStrategy.DisplayScheduleByDayController;
import controller.DisplayScheduleStrategy.DisplayScheduleBySpeakerController;
import use_case.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// If you correctly implement this, you should NOT import any entity.

public class UserController {

    EventManager eventManager;
    UserManager userManager;
    MessageManager messageManager;
    AttendeeManager attendeeManager;
    UserPresenter userPresenter;
    InitialPresenter initialPresenter;
    SpeakerManager speakerManager;
    OrganizerManager organizerManager;
    DisplayMyScheduleController displayMyScheduleController;
    DisplayScheduleByDayController displayScheduleByDayController;
    DisplayScheduleBySpeakerController displayScheduleBySpeakerController;
    LogoutController logoutController;

    public UserController(EventManager em, UserManager um, MessageManager mm, AttendeeManager am,
                          UserPresenter up, InitialPresenter ip, SpeakerManager sm, OrganizerManager om,
                          DisplayMyScheduleController dmc, LogoutController lc,
                          DisplayScheduleBySpeakerController dsc, DisplayScheduleByDayController ddc) {
        eventManager = em;
        userManager = um;
        messageManager = mm;
        attendeeManager = am;
        userPresenter = up;
        initialPresenter = ip;
        speakerManager = sm;
        organizerManager = om;
        displayMyScheduleController = dmc;
        logoutController = lc;
        displayScheduleByDayController = ddc;
        displayScheduleBySpeakerController = dsc;
    }


    /**
     * Menu for events that a user is currently enrolled in/managing/talking.
     * User can go back to main menu by pressing Enter, or choose a specific event to manage.
     *
     * @return String event name that the user chose.
     * @throws InputMismatchException when the user input is not from the given integer option.
     */
    public String chooseEventControl() {
        System.out.println(userPresenter.showMyEvents());
        System.out.println(userPresenter.chooseEventInstruction());
        Scanner in = new Scanner(System.in);
        if (userManager.getCurrentUserEvents().size() == 0) {
            System.out.println(userPresenter.noEventToShow());
            return null;
        }
        try {
            int option = in.nextInt();
            if (option == 0) {
                return null;
            }
            if (option > 0 && option <= userManager.getCurrentUserEvents().size()) {
                return userManager.getCurrentUserEvents().get(option - 1);
            } else {
                System.out.println(initialPresenter.OutOfChoice());
                return null;
            }
        } catch (InputMismatchException ime) {
            System.out.println(userPresenter.nonIntegerInputDisplay());
            return chooseEventControl();
        }
    }


    /**
     * Convert a number labeled to an event to event name.
     *
     * @param eventChoice a string representing a selected event's labeled number
     * @return a string "Invalid event number" to represent that the number entered does not exist from the list.
     */
    public String convertEventChoiceToName(String eventChoice) {
        int eventNum = Integer.parseInt(eventChoice);
        return userManager.getCurrentUserEvents().get(eventNum - 1);
    }

    /**
     * Checking if an event selected by the user is in the list.
     *
     * @param eventChoice a string representing an event selected by the user.
     * @return a boolean value true or false. If inputNum is less than or equal to numOfEvents it returns true.
     * @throws NumberFormatException when the user's input is not a correct format.
     */
    public boolean isValidEventChoice(String eventChoice) {
        try {
            int eventNum = Integer.parseInt(eventChoice);
            if (!(eventNum <= userManager.getCurrentUserEvents().size())) {
                System.out.println(userPresenter.messageDisplay("invalidEventChoice"));
            }
            return eventNum <= userManager.getCurrentUserEvents().size();
        } catch (NumberFormatException e) {
            System.out.println(userPresenter.messageDisplay("invalidEventChoice"));
            return false;
        }
    }

    /**
     * Validates a given userId and prints "invalid User Id" if not valid.
     * @param userId String UserId
     * @return boolean true iff userId is valid.
     */
    public boolean isValidUserId(String userId) {
        if (!userManager.isExistingUser(userId)) {
            System.out.println(userPresenter.messageDisplay("invalidUserId"));
            return false;
        }
        return true;
    }

    /**
     * Show every user related to an event and let one add another user as a friend.
     *
     * @param eventName String value of the event Name that the user wants to see related users of.
     */

    public boolean seeAllUsersAndAddFriend(String eventName) {
        System.out.println(userPresenter.displayAllUserInEvent(eventName));
        System.out.println(userPresenter.whoToAddDisplay());
        System.out.println(userPresenter.proceedNoAddDisplay());
        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();
        if (choice.isEmpty()) {
            return false;
        }
        if (choice.equals(userManager.getCurrentUserId())) {
            System.out.println(userPresenter.cannotAddSelfDisplay());
            return false;
        }
        addFriend(userManager.getCurrentUserId(), choice);
        return true;
    }


    /**
     * Check if a speaker is available at the time of an event.
     *
     * @param speakerName String value of the name of a speaker to check availability for.
     * @param event       String value of the event name.
     * @return true iff the speaker is available at the time that an event is happening.
     */
    public boolean speakerTimeAvailable(String speakerName, String event) {
        List<String> eventList = userManager.getUserEvents(speakerName);
        List<LocalDateTime> timeList = eventManager.eventListTime(eventList);
        return !timeList.contains(eventManager.getEventTime(event));
    }


    /**
     * Add a user as a friend.
     *
     * @param userId   String value of the id of the user that wants to add a friend.
     * @param friendId String value of the id of the user that wants to add as a friend.
     */
    public boolean addFriend(String userId, String friendId) {

        if (friendId.equals("")) {
            return false;
        }
        System.out.println(userPresenter.successfullyAddedFriend(friendId));
        return userManager.addFriend(userId, friendId);

    }

    /**
     * Show friends to a user and let the user choose which friend to see more about.
     *
     * @param user String value of the id of the user that wants to see one's friends.
     * @return String value of the user's choice of friend.
     */
    public String seeMyFriends(String user) {
        if (!userPresenter.displayFriends(user).contains("\n")) {
            return null;
        } else {
            System.out.println(userPresenter.whoToSeeDisplay());
            System.out.println(userPresenter.proceedEnterDisplay());
            System.out.println(userPresenter.displayFriends(user));
            String choice;
            Scanner in = new Scanner(System.in);

            choice = in.nextLine();
            if (choice.isEmpty()) {
                return "empty";
            } else {
                return choice;
            }
        }
    }

    public boolean seeSchedule(){
        System.out.println(userPresenter.seeScheduleDisplay());
        String choice;
        Scanner in = new Scanner(System.in);
        choice = in.nextLine();
        switch (choice) {
            case "1":
                displayScheduleByDayController.display();
                return seeSchedule();
            case "2":
                displayMyScheduleController.display();
                return seeSchedule();
            case "3":
                displayScheduleBySpeakerController.display();
                return seeSchedule();
            case "4":
                return false;
            case "5":
                return logoutController.logout();
            default:
                System.out.println(initialPresenter.OutOfChoice());
                return false;
        }
    }


}
