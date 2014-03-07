package meta;

import java.util.ArrayList;
import java.util.List;


import de.phoenix.rs.entity.PhoenixTask;
import de.phoenix.rs.entity.PhoenixTaskSheet;
import de.phoenix.rs.key.ConnectionEntity;
import de.phoenix.rs.key.SelectEntity;

public class TaskSheetElement extends PhoenixRequest {
    
    private int status;
    
    public TaskSheetElement() {
        this.status = 0;
    }
    
    public int create(String taskSheetName, List<String> taskTitles) {
        ConnectionEntity connectionEntity = new ConnectionEntity();
        List<SelectEntity<PhoenixTask>> list = new ArrayList<SelectEntity<PhoenixTask>>();
        for(String title : taskTitles) 
            list.add(new SelectEntity<PhoenixTask>().addKey("title", title));
        
        connectionEntity.addSelectEntities(PhoenixTask.class, list);
        connectionEntity.addAttribute("title", taskSheetName);
        
        this.status = this.create(PhoenixTaskSheet.connectTaskSheetWithTaskResource(CLIENT, BASE_URI), connectionEntity);
        return this.status;
    }
    
    private PhoenixTaskSheet get(SelectEntity<PhoenixTaskSheet> selectEntity) {
        return this.get(PhoenixTaskSheet.getResource(CLIENT, BASE_URI), selectEntity);
    }
    
    public PhoenixTaskSheet get(String Tasktitle) {
        PhoenixTaskSheet result = this.get(new SelectEntity<PhoenixTaskSheet>().addKey("title", Tasktitle));
        return result;
    }
    
    public List<PhoenixTaskSheet> getAll() {
        List<PhoenixTaskSheet> result = this.getAll(PhoenixTaskSheet.getResource(PhoenixRequest.CLIENT, PhoenixRequest.BASE_URI));
        return result;
    }
    
    public int delete(SelectEntity<PhoenixTaskSheet> selectEntity) {
        throw new UnsupportedOperationException(); //TODO implement delete (TaskSheet)
    }
    
    public int update(SelectEntity<PhoenixTaskSheet> selectEntity) {
        throw new UnsupportedOperationException(); //TODO implement update (TaskSheet)
    }
    
    public int getStatus() {
        return this.status;
    }

}
