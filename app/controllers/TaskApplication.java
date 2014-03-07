package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import de.phoenix.rs.entity.PhoenixAttachment;
import de.phoenix.rs.entity.PhoenixText;
import meta.Requester;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.createTask;
import views.html.showTasks;
import views.html.stringShower;

public class TaskApplication extends Controller {
    
    public static Result createTask() {
        return ok(createTask.render("Create Task"));
    }
    
    public static Result sendTask() {
        MultipartFormData form = request().body().asMultipartFormData();
        
        ArrayList<PhoenixAttachment> attachmentLst = new ArrayList<PhoenixAttachment>();
        ArrayList<PhoenixText> patternLst = new ArrayList<PhoenixText>();

        if (!Form.form().bindFromRequest().get("title").matches("[a-zA-Z0-9_äÄöÖüÜ!-]*")) {
            flash("maliciousChar","true");
            return createTask();
        }
        
        for (FilePart fp : form.getFiles()) {
            try {
                if (!fp.getFilename().trim().equals(""))
                    if (fp.getKey().equals("binary")) 
                        attachmentLst.add(new PhoenixAttachment(fp.getFile(), fp.getFilename())); 
                    else if (fp.getKey().equals("pattern")) 
                        patternLst.add(new PhoenixText(fp.getFile(), fp.getFilename()));   
            } catch (IOException e) {
                return ok(stringShower.render("ERROR", e.toString()));
            }
        }  
        
        Requester.Task.create(Form.form().bindFromRequest().get("title"), Form.form().bindFromRequest().get("description"), patternLst, attachmentLst);   
        if(Requester.Task.getStatus() == 200)
            return ok(stringShower.render("Task created", "Task has been created successfully"));
        else
            return ok(stringShower.render("send Lecture", "Ups, da ist ein Fehler aufgetreten!(" + Requester.Task.getStatus() + ")"));
    }
    
    public static Result showTasks() {
        if (request().queryString().get("option") != null)
            if (request().queryString().get("option")[0].equals("all"))
                return ok(showTasks.render("showTasks", Requester.Task.getAll()));
            else
                return ok(showTasks.render("showTasks", Arrays.asList( Requester.Task.get(request().queryString().get("option")[0])) ));
        return ok(stringShower.render("Show Tasks", "So nicht! ;PP"));
    }

}
