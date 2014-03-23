package meta;

import java.util.Arrays;
import java.util.List;

import de.phoenix.rs.entity.PhoenixAttachment;
import de.phoenix.rs.entity.PhoenixSubmission;
import de.phoenix.rs.entity.PhoenixTask;
import de.phoenix.rs.entity.PhoenixText;
import de.phoenix.rs.key.KeyReader;
import de.phoenix.rs.key.SelectEntity;

public class SubmissionElement extends PhoenixRequest {
    
    public int submitTask(String taskTitle, List<PhoenixAttachment> fileAttachments, List<PhoenixText> fileTexts) {
        PhoenixSubmission submission = new PhoenixSubmission(fileAttachments, fileTexts);
        PhoenixTask task = this.get(PhoenixTask.getResource(CLIENT, BASE_URI), new SelectEntity<PhoenixTask>().addKey("title", taskTitle));
        if (task != null) 
            return this.create(PhoenixTask.submitResource(CLIENT, BASE_URI), KeyReader.createAddTo(task, Arrays.asList(submission)));
        else
            return -1;
    }
    
    //TODO _AA Start here next time working!
//    public int submitAutomaticTask(String taskTitle, List<PhoenixAttachment> fileAttachments, List<File> fileTexts) {
//        PhoenixSubmission toSend = new PhoenixSubmission(fileAttachments, fileTexts);
//    }
    
    public List<PhoenixSubmission> getAll(String lectureTitle) {
        throw new UnsupportedOperationException(); //TODO implement delete (Submission)
    }
    
    public List<PhoenixSubmission> get(String taskTitle) {
        List<PhoenixSubmission> result = 
                this.getList(PhoenixSubmission.getResource(CLIENT, BASE_URI),
                        new SelectEntity<PhoenixSubmission>().addKey("task", 
                        new SelectEntity<PhoenixTask>().addKey("title", taskTitle)));
        return result;
    }

    public int delete(SelectEntity<PhoenixSubmission> selectEntity) {
        throw new UnsupportedOperationException(); //TODO implement delete (Submission)
    }
    
    public int update(SelectEntity<PhoenixSubmission> selectEntity) {
        throw new UnsupportedOperationException(); //TODO implement update (Submission)
    }

}
