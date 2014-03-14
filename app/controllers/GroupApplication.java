package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import meta.Requester;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

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
import views.html.showTaskSheet;
import views.html.stringShower;

public class GroupApplication extends Controller {
   
    
    public static Result addGroup() {
        return ok(addGroup.render("add Group", Requester.Lecture.getAll()));
    }
    
    public static Result sendGroup() {        
        //Array to get the inputs form addGroup
        Map<String, String> data = Form.form().bindFromRequest().data();
        System.out.println(data);
        String title = data.get("title");
        String lecture = data.get("selectLecture");
        int size = Integer.parseInt(data.get("size"));
        String submissionDay = data.get("submissionDay");
        LocalTime submissionTime = LectureCheck.getTime(data.get("submissionTime"));
        
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
        Requester.Lecture.addGroup(lecture, title, size, submissionDay, submissionTime, allDetails);

        if (Requester.Lecture.getStatus() == 200)
            return ok(stringShower.render("strings to show", "Good News!"));
        else
            return ok(stringShower.render("Add Group", "Ups, da ist ein Fehler aufgetreten!(" + Requester.Lecture.getStatus() + ")"));        
    }
    
    public static Result choseGroups() {
        List<PhoenixLecture> lectures = Requester.Lecture.getAll();
        List<PhoenixLectureGroup> empty = new ArrayList<PhoenixLectureGroup>();
        if (Requester.Lecture.getStatus() == 200){
            return ok(showGroups.render("show Groups", empty, lectures));
        }else{
            return ok(stringShower.render("show Groups", "Ups, da ist ein Fehler aufgetreten!(" + Requester.Lecture.getStatus() + ")"));
        }
    }
    
    public static Result showLectureGroups() {
        List<PhoenixLecture> lectures = Requester.Lecture.getAll();   
        List<PhoenixLectureGroup> groups = Requester.Group.getAll( Form.form().bindFromRequest().get("lecture") );
        if (Requester.Group.getStatus() == 200)
            return ok(showGroups.render("show Groups", groups, lectures));
        else
            return ok(stringShower.render("show Groups", "Ups, da ist ein Fehler aufgetreten!(" + Requester.Group.getStatus() + ")"));
    }
    
    public static Result showGroupTaskSheets(){
        String groupName = Form.form().bindFromRequest().get("groupName");
        String lectureTitle = Form.form().bindFromRequest().get("lectureTitle");
        PhoenixLectureGroup group = Requester.Group.get(lectureTitle, groupName);
        if(Requester.Group.getStatus()!=200) return ok(stringShower.render("showGroupTaskSheet", "Es ist ein Fehler bei dem Finden der Gruppe aufgetreten("+ Requester.Group.getStatus()+")"));
        
        if(Requester.Group.getStatus()!=200) return ok(stringShower.render("showGroupTaskSheet", "Es ist ein Fehler bei dem Finden der TaskSheets aufgetreten("+Requester.Group.getStatus()+")"));
        List<PhoenixTaskSheet> taskSheets = new ArrayList<PhoenixTaskSheet>();
        for(PhoenixLectureGroupTaskSheet lectureGroupTaskSheet: Requester.Group.getGroupTaskSheets(group)){
            taskSheets.add(Requester.TaskSheet.get(lectureGroupTaskSheet.getTaskSheetTitle()));
        }
        return ok(showTaskSheet.render("show TaskSheet", taskSheets));
    }
    

    
    public static Result addTaskSheetToGroup() {
        if (request().queryString().get("lecture") != null) {
            String lecture = request().queryString().get("lecture")[0];
            if (lecture != null) {
                List<TimeGroup> groups = TimeGroup.toTimeGroup( Requester.Group.getAll(lecture) );
                List<PhoenixTaskSheet> tasksheets = Requester.TaskSheet.getAll();
                return ok(addTaskSheetToGroup.render("Add Sheet to Group",lecture,tasksheets,groups));
            } else 
                return ok(stringShower.render("ERROR", "This Lecture does not exist!"));
        }
        else 
            return ok(stringShower.render("ERROR", "No Lecture selected!")); 
    }
    
    //TODO check tasksheet name
    public static Result sendTaskSheetToGroup() {
        Map<String, String> data = Form.form().bindFromRequest().data();
        String lectureTitle = data.get("lecture");
        data.remove("lecture");
        String tasksheetName = data.get("tasksheet");
        data.remove("tasksheet");
        
        if (tasksheetName != null) {
            for(String time : data.keySet()) {
                if (time.startsWith("dealine_")) {
                    DateTime deadline = DateTime.parse(data.get(time), DateTimeFormat.forPattern("Y-MM-d'T'H:mm") );
                    DateTime release = DateTime.parse(data.get(time.replace("deadline_", "release_")), DateTimeFormat.forPattern("Y-MM-d'T'H:mm") );
                    PhoenixLectureGroup group = Requester.Group.get(lectureTitle, time.replace("deadline_", ""));
                    data.remove(time);
                    data.remove(time.replace("dealine_","release_"));
                    data.remove(time.replace("deadline_", ""));
                    Requester.Group.addTaskSheet(deadline, release, Requester.TaskSheet.get(tasksheetName), group);
                    if (Requester.Group.getStatus() != 200)
                        return ok(stringShower.render("FAIL!", "Fehler beim senden von:"+group.getName()+" - Fehler: "+Requester.Group.getStatus()));
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
        if (Requester.Group.getStatus() == 200){
            return ok(showGroups.render("show Group", Requester.Group.getAll(lectureTitle), Requester.Lecture.getAll()));
        }
        else return ok(stringShower.render("Groups deleted", "Error while deleting a Group(" + Requester.Group.getStatus() + ")"));
    }
    
//  TODO: Kilian has to add an update resource for lectureGroup
    public static Result updateGroup(){
      /*  Map<String, String> boxes = Form.form().bindFromRequest().data();
        WebResource ws = PhoenixLectureGroup.updateResource(CLIENT, BASE_URI);
        String lecture = boxes.get("lecture");
        String Group = boxes.get("group");
        for(String key : boxes.keySet()) {
            System.out.println(key);
        }*/
        return ok(stringShower.render("YESSSS", "no update method for groups yet"));       
    }

}
