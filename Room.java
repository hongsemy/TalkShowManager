package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Represents a room in the program.
 */
public class Room implements Serializable {

    private final int roomNumber;
    /**
     * Represents the capacity of the room. It is currently set to two.
     */
    private int capacity = 2;
    /**
     * Represents the booking schedule of this Room.
     * Each LocalDateTime object and String pair represents the time and name of an event being held in this Room.
     */
    HashMap<LocalDateTime, String> schedule;

    /**
     * Creates a Room with the specified room number.
     * @param roomNumber An Integer representing the room number.
     */
    public Room(int roomNumber){
        this.roomNumber = roomNumber;
        this.schedule = new HashMap<>();
    }

    /**
     * Gets the number of this Room.
     * @return An Integer representing the number of this Room.
     */
    public int getRoomNumber(){
        return roomNumber;
    }

    // getter for capacity (in phase2, we will have to change room capacity)

    /**
     * Gets the capcity of this Room.
     * @return An Integer representing the capacity of this Room.
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * Modify the capacity of this Room by the specified number.
     * The capcity will only be successfully modified when the final capacity is greater than zero.
     * @param increment An integer representing how much to increase the capcity by.
     *                  A negative increment would indicate a decrease in the capacity.
     * @return A boolean value showing whether or not the capacity has been successfully modified.
     */
    public boolean modifyCapacity(int increment) {
        if (this.capacity + increment < 0){
            return false;
        } else{
            this.capacity += increment;
            return true;
        }
    }

    /**
     * Gets the booking schedule of this Room.
     * @return An HashMap representing the schedule of this Room.
     */
    public HashMap<LocalDateTime, String> getSchedule() {
        return schedule;
    }


    /**
     * Add the specified event to this Room's schedule.
     * @param time The time that this new Event is being held at.
     * @param event The name of the new Event.
     */
    public void addSchedule(String time, String event){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime t = LocalDateTime.parse(time, formatter);
        schedule.put(t, event);
    }

    /**
     * Cancel the specified event from this Room's schedule.
     * @param time The time that the event that is being cancelled is held at.
     */
    public void cancelSchedule(LocalDateTime time){
        schedule.remove(time);
    }

}
