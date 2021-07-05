package controller.DisplayScheduleStrategy;

import Presenter.DisplaySchedulePresenter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DisplayScheduleByDayController implements Displayer{

    DisplaySchedulePresenter displaySchedulePresenter;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DisplayScheduleByDayController(DisplaySchedulePresenter dsp){
        displaySchedulePresenter = dsp;
    }

    public boolean display() {
        System.out.println(displaySchedulePresenter.chooseDateToSee());
        String choice;
        Scanner in = new Scanner(System.in);
        choice = in.nextLine();
        if (choice.equals("")) {
            return false;
        }
        try {
            LocalDate.parse(choice, formatter);
            System.out.println(displaySchedulePresenter.dayScheduleDisplay(choice));
            return true;

        } catch (DateTimeParseException e) {
            displaySchedulePresenter.chooseDateToSee();
            return display();
        }
    }
}
