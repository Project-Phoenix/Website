package controllers;

import java.util.List;
import meta.Requester;

import de.phoenix.rs.entity.PhoenixSubmission;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.showSubmissions;
import views.html.stringShower;

public class SubmissionApplication extends Controller {
    
    public static Result showSubmissions() {
        List<PhoenixSubmission> submissions = Requester.Submission.get( Form.form().bindFromRequest().data().get("task") );

        if (Requester.Submission.getStatus() == 200)
            return ok(showSubmissions.render("showSubmissions", submissions));

        return ok(stringShower.render("show Submissions", "Ups, da ist ein Fehler aufgetreten!(" + Requester.Submission.getStatus() + ")"));
    }


}
