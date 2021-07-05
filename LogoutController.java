package controller;

import Gateway.ReadEventFile;
import use_case.EventManager;
import use_case.MessageManager;
import use_case.UserManager;


public class LogoutController {
    UserManager userManager;
    EventManager eventManager;
    MessageManager messageManager;
    ReadEventFile readEventFile;


    public LogoutController(UserManager um, EventManager em, MessageManager mm, ReadEventFile ref){
        userManager = um;
        eventManager = em;
        messageManager = mm;
        readEventFile = ref;
    }


    /**
     * Serialize all the info and end the program.
     */
    public boolean logout() {
        serializeAll();
        return true;
    }

    /**
     * Serialize all the information of each entities newly made.
     */
    public void serializeAll(){
        userManager.serialize();
        eventManager.serialize();
        messageManager.serialize();
        readEventFile.serialize();
        System.out.println("Serialized all");
    }


}