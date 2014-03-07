package controllers;

import java.util.ArrayList;
import java.util.Map;


import meta.Requester;

import de.phoenix.rs.entity.PhoenixTask;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.createTaskSheet;
import views.html.showTaskSheet;
import views.html.stringShower;

public class TaskSheetApplication extends Controller {
    
    public static Result showTaskSheets() {
        return ok(showTaskSheet.render("Show Task Sheets", Requester.TaskSheet.getAll()));
    }
   
    public static Result createTaskSheet() {
        ArrayList<String> titles = new ArrayList<String>();
        for(PhoenixTask t : Requester.Task.getAll())
            titles.add(t.getTitle());
        return ok(createTaskSheet.render("Create Task Sheet", titles));
    }
    
    public static Result sendTaskSheet() {
        Map<String, String> raw = Form.form().bindFromRequest().data();
        raw.remove("submit");
        String sheetname = raw.get("sheetname");
        raw.remove("sheetname");
        
        ArrayList<String> taskTitlesList = new ArrayList<String>();
        taskTitlesList.addAll(raw.keySet());
        Requester.TaskSheet.create(sheetname, taskTitlesList);
         
        if(Requester.TaskSheet.getStatus() == 200)
            return ok(stringShower.render("TaskSheet created", "Your Tasksheet has been created successfully!"));
        else
            return ok(stringShower.render("ERROR", "ERROR: "+Requester.TaskSheet.getStatus()));
    }

}
