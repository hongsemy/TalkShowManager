package Gateway;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;


public class TicketPrint {


    public void generateTicket(String userID, String userName, String eventName,
                                    String time, String userType, int roomNo) throws IOException {
        try {
            File myObj = new File("generated_ticket/"+userID+"_"+eventName+".pdf");
            if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        try {
            FileWriter myWriter = new FileWriter("generated_ticket/"+
                    userID+"_"+eventName+".pdf");
            myWriter.write("Thank you for choosing EVENTLINK. \n");
            myWriter.write("___________________________________________ \n");
            myWriter.write("Event: "+ eventName + " \n"+ " \n");
            myWriter.write("Ticket type: "+ userType + " \n"+ " \n");
            myWriter.write("Name: "+ userName + " \n"+ " \n");
            myWriter.write("userID: "+ userID + " \n"+ " \n");
            myWriter.write("Time and Date: "+ time + " \n"+ " \n");
            myWriter.write("Room no: "+ roomNo);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        }
    }

