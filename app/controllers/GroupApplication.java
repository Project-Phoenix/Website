package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import meta.Requester;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

import de.phoenix.date.Weekday;
import de.phoenix.rs.entity.PhoenixDetails;
import de.phoenix.rs.entity.PhoenixLecture;
import de.phoenix.rs.entity.PhoenixLectureGroup;
import de.phoenix.rs.entity.PhoenixLectureGroupTaskSheet;
import de.phoenix.rs.entity.PhoenixTaskSheet;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import util.LectureCheck;
import util.TimeGroup;
import views.html.addGroup;
import views.html.addTaskSheetToGroup;
import views.html.showGroups;
import views.html.showLectureGroupTaskSheets;
import views.html.stringShower;

public class GroupApplication extends Controller {
   
    
    public static Result addGroup() {
        LocalTime time = new LocalTime(00,00); 
        LocalDate date = new LocalDate(util.TimeGroup.now("Y-MM-dd"));
        Period period = Period.ZERO;
        PhoenixDetails details = new PhoenixDetails("", Weekday.MONDAY, time, time, period, date, date);
        List<PhoenixDetails> listDetails = new ArrayList<PhoenixDetails>();
        listDetails.add(details);
        PhoenixLecture emptyLecture = new PhoenixLecture("", listDetails);
        PhoenixLectureGroup emptyGroup = new PhoenixLectureGroup("", 0, Weekday.MONDAY, time, listDetails, emptyLecture);
        return ok(addGroup.render("add Group", Requester.Lecture.getAll(), emptyGroup));
    }
    
    public static Result sendGroup() {        
        //Array to get the inputs form addGroup
        Map<String, String> data = Form.form().bindFromRequest().data();
        String title = data.get("title");
        String lecture = data.get("selectLecture");
        int size = Integer.parseInt(data.get("size"));
        String submissionDay = data.get("submissionDay");
        LocalTime submissionTime = LectureCheck.getTime(data.get("submissionTime"));       
        
        if(data.get("Submit").equals("Create")){
            Requester.Lecture.addGroup(lecture, title, size, submissionDay, submissionTime, LectureCheck.createDetails(data));
            if(Requester.Lecture.getStatus() == 200)
                return ok(stringShower.render("strings to show", "Good News!"));
            else
                return util.Err.displayError(Requester.Lecture.getStatus(),"Error adding this group!");
        
        }else if(data.get("Submit").equals("Update")){
            String oldName = data.get("oldName");
            String oldLectureTitle = data.get("oldLecture");
            PhoenixLectureGroup oldGroup = Requester.Group.get(oldLectureTitle, oldName);
            System.out.println(oldGroup);
            
            //Update Details
            List<PhoenixDetails> oldDetails = oldGroup.getDetails();
            List<PhoenixDetails> newDetails = LectureCheck.createDetails(data);
            
            //Update Title
            PhoenixLectureGroup newGroup = new PhoenixLectureGroup(title, size, Weekday.forName(submissionDay), submissionTime, oldDetails, Requester.Lecture.get("lecture"));
            Requester.Group.update(oldGroup, newGroup);
            System.out.println("groupstatus:" + Requester.Group.getStatus());
            System.out.println("Lecturestatus:" + Requester.Lecture.getStatus());
            //update Details for new Lecture
            LectureCheck.setNewDetails(oldDetails, newDetails, newGroup);
            
            if (Requester.Lecture.getStatus() == 200)
                return ok(stringShower.render("strings to show", "Good News!"));
            else
                return util.Err.displayError(Requester.Lecture.getStatus(),"Error updating the group");       
        }else
            return util.Err.displayError(Requester.Lecture.getStatus(),"Error creating/updating this group!");
    }
    
    public static Result chooseGroups() {
        List<PhoenixLecture> lectures = Requester.Lecture.getAll();
        List<PhoenixLectureGroup> empty = new ArrayList<PhoenixLectureGroup>();
        if (Requester.Lecture.getStatus() == 200)
            return ok(showGroups.render("show Groups", empty, lectures));
        else
            return util.Err.displayError(Requester.Lecture.getStatus(),"Error receiving lecture information!");  
          
    }
    
    public static Result showLectureGroups() {
        List<PhoenixLecture> lectures = Requester.Lecture.getAll();   
        List<PhoenixLectureGroup> groups = Requester.Group.getAll( Form.form().bindFromRequest().get("lecture") );
        if (Requester.Group.getStatus() == 200)
            return ok(showGroups.render("show Groups", groups, lectures));
        else
            return util.Err.displayError(Requester.Group.getStatus(),"Error receiving group information!");  
    }
    
