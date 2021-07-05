package use_case;

import entity.Event;
import entity.Room;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventManager implements Serializable {

    /**
     * Represents a list of all events happening at this conference.
     */
    private List<Event> allEvents;
    /**
     * Represents a list of all rooms at the location of this conference.
     */
    private List<Room> allRooms;


    /**
     * Gets the list of all events happening in this conference.
     * @return A list of Strings representing the names of all events happening at this conference.
     */
    public List<String> getAllEventNames(){
        List<String> eventNames = new ArrayList<>();
        for (Event event : allEvents){
            eventNames.add(event.getName());
        }
        return eventNames;
    }

    /**
     * Gets the capacity of the specified event.
     * @param eventName A String representing the name of the event.
     * @return An Integer representing the capacity of the event given by eventName.
     */
    public int getEventCapacity(String eventName){
        Event event = this.eventNamed(eventName);
        return event.getMaxCapacity();
    }

    /**
     * Gets the list of all rooms in the location of this conference.
     * @return A list of Room objects.
     */
    public List<Room> getAllRooms(){
        return allRooms;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    LocalTime firstHour = LocalTime.of(9, 0);
    LocalTime lastHour = LocalTime.of(16, 0);

    /**
     * Creates a new EventManager.
     */
    public EventManager(){
        allRooms = new ArrayList<>();
        allEvents = new ArrayList<>();
    }


    /**
     * Evaluates whether the specified event is full or not.
     * @param eventName A String representing the name of the event that is being evaluated.
     * @return A boolean value showing whether or not the specified event is full.
     */
    public boolean eventIsFull(String eventName){
        Event event = eventNamed(eventName);
        return roomNumbered(event.getRoom()).getCapacity() <= event.getAttendeeList().size();
    }


    /**
     * Creates a new event with the specified eventName, that is happening at the specified time, in the specified room,
     * and is organized by the specified organizer.
     * @param eventName A String representing the name of this new event.
     * @param startTime A String representing the start time that this event takes place at.
     * @param endTime A String representing the end time that this event takes place at.
     * @param room An integer representing the number of the room that this event takes place in.
     * @param organizer A String representing the name of the organizer that is organizing this event.
     * @return
     */
    public Event makeNewEvents(String eventName, String startTime, String endTime, int room, int maxCapacity, String organizer){
        Event event = new Event(eventName, startTime, endTime, organizer, room, maxCapacity);
        Room roomObject = roomNumbered(room);
        roomObject.addSchedule(startTime, eventName);
        allEvents.add(event);
        return event;
    }

    /**
     * Gets a list of all attendees who are signed up for the specified event.
     * @param eventName A String representing the name of the event.
     * @return A list of Strings representing the names of all attendees signed up for the specified event.
     */
    public List<String> getAttendeesOfEvent(String eventName){
        Event event = eventNamed(eventName);
        System.out.println(event);
        return event.getAttendeeList();
    }

    /**
     * Gets a list of all speakers who are giving a talk at the specified event.
     * @param eventName A String representing the name of the event.
     * @return A list of Strings representing the names of all speakers who are giving a talk at the specified event.
     */
    public List<String> getSpeakersOfEvent(String eventName){
        Event event = eventNamed(eventName);
        return event.getSpeakers();
    }

    /**
     * Gets the organizer who is organizing the specified event.
     * @param eventName A String representing the name of the event.
     * @return A String representing the name of the organizer who is organizing the specified event.
     */
    public String getOrganizer(String eventName){
        return eventNamed(eventName).getOrganizer();
    }

    /**
     * Returns the room with the specified room number.
     * @param i An integer representing the number of the target Room.
     * @return A Room object whose number is specified.
     */
    public Room roomNumbered(int i){
        for (Room room: allRooms){
            if(room.getRoomNumber() == i){
                return room;
            }
        }
        return null;
    }


    /**
     * Gets the event with the specified name.
     * @param s A String representing the name of the target Event.
     * @return An Event object whose name is specified.
     */
    public Event eventNamed(String s){
        for (Event event: allEvents){
            if (event.getName().equals(s)){
                return event;
            }
        }
        return null;
    }

    public int getRoomNumber(String eventName){
        return eventNamed(eventName).getRoom();
    }


    /**
     * Cancels the specified event.
     * @param eventName A String representing the name of the event that needs to be cancelled.
     * @return A boolean value -- this is set to be always true in this version.
     */
    public boolean cancelEvent(String eventName){
        Event event = eventNamed(eventName);
        allEvents.remove(event);
        Room room = roomNumbered(event.getRoom());
        room.cancelSchedule(event.getStartTime());
        return true;
    }

    /**
     * Adds the specified room to the list of all rooms.
     * @param room A Room object that is being added.
     */
    public void addToAllRooms(Room room){
        allRooms.add(room);
    }

    /**
     * Removes the specified room from the list of all rooms.
     * @param room A Room object that is being removed.
     */
    public void removeFromAllRooms(Room room){
        allRooms.remove(room);
    }


    /**
     * Adds the specified attendee to the list of all attendees that are signed up for the specified event.
     * @param attendee A String representing the name of the attendee to be added.
     * @param eventName A String representing the name of the event.
     * @return A boolean value -- this is set to be always true in this version.
     */
    public boolean addToAttendeeList(String attendee, String eventName){
        Event event = eventNamed(eventName);
        event.addAttendee(attendee);
        return true;
    }

    /**
     * Removes the specified attendee from the list of all attendees that are signed up for the specified event.
     * @param attendee A String representing the name of the attendee to be removed.
     * @param eventName A String representing the name of the event.
     * @return A boolean value -- this is set to be always true in this version.
     */
    public boolean removeFromAttendeeList(String attendee, String eventName){
        Event event = eventNamed(eventName);
        event.removeAttendee(attendee);
        return true;
    }

    /**
     * Gets a list of the times at which each of the specified event take place, in the specified order.
     * @param eventNames A list of strings representing the names of the events.
     * @return A list of LocalDateTime objects.
     */
    public List<LocalDateTime> eventListTime(List<String> eventNames){
        List<LocalDateTime> timeList = new ArrayList<>();
        for (Event event: allEvents){
            if(eventNames.contains(event.getName())){
                timeList.add(event.getStartTime());
            }
        }
        return timeList;
    }

    /**
     * Adds the specified speaker to the specified event.
     * @param speakerName A String representing the name of the new speaker to be added.
     * @param eventName A String representing the name of the event.
     * @return A boolean value -- this is set to be always true in this version.
     */
    public boolean addSpeaker(String speakerName, String eventName){
        Event event = eventNamed(eventName);
        event.addSpeaker(speakerName);
        return true;
    }

    /**
     * Removes the specified speaker from the specified event.
     * @param speaker A String representing the name of the speaker who is being removed.
     * @param event A String representing the name of the event.
     * @return A boolean value -- this is set to be always true in this version.
     */
    public boolean removeSpeaker(String speaker, Event event){
        event.removeSpeaker(speaker);
        return true;
    }

    /**
     * Gets the room with the specified number.
     * @param roomNo An integer representing the number of the target room.
     * @return A Room object who is designated the specified number. If no such Room exists, returns null.
     */
    public Room thisRoomNo(int roomNo){
        for (Room room: allRooms){
            if (room.getRoomNumber() == roomNo){
                return room;
            }
        }
        return null;
    }

    /**
     * Gets the capacity of the specified room.
     * @param roomNo An integer representing the number of the Room whose capacity is being determined.
     * @return An integer representing the capacity of the specified Room.
     */
    public int getRoomCapacity(int roomNo){
        Room room = thisRoomNo(roomNo);
        if (room == null){
            return 0;
        } else {
            return room.getCapacity();
        }
    }

    /**
     * Gets the time at which the specified Event takes place.
     * @param eventName A String representing the name of the Event whose time is being determined.
     * @return A LocalDateTime object representing the time at which the specified Event takes place.
     */
    public LocalDateTime getEventTime(String eventName){
        Event event = eventNamed(eventName);
        return event.getStartTime();
    }


    /**
     * Determines whether or not the specified room can be scheduled a new event at the specified time.
     * @param dateTimeFormat A String representing the time. The string needs to be in the format of DateTime.
     * @param roomNo The number of the Room.
     * @return A boolean value. Returns true if the specified Room can accept a new schedule at the specified time.
     */
    public boolean canSchedule(String dateTimeFormat, int roomNo){
        Room room = thisRoomNo(roomNo);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeFormat, formatter);
        String[] formatTimeList = dateTimeFormat.split(" ");
        String formatTime = formatTimeList[1];
        LocalTime time = LocalTime.parse(formatTime, timeFormatter);
        if(time.isAfter(lastHour) | time.isBefore(firstHour)){
            return false;
        }
        return !room.getSchedule().containsKey(dateTime);
    }

    /**
     * Opens a new room with the specified room number.
     * @return A boolean value. Returns true if a Room with the specified room number doesn't yet exist and is successfully added.
     */
    public boolean openNewRoom(int roomNo) {
        if (this.isExistingRoom(roomNo)) {
            return false;
        } else {
            addToAllRooms(new Room(roomNo));
            return true;
        }
    }

    /**
     * Evaluates whether or not a room with the specified room number already exists.
     * @param roomNo An integer representing the room number to be evaluated.
     * @return A boolean value. Returns true if a Room with the specified room number already exists.
     */
    public boolean isExistingRoom(int roomNo) {

        Room room = this.thisRoomNo(roomNo);
        return !(room == null);
    }


    /**
     * Reschedule the specified event at the specified time and room.
     * @param eventName A String representing the name of the Event that is being rescheduled.
     * @param newStartTime A String representing the start time at which the Event should be rescheduled to.
     * @param newEndTime A String representing the end time at which the Event should be rescheduled to.
     * @param roomNo An integer representing the room number where the Event should be rescheduled to.
     * @return A boolean value. Returns true if the rescheduling is successful.
     */
    public boolean rescheduleEvent(String eventName, String newStartTime, String newEndTime, int maxCapacity, int roomNo) {
        String orgName = this.eventNamed(eventName).getOrganizer();
        if (this.canSchedule(newStartTime, roomNo)) {
            this.cancelEvent(eventName);
            this.makeNewEvents(eventName, newStartTime, newEndTime, roomNo, maxCapacity, orgName);
            return true;
        } else{
            return false;
        }
    }


    /**
     * Serialize the all scheduled events and all rooms.
     */
    public void serialize() {
        try {
            FileOutputStream fos = new FileOutputStream("phase2/ser_files/Event.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(allEvents);
            oos.close();
            fos.close();
            FileOutputStream fos2 = new FileOutputStream("phase2/ser_files/Room.ser");
            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
            oos2.writeObject(allRooms);
            oos2.close();
            fos2.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Deserialize all scheduled events from "Event.ser" file and all rooms from "Room.ser" file.
     */
    public boolean deserialize(){

        ArrayList<Event> events;
        ArrayList<Room> rooms;
        try {
            FileInputStream fis = new FileInputStream("phase2/ser_files/Event.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            events = (ArrayList<Event>) ois.readObject();

            ois.close();
            fis.close();

            FileInputStream fis2 = new FileInputStream("phase2/ser_files/Room.ser");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);

            rooms = (ArrayList<Room>) ois2.readObject();

            ois2.close();
            fis2.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return false;
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return false;
        }

        allEvents = events;

        allRooms = rooms;

        return true;
    }

    /**
     * Modify the capacity of the specified room by the specified amount.
     * @param roomNo An integer representing the number of the specified Room.
     * @param constant An integer representing the amount that the capacity is modified by.
     * @return A boolean value showing whether or not this modification is successful.
     */
    public boolean modifyRoomCapacity(int roomNo, int constant) {
        return thisRoomNo(roomNo).modifyCapacity(constant);
    }


    /**
     * Gets the the list of all rooms in text format.
     * @return A String representation of all rooms in this program.
     */
    public List<String> getAllRoomsInString() {
        List<String> roomsInString= new ArrayList<>();
        for (Room room: this.getAllRooms()) {
            roomsInString.add(Integer.toString(room.getRoomNumber()));
        }
        return roomsInString;
    }

    public String getEventInfo(String event){
        return eventNamed(event).toString();
    }


    public boolean futureEvent(String eventName){
        Event event = eventNamed(eventName);
        return event.getStartTime().isAfter(LocalDateTime.now());
    }



//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//
//    LocalTime firstHour = LocalTime.of(9, 0);
//    LocalTime lastHour = LocalTime.of(16, 0);

    public List<String> getAllEventsOfDay(String chosenDate){
        List<String> eventList = new ArrayList<>();
        for (Event event:allEvents){
            String dateTime = formatter.format(event.getStartTime());
            String[] formatDateList = dateTime.split(" ");
            String formatDate = formatDateList[0];
            if(formatDate.equals(chosenDate)){
                eventList.add(event.getName());
            }
        }
        return eventList;
    }

    /**
     * Check whether specific capacity is valid for a room.
     * @param roomNo An integer representing the room number where the Event should be rescheduled to.
     * @return A boolean value. Returns true if the capacity is valid.
     */
    public boolean possibleCapacity(int roomNo, int capacity) {
        Room room = this.roomNumbered(roomNo);
        return room.getCapacity() >= capacity;
    }

    /**
     * Sets the the capacity of an event.
     *  @param eventName A String representing the name of the Event that is being rescheduled.
     *  @param eventCap An integer representing the new capacity wanted.
     */
    public void setEventCapacity(String eventName, int eventCap) {
        Event event = eventNamed(eventName);
        event.setMaxCapacity(eventCap);
    }
}
