package Presenter;

import use_case.UserManager;

public class InitialPresenter {

    UserManager userManager;

    public InitialPresenter(UserManager um){
        userManager = um;
    }

    // This class presents login menu.
    //TODO (GENERAL): "sign up" or "log in" to the account options/ log in page/ sign up page/...

    public String display1(){
        String option1 = "1: login";
        String option2 = "2: sign up";
        return option1 + "\n" + option2;
    }

    /**
     * @return a string "Enter your ID: ".
     */
    public String loginIdDisplay(){
        return "Enter your ID: ";
    }

    /**
     * @return a string "Enter your Password: ".
     */
    public String loginPwDisplay(){
        return "Enter your Password: ";
    }

    /**
     * @return a string "Authentication failed.".
     */
    public String authenticationFailed(){
        return "Authentication failed.";
    }

    /**
     * @return a string welcoming the user when the authentication was successful.
     */
    public String authenticationSuccessful(String userId){
        String name = userManager.userIdToName(userId);
        return "Welcome, " + name;
    }

    /**
     * @return a string telling the user that their input is not valid.
     */
    public String OutOfChoice(){
        return "Please choose among the given options.";
    }

    public String signupTypeDisplay(){
        String option1 = "1. Sign up as a regular attendee";
        String option2 = "2. Sign up with a VIP code";
        String option3 = "3. Go back to main menu";
        return option1 + "\n" + option2 + "\n" + option3;
    }

    /**
     * @return a string "Enter your new ID: ".
     */
    public String signupIdDisPlay(){
        return "Enter your new ID: ";
    }

    public String vipSignupIdDisplay() { return "Enter your given ID: ";}
    public String vipSignupCodeDisplay() { return "Enter your given code: ";}

    /**
     * @return a string "Enter your new Password: ".
     */
    public String signupPwDisplay(){
        return "Enter your Password: ";
    }

    /**
     * @return a string telling the user that their input id already exists that they need to choose another id.
     */
    public String newIdRequestDisplay(){
        return "This ID already exists. Enter another ID: ";
    }

    public String newIdCodeRequestDisplay() { return "You have entered an invalid set of id and code. Please try again.";}
    /**
     * @return a string telling the user that their password is too short.
     */
    public String newPwRequestDisplay(){
        return "Password has to be at least length of 1";
    }

    /**
     * @return a string "Enter your name: ".
     */
    public String signupNameDisplay(){
        return "Enter your name: ";
    }



    /**
     * @return a string telling the user that they successfully signed up.
     */
    public String signupSuccessful(){
        return "Sign Up successful!";
    }


}
