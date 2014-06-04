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
import util.Http;
import util.LectureCheck;
import views.html.*;
import bootstrap.html.*;

public class LectureApplication extends Controller {

    public static Result createLecture() {
        LocalTime time = new LocalTime(00,00); 
        LocalDate date = new LocalDate(util.TimeGroup.now("Y-MM-dd"));
        Period period = Period.ZERO;
        PhoenixDetails details = new PhoenixDetails("", Weekday.MONDAY, time, time, period, date, date);
        List<PhoenixDetails> listDetails = new ArrayList<PhoenixDetails>();
        listDetails.add(details);
        PhoenixLecture lecture = new PhoenixLecture("", "", listDetails);
        return ok(bootstrap.html.createNewLecture.render("Create Lecture", lecture));
    }
    
    public static Result sendLecture() {   
        Map<String, String> data = Form.form().bindFromRequest().data();
        String title = data.get("title");
        String description = data.get("description");
        System.out.println(data.get("startDate_1"));
        System.out.println(data.get("endDate_1"));
        if(data.get("submit").equals("Create")){
            Requester.Lecture.create(title, description, LectureCheck.createDetails(data));   
            if(Requester.Lecture.getStatus() == 200)
                return ok(bootstrap.html.stringShower.render("success","Erfolgreich", "Die Veranstaltung wurde erfolgreich erstellt."));
            else
                return util.Err.displayError(Requester.Lecture.getStatus(),"Error creating this lecture!");
        }else if(data.get("submit").equals("Update")){          
            String oldTitle = data.get("oldLecture");
            PhoenixLecture oldLecture = Requester.Lecture.get(oldTitle);
            
            //Update Details
            List<PhoenixDetails> oldDetails = oldLecture.getLectureDetails();
            List<PhoenixDetails> newDetails = LectureCheck.createDetails(data);
            
            //Update Title
            if(!oldTitle.equals(title)){
                PhoenixLecture newLecture = new PhoenixLecture(title, oldDetails);
                Requester.Lecture.update(oldLecture, newLecture);
                
                //update Details for new Lecture
                LectureCheck.setNewDetails(oldDetails, newDetails, newLecture);
         
            // Titel is the same, so just update the details
            }else{
                LectureCheck.setNewDetails(oldDetails, newDetails, oldLecture);
            }
            
            if(Requester.Lecture.getStatus() == 200)
                return ok(bootstrap.html.stringShower.render("success","Erfolgreich", "Die Änderung wurde gespeichert."));
            else
                return util.Err.displayError(Requester.Lecture.getStatus(),"Error updating this lecture!");
        }else
            return util.Err.displayError(Requester.Lecture.getStatus(),"Error creating/updating this lecture!");
    }
    
    public static Result existLecture(){
        String lectureTitle = request().queryString().get("title")[0];
        Requester.Lecture.get(lectureTitle);
        if(Requester.Lecture.getStatus()==200){
            System.out.println("habe Veranstaltung gefunden!");
            return ok();  
        }else{
            return notFound();
        }        
    }
    
    public static Result showLectures(){   
        if (Http.GET("option") != null)
            if (Http.GET("option").equals("all"))
                return ok(bootstrap.html.showAllLectures.render("Alle Veranstaltungen", Requester.Lecture.getAll()));
            else
                return ok(bootstrap.html.showLecture.render("Veranstaltung", 
                            Requester.Lecture.get(Http.GET("option")),
                            Requester.Group.getAll(Http.GET("option"))) 
                        );
       return util.Err.displayError(Requester.Lecture.getStatus(),"Error receiving lecture information!");
    }
    
    public static Result deleteLecture(){
        Requester.Lecture.delete( Form.form().bindFromRequest().data().get("lecture") );        
        if (Requester.Lecture.getStatus() != 200)
            return util.Err.displayError(Requester.Lecture.getStatus(),"Error deleting this lecture!");
        else
            return ok(views.html.showLectures.render("show Lectures", Requester.Lecture.getAll()));
    } 
    
    public static Result updateLecture(){
        PhoenixLecture lecture = Requester.Lecture.get(Form.form().bindFromRequest().get("lecture"));
        return ok(createLecture.render("Create Lecture", lecture));
    }
    
    
    
}