    public static Result showGroupTaskSheets(){
        String groupName = Form.form().bindFromRequest().get("groupName");
        String lectureTitle = Form.form().bindFromRequest().get("lectureTitle");
        List<PhoenixLectureGroupTaskSheet> sheets = Requester.Group.getGroupTaskSheets(lectureTitle, groupName);

        if(Requester.Group.getStatus()==200) 
            return ok(showLectureGroupTaskSheets.render("show TaskSheet", lectureTitle, groupName, sheets));
        else
            return util.Err.displayError(Requester.Group.getStatus(),"Error receiving tasksheet information!");

    }
    
    public static Result changeTaskDate() {
        String[] meta = Form.form().bindFromRequest().get("meta").split(";");
        Requester.Task.setDatedTask(
                DateTime.parse(Form.form().bindFromRequest().get("deadline"), DateTimeFormat.forPattern("Y-MM-dd'T'HH:mm")), 
                        DateTime.parse(Form.form().bindFromRequest().get("release"), DateTimeFormat.forPattern("Y-MM-dd'T'HH:mm")),
                                meta[0], meta[1], meta[2], meta[3]);

        if(Requester.Task.getStatus()!=200) 
            return util.Err.displayError(Requester.Task.getStatus(),"Unable to set date properly!");
  
        List<PhoenixLectureGroupTaskSheet> sheets = Requester.Group.getGroupTaskSheets(meta[0], meta[1]);
        if(Requester.Group.getStatus()!=200) 
            return util.Err.displayError(Requester.Group.getStatus(),"Error receiving tasksheet information!");
        
        return ok(showLectureGroupTaskSheets.render("show TaskSheet", meta[0], meta[1], sheets));
    }
    

    
    public static Result addTaskSheetToGroup() {
        if (request().queryString().get("lecture") != null) {
            String lecture = request().queryString().get("lecture")[0];
            if (lecture != null) {
                List<TimeGroup> groups = TimeGroup.toTimeGroup( Requester.Group.getAll(lecture) );
                List<PhoenixTaskSheet> tasksheets = Requester.TaskSheet.getAll();
                return ok(addTaskSheetToGroup.render("Add Sheet to Group",lecture,tasksheets,groups));
            } else 
                return util.Err.displayError(Requester.Group.getStatus(),"This lecture does not exist! (<i>"+lecture+"</i>)");
        }
        else 
            return util.Err.displayError(Requester.Group.getStatus(),"No lecture selected!");
    }
    

    public static Result sendTaskSheetToGroup() {
        Map<String, String> data = Form.form().bindFromRequest().data();

        String lectureTitle = data.get("lecture");
        data.remove("lecture");
        String tasksheetName = data.get("tasksheet");
        data.remove("tasksheet");

        
        if (tasksheetName != null) {
            for(String time : data.keySet()) {
                if (!time.startsWith("deadline_") && !time.startsWith("release_")) {
                    DateTime deadline = DateTime.parse(data.get("deadline_"+time),  DateTimeFormat.forPattern("Y-MM-dd'T'HH:mm"));
                    DateTime release = DateTime.parse(data.get("release_"+time), DateTimeFormat.forPattern("Y-MM-dd'T'HH:mm") );
                    PhoenixLectureGroup group = Requester.Group.get(lectureTitle, time);
                    Requester.Group.addTaskSheet(deadline, release, Requester.TaskSheet.get(tasksheetName), group);
                    if (Requester.Group.getStatus() != 200)
                        return util.Err.displayError(Requester.Group.getStatus(),"Error adding tasksheet to selected groups!");
                }
            }
            return ok(stringShower.render("Erfolg!", "Die Tasksheets wurden den ausgewählten Gruppen hinzugefügt!"));
        }
        return ok(stringShower.render("FAIL!", "Kein TaskSheet angegeben!!"));
    } 
    
    public static Result deleteGroup(){
        //deleteResource
        String groupName = Form.form().bindFromRequest().get("groupName");
        String lectureTitle = Form.form().bindFromRequest().get("lectureTitle");
        Requester.Group.delete(lectureTitle, groupName);
        if (Requester.Group.getStatus() == 200)
            return ok(showGroups.render("show Group", Requester.Group.getAll(lectureTitle), Requester.Lecture.getAll()));
        else 
            return util.Err.displayError(Requester.Group.getStatus(),"Error deleting group!");
    }
    

    public static Result updateGroup(){
        PhoenixLectureGroup group = Requester.Group.get(Form.form().bindFromRequest().get("lectureTitle"), 
                                                          Form.form().bindFromRequest().get("groupName"));
        return ok(addGroup.render("Create Lecture", Requester.Lecture.getAll(), group));      
    }

}
