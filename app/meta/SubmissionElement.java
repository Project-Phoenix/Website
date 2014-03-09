package meta;

import java.util.List;

import de.phoenix.rs.entity.PhoenixSubmission;
import de.phoenix.rs.entity.PhoenixTask;
import de.phoenix.rs.key.SelectEntity;

public class SubmissionElement extends PhoenixRequest {
    
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
