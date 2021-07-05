package controller;

import Presenter.InitialPresenter;
import use_case.AttendeeManager;
import use_case.OrganizerManager;
import use_case.SpeakerManager;
import use_case.UserManager;
import use_case.VipManager;

import java.io.IOException;
import java.util.Scanner;

// If you correctly implement this, you should NOT import any entity.

public class LoginController {

    UserManager userManager;
    OrganizerManager organizerManager;
    AttendeeController attendeeController;
    OrganizerController organizerController;
    InitialPresenter initialPresenter;
    SpeakerController speakerController;
    AttendeeManager attendeeManager;
    SpeakerManager speakerManager;
    VipManager vipManager;

    public LoginController(UserManager um, AttendeeController ac, OrganizerController oc, InitialPresenter ip,
                           SpeakerController sc, OrganizerManager om, AttendeeManager am, SpeakerManager sm,
                           VipManager vm){
        userManager = um;
        initialPresenter = ip;
        organizerController = oc;
        organizerManager = om;
        attendeeController = ac;
        speakerController = sc;
        attendeeManager = am;
        speakerManager = sm;
        vipManager = vm;

    }

    /**
     * Menu for login or signup, displayed at the very beginning of the program.
     */

    public boolean initialChoice() throws IOException {
        String choice;

        System.out.println(initialPresenter.display1());
        Scanner in = new Scanner(System.in);
        choice = in.nextLine();
        switch (choice) {
            case "1":
                return logIn();
            case "2":
                return getSignupType();
            default:
                System.out.println(initialPresenter.OutOfChoice());
                return initialChoice();
        }

    }


    /**
     * User puts the input id and pw to login.
     * @return user Controller for the specific user type.
     */

    public boolean logIn() throws IOException {
        String id;
        String pw;

        Scanner in = new Scanner(System.in);

        System.out.print(initialPresenter.loginIdDisplay());
        id = in.nextLine();


        System.out.print(initialPresenter.loginPwDisplay());
        pw = in.nextLine();

        if(userManager.authenticate(id, pw)){
            String type = userManager.getUserType(id);
            System.out.println(initialPresenter.authenticationSuccessful(id));
            userManager.setCurrentUser(id);

            if (type.equals("organizer")) {
                return organizerController.mainOrganizerControl();
            } if (type.equals("attendee")) {
                return attendeeController.mainAttendeeControl();
            } else {
                return speakerController.mainSpeakerControl();
            }


        } else{
            System.out.println(initialPresenter.authenticationFailed());
            return initialChoice();
        }
    }

    public boolean getSignupType() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println(initialPresenter.signupTypeDisplay());
        String choice = in.nextLine();
        switch (choice) {
            case "1":
                return attendeeSignUp();
            case "2":
                return vipSignUp();
            case "3":
                return initialChoice();
            default:
                System.out.println(initialPresenter.OutOfChoice());
                return initialChoice();
        }
    }


    /**
     * User puts the new input id and pw to sign up.
     * @return back to initial choice option (login/signup).
     */
    public boolean attendeeSignUp() throws IOException {
        String id;
        String pw;
        String name;
        Scanner in = new Scanner(System.in);
        System.out.println(initialPresenter.signupIdDisPlay());
        id = in.nextLine();

        while(!userManager.isUniqueId(id)){
            System.out.println(initialPresenter.newIdRequestDisplay());
            id = in.nextLine();
        }
        System.out.println(initialPresenter.signupPwDisplay());
        pw = in.nextLine();
        while(pw.length()<1){
            System.out.println(initialPresenter.newPwRequestDisplay());
            pw = in.nextLine();
        }
        System.out.println(initialPresenter.signupNameDisplay());
        name = in.nextLine();
        System.out.println(initialPresenter.signupSuccessful());
        attendeeManager.newAttendee(name, id, pw);
        return initialChoice();
    }

    public boolean vipSignUp() throws IOException {
        String code;
        String id;
        String pw;
        String name;
        Scanner in = new Scanner(System.in);

        System.out.println(initialPresenter.vipSignupIdDisplay());
        id = in.nextLine();

        System.out.println(initialPresenter.vipSignupCodeDisplay());
        code = in.nextLine();

        if(!vipManager.isValidCode(id, code)){
            System.out.println(initialPresenter.newIdCodeRequestDisplay());
            return vipSignUp();
        }

        System.out.println(initialPresenter.signupPwDisplay());
        pw = in.nextLine();

        while(pw.length()<1){
            System.out.println(initialPresenter.newPwRequestDisplay());
            pw = in.nextLine();
        }

        System.out.println(initialPresenter.signupNameDisplay());
        name = in.nextLine();

        System.out.println(initialPresenter.signupSuccessful());
        vipManager.newVip(name, id, pw, code);
        return initialChoice();
    }




}
