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
import views.html.*;

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
        boxes.remove("Delete");
        WebResource ws = PhoenixLectureGroup.deleteResource(CLIENT, BASE_URI);
        String lecture = boxes.get("lecture").replaceAll("_", " ");
        for(String key : boxes.keySet()) {
            key = key.replaceAll("_", " ");                
            System.out.println(key);
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
}
