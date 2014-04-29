package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import meta.Requester;

import de.phoenix.rs.entity.PhoenixTask;
import de.phoenix.rs.entity.PhoenixTaskSheet;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.createTaskSheet;
import views.html.showTaskSheet;
import views.html.stringShower;
import views.html.addTaskToTaskSheet;

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
            return util.Err.displayError(Requester.TaskSheet.getStatus(),"Error creating tasksheet!");
    }
    
    public static Result deleteTask() {
        try {
            String taskSheetTitle = request().queryString().get("tasksheet")[0];
            String taskTitle = request().queryString().get("task")[0];
            Requester.TaskSheet.deleteTask(taskTitle, taskSheetTitle);
            if (Requester.TaskSheet.getStatus() == 200) 
                return redirect("/showTaskSheet");
            else
                return util.Err.displayError(Requester.TaskSheet.getStatus(),"An error occured while deleting the task.");
        } catch (Exception e) {
            return util.Err.displayError(400,"Bad request! Maybe malformed url.");
        }
    }
    
    public static Result addTask() {
        try {
            String taskSheetTitle = request().queryString().get("tasksheet")[0];
            PhoenixTaskSheet sheet = Requester.TaskSheet.get(taskSheetTitle); 
            List<PhoenixTask> tasks = Requester.Task.getAll(); 
            if (Requester.TaskSheet.getStatus() == 200 && Requester.Task.getStatus() == 200)
                return ok(addTaskToTaskSheet.render("Add Task", sheet, tasks));
            else if (Requester.TaskSheet.getStatus() != 200)
                return util.Err.displayError(Requester.TaskSheet.getStatus(),"Fehler beim holen des Tasksheets!");
            else
                return util.Err.displayError(Requester.Task.getStatus(),"Fehler beim holen der Tasks!");
        }
        catch (Exception e) {
            return util.Err.displayError(400,"Bad request! Maybe malformed url.");
        }
    }
    
    public static Result mergeTaskWithTaskSheet() {
        Map<String, String> data = Form.form().bindFromRequest().data();

        String tasksheet = data.get("tasksheet");
        data.remove("tasksheet");
        data.remove("submit");
        
        ArrayList<String> tasks = new ArrayList<String>();
        
        if (tasksheet != null) 
            for(String task: data.keySet()) 
                tasks.add(task);
            
        Requester.TaskSheet.addTask(tasksheet, tasks);
        
        if (Requester.TaskSheet.getStatus() == 200)
            return redirect("/showTaskSheet");
        else 
            return util.Err.displayError(Requester.TaskSheet.getStatus(),"Fehler beim Senden der Tasks!");

    }

}
