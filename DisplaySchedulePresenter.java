package Presenter;

import use_case.EventManager;
import use_case.UserManager;

import java.util.ArrayList;
import java.util.List;

public class DisplaySchedulePresenter {

    EventManager eventManager;
    UserManager userManager;


    public DisplaySchedulePresenter(EventManager em, UserManager um){
        eventManager = em;
        userManager = um;
    }

    public String chooseDateToSee(){
        StringBuilder info = new StringBuilder();
        info.append("Please choose a day to view scheduled events. \n");
        info.append("Correct format is: yyyy-MM-dd");
        return info.toString();
    }


    public String dayScheduleDisplay(String chosenDate){
        List<String> events = eventManager.getAllEventsOfDay(chosenDate);
        StringBuilder returnString = new StringBuilder();
        returnString.append("All scheduled events of ").append(chosenDate).append(":\n\n");
        for (String event: events){
            returnString.append(eventManager.getEventInfo(event)).append("\n");
        }
        return returnString.toString();
    }

    public String myScheduleDisplay(){
        List<String> eventList = userManager.getCurrentUserEvents();
        StringBuilder returnString = new StringBuilder();
        returnString.append("Your upcoming events:\n\n");
        for (String event: eventList){
            if (eventManager.futureEvent(event)){
                returnString.append(eventManager.getEventInfo(event)).append("\n");
            }
        }
        return returnString.toString();
    }

    public String scheduleBySpeakerDisplay(String speaker){
        List<String> events = eventManager.getAllEventNames();
        StringBuilder returnString = new StringBuilder();
        returnString.append("Upcoming events of ").append(speaker).append(":\n\n");
        for (String event: events){
            if (eventManager.getSpeakersOfEvent(event).contains(speaker)){
                returnString.append(eventManager.getEventInfo(event)).append("\n");
            }
        }
        return returnString.toString();
    }

    public String chooseSpeakerToSee(){
        return "Type in the name of the speaker to view schedule";
    }
}
