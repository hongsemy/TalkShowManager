package controller;

import Presenter.AttendeePresenter;
import Presenter.InitialPresenter;
import Presenter.UserPresenter;
import use_case.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AttendeeController {

    UserController userController;
    MessageController messageController;
    LogoutController logoutController;
    AttendeePresenter attendeePresenter;
    InitialPresenter initialPresenter;
    UserPresenter userPresenter;
    AttendeeManager attendeeManager;
    EventManager eventManager;
    MessageManager messageManager;
    UserManager userManager;
    OrganizerManager organizerManager;
    SpeakerManager speakerManager;
    TicketController ticketController;
    


    public AttendeeController(MessageController mc, LogoutController lc, AttendeePresenter ap, InitialPresenter ip,
                              AttendeeManager am, EventManager em, UserManager um, UserPresenter up,
                              MessageManager mm, OrganizerManager om, SpeakerManager sm, UserController uc,
                              TicketController tc) {
        messageController = mc;
        userController = uc;
        logoutController = lc;
        attendeePresenter = ap;
        initialPresenter = ip;
        attendeeManager = am;
        eventManager = em;
        userManager = um;
        userPresenter = up;
        messageManager = mm;
        organizerManager = om;
        speakerManager = sm;
        ticketController = tc;
    }

    /**
     * Main menu for Attendee, where Attendee can: browse all events, manage events Attendee is enrolled in,
     * go to message menu, see friends and their events, or logout.
     */
    public boolean mainAttendeeControl() throws IOException {
        System.out.println(attendeePresenter.mainDisplay());
        String choice;
        Scanner in = new Scanner(System.in);
        choice = in.nextLine();
        switch (choice) {
            //TODO: change return values. Make corresponding controllers.
            case "1":
                return browseEventsControl();
            case "2":
                userController.seeSchedule();
                return mainAttendeeControl();
            case "3":
                String eventName = userController.chooseEventControl();
                if (eventName == null){
                    System.out.println("Please choose from the given options.");
                    return mainAttendeeControl();
                }
                return myEventControl(eventName);
            case "4":
                return messageControl();
            case "5":
                String friend = userController.seeMyFriends(userManager.getCurrentUserId());
                if (friend == null){
                    System.out.println(attendeePresenter.noFriendsDisplay());
                    return mainAttendeeControl();
                }
                if (friend.equals("empty")){
                    System.out.println(attendeePresenter.toMainMenuDisplay());
                    return mainAttendeeControl();
                }
                return seeMyFriendsEvents(friend);
            case "6":
                return logoutController.logout();
            default:
                System.out.println(initialPresenter.OutOfChoice());
                // repeat this method again if the choice was not made properly.
                return mainAttendeeControl();
        }

    }


    /**
     * Menu for browsing all events. Makes sure input from Attendee is valid and depending on the enrollment status
     * of the event the Attendee chose to browse, they will have different options to manage the event.
     */
    public boolean browseEventsControl() throws IOException {
        System.out.println(attendeePresenter.allEventsDisplay());
        String choice;
        Scanner in = new Scanner(System.in);

        choice = in.nextLine();
        String eventName;
        if (choice.isEmpty()) {
            System.out.println(attendeePresenter.toMainMenuDisplay());
            return mainAttendeeControl();
        }
        int choiceInt = Integer.parseInt(choice);
        if (choiceInt <= eventManager.getAllEventNames().size()) {
            eventName = eventManager.getAllEventNames().get(choiceInt - 1);
            if (attendeeManager.isEnrolled(eventName)) {
                return myEventControl(eventName);
            } else {
                return newEventControl(eventName);
            }
        }
        System.out.println(initialPresenter.OutOfChoice());
        return browseEventsControl();
    }


    /**
     * Menu for managing the specific event Attendee is enrolled in. Attendee can cancel enrollment, see all
     * users attending the event, go back to previous menu, go back to main menu, or logout.
     * @param eventName The name of the event that the Attendee wants to manage.
     */
    public boolean myEventControl(String eventName) throws IOException {
        System.out.println(attendeePresenter.myEventDisplay());
        String choice;
        Scanner in = new Scanner(System.in);
        choice = in.nextLine();
        switch (choice) {
            case "1":
                userManager.removeFromEventList(eventName);
                eventManager.removeFromAttendeeList(userManager.getCurrentUserId(), eventName);
                System.out.println(attendeePresenter.cancelledEventDisplay());
                return mainAttendeeControl();
            case "2":
                userController.seeAllUsersAndAddFriend(eventName);
                return myEventControl(eventName);
            case "3" :
                ticketController.getTicket(eventName);
                return myEventControl(eventName);
            case "4":
                return browseEventsControl();
            case "5":
                return mainAttendeeControl();
            case "6":
                return logoutController.logout();
            default:
                System.out.println(initialPresenter.OutOfChoice());
                return myEventControl(eventName);
        }
    }



    /**
     * Menu for managing the specific event Attendee is not enrolled in. Attendee can enroll in the event, or go back
     * to main menu, or logout.
     * @param eventName The name of the event that the Attendee wants to manage.
     */
    public boolean newEventControl(String eventName) throws IOException {
        System.out.println(attendeePresenter.newEventDisplay());
        String choice;
        Scanner in = new Scanner(System.in);
        choice = in.nextLine();
        switch (choice) {
            case "1":
                if(!eventManager.eventIsFull(eventName)){
                    userManager.addToEventList(eventName);
                    eventManager.addToAttendeeList(userManager.getCurrentUserId(), eventName);
                    System.out.println(attendeePresenter.enrolledEventDisplay());
                }
                else{
                    System.out.println(attendeePresenter.roomFullDisplay());
                }
                return mainAttendeeControl();
            case "2":
                return mainAttendeeControl();
            case "3":
                return logoutController.logout();
            default:
                System.out.println(initialPresenter.OutOfChoice());
                return myEventControl(eventName);
        }

    }

    /**
     * Selects from different cases based on user's choice.
     * Options include displaying all chats, sending private message, going back to the main menu, and logging out.
     * @return privateMessage() or messageControl() to go back to the options display / return another method (mainAttendeeControl(), logoutController.logout()) to process selected option.
     */
    public boolean messageControl() throws IOException {
        System.out.println(attendeePresenter.messageMenuDisplay());
        String choice;
        Scanner in = new Scanner(System.in);
        choice = in.nextLine();
        switch (choice) {
            case "1":
                messageController.allChatMessage();
                return messageControl();
            case "2":
                messageController.privateMessage();
                return messageControl();
            case "3":
                messageController.viewArchivedReceiver();
                return messageControl();
            case "4":
                return mainAttendeeControl();
            case "5":
                return logoutController.logout();
            default:
                System.out.println(initialPresenter.OutOfChoice());
                return messageControl();
        }
    }

    /**
     * Allows Attendee to see friends, and choose specific friend to browse their events. From the friend's
     * list of events, Attendee can choose specific events to browse and manage, and depending on the Attendee's
     * enrollment status, Attendee will have different options to manage the event.
     * @param user The name of the user that the Attendee wants to see.
     */
    public boolean seeMyFriendsEvents(String user) throws IOException {
        System.out.println(attendeePresenter.friendEventListView());
        System.out.println(userPresenter.displayUsersEvents(user));
        String choice;
        String eventName;
        Scanner in = new Scanner(System.in);

        List<String> list;
        list = userManager.getUserEvents(user);

        choice = in.nextLine();
        if (choice.isEmpty()) {
            System.out.println(initialPresenter.OutOfChoice());
            return false;
        }

        int choiceInt = Integer.parseInt(choice);
        if (choiceInt <= list.size()) {
            eventName = list.get(choiceInt - 1);
            if (attendeeManager.isEnrolled(eventName)) {
                return myEventControl(eventName);
            } else {
                return newEventControl(eventName);
            }
        }
        System.out.println(initialPresenter.OutOfChoice());
        return mainAttendeeControl();
    }

}

