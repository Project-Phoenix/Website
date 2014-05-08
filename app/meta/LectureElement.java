package meta;

import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalTime;

import de.phoenix.date.Weekday;
import de.phoenix.rs.entity.PhoenixDetails;
import de.phoenix.rs.entity.PhoenixLecture;
import de.phoenix.rs.entity.PhoenixLectureGroup;
import de.phoenix.rs.key.KeyReader;
import de.phoenix.rs.key.SelectEntity;
import de.phoenix.rs.key.UpdateEntity;

public class LectureElement extends PhoenixRequest {
    
    public int addGroup(String Lecturetitle, String name, int maxMember, String submissionDeadlineWeekday, LocalTime submissionDeadlineTime, List<PhoenixDetails> details) {
        PhoenixLecture lecture = this.get(PhoenixLecture.getResource(CLIENT, BASE_URI), new SelectEntity<PhoenixLecture>().addKey("title", Lecturetitle));
        System.out.println(lecture);
        PhoenixLectureGroup group = new PhoenixLectureGroup(name, maxMember, Weekday.forName(submissionDeadlineWeekday), submissionDeadlineTime, details, lecture);
        this.create(PhoenixLecture.addGroupResource(CLIENT, BASE_URI), KeyReader.createAddTo(lecture, Arrays.asList(group)));
        return this.getStatus();
    }

    public int create(String title, List<PhoenixDetails> detailsList) {
        PhoenixLecture toSend = new PhoenixLecture(title, detailsList);
        this.create(PhoenixLecture.createResource(CLIENT, BASE_URI), toSend);
        return this.getStatus();
    }

    public PhoenixLecture get(String Lecturetitle) {
        PhoenixLecture result = this.get(PhoenixLecture.getResource(CLIENT, BASE_URI), new SelectEntity<PhoenixLecture>().addKey("title", Lecturetitle));
        return result;
    }
    
    public List<PhoenixLecture> getAll() { 
            List<PhoenixLecture> result = this.getAll(PhoenixLecture.getResource(CLIENT, BASE_URI));
            return result;
    }
    
    public int delete(String lectureTitle) {
        this.change(PhoenixLecture.deleteResource(CLIENT, BASE_URI), new SelectEntity<PhoenixLecture>().addKey("title", lectureTitle));
        return this.getStatus();
    }
    
    public int update(PhoenixLecture oldLecture, PhoenixLecture newLecture) {
        this.update(PhoenixLecture.updateResource(CLIENT, BASE_URI), KeyReader.createUpdate(oldLecture, newLecture));
        return this.getStatus();
    }
    
    public int addDetails(PhoenixLecture lecture, List<PhoenixDetails> details){
        this.change(PhoenixLecture.addDetailResource(CLIENT, BASE_URI), KeyReader.createAddTo(lecture, details));
        return this.getStatus();
    }
    
    public int deleteDetails(PhoenixDetails Details){
        System.out.println(KeyReader.createSelect(Details));
        this.change(PhoenixDetails.deleteResource(CLIENT, BASE_URI), KeyReader.createSelect(Details));
        return this.getStatus();
    }
    
    public int updateDetails(PhoenixDetails oldDetails, PhoenixDetails newDetails){
        UpdateEntity<PhoenixDetails> updateEntity = KeyReader.createUpdate(oldDetails, newDetails);
        this.update(PhoenixDetails.updateResource(CLIENT, BASE_URI), updateEntity);
        return this.getStatus();
    }
    
}
