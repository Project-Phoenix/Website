package controllers;


import java.util.ArrayList;
import java.util.List;

import de.phoenix.database.entity.Submission;
import de.phoenix.database.entity.SubmissionFiles;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

public class SubmissionHandler {
    
    private final static String BASE_URL = "http://meldanor.dyndns.org:8080/PhoenixWebService/rest/";
    
    public void uploadSubmission(String data) {
        //TODO implement upload of a submission
    }
    
    public ArrayList<Einreichung> getSubmissions() {
        
        Client c = Client.create();
        // Webresource to get all submission
        WebResource submissionResource = c.resource(BASE_URL).path("/submission").path("/getAll");
        // Webresource to get all files attached to one submission
        WebResource submissionFileResource = c.resource(BASE_URL).path("/submission").path("/getFiles");

        // Ugly constructs to receive lists of generic types - no other way to
        // solve this
        GenericType<List<Submission>> submissionType = new GenericType<List<Submission>>() {
        };
        GenericType<List<SubmissionFiles>> submissionFileType = new GenericType<List<SubmissionFiles>>() {
        };

        // Get all submissions
        List<Submission> temp = submissionResource.get(submissionType);
        ArrayList<Einreichung> result= new ArrayList<Einreichung>();

        for (Submission submission : temp) {
            String ctrlMess = submission.getControllMessage();
            String date = submission.getSubmissionDate().toString();

            List<SubmissionFiles> files = submissionFileResource.path(submission.getId().toString()).get(submissionFileType);

            Einreichung E = new Einreichung(ctrlMess, date);
            for (SubmissionFiles file : files) 
                    E.addFile(file.getFilename(), file.getContent());
            
            result.add(E);
        }
        return result;
        
    }

}
