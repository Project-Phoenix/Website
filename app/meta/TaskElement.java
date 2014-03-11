package meta;

import java.util.List;

import de.phoenix.rs.entity.PhoenixAttachment;
import de.phoenix.rs.entity.PhoenixAutomaticTask;
import de.phoenix.rs.entity.PhoenixTask;
import de.phoenix.rs.entity.PhoenixTaskTest;
import de.phoenix.rs.entity.PhoenixText;
import de.phoenix.rs.key.SelectEntity;
import de.phoenix.submission.DisallowedContent;

/**
 * Request class for the PhoenixTask Entity.<br>
 * It "wrappes" the methods from the PhoenixRequest class <br>
 * and provides specific methods for the PhoenixTask Entity.<br>
 * 
 * @author Matthias Eiserloh; Markus Wolf
 *
 */
public class TaskElement extends PhoenixRequest{
    
    public int create(String title, String description, List<PhoenixText> answerPattern, List<PhoenixAttachment> AttachmentList) {
        PhoenixTask toSend = new PhoenixTask(AttachmentList, answerPattern, description, title);
        this.create(PhoenixTask.createResource(CLIENT, BASE_URI), toSend);
        return this.getStatus();
    }
    
    public int createAutomatic(List<PhoenixAttachment> attachments, List<PhoenixText> answerPattern, String description, String title, String backend, List<PhoenixTaskTest> tests, List<String> disallowed) {
        PhoenixAutomaticTask toSend = new PhoenixAutomaticTask(attachments, answerPattern, description, title, backend, tests);
        DisallowedContent de = new DisallowedContent();
        for (String d : disallowed)
            de.disallow(d);
        toSend.setDisallowedContent(de);
        this.create(PhoenixTask.createResource(CLIENT, BASE_URI), toSend);
        return this.getStatus();
    }
    
    public PhoenixTask get(String Tasktitle) {
        PhoenixTask result = this.get(PhoenixTask.getResource(CLIENT, BASE_URI), new SelectEntity<PhoenixTask>().addKey("title", Tasktitle));
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
}
