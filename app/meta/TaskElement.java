package meta;

import java.util.List;

import de.phoenix.rs.entity.PhoenixAttachment;
import de.phoenix.rs.entity.PhoenixTask;
import de.phoenix.rs.entity.PhoenixText;
import de.phoenix.rs.key.SelectEntity;

public class TaskElement extends PhoenixRequest{
    
    private int status;
    
    public TaskElement() {
        this.status = 0;
    }
    
    public int create(String title, String description, List<PhoenixText> answerPattern, List<PhoenixAttachment> AttachmentList) {
        PhoenixTask toSend = new PhoenixTask(AttachmentList, answerPattern, description, title);
        this.status = this.create(PhoenixTask.createResource(CLIENT, BASE_URI), toSend);
        return this.status;
    }
    
    private PhoenixTask get(SelectEntity<PhoenixTask> selectEntity) {
        return this.get(PhoenixTask.getResource(CLIENT, BASE_URI), selectEntity);
    }
    
    public PhoenixTask get(String Tasktitle) {
        PhoenixTask result = this.get(new SelectEntity<PhoenixTask>().addKey("title", Tasktitle));
        return result;
    }
    
    public List<PhoenixTask> getAll() {
        List<PhoenixTask> result = this.getAll(PhoenixTask.getResource(CLIENT, BASE_URI));
        return result;
    }
    
    public int delete(SelectEntity<PhoenixTask> selectEntity) {
        throw new UnsupportedOperationException(); //TODO implement delete (Task)
    }
    
    public int update(SelectEntity<PhoenixTask> selectEntity) {
        throw new UnsupportedOperationException(); //TODO implement update (Task)
    }
    
    public int getStatus() {
        return this.status;
    }

}
