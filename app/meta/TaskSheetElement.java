package meta;

import java.util.ArrayList;
import java.util.List;


import de.phoenix.rs.entity.PhoenixTask;
import de.phoenix.rs.entity.PhoenixTaskSheet;
import de.phoenix.rs.entity.connection.TaskSheetConnection;
import de.phoenix.rs.entity.disconnection.DisconnectTaskTaskSheet;
import de.phoenix.rs.key.ConnectionEntity;
import de.phoenix.rs.key.SelectEntity;

/**
 * Request class for the PhoenixTaskSheet Entity.<br>
 * It "wrappes" the methods from the PhoenixRequest class <br>
 * and provides specific methods for the PhoenixTaskSheet Entity.<br>
 * 
 * @author Matthias Eiserloh; Markus Wolf
 *
 */
public class TaskSheetElement extends PhoenixRequest {
    
    public int create(String taskSheetName, List<String> taskTitles) {
        ConnectionEntity connectionEntity = new ConnectionEntity();
        List<SelectEntity<PhoenixTask>> list = new ArrayList<SelectEntity<PhoenixTask>>();
        for(String title : taskTitles) 
            list.add(new SelectEntity<PhoenixTask>().addKey("title", title));
        
        connectionEntity.addSelectEntities(PhoenixTask.class, list);
        connectionEntity.addAttribute("title", taskSheetName);
        
        this.create(PhoenixTaskSheet.connectTaskSheetWithTaskResource(CLIENT, BASE_URI), connectionEntity);
        return this.getStatus();
    }
    
    public PhoenixTaskSheet get(String TaskSheettitle) {
        PhoenixTaskSheet result = this.get(PhoenixTaskSheet.getResource(CLIENT, BASE_URI), new SelectEntity<PhoenixTaskSheet>().addKey("title", TaskSheettitle));
        return result;
    }
    
    public List<PhoenixTaskSheet> getAll() {
        List<PhoenixTaskSheet> result = this.getAll(PhoenixTaskSheet.getResource(PhoenixRequest.CLIENT, PhoenixRequest.BASE_URI));
        return result;
    }

    public int deleteTask(String taskTitle, String taskSheetTitle) {
        SelectEntity<PhoenixTask> taskToDelete = new SelectEntity<PhoenixTask>().addKey("title", taskTitle);
        SelectEntity<PhoenixTaskSheet> taskSheetSelector = new SelectEntity<PhoenixTaskSheet>().addKey("title", taskSheetTitle);
        DisconnectTaskTaskSheet deleteEntity = new DisconnectTaskTaskSheet(taskToDelete, taskSheetSelector);
        this.change(PhoenixTaskSheet.removeTaskFromTaskSheetResource(CLIENT, BASE_URI), deleteEntity);
        return this.getStatus();        
    }
    
    public int addTask(String taskSheetTitle, List<String> tasks) {
        List<SelectEntity<PhoenixTask>> taskSelectors = new ArrayList<SelectEntity<PhoenixTask>>();
        for(String task : tasks)
            taskSelectors.add( new SelectEntity<PhoenixTask>().addKey("title", task));
        TaskSheetConnection toSend = new TaskSheetConnection(taskSelectors, taskSheetTitle);
        this.change(PhoenixTaskSheet.addTaskToTaskSheet(CLIENT, BASE_URI), toSend);
        return this.getStatus();
    }
    
    public int update(SelectEntity<PhoenixTaskSheet> selectEntity) {
        throw new UnsupportedOperationException(); //TODO implement update (TaskSheet)
    }
}
