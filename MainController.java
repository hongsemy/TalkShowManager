package controller;

import Gateway.ReadEventFile;
import Gateway.TicketPrint_pdf_ver;
import Presenter.*;
import controller.DisplayScheduleStrategy.DisplayMyScheduleController;
import controller.DisplayScheduleStrategy.DisplayScheduleByDayController;
import controller.DisplayScheduleStrategy.DisplayScheduleBySpeakerController;
import use_case.*;

import java.io.IOException;

public class MainController {

    // Main method instantiates this class.
    // MainController instantiates every other controllers, presenters, and use cases. One instance for each class.

    public EventManager eventManager;
    public MessageManager messageManager;
    public UserManager userManager;
    public AttendeePresenter attendeePresenter;
    public InitialPresenter initialPresenter;
    public OrganizerPresenter organizerPresenter;
    public SpeakerPresenter speakerPresenter;
    public OrganizerController organizerController;
    public LoginController loginController;
    public MessageController messageController;
    public EventController eventController;
    public AttendeeController attendeeController;
    public SpeakerController speakerController;
    public UserController userController;
    public OrganizerManager organizerManager;
    public SpeakerManager speakerManager;
    public AttendeeManager attendeeManager;
    public ReadEventFile readEventFile;
    public UserPresenter userPresenter;
    public LogoutController logoutController;
    public TicketPrint_pdf_ver ticketPrint;
    public TicketController ticketController;
    public DisplayMyScheduleController displayMyScheduleController;
    public DisplayScheduleByDayController displayScheduleByDayController;
    public DisplayScheduleBySpeakerController displayScheduleBySpeakerController;
    public DisplaySchedulePresenter displaySchedulePresenter;
    public VipManager vipManager;

    public MainController() throws IOException {
        eventManager = new EventManager();
        messageManager = new MessageManager();
        userManager = new UserManager();
        organizerManager = new OrganizerManager(userManager);
        speakerManager = new SpeakerManager(userManager);
        attendeeManager = new AttendeeManager(userManager);
        vipManager = new VipManager(userManager);

        readEventFile = new ReadEventFile(eventManager);


        ticketPrint = new TicketPrint_pdf_ver();
        ticketController = new TicketController(userManager, eventManager, ticketPrint);

        attendeePresenter = new AttendeePresenter(attendeeManager, eventManager);
        initialPresenter = new InitialPresenter(userManager);
        organizerPresenter = new OrganizerPresenter(eventManager, organizerManager, readEventFile, userManager);
        speakerPresenter = new SpeakerPresenter(speakerManager, eventManager);
        userPresenter = new UserPresenter(eventManager, userManager, organizerManager, attendeeManager,
                speakerManager, messageManager);
        displaySchedulePresenter = new DisplaySchedulePresenter(eventManager, userManager);


        logoutController = new LogoutController(userManager, eventManager, messageManager, readEventFile);
        displayMyScheduleController = new DisplayMyScheduleController(displaySchedulePresenter);
        userController = new UserController(eventManager, userManager, messageManager, attendeeManager, userPresenter,
                initialPresenter, speakerManager, organizerManager, displayMyScheduleController, logoutController,
                displayScheduleBySpeakerController, displayScheduleByDayController);
        messageController = new MessageController(eventManager, userManager, messageManager, attendeeManager, userPresenter,
                initialPresenter, speakerManager, organizerManager, userController);
        eventController = new EventController(eventManager, organizerPresenter, initialPresenter,
                logoutController, messageController, userManager, readEventFile, speakerManager, userController);
        organizerController = new OrganizerController(eventManager, messageManager, organizerPresenter,
                initialPresenter, logoutController, messageController, eventController,
                userManager, organizerManager, speakerManager, readEventFile, userPresenter, userController, vipManager, attendeeManager);
        attendeeController = new AttendeeController(messageController, logoutController, attendeePresenter,
                initialPresenter, attendeeManager, eventManager, userManager, userPresenter,
                messageManager, organizerManager, speakerManager, userController, ticketController);
        speakerController = new SpeakerController(speakerManager, speakerPresenter, initialPresenter,
                messageManager, eventManager, messageController, logoutController, userPresenter, userManager,
                userController, ticketController);
        loginController = new LoginController(userManager, attendeeController, organizerController, initialPresenter,
                speakerController, organizerManager, attendeeManager, speakerManager, vipManager);


        deserializeAll();
        loginController.initialChoice();

    }


    /**
     * Deserialize all the info back into the program from the previously made serialization files.
     */
    public void deserializeAll() {
        userManager.deserialize();
        eventManager.deserialize();
        messageManager.deserialize();
        readEventFile.deserialize();
        System.out.println("Deserialized all");
    }


}
