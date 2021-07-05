package controller;
import Gateway.TicketPrint_pdf_ver;
import entity.User;
import use_case.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketController {

    UserManager userManager;
    EventManager eventManager;
    TicketPrint_pdf_ver ticketPrint_pdf_ver;

    public TicketController(UserManager um, EventManager em, TicketPrint_pdf_ver tpv){
        userManager = um;
        eventManager = em;
        ticketPrint_pdf_ver = tpv;
    }


    /**
     * Request TicketPrint_pdf_ver class to generate a pdf of the ticket with the given information.
     *
     * @param eventName String name of the chosen event
     * @throws IOException
     */
    public void getTicket(String eventName) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        String id = userManager.getCurrentUserId();
        String name = userManager.getCurrentUserName();
        LocalDateTime time = eventManager.getEventTime(eventName);
        String timeString = time.format(formatter);
        int roomNo = eventManager.getRoomNumber(eventName);
        String userType = userManager.getUserType(id);
        ticketPrint_pdf_ver.generateTicket(id, name, eventName, timeString, userType, roomNo);

    }
}
