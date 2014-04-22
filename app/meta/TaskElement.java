package meta;

import java.util.List;

import org.joda.time.DateTime;

import de.phoenix.rs.entity.PhoenixAttachment;
import de.phoenix.rs.entity.PhoenixAutomaticTask;
import de.phoenix.rs.entity.PhoenixLecture;
import de.phoenix.rs.entity.PhoenixLectureGroup;
import de.phoenix.rs.entity.PhoenixLectureGroupTaskSheet;
import de.phoenix.rs.entity.PhoenixTask;
import de.phoenix.rs.entity.PhoenixTaskSheet;
import de.phoenix.rs.entity.PhoenixTaskSubmissionDates;
import de.phoenix.rs.entity.PhoenixTaskTest;
import de.phoenix.rs.entity.PhoenixText;
import de.phoenix.rs.entity.connection.TaskSubmissionDatesConnection;
import de.phoenix.rs.key.ConnectionEntity;
import de.phoenix.rs.key.KeyReader;
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
    
    public int setDatedTask(DateTime deadlineDate, DateTime releaseDate,String lectureTitle, String groupName, String taskSheetTitle, String taskName) {
        PhoenixLectureGroup lectureGroup = this.get(PhoenixLectureGroup.getResource(CLIENT, BASE_URI), 
                new SelectEntity<PhoenixLectureGroup>()
                .addKey("name", groupName)
                .addKey("lecture", new SelectEntity<PhoenixLecture>().addKey("title", lectureTitle)));
        
        PhoenixTaskSheet taskSheet = this.get(PhoenixTaskSheet.getResource(CLIENT, BASE_URI), new SelectEntity<PhoenixTaskSheet>().addKey("title", taskSheetTitle));
        
        SelectEntity<PhoenixLectureGroupTaskSheet> selectEntity =  new SelectEntity<PhoenixLectureGroupTaskSheet>()
            .addKey("lectureGroup", lectureGroup).addKey("taskSheet", taskSheet);
        
        ConnectionEntity toSend = new TaskSubmissionDatesConnection(deadlineDate, releaseDate, selectEntity, new SelectEntity<PhoenixTask>().addKey("title", taskName));
        this.create(PhoenixTaskSubmissionDates.createResource(CLIENT, BASE_URI), toSend);
        return this.getStatus();
    }
    
    public int deleteAttachment(String taskTitle, String attachmentFullname) {
        PhoenixTask task = this.get(taskTitle);

        for(PhoenixAttachment att : task.getAttachments()) {
            if (att.getFullname().equals(attachmentFullname)) {
                this.change(PhoenixAttachment.deleteResource(CLIENT, BASE_URI), KeyReader.createSelect(att));
                return 0;
            }
        }
        return -1;
    }
    
    public int update(SelectEntity<PhoenixTask> selectEntity) {
        throw new UnsupportedOperationException(); //TODO implement update (Task)
    }
}
