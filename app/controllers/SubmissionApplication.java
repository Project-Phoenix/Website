package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import meta.Requester;

import de.phoenix.rs.entity.PhoenixAttachment;
import de.phoenix.rs.entity.PhoenixSubmission;
import de.phoenix.rs.entity.PhoenixText;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.showSubmissions;
import views.html.stringShower;
import views.html.submitSolution;

public class SubmissionApplication extends Controller {
    
    public static Result showSubmissions() {
        String task = util.Http.GET("task");
        List<PhoenixSubmission> submissions = Requester.Submission.get( task );

        if (Requester.Submission.getStatus() == 200)
            return ok(bootstrap.html.showSubmissions.render("Einreichungen", submissions));

        if (Requester.Submission.getStatus() == 461) //no entities
            return ok(bootstrap.html.stringShower.render("warning","Keine Einreichungen","Für diese Aufgabe sind noch keine Einreichungen vorhanden."));
                    
        return util.Err.displayError(Requester.Submission.getStatus(),"Error displaying submissions!");
    }
    
    public static Result submitSolution() {
        return ok(submitSolution.render("Einreichung", Requester.TaskSheet.getAll()));
    }
    
    public static Result sendSubmission() {
        MultipartFormData form = request().body().asMultipartFormData();
        String taskTitle = Form.form().bindFromRequest().get("task");
        
        List<PhoenixAttachment> attachments = new ArrayList<PhoenixAttachment>();
        List<PhoenixText> solutions = new ArrayList<PhoenixText>();
        
        for (FilePart fp : form.getFiles()) {
            try {
                if (!fp.getFilename().trim().equals(""))
                    if (fp.getKey().equals("solution")) 
                        solutions.add(new PhoenixText(fp.getFile(), fp.getFilename())); 
                    else if (fp.getKey().equals("attachment")) 
                        attachments.add(new PhoenixAttachment(fp.getFile(), fp.getFilename()));   
            } catch (IOException e) {
                return util.Err.displayError(500,e.toString());
            }
        }
        
        if (Requester.Submission.submitTask(taskTitle, attachments, solutions) < 0)
            util.Err.displayError(404,"Task has not been found in database!");
        if (Requester.Submission.getStatus() == 200) 
            return ok(stringShower.render("Success!", "Your submission was saved successfully!"));
        else
            return util.Err.displayError(Requester.Submission.getStatus());

    }


}
