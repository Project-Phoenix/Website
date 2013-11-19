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


import java.util.List;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import de.phoenix.rs.PhoenixClient;
import de.phoenix.rs.entity.PhoenixDetails;
import de.phoenix.rs.entity.PhoenixSubmission;
import de.phoenix.rs.entity.PhoenixTask;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;


/**
 *  This class handles all the functionality used by the frontend.
 * @author Markus W.<br>Matthias E.
 * 
 */
public class Application extends Controller {
    
    private final static String BASE_URI = "http://meldanor.dyndns.org:8080/PhoenixWebService/rest";
    private final static Client c = PhoenixClient.create();
    
    /**
     * Displays the home page
     * @return play.mvc.Results.Status
     */
    public static Result home() {
        return ok(home.render("Home"));
    }
    
    public static Result showTasks() {
        List<PhoenixTask> tasks = getAllTasks();
        return ok(showTasks.render("showTasks", tasks));
    }
    
    public static Result showSubmissions() {
        WebResource wr = c.resource(BASE_URI).path(PhoenixSubmission.WEB_RESOURCE_ROOT).path(PhoenixSubmission.WEB_RESOURCE_GET_TASK_SUBMISSIONS);
        List<PhoenixTask> tasks = getAllTasks();
        ClientResponse post = wr.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, tasks.get(0));
        List<PhoenixSubmission> submissions = PhoenixSubmission.fromSendableList(post);
        return ok(showSubmissions.render("showSubmissions", submissions));
    }

    public static List<PhoenixTask> getAllTasks(){
        WebResource wr = c.resource(BASE_URI).path(PhoenixTask.WEB_RESOURCE_ROOT).path(PhoenixTask.WEB_RESOURCE_GETALL);
        ClientResponse resp = wr.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        
        return PhoenixTask.fromSendableList(resp);       
    }
    
    public static Result createLecture() {
        return ok(createLecture.render("Create Lecture"));
    }
    
    public static Result sendLecture() {
        System.out.println(Form.form().bindFromRequest().get("period2"));
        System.out.println(Form.form().bindFromRequest().get("selectDay"));
        System.out.println(Form.form().bindFromRequest().get("day"));
        
        return ok();
    }
}
