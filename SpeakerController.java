package controller;

import Presenter.InitialPresenter;
import Presenter.SpeakerPresenter;
import Presenter.UserPresenter;
import use_case.EventManager;
import use_case.MessageManager;
import use_case.SpeakerManager;
import use_case.UserManager;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SpeakerController {

    SpeakerManager speakerManager;
    SpeakerPresenter speakerPresenter;
    InitialPresenter initialPresenter;
    MessageManager messageManager;
    EventManager eventManager;
    UserController userController;
    MessageController messageController;
    LogoutController logoutController;
    UserPresenter userPresenter;
    UserManager userManager;
    TicketController ticketController;

    public SpeakerController(SpeakerManager sm, SpeakerPresenter sp,
                             InitialPresenter ip, MessageManager mm, EventManager em,
                             MessageController mc, LogoutController lc, UserPresenter up, UserManager um,
                             UserController uc, TicketController tc) {

        this.speakerManager = sm;
        this.speakerPresenter = sp;
        this.initialPresenter = ip;
        this.messageManager = mm;
        this.eventManager = em;
        this.userController = uc;
        this.messageController = mc;
        this.logoutController = lc;
        this.userPresenter = up;
        this.userManager = um;
        this.ticketController = tc;
    }

    public void displayMain() {
        System.out.println(speakerPresenter.mainDisplay());
    }

    /**
     * Main menu for Speaker, where Speaker can: manage talks Speaker is enrolled in, go to message menu,
     * manage friends, or logout.
     */
    public boolean mainSpeakerControl() throws IOException {
        displayMain();
        String choice;
        Scanner in = new Scanner(System.in);
        choice = in.nextLine();
        switch (choice) {
            case "1":
                String eventName = userController.chooseEventControl();
                if (eventName == null){
                    System.out.println(initialPresenter.OutOfChoice());
                    return mainSpeakerControl();
                }
                return manageTalkControl(eventName);
            case "2":
                userController.seeSchedule();
                return mainSpeakerControl();
            case "3":
                return messageControl();
            case "4":
                return friendControl();
            case "5":
                return logoutController.logout();
            default:
                System.out.println(initialPresenter.OutOfChoice());
                return mainSpeakerControl();
        }
    }

    /**
     * Menu for managing friends. Program takes String input and return specific controls based on user's choice.
     * User can choose to display their friends list, add a new friend, remove friend or return to main menu.
     */
    public boolean friendControl() throws IOException {
        System.out.println(speakerPresenter.friendControlInstruction());
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                System.out.println(userPresenter.displayFriends(userManager.getCurrentUserId()));
                return friendControl();
            case "2":
                return addNewFriend();
            case "3":
                return deleteFriend();
            case "4":
                System.out.println(speakerPresenter.returningToMainDisplay());
                return mainSpeakerControl();
            default:
                System.out.println(speakerPresenter.notAnOptionDisplay());
                return friendControl();
        }

    }

    /**
     * Allow Speaker to delete their friend. Program takes user ID to recognize which user to remove from Speaker's
     * friends list. The program will let the user to try again until they put in a valid input if input is invalid.
     * User can go back to previous menu by using non-integer input.
     * Sends user back to main menu if the deletion was successful.
     */
    private boolean deleteFriend() throws IOException {
        System.out.println(userPresenter.displayFriends(userManager.getCurrentUserId()));
        System.out.println(speakerPresenter.deleteFriendInstruction());
        Scanner scanner = new Scanner(System.in);
        try {
            int option = scanner.nextInt();
            if (userManager.removeFriend(option, userManager.getCurrentUserId())) {
                System.out.println(speakerPresenter.deleteFriendSuccessful());
                System.out.println(speakerPresenter.returningToMainDisplay());
                return mainSpeakerControl();
            } else {
                System.out.println(speakerPresenter.deleteFriendFailed());
                return deleteFriend();
            }
        } catch (InputMismatchException | IOException ime) {
            System.out.println(speakerPresenter.nonIntegerInputDisplay());
            return friendControl();
        }
    }

    /**
     * Allow Speaker to add a new friend. Program takes user ID to recognize which user to add to Speaker's
     * friends list. The program will let the user to try again until they put in a valid input if input is invalid.
     * Input is invalid if user with input user ID is already in Speaker's friends list or does not exist at all.
     * Program returns to the previous menu when the input ' -return- ' is received.
     * Sends user back to main menu if the addition was successful.
     */
    private boolean addNewFriend() throws IOException {
        System.out.println(speakerPresenter.addFriendInstruction());
        Scanner scanner = new Scanner(System.in);
        String wishToAdd = scanner.nextLine();
        if ("-return-".equals(wishToAdd)) {
            System.out.println(speakerPresenter.returningToFriendControlDisplay());
            return friendControl();
        }
        if (userManager.addFriend(userManager.getCurrentUserId(), wishToAdd)) {
            System.out.println(speakerPresenter.addFriendSuccessful());
            System.out.println(speakerPresenter.returningToMainDisplay());
            return mainSpeakerControl();
        } else {
            System.out.println(speakerPresenter.addFriendFailed());
            return addNewFriend();
        }

    }

    /**
     * Selects from different cases based on user's choice.
     * Options include displaying all chats, sending private message, sending messages to all attendees, going back to the main menu, and logging out.
     * @return privateMessage() or messageControl() to go back to the options display / return another method (logoutController.logout()) to process selected option.
     */
    public boolean messageControl() throws IOException {
        System.out.println(speakerPresenter.messageDisplay());
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
                return mainSpeakerControl();
            case "6":
                return logoutController.logout();
            default:
                initialPresenter.OutOfChoice();
                return messageControl();
        }
    }



    /**
     * Show options on managing talks.
     * @param talkName a string representing the talk that the speaker wants to manage.
     */
    public boolean manageTalkControl(String talkName) throws IOException {
        System.out.println(speakerPresenter.manageTalkDisplay());
        String choice2;
        Scanner in = new Scanner(System.in);

        choice2 = in.nextLine();

        switch (choice2) {
            case "1":
                userController.seeAllUsersAndAddFriend(talkName);
                return mainSpeakerControl();
            case "2":
                return talkMessageControl(talkName);
            case "3":
                ticketController.getTicket(talkName);
                return manageTalkControl(talkName);
            case "4":
                String eventName = userController.chooseEventControl();
                if (eventName == null){
                    System.out.println(initialPresenter.OutOfChoice());
                    return mainSpeakerControl();
                }
                return manageTalkControl(eventName);
            case "5":
                return logoutController.logout();
            default:
                System.out.println(initialPresenter.OutOfChoice());
                return mainSpeakerControl();
        }
    }


    /**
     * Send event message to every attendee in the talk.
     * @param eventName a string representing an event that the speaker wants to send message to.
     */
    public boolean talkMessageControl(String eventName) throws IOException {
        String announcement;
        System.out.println(speakerPresenter.talkMessageDisplay());
        Scanner in = new Scanner(System.in);
        announcement = in.nextLine();
        if (!announcement.isEmpty()) {
            messageController.sendEventMessage(eventName, "1", announcement);
            System.out.println(speakerPresenter.announcementSentDisplay());
        } else {
            return mainSpeakerControl();
        }

        System.out.println(speakerPresenter.announcementSentDisplay());
        return mainSpeakerControl();
    }
}
