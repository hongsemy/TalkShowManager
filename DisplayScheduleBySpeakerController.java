package controller.DisplayScheduleStrategy;

import Presenter.DisplaySchedulePresenter;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DisplayScheduleBySpeakerController implements Displayer{


    DisplaySchedulePresenter displaySchedulePresenter;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DisplayScheduleBySpeakerController(DisplaySchedulePresenter dsp){
        displaySchedulePresenter = dsp;
    }

    public boolean display(){
        System.out.println(displaySchedulePresenter.chooseSpeakerToSee());
        String choice;
        Scanner in = new Scanner(System.in);
        choice = in.nextLine();
        if (choice.equals("")){
            return false;
        }
        System.out.println(displaySchedulePresenter.scheduleBySpeakerDisplay(choice));
        return true;
    }
}
