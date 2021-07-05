import Gateway.TicketPrint;
import controller.MainController;

import java.io.IOException;
import Gateway.TicketPrint_pdf_ver;
import entity.Room;

public class Main {

    public static void main(String[] args) throws IOException {

        MainController mainController = new MainController();
//        Room room1 = new Room(1);
//        mainController.eventManager.addToAllRooms(room1);
//        Room room2 = new Room(2);
//        mainController.eventManager.addToAllRooms(room2);
//        mainController.organizerManager.newOrganizer("a", "a", "a");
//        mainController.attendeeManager.newAttendee("Shanna","b","b");
//        mainController.attendeeManager.newAttendee("Kevin","c","c");
//        mainController.attendeeManager.newAttendee("Roy","d","d");
//        mainController.attendeeManager.newAttendee("Lance","e","e");
//        mainController.speakerManager.newSpeaker("Asif", "Asif", "Asif");
//        mainController.readEventFile.read();
//        mainController.loginController.initialChoice();
        // initialChoice run 하는거 mainController constructor 안으로 옮김. Deserialize()도 마찬가지.
    }
}
