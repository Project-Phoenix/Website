package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import de.phoenix.rs.entity.PhoenixAttachment;
import de.phoenix.rs.entity.PhoenixTaskTest;
import de.phoenix.rs.entity.PhoenixText;
import de.phoenix.util.JavaSourceUtil;
import meta.Requester;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import util.Http;
import bootstrap.html.*;

public class TaskApplication extends Controller {
    
    public static Result createTask() {
        return ok(bootstrap.html.createTask.render("Create Task"));
    }
    
    public static Result createAutomaticTask() {
        return ok(bootstrap.html.createAutomaticTask.render("Create Automatic Task"));
    }
    
    
    public static Result deleteFromTask() {
        String taskTitle = Http.GET("task");
        String attachment = Http.GET("attachment");
        String pattern = Http.GET("pattern");
        int success = 1;

        if (taskTitle != null) {
            if (attachment != null && pattern == null)
                success = Requester.Task.deleteAttachment(taskTitle, attachment);
            else if (pattern != null && attachment == null)
                success = Requester.Task.deletePattern(taskTitle, pattern);
            
            if (success == 0) 
                if (Requester.Task.getStatus() == 200)
                    return redirect("/showTask?option=all");
                else
                    return util.Err.displayError(Requester.Task.getStatus(),"Error getting information!");
            else
                return util.Err.displayError(404,"No such attachment/pattern found!");
        }
        else
            return util.Err.displayError(404,"Malicious URL!");

    }
    
    public static Result sendTask() {
        MultipartFormData form = request().body().asMultipartFormData();
        
        ArrayList<PhoenixAttachment> attachmentLst = new ArrayList<PhoenixAttachment>();
        ArrayList<PhoenixText> patternLst = new ArrayList<PhoenixText>();
        
        for (FilePart fp : form.getFiles()) {
            try {
                if (!fp.getFilename().trim().equals(""))
                    if (fp.getKey().equals("binary")) 
                        attachmentLst.add(new PhoenixAttachment(fp.getFile(), fp.getFilename())); 
                    else if (fp.getKey().equals("pattern")) 
                        patternLst.add(new PhoenixText(fp.getFile(), fp.getFilename()));   
            } catch (IOException e) {
                return util.Err.displayError(500,e.toString());
            }
        }  
        
        Requester.Task.create(Form.form().bindFromRequest().get("title"), Form.form().bindFromRequest().get("description"), patternLst, attachmentLst);   
        if(Requester.Task.getStatus() == 200)
            return ok(bootstrap.html.stringShower.render("success", "Task created", "Die Aufgabe wurde erfolgreich erstellt."));
        else
            return util.Err.displayError(Requester.Task.getStatus(),"Error creating this task!");
    }
    
    
    public static Result showTasks() {
        if (request().queryString().get("option") != null)
            if (request().queryString().get("option")[0].equals("all"))
                return ok(showAllTasks.render("Alle Aufgaben", Requester.Task.getAll()));
            else
                return ok(bootstrap.html.showTask.render("Aufgabe", Requester.Task.get(request().queryString().get("option")[0])) );
        return util.Err.displayError(400,"Bad URL.");
    }
    
    private static List<String> getDisallowedContent(Map<String, String> data) {
        ArrayList<String> result = new ArrayList<String>();
        if (data.get("javaio") != null)
            result.addAll(Arrays.asList(data.get("javaio").split(",")));
        if (!data.get("disallowed").isEmpty())
            result.addAll(Arrays.asList(data.get("disallowed").replace(" ", "").split(",")));
        return result;
    }
    
    public static Result sendAutomaticTask () {
        String backend = Form.form().bindFromRequest().data().get("backend");

        MultipartFormData form = request().body().asMultipartFormData();

        ArrayList<PhoenixAttachment> attachmentLst = new ArrayList<PhoenixAttachment>();
        ArrayList<PhoenixText> patternLst = new ArrayList<PhoenixText>();

        if (!Form.form().bindFromRequest().get("title").matches("[a-zA-Z0-9_äÄöÖüÜ!\\-\\s]+")) {
            flash("maliciousChar","true");
            return createAutomaticTask();
        }
        
        for (FilePart fp : form.getFiles()) {
            try {
                if (!fp.getFilename().trim().equals(""))
                    if (fp.getKey().equals("binary")) 
                        attachmentLst.add(new PhoenixAttachment(fp.getFile(), fp.getFilename())); 
                    else if (fp.getKey().equals("pattern")) 
                        patternLst.add(new PhoenixText(fp.getFile(), fp.getFilename()));   
            } catch (IOException e) {
                return util.Err.displayError(500,e.toString());
            }
        } 

        ArrayList<PhoenixTaskTest> testList = new ArrayList<PhoenixTaskTest>();
        for (int testnr = 1; Form.form().bindFromRequest().data().get("test_"+testnr) != null; testnr++) {
            try {
                String test = Form.form().bindFromRequest().data().get("test_"+testnr);
                if (!test.isEmpty())
                    testList.add(new PhoenixTaskTest( new PhoenixText(test, JavaSourceUtil.extractClassName(test) ))); 
            } catch (IllegalArgumentException e) {
                flash("syntaxError","true");
                return redirect("/createAutomaticTask");
            }
        }

        Requester.Task.createAutomatic(attachmentLst, patternLst, Form.form().bindFromRequest().get("description"), Form.form().bindFromRequest().get("title"),
                backend, testList, getDisallowedContent(Form.form().bindFromRequest().data()) );   
        if(Requester.Task.getStatus() == 200)
            return ok(bootstrap.html.stringShower.render("success", "Task created", "Die Aufgabe wurde erfolgreich erstellt."));
        else
            return util.Err.displayError(Requester.Task.getStatus(),"Error creating automatic task!");
    }

}
