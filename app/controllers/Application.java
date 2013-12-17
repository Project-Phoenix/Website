/*
 * Copyright (C) 2013 Project-Phoenix
 * 
 * This file is part of Website.
 * 
 * Website is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * Website is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Website.  If not, see <http://www.gnu.org/licenses/>.
 */

package controllers;



import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.createLecture;
import views.html.createTask;
import views.html.home;
import views.html.showSubmissions;
import views.html.showTasks;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import de.phoenix.rs.PhoenixClient;
import de.phoenix.rs.entity.PhoenixAttachment;
import de.phoenix.rs.entity.PhoenixDetails;
import de.phoenix.rs.entity.PhoenixLecture;
import de.phoenix.rs.entity.PhoenixSubmission;
import de.phoenix.rs.entity.PhoenixTask;
import de.phoenix.rs.entity.PhoenixText;



/**
 *  This class handles all the functionality used by the frontend.
 * @author Markus W.<br>Matthias E.
 * 
 */
public class Application extends Controller {
    
    private final static String BASE_URI = "http://meldanor.dyndns.org:8080/PhoenixWebService/rest";
    private final static Client CLIENT = PhoenixClient.create();
    /**
     * Displays the home page
     * @return play.mvc.Results.Status
     */
    
    public static Result home() {
        return ok(home.render("Home"));
    }
    
    public static Result createTask() {
        return ok(createTask.render("Create Task"));
    }
    
    public static Result sendTask() {
        MultipartFormData form = request().body().asMultipartFormData();
        
        ArrayList<PhoenixAttachment> attachmentLst = new ArrayList<PhoenixAttachment>();
        ArrayList<PhoenixText> patternLst = new ArrayList<PhoenixText>();
        for (FilePart fp : form.getFiles()) {
            try {
                if (fp.getKey().equals("binary"))
                    attachmentLst.add(new PhoenixAttachment(fp.getFile(), fp.getFilename()));
                else if (fp.getKey().equals("pattern"))
                    patternLst.add(new PhoenixText(fp.getFile(), fp.getFilename()));
            } catch (IOException e) {}
        }  

        WebResource wr = CLIENT.resource(BASE_URI).path(PhoenixTask.WEB_RESOURCE_ROOT).path(PhoenixTask.WEB_RESOURCE_CREATE);
                
        PhoenixTask task = new PhoenixTask(attachmentLst,patternLst, Form.form().bindFromRequest().get("description"), Form.form().bindFromRequest().get("title"));
        ClientResponse post = wr.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, task);
        System.out.println("CreateTask Status: "+post.getStatus());

