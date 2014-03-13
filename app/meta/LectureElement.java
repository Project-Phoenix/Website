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

public class LectureElement extends PhoenixRequest {
    
    public int addGroup(String Lecturetitle, String name, int maxMember, int submissionDeadlineWeekday, LocalTime submissionDeadlineTime, List<PhoenixDetails> details) {
        PhoenixLecture lecture = this.get(PhoenixLecture.getResource(CLIENT, BASE_URI), new SelectEntity<PhoenixLecture>().addKey("title", Lecturetitle));
        System.out.println(lecture);
        PhoenixLectureGroup group = new PhoenixLectureGroup(name, maxMember, Weekday.forID(submissionDeadlineWeekday), submissionDeadlineTime, details, lecture);
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
    
    public int updateLecture(SelectEntity<PhoenixLecture> selectEntity) {
        throw new UnsupportedOperationException(); //TODO implement update (Task)
    }
    
}
