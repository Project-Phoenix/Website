package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import meta.Requester;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalTime;




import de.phoenix.rs.entity.PhoenixDetails;
import de.phoenix.rs.entity.PhoenixLecture;
import de.phoenix.rs.entity.PhoenixLectureGroup;
import de.phoenix.rs.entity.PhoenixLectureGroupTaskSheet;
import de.phoenix.rs.entity.PhoenixTaskSheet;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.addGroup;
import views.html.addTaskSheetToGroup;
import views.html.showGroups;
import views.html.showTaskSheet;
import views.html.stringShower;

public class GroupApplication extends Controller {
   
    
    public static Result addGroup() {
        return ok(addGroup.render("add Group"));
    }
    
    private static int checkDay(String day){
        switch(day){
            case "monday":
                return DateTimeConstants.MONDAY;
            case "tuesday":
                return DateTimeConstants.TUESDAY;
            case "wednesday":
                return DateTimeConstants.WEDNESDAY;
            case "thursday":
                return DateTimeConstants.THURSDAY;
            case "friday":
                return DateTimeConstants.FRIDAY;
            case "saturday":
                return DateTimeConstants.SATURDAY;
            case "sunday":
                return DateTimeConstants.SUNDAY;
            default:
                //throw invalid input exception
                System.out.println("null, dafuq?!");
                return -1;
        }
    }
    
    public static Result sendGroup() {        
        //Array to get the inputs form addGroup
        String[] keyStrings = new String[] {"title","lecture", "size", "room", "day", "startHours","startMinutes", "endHours", 
                                            "endMinutes", "period", "startYear", "startMonth", "startDay",
                                            "endYear", "endMonth", "endDay", "submitDay", "submitHours", "submitMinutes"};

        //Array, which will be filled with the requeststrings
        String[] requests = new String[13];
        String title = "";
        String lecture = "";
        int itemCount = 0;
        int size = 0;
        int submitDay = 0;
        int submitHours = 0;
        int submitMinutes = 0;

        // TODO: exception handling
        // Get the requests and if something missing, set wronginput[arrayCount] to true and test the next detail
        for(String item: keyStrings){
            String temp = Form.form().bindFromRequest().get(item);
            //if everything's filled in, set title and put the rest in the requestarrays
            switch(itemCount){
            case 0:    
                title = temp;
                itemCount++;
                break;
            case 1:    
                lecture = temp;
                itemCount++;
                break;
            case 2:
                size = Integer.parseInt(temp);
                itemCount++;
                break;
            case 16:
                submitDay = checkDay(temp);
                itemCount++;
                break;
            case 17:
                submitHours = Integer.parseInt(temp);
                itemCount++;
                break;
            case 18:
                submitMinutes = Integer.parseInt(temp);
                break;
            default:
                requests[itemCount-3] = temp;
                itemCount++;
                break;
            }
        }

        // unique title (maybe not necessary)
        //title = lecture + " - " + title;
        
        LocalTime submitTime = new LocalTime(submitHours, submitMinutes);
        //PhoenixDetaillist
        List<PhoenixDetails> allDetails = new ArrayList<PhoenixDetails>();
        LectureCheck lectureCheck = new LectureCheck(requests);
        //add it to allDetails
        allDetails.add(lectureCheck.getPhoenixDetails());
        //send it to server
        Requester.Lecture.addGroup(lecture, title, size, submitDay, submitTime, allDetails);

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
            taskSheets.add(lectureGroupTaskSheet.getTaskSheet());
        }
        return ok(showTaskSheet.render("show TaskSheet", taskSheets));
    }
    
    public static Result addTaskSheetToGroup() {
        if (request().queryString().get("lecture") != null) {
            String lecture = request().queryString().get("lecture")[0];
            if (lecture != null) {
                List<PhoenixLectureGroup> groups = Requester.Group.getAll(lecture);
                List<PhoenixTaskSheet> tasksheets = Requester.TaskSheet.getAll();
                return ok(addTaskSheetToGroup.render("Add Sheet to Group",lecture,tasksheets,groups));
            } else {
                return ok(stringShower.render("ERROR", "This Lecture does not exist!"));
            }
        } else {
            return ok(stringShower.render("ERROR", "No Lecture selected!"));
        }    
    }
    
    
    public static Result sendTaskSheetToGroup() {
        List<PhoenixLectureGroup> groups = new ArrayList<PhoenixLectureGroup>();
        Map<String, String> data = Form.form().bindFromRequest().data();
        String lectureTitle = data.get("lecture");
        data.remove("lecture");
        String tasksheetName = data.get("tasksheet");
        
        if (tasksheetName != null) {
            data.remove("tasksheet");
            if (!data.isEmpty()) {
                PhoenixLectureGroup group = null;
                for(String g : data.keySet()) {
                     group = Requester.Group.get(lectureTitle, g);
                     if (group == null)
                         return ok(stringShower.render("FEHLER!","Gruppe "+g+" existiert nicht!!"));
                     groups.add(group);
                }   
            }
            Requester.Group.addTaskSheet(DateTime.now(), DateTime.now().plusWeeks(1), Requester.TaskSheet.get(tasksheetName), groups);
            
            if (Requester.Group.getStatus() == 200)
                return ok(stringShower.render("Erfolg!", "Die Tasksheets wurden den ausgewählten Gruppen hinzugefügt!"));
            
            return ok(stringShower.render("FAIL!", "Ups, da ist ein Fehler aufgetreten!(" + Requester.Group.getStatus() + ")"));
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
