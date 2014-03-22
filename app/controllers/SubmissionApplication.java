package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import meta.Requester;

import de.phoenix.rs.entity.PhoenixSubmission;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.showSubmissions;
import views.html.submitSolution;

public class SubmissionApplication extends Controller {
    
    public static Result showSubmissions() {
        List<PhoenixSubmission> submissions = Requester.Submission.get( Form.form().bindFromRequest().data().get("task") );

        if (Requester.Submission.getStatus() == 200)
            return ok(showSubmissions.render("showSubmissions", submissions));

        return util.Err.displayError(Requester.Submission.getStatus(),"Error displaying submissions!");
    }
    
    public static Result submitSolution() {
        return ok(submitSolution.render("Einreichung", Requester.TaskSheet.getAll()));
    }
    
    public static Result sendSubmission() {
        MultipartFormData form = request().body().asMultipartFormData();
        
        List<File> solutions = new ArrayList<File>();
        List<File> attachments = new ArrayList<File>();
        
        for (FilePart fp : form.getFiles()) 
            if (!fp.getFilename().trim().equals(""))
                if (fp.getKey().equals("solution")) 
                    solutions.add(new File(fp.getFile(), fp.getFilename())); // TODO wrong, waiting for Construktor
                else if (fp.getKey().equals("attachment")) 
                    attachments.add(new File(fp.getFile(), fp.getFilename())); //wrong, waiting for Construktor
        return ok();
    }


}
