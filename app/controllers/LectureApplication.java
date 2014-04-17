package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import meta.Requester;
import de.phoenix.date.Weekday;
import de.phoenix.rs.entity.PhoenixDetails;
import de.phoenix.rs.entity.PhoenixLecture;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import util.LectureCheck;
import views.html.*;

public class LectureApplication extends Controller {

    public static Result createLecture() {
        LocalTime time = new LocalTime(00,00); 
        LocalDate date = new LocalDate(0001,01,01);
        Period period = Period.ZERO;
        PhoenixDetails details = new PhoenixDetails("", Weekday.MONDAY, time, time, period, date, date);
        List<PhoenixDetails> listDetails = new ArrayList<PhoenixDetails>();
        listDetails.add(details);
        PhoenixLecture lecture = new PhoenixLecture("", listDetails);
        return ok(createLecture.render("Create Lecture", lecture));
    }
    
    public static Result sendLecture() {   
        Map<String, String> data = Form.form().bindFromRequest().data();
        String title = data.get("title");
        if(data.get("submit").equals("Create")){
            Requester.Lecture.create(title, createDetails(data));   
            if(Requester.Lecture.getStatus() == 200)
                return ok(stringShower.render("strings to show", "Good News!"));
            else
                return util.Err.displayError(Requester.Lecture.getStatus(),"Error creating this lecture!");
        }else if(data.get("submit").equals("Update")){          
            String oldTitle = data.get("oldLecture");
            PhoenixLecture oldLecture = Requester.Lecture.get(oldTitle);
            
            //Update Details
            List<PhoenixDetails> oldDetails = oldLecture.getLectureDetails();
            List<PhoenixDetails> newDetails = createDetails(data);
            
            //Update Title
            if(!oldTitle.equals(title)){
                PhoenixLecture newLecture = new PhoenixLecture(title, newDetails);
                setNewDetails(oldDetails, newDetails, oldLecture);
                setNewDetails(oldDetails, newDetails, newLecture);
                // status!
                Requester.Lecture.update(oldLecture, newLecture);


            }else{
                setNewDetails(oldDetails, newDetails, oldLecture);
            }
            
            if(Requester.Lecture.getStatus() == 200)
                return ok(stringShower.render("strings to show", "Good News!"));
            else
                return util.Err.displayError(Requester.Lecture.getStatus(),"Error updating this lecture!");
        }else
            return util.Err.displayError(Requester.Lecture.getStatus(),"Error creating/updating this lecture!");
    }
    

    private static void setNewDetails(List<PhoenixDetails> oldDetails, List<PhoenixDetails> newDetails, PhoenixLecture newLecture){
        int oldDetailsCount = oldDetails.size();
        int newDetailsCount = newDetails.size();
        
        if(oldDetailsCount <= newDetailsCount){
            updateDetails(oldDetails, newDetails, oldDetailsCount);
            if(oldDetailsCount < newDetailsCount){
                List<PhoenixDetails> newDetailList = new ArrayList<PhoenixDetails>();
                for(int i = oldDetailsCount; i < newDetailsCount; i++){
                    newDetailList.add(newDetails.get(i));
                }
                Requester.Lecture.addDetails(newLecture, newDetailList);
                if (Requester.Lecture.getStatus() != 200){
                    util.Err.displayError(Requester.Lecture.getStatus(),"Error adding some Details to this lecture!"); 
                }
            }
            
        }else{
            updateDetails(oldDetails, newDetails, newDetailsCount);
            for(int i = newDetailsCount; i < oldDetailsCount; i++){
                Requester.Lecture.deleteDetails(oldDetails.get(i));
                if (Requester.Lecture.getStatus() != 200){
                    util.Err.displayError(Requester.Lecture.getStatus(),"Error deleting this Detail!"); 
                    break;
                }
            }
        }
    }

    private static void updateDetails(List<PhoenixDetails> oldDetails, List<PhoenixDetails> newDetails, int updateCount){
        for(int i = 0; i < updateCount; i++){
            Requester.Lecture.updateDetails(oldDetails.get(i), newDetails.get(i));
            if (Requester.Lecture.getStatus() != 200){
                util.Err.displayError(Requester.Lecture.getStatus(),"Error updating this Detail!"); 
                break;
            }
        } 
    }
    
    
    private static List<PhoenixDetails> createDetails(Map<String, String> data){
        List<PhoenixDetails> allDetails = new ArrayList<PhoenixDetails>();
        for(String item: data.keySet()){
            String number = "";
            if(item.startsWith("room_")){
                number = item.substring(5);
                System.out.println(data.get("startDate_"+number));
                allDetails.add(LectureCheck.createPhoenixDetails( data.get("room_"+number), 
                                                               data.get("day_"+number),
                                                               LectureCheck.getTime(data.get("startTime_"+number)),
                                                               LectureCheck.getTime(data.get("endTime_"+number)),
                                                               LectureCheck.getPeriod(Integer.parseInt(data.get("period_"+number)), data.get("periodDD_"+number)),
                                                               LectureCheck.getDate(data.get("startDate_"+number)),
                                                               LectureCheck.getDate(data.get("endDate_"+number))));
            }
        }
        return allDetails;
    }
    

    public static Result existLecture(){
        String lectureTitle = request().queryString().get("title")[0];
        Requester.Lecture.get(lectureTitle);
        if(Requester.Lecture.getStatus()==200){
            System.out.println("habe Veranstaltung gefunden!");
        }
            return ok();
            
    }
    
    public static Result showLectures(){
        List<PhoenixLecture> lectures = Requester.Lecture.getAll();  
        if (Requester.Lecture.getStatus() == 200)
            return ok(showLectures.render("show Lectures", lectures));
        else
            return util.Err.displayError(Requester.Lecture.getStatus(),"Error receiving lecture information!");
    }
    
    public static Result deleteLecture(){
        Requester.Lecture.delete( Form.form().bindFromRequest().data().get("lecture") );
        
        if (Requester.Lecture.getStatus() != 200)
            return util.Err.displayError(Requester.Lecture.getStatus(),"Error deleting this lecture!");
        
        return ok(showLectures.render("show Lectures", Requester.Lecture.getAll()));
    } 
    
    public static Result updateLecture(){
        String title = Form.form().bindFromRequest().get("lecture");
        PhoenixLecture lecture = Requester.Lecture.get(title);
        System.out.println(lecture.getLectureDetails().get(0).getStartDate());
        return ok(createLecture.render("Create Lecture", lecture));
    }
    
}
