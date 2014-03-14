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
import views.html.createLecture;
import views.html.showLectures;
import views.html.stringShower;

public class LectureApplication extends Controller {

    public static Result createLecture() {
        LocalTime time = new LocalTime(0,0); 
        LocalDate date = new LocalDate(1,1,1);
        Period period = Period.ZERO;
        PhoenixDetails details = new PhoenixDetails("", Weekday.MONDAY, time, time, period, date, date);
        List<PhoenixDetails> listDetails = new ArrayList<PhoenixDetails>();
        listDetails.add(details);
        PhoenixLecture lecture = new PhoenixLecture("", listDetails);
        return ok(createLecture.render("Create Lecture", lecture));
    }
    
    public static Result sendLecture() {        
        Map<String, String> data = Form.form().bindFromRequest().data();
        System.out.println(data);
        String title = data.get("title");
        
        List<PhoenixDetails> allDetails = new ArrayList<PhoenixDetails>();
        for(String item: data.keySet()){
            String number = "";
            if(item.startsWith("room_")){
                number = item.substring(5);
                System.out.println(number);
                allDetails.add(LectureCheck.getPhoenixDetails( data.get("room_"+number), 
                                                               data.get("day_"+number),
                                                               LectureCheck.getTime(data.get("startTime_"+number)),
                                                               LectureCheck.getTime(data.get("endTime_"+number)),
                                                               LectureCheck.getPeriod(Integer.parseInt(data.get("period_"+number)), data.get("periodDD_"+number)),
                                                               LectureCheck.getDate(data.get("startDate_"+number)),
                                                               LectureCheck.getDate(data.get("endDate_"+number))));
            }
        } 
        Requester.Lecture.create(title, allDetails);
        
        if(Requester.Lecture.getStatus() == 200){
            return ok(stringShower.render("strings to show", "Good News!"));
        }else{
            return ok(stringShower.render("send Lecture", "Ups, da ist ein Fehler aufgetreten!(" + Requester.Lecture.getStatus() + ")"));
        }
        
        /************************ OLD STUFF !!!
        
        PhoenixLecture lecture = new PhoenixLecture(title, allDetails);
        if(Form.form().bindFromRequest().get("submit").equals("Create")){
            WebResource ws = PhoenixLecture.createResource(CLIENT, BASE_URI);
            //send it to server
            ClientResponse response = ws.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, lecture);
            if(response.getStatus() == 200){
                return ok(stringShower.render("strings to show", "Good News!"));
            }else{
                return ok(stringShower.render("send Lecture", "Ups, da ist ein Fehler aufgetreten!(" + response.getStatus() + ")"));
            }
        }
        else if (Form.form().bindFromRequest().get("submit").equals("Update")){
            
            SelectEntity<PhoenixLecture> selectLecture = new SelectEntity<PhoenixLecture>().addKey("title", Form.form().bindFromRequest().get("oldLecture"));
            UpdateEntity<PhoenixLecture> updateLecture = new UpdateEntity<PhoenixLecture>(lecture, selectLecture);
            //Lecture Webresources
            WebResource wsUpdateLec = PhoenixLecture.updateResource(CLIENT, BASE_URI);  
            
             Update Details throws 500-error.
            WebResource wsGetLec = PhoenixLecture.getResource(CLIENT, BASE_URI);   
            //Detail Webresources
            WebResource wsDetails = PhoenixDetails.updateResource(CLIENT, BASE_URI);
            //Lecture ClientResponses
            ClientResponse resGetLec= wsGetLec.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, selectLecture);
            
            //old Stuff
            PhoenixLecture oldLecture = EntityUtil.extractEntity(resGetLec);
            List<PhoenixDetails> oldDetails = oldLecture.getLectureDetails();
            //Update Details
            UpdateEntity<PhoenixDetails> updateDetails = KeyReader.createUpdate(oldDetails.get(0), allDetails.get(0));
            ClientResponse responseDetails = wsDetails.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, updateDetails);
            if (responseDetails.getStatus() != 200) return ok(stringShower.render("update Lecture", "Fehler bei Detail-Update(" + responseDetails.getStatus() + ")"));
            
            //Update Lecture
            ClientResponse resUpdateLec= wsUpdateLec.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, updateLecture);
            if(resUpdateLec.getStatus() == 200){
                return ok(stringShower.render("send Lecture", "Good News!"));
            }else{
                return ok(stringShower.render("send Lecture", "Ups, da ist ein Fehler aufgetreten!(" + resUpdateLec.getStatus() + ")"));
            }
        }else return ok(stringShower.render("send Lecture", "Etwas unerwartetes ist passiert"));
        
        
        *****/
    }
    

    public static Result existLecture(){
        String lectureTitle = request().queryString().get("title")[0];
//        try{
            Requester.Lecture.get(lectureTitle);
//        }catch(ClientHandlerException e){
//            return ok();
//        }
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
            return ok(stringShower.render("send Lecture", "Ups, da ist ein Fehler aufgetreten!(" + Requester.Lecture.getStatus() + ")")); 
    }
    
    public static Result deleteLecture(){
        Requester.Lecture.delete( Form.form().bindFromRequest().data().get("lecture") );
        
        if (Requester.Lecture.getStatus() != 200)
            return ok(stringShower.render("lecture delete", "Ups, da ist ein Fehler aufgetreten!(" + Requester.Lecture.getStatus() + ")"));
        
        return ok(showLectures.render("show Lectures", Requester.Lecture.getAll()));
    } 
    
    public static Result updateLecture(){
        String title = Form.form().bindFromRequest().get("lecture");
        System.out.println(title);
        PhoenixLecture lecture = Requester.Lecture.get(title);
        return ok(createLecture.render("Create Lecture", lecture));
    }
    
}
