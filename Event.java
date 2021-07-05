package entity;

import java.io.Serializable;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Represents an event at this conference.
 */
public class Event implements Serializable {

    private final String name;
    private String event_type;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private final String duration;
    private final String organizer;
    private int room;
    private int maxCapacity;
    /**
     * Represents a list of attendees coming to this Event.
     */
    private final List<String> attendeeList;
    /**
     * Represents a list of speakers giving a talk at this Event.
     */
    private final List<String> speakers;


    /**
     * Creates a new Event with the specified name, start time, end time, organizer, and room.
     *
     * @param name      A String representing the name of this Event.
     * @param start_time      A LocalDateTime object representing the starting time of this Event.
     * @param end_time      A LocalDateTime object representing the end time of this Event.
     * @param organizer A String representing the name of the Organizer who is responsible for this Event.
     * @param room      An Integer representing te number of the room that this Event is held in.
     */
    public Event(String name, String start_time, String end_time, String organizer, int room, int maxCapacity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.name = name;
        this.event_type = "Party";
        this.start_time = LocalDateTime.parse(start_time, formatter);
        this.end_time = LocalDateTime.parse(end_time, formatter);
        long dur = Duration.between(getStartTime(), getEndTime()).toMinutes();
        this.duration = String.valueOf(dur);
        this.organizer = organizer;
        this.speakers = new ArrayList<>();
        this.room = room;
        this.attendeeList = new ArrayList<>();
        this.maxCapacity = maxCapacity;
    }

    //TODO: Constructor overloading if we don't know speaker yet
    //       If this is unnecessary, no need to implement it.

    // getters for every attribute

    /**
     * Gets the name of this Event.
     *
     * @return A String representing the name of this Event.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type - party, talk, or panel discussion - of this Event.
     *
     * @return A String representing the type of this Event.
     */
    public String getEventType(){
        return event_type;
    }

    /**
     * Gets the time this Event starts at.
     *
     * @return A LocalDateTime object representing the time this Event starts at.
     */
    public LocalDateTime getStartTime() {
        return start_time;
    }

    /**
     * Gets the time this Event ends at.
     *
     * @return A LocalDateTime object representing the time this Event ends at.
     */
    public LocalDateTime getEndTime() {
        return end_time;
    }

    /**
     * Gets the duration of this Event.
     *
     * @return A String object representing the duration of this Event.
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Gets the name of the organizer who is responsible for this Event.
     *
     * @return A String representing the name of the Organizer who is responsible for this Event.
     */
    public String getOrganizer() {
        return organizer;
    }

    /**
     * Gets the list of the names of the speakers who are giving a talk at this Event.
     *
     * @return An ArrayList of the names of the Speakers who are giving a talk at this Event.
     */
    public List<String> getSpeakers() {
        return speakers;
    }

    /**
     * Gets the number of the room that this Event is being held in.
     *
     * @return An Integer representing the number of the room that this Event is being held in.
     */
    public int getRoom() {
        return room;
    }

    /**
     * Gets the list of attendees coming to this Event.
     *
     * @return An ArrayList of Strings representing the names of the Attendees coming to this Event.
     */
    public List<String> getAttendeeList() {
        return attendeeList;
    }


    /**
     * Adds the specified speaker to this Event.
     *
     * @param speaker A String representing the name of the Speaker who is newly added to this Event.
     */
    public void addSpeaker(String speaker) {
        speakers.add(speaker);
        if (speakers.size() == 1){
            event_type = "Talk";
        }
        if (speakers.size() > 1){
            event_type = "Panel Discussion";
        }
    }

    /**
     * Removes the specified speaker from this Event.
     *
     * @param speaker A String representing the name of the Speaker who is no longer giving a talk at this Event.
     */
    public void removeSpeaker(String speaker) {
        speakers.remove(speaker);
        if (speakers.isEmpty()){
            event_type = "Party";
        }
        if (speakers.size() == 1){
            event_type = "Talk";
        }
        if (speakers.size() > 1){
            event_type = "Panel Discussion";
        }
    }

    /**
     * Adds the specified attendee to this Event.
     *
     * @param attendee A String representing the name of the Attendee who is newly attending to this Event.
     */
    public void addAttendee(String attendee) {
        attendeeList.add(attendee);
    }

    /**
     * Removes the specified attendee from this Event.
     *
     * @param attendee A String representing the name of the Attendee who is no longer attending this Event.
     */
    public void removeAttendee(String attendee) {
        attendeeList.remove(attendee);
    }

    /**
     * Set the time that this Event starts at.
     *
     * @param time A String representing the time that this Event starts at.
     */
    public void setStartTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.start_time = LocalDateTime.parse(time, formatter);
    }

    /**
     * Set the time that this Event ends at.
     *
     * @param time A String representing the time that this Event ends at.
     */
    public void setEndTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.end_time = LocalDateTime.parse(time, formatter);
    }

    /**
     * Set the room that this Event is being held in.
     *
     * @param room A String the name of the room that this Event is being held in.
     */
    public void setRoom(int room) {
        this.room = room;
    }

    @ Override
    public String toString(){
        StringBuilder info = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        String startTimeString = this.start_time.format(formatter);
        String endTimeString = this.end_time.format(formatter);
        info.append("Event: ").append(this.name).append("\n");
        info.append("Type: ").append(this.event_type).append("\n");
        info.append("Time: ").append(startTimeString).append(" to ").append(endTimeString).append(" (").
                append(this.duration).append(" minutes)").append("\n");
        info.append("Speakers: ");
        if(speakers.size()==0){
            info.append("none").append("\n");
        } else {
            for (int i = 0; i < speakers.size() - 1; i++) {
                info.append(speakers.get(i)).append(", ");
            }
            info.append(speakers.get(speakers.size() - 1)).append("\n");
        }
        info.append("Room no: ").append(this.room).append("\n");
        return info.toString();
    }
    /**
     * Gets the maximum capacity of this Event.
     *
     * @return An Integer representing the maximum capacity of this Event.
     */
    public int getMaxCapacity(){
        return this.maxCapacity;
    }

    /**
     * Sets the maximum capacity of this event.
     *
     * @param newCap An Integer representing the wanted capacity to be modified.
     */
    public void setMaxCapacity(int newCap) {this.maxCapacity = newCap; }
}