        return ok();
    }
      
    public static Result showTasks() {
        List<PhoenixTask> tasks = getAllTasks();
        return ok(showTasks.render("showTasks", tasks));
    }
    
    public static Result showSubmissions() {
        WebResource wr = CLIENT.resource(BASE_URI).path(PhoenixSubmission.WEB_RESOURCE_ROOT).path(PhoenixSubmission.WEB_RESOURCE_GET_TASK_SUBMISSIONS);
        List<PhoenixTask> tasks = getAllTasks();
        ClientResponse post = wr.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, tasks.get(0));
        List<PhoenixSubmission> submissions = PhoenixSubmission.fromSendableList(post);
        return ok(showSubmissions.render("showSubmissions", submissions));
    }

    public static List<PhoenixTask> getAllTasks(){
        WebResource wr = CLIENT.resource(BASE_URI).path(PhoenixTask.WEB_RESOURCE_ROOT).path(PhoenixTask.WEB_RESOURCE_GETALL);
        ClientResponse resp = wr.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        
        return PhoenixTask.fromSendableList(resp);       
    }
    

    public static Result createLecture() {
        return ok(createLecture.render("Create Lecture"));
    }
    
    public static Result sendLecture() {
        
        //Arrays to get the inputs form CreateLecture
        String[] keyStrings = new String[] {"title", "room", "day", "startHours","startMinutes", "endHours", 
                                            "endMinutes", "period", "startYear", "startMonth", "startDay",
                                            "endYear", "endMonth", "endDay"};
        String[] keyStrings2 = new String[] {"room2", "day2", "startHours2","startMinutes2", "endHours2", 
                                            "endMinutes2", "period2", "startYear2", "startMonth2", "startDay2",
                                            "endYear2", "endMonth2", "endDay2"};
        String[] keyStrings3 = new String[] {"room3", "day3", "startHours3","startMinutes3", "endHours3", 
                                            "endMinutes3", "period3", "startYear3", "startMonth3", "startDay3",
                                            "endYear3", "endMonth3", "endDay3"};
        String[][] details = new String[][] {keyStrings, keyStrings2, keyStrings3};
        //Arrays, which will be filled with the requeststrings
        String[][] requests = new String[3][13];
        WebResource ws = CLIENT.resource(BASE_URI).path(PhoenixLecture.WEB_RESOURCE_ROOT).path(PhoenixLecture.WEB_RESOURCE_CREATE);
        
        String title = "";
        //if input is missing don't create a detail later
        boolean[] wrongInput = new boolean[] {false,false,false};
        int itemCount = 0;
        int arrayCount = 0;
        for(String[] stringArray: details){   
            // TODO: exception handling
            // Get the requests and if something missing, set wronginput[arrayCount] to true and test the next detail
            for(String item: stringArray){
                String temp = Form.form().bindFromRequest().get(item);
                if ((Form.form().bindFromRequest().get("box1") == null) && (arrayCount == 1) ||(Form.form().bindFromRequest().get("box2") == null) && (arrayCount == 2)){
                    wrongInput[arrayCount] = true;         //TODO: if (wrongInput == true) throw IOException;
                    break;                
                }
                //if everything's filled in, set title and put the rest in the requestarrays
                else {
                    if (itemCount == 0) {
                        title = temp;
                        itemCount++;
                    }
                    else{
                        requests[arrayCount][itemCount-1] = temp;
                        itemCount++;               
                    }
                }
            }
            //just one title so start next loop at itemcount 1
            itemCount = 1;
            arrayCount++;
        } 
        //PhoenixDetaillist
        List<PhoenixDetails> allDetails = new ArrayList<PhoenixDetails>();
        int boolIndex = 0;
        for(String[] item: requests){
            //only create LectureCheck if detail is complete
            if(!wrongInput[boolIndex]){
                LectureCheck lectureCheck = new LectureCheck(item);
                //add it to allDetails
                allDetails.add(lectureCheck.getPhoenixDetails());
            }
            boolIndex++;
        }
        //send it to server
        PhoenixLecture lecture = new PhoenixLecture(title, allDetails);
        ws.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, lecture);
        
        return ok();
    }

    public static Result download(String title, String filename, String type){
        WebResource wr = CLIENT.resource(BASE_URI).path(PhoenixTask.WEB_RESOURCE_ROOT).path(PhoenixTask.WEB_RESOURCE_GETBYTITLE);
        ClientResponse post = wr.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, title);
        List<PhoenixTask> taskList = PhoenixTask.fromSendableList(post);
        
        if (!taskList.isEmpty()){
            PhoenixTask task = taskList.get(0);
            if (task != null){
                try {
                    response().setContentType("application/x-download");
                    if (type.equals("attachment")) { 
                        for(PhoenixAttachment a : task.getAttachments()) 
                            if ((a.getName()+"."+a.getType()).equals(filename)) { 
                                    //TODO: Filenames in HTML header without spaces?
                                    response().setHeader("Content-disposition","attachment; filename="+URI.create(a.getName()+"."+a.getType())); 
                                    response().setHeader("Content-Lenght", String.valueOf(a.getFile().length()));
                                    return ok(a.getFile());
                                }
                    }
                    else if (type.equals("pattern")) {
                        for(PhoenixText t : task.getPattern()) 
                            if ((t.getName()+"."+t.getType()).equals(filename)) { 
                                    response().setHeader("Content-disposition","attachment; filename="+t.getName()+"."+t.getType()); 
                                    response().setHeader("Content-Lenght", String.valueOf(t.getFile().length()));
                                    return ok(t.getFile());
                                }
                    }
                } catch (IOException e) { return internalServerError("File not found!"); }
            }
        }

        return internalServerError();
    }
    
    public static Result createTaskSheet() {
        //TODO MACH WAS!
        return ok();
    }

}
