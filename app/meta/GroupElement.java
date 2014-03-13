package meta;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;


import de.phoenix.rs.entity.PhoenixLecture;
import de.phoenix.rs.entity.PhoenixLectureGroup;
import de.phoenix.rs.entity.PhoenixLectureGroupTaskSheet;
import de.phoenix.rs.entity.PhoenixTaskSheet;
import de.phoenix.rs.entity.connection.LectureGroupTaskSheetConnection;
import de.phoenix.rs.key.ConnectionEntity;
import de.phoenix.rs.key.SelectEntity;

public class GroupElement extends PhoenixRequest {
    
    public int addTaskSheet(DateTime defaultDeadline, DateTime defaultReleaseDate, PhoenixTaskSheet taskSheet, PhoenixLectureGroup group) {
        ConnectionEntity toSend = new LectureGroupTaskSheetConnection(defaultDeadline, defaultReleaseDate, taskSheet, Arrays.asList(group));
        this.create(PhoenixLectureGroupTaskSheet.createResource(CLIENT, BASE_URI), toSend);
        return this.getStatus();
    }
       
    public List<PhoenixLectureGroup> getAll(String lectureTitle) {
        List<PhoenixLectureGroup> result = 
                this.getList(PhoenixLectureGroup.getResource(CLIENT, BASE_URI), 
                        new SelectEntity<PhoenixLectureGroup>().addKey("lecture", 
                        new SelectEntity<PhoenixLecture>().addKey("title", lectureTitle)));
        return result;
    }
    
    public PhoenixLectureGroup get(String lectureTitle, String groupName) {
        PhoenixLectureGroup result = 
                this.get(PhoenixLectureGroup.getResource(CLIENT, BASE_URI), 
                        new SelectEntity<PhoenixLectureGroup>().addKey("name", groupName).addKey("lecture", 
                        new SelectEntity<PhoenixLecture>().addKey("title", lectureTitle)));
        return result;
    }
    
    public List<PhoenixLectureGroupTaskSheet> getGroupTaskSheets(PhoenixLectureGroup group){
        List<PhoenixLectureGroupTaskSheet> result = 
                this.getList(PhoenixLectureGroupTaskSheet.getResource(CLIENT, BASE_URI),
                        new SelectEntity<PhoenixLectureGroupTaskSheet>().addKey("lectureGroup", group));
        return result;
    }

    public int delete(String lectureTitle, String groupName) {
        this.change(PhoenixLectureGroup.deleteResource(CLIENT, BASE_URI),
                new SelectEntity<PhoenixLectureGroup>().addKey("name", groupName).addKey("lecture", 
                new SelectEntity<PhoenixLecture>().addKey("title", lectureTitle)));
        return this.getStatus();
        //throw new UnsupportedOperationException(); //TODO implement delete (Group)
    }
    
    public int update(SelectEntity<PhoenixLectureGroup> selectEntity) {
        throw new UnsupportedOperationException(); //TODO implement update (Group)
    }
}
