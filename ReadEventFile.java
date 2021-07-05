package Gateway;

import use_case.EventManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ReadEventFile implements Iterator<String> {
    ArrayList<String> eventNames = new ArrayList<>();
    private int current = 0;
    EventManager eventManager;

    public ReadEventFile(EventManager em){
        eventManager = em;
    }


    /**
     * The prompt Strings are read from a file, eventNames.txt,
     * and added to the list of not yet scheduled events.
     */
    public void read() {

        //open file and read from it
        File file = new File("phase2/eventNames.txt");
        System.out.println(file.exists());

        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(!eventManager.getAllEventNames().contains(data)){
                    eventNames.add(data);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("eventNames.txt is missing");
            e.printStackTrace();
        }
    }


    public ArrayList<String> getEventNames() {
        return eventNames;
    }

    /**
     * Checks for subsequent prompts.
     * @return true if there is prompt that has not yet been returned.
     */
    @Override
    public boolean hasNext() {
        return current < eventNames.size();
    }




    /**
     * Returns the next prompt to be printed.
     * @return the next prompt.
     */
    @Override
    public String next() {
        String res;

        // List.get(i) throws an IndexOutBoundsException if
        // we call it with i >= properties.size().
        // But Iterator's next() needs to throw a
        // NoSuchElementException if there are no more elements.
        try {
            res = eventNames.get(current);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
        current += 1;
        return res;
    }

    /**
     * Removes the prompt just returned. Unsupported.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }


    /**
     * Serialize the list of not yet scheduled events.
     */
    public void serialize(){
        try {
            FileOutputStream fos = new FileOutputStream("phase2/ser_files/Unscheduled_events.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(eventNames);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Deserialize the list of not yet scheduled events from "Unscheduled_events.ser" file.
     */
    public void deserialize(){
        ArrayList<String> events = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("phase2/ser_files/Unscheduled_events.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            events = (ArrayList<String>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Unscheduled_events.ser not found");
            c.printStackTrace();
        }

        eventNames = events;
    }

}
