package controllers;

import java.util.ArrayList;
import java.util.List;

import meta.Requester;
import de.phoenix.rs.entity.PhoenixDetails;
import de.phoenix.rs.entity.PhoenixLecture;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.createLecture;
import views.html.showLectures;
import views.html.stringShower;

public class LectureApplication extends Controller {

    public static Result createLecture() {
        return ok(createLecture.render("Create Lecture"));
    }
    
    public static Result sendLecture() {        
        //Arrays to get the inputs form CreateLecture
        String[] keyStrings = new String[] {"title", "room", "day", "startHours","startMinutes", "endHours", 
                                            "endMinutes", "period", "startYear", "startMonth", "startDay",
                                            "endYear", "endMonth", "endDay"};
        String[] keyStrings2 = new String[] {"room2", "day2", "startHours2","startMinutes2", "endHours2", 
                                            "endMinutes2", "period2", "startYear2", "startMonth2", "startDay2",
                                            "endYear2", "endMonth2", "endDay2"};
        String[] keyStrings3 = new String[] {"room3", "day3", "startHours3","startMinutes3", "endHours3", 
                                            "endMinutes3", "period3", "startYear3", "startMonth3", "startDay3",
                                            "endYear3", "endMonth3", "endDay3"};
        String[][] details = new String[][] {keyStrings, keyStrings2, keyStrings3};
        //Arrays, which will be filled with the requeststrings
        String[][] requests = new String[3][13];
        
        String title = "";
        //if input is missing don't create a detail later
        boolean[] wrongInput = new boolean[] {false,false,false};
        int itemCount = 0;
        int arrayCount = 0;
        for(String[] stringArray: details){   
            // TODO: exception handling
            // Get the requests and if something missing, set wronginput[arrayCount] to true and test the next detail
            for(String item: stringArray){
                String temp = Form.form().bindFromRequest().get(item);
                if ((Form.form().bindFromRequest().get("box1") == null) && (arrayCount == 1) ||(Form.form().bindFromRequest().get("box2") == null) && (arrayCount == 2)){
                    wrongInput[arrayCount] = true;         //TODO: if (wrongInput == true) throw IOException;
                    break;                
                }
                //if everything's filled in, set title and put the rest in the requestarrays
                else {
                    if (itemCount == 0) {
                        title = temp;
                        itemCount++;
                    }
                    else{
                        requests[arrayCount][itemCount-1] = temp;
                        itemCount++;               
                    }
                }
            }
            //just one title so start next loop at itemcount 1
            itemCount = 1;
            arrayCount++;
        } 
        //PhoenixDetaillist
        List<PhoenixDetails> allDetails = new ArrayList<PhoenixDetails>();
        int boolIndex = 0;
        for(String[] item: requests){
            //only create LectureCheck if detail is complete
            if(!wrongInput[boolIndex]){
                LectureCheck lectureCheck = new LectureCheck(item);
                //add it to allDetails
                allDetails.add(lectureCheck.getPhoenixDetails());
            }
            boolIndex++;
        }
        //send it to server
        Requester.Lecture.create(title, allDetails);
        
        if(Requester.Lecture.getStatus() == 200){
            return ok(stringShower.render("strings to show", "Good News!"));
        }else{
            return ok(stringShower.render("send Lecture", "Ups, da ist ein Fehler aufgetreten!(" + Requester.Lecture.getStatus() + ")"));
        }
    }
    
    public static Result showLectures(){
        List<PhoenixLecture> lectures = Requester.Lecture.getAll();  
        if (Requester.Lecture.getStatus() == 200)
            return ok(showLectures.render("show Lectures", lectures));
        else
            return ok(stringShower.render("send Lecture", "Ups, da ist ein Fehler aufgetreten!(" + Requester.Lecture.getStatus() + ")")); 
    }
    
    public static Result deleteLecture(){
        Requester.Lecture.delete( Form.form().bindFromRequest().data().get("lecture") );
        
        if (Requester.Lecture.getStatus() != 200)
            return ok(stringShower.render("lecture delete", "Ups, da ist ein Fehler aufgetreten!(" + Requester.Lecture.getStatus() + ")"));
        
        return ok(stringShower.render("Lecture deleted", "Lecture deleted!"));
    } 
    
}
