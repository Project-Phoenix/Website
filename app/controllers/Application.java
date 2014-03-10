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
import java.util.Map;

import javax.ws.rs.core.MediaType;

import meta.Requester;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.createLecture;
import views.html.home;
import views.html.stringShower;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import de.phoenix.rs.PhoenixClient;
import de.phoenix.rs.entity.PhoenixAttachment;
import de.phoenix.rs.entity.PhoenixLecture;
import de.phoenix.rs.entity.PhoenixLectureGroup;
import de.phoenix.rs.entity.PhoenixTask;
import de.phoenix.rs.entity.PhoenixText;

import de.phoenix.rs.key.SelectEntity;



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

    public static Result download(String title, String filename, String type){
            PhoenixTask task = Requester.Task.get(title);

            try {
                response().setContentType("application/x-download");
                if (type.equals("attachment")) { 
                    for(PhoenixAttachment a : task.getAttachments()) 
                        if ((a.getName()+"."+a.getType()).equals(filename)) { 
                                response().setHeader("Content-disposition","attachment; filename="+URI.create(a.getName().replace(" ", "_")+"."+a.getType())); 
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

        return internalServerError();
    }

    //TODO:
    //- Exceptions(?)
    //- Statushandling
    public static Result deleteGroups(){
        //deleteResource
        Map<String, String> boxes = Form.form().bindFromRequest().data();
        boxes.remove("delete");
        WebResource ws = PhoenixLectureGroup.deleteResource(CLIENT, BASE_URI);
        String lecture = boxes.get("lecture");
        for(String key : boxes.keySet()) {              
            // Create Lecture Group Selector
            SelectEntity<PhoenixLectureGroup> groupSelector = new SelectEntity<PhoenixLectureGroup>();
            // Add Lecture Group Key - the name
            groupSelector.addKey("name", key);

            // Create Lecture Selector
            SelectEntity<PhoenixLecture> lectureSelector = new SelectEntity<PhoenixLecture>();
            // Add Lecture Key - the title
            lectureSelector.addKey("title", lecture);
            // Add lecture selector to group selector (only groups from this lecture are selected)
            groupSelector.addKey("lecture", lectureSelector);

            // Send delete response
            ClientResponse response = ws.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, groupSelector);
            if (response.getStatus() != 200){
                return ok(stringShower.render("Groups deleted", "Ups, da ist ein Fehler aufgetreten!(" + response.getStatus() + ")"));
            }
        }
        return ok(stringShower.render("Groups deleted", "Groups deleted!"));
    }
    
//  TODO: Kilian has to add an update resource for lectureGroup
    public static Result updateGroup(){
      /*  Map<String, String> boxes = Form.form().bindFromRequest().data();
        WebResource ws = PhoenixLectureGroup.updateResource(CLIENT, BASE_URI);
        String lecture = boxes.get("lecture");
        String Group = boxes.get("group");
        for(String key : boxes.keySet()) {
            System.out.println(key);
        }*/
        return ok(stringShower.render("YESSSS", "no update method for groups yet"));       
    }
    
    /* TO GROUPAPPLICATION !!
    public static Result showGroupTaskSheets(){
        String group = Form.form().bindFromRequest().get("group");
        // get Group
        SelectEntity<PhoenixLectureGroup> lectureGroupSelector = new SelectEntity<PhoenixLectureGroup>().addKey("name", group);
        WebResource wsGroup = PhoenixLectureGroup.getResource(CLIENT, BASE_URI);
        ClientResponse responseGroup = wsGroup.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, lectureGroupSelector);
        if(responseGroup.getStatus()!=200) return ok(stringShower.render("showGroupTaskSheet", "Es ist ein Fehler bei dem Finden der Gruppe aufgetreten("+responseGroup.getStatus()+")"));
        PhoenixLectureGroup lectureGroup = EntityUtil.extractEntity(responseGroup);
        System.out.println(lectureGroup);
        
        // get LectureGroupTaskSheet
        SelectEntity<PhoenixLectureGroupTaskSheet> lectureGroupTaskSheetSelector = new SelectEntity<PhoenixLectureGroupTaskSheet>().addKey("lectureGroup", lectureGroup);
        WebResource ws = PhoenixLectureGroupTaskSheet.getResource(CLIENT, BASE_URI);
        ClientResponse response = ws.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, lectureGroupTaskSheetSelector);
        if(responseGroup.getStatus()!=200) return ok(stringShower.render("showGroupTaskSheet", "Es ist ein Fehler bei dem Finden der TaskSheets aufgetreten("+responseGroup.getStatus()+")"));
        List<PhoenixLectureGroupTaskSheet> listLectureGroupTaskSheet = EntityUtil.extractEntityList(response);
        List<PhoenixTaskSheet> taskSheets = new ArrayList<PhoenixTaskSheet>();
        for(PhoenixLectureGroupTaskSheet lectureGroupTaskSheet: listLectureGroupTaskSheet){
            taskSheets.add(lectureGroupTaskSheet.getTaskSheet());
        }
        return ok(showTaskSheet.render("show TaskSheet", taskSheets));
    }

    */
    //VERSCHIEBEN
    public static Result updateLecture(){
        String title = Form.form().bindFromRequest().get("lecture");
        System.out.println(title);
        PhoenixLecture lecture = Requester.Lecture.get(title);
        return ok(createLecture.render("Create Lecture", lecture));
    }
}
