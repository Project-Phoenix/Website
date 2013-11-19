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



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.data.Form;
import views.html.*;
import de.phoenix.rs.entity.PhoenixTask;

/**
 *  This class handles all the functionality used by the frontend.
 * @author Markus W.<br>Matthias E.
 * 
 */
public class Application extends Controller {
    
    /**
     * Displays the home page
     * @return play.mvc.Results.Status
     */
    
    private final static String BASE_URI = "http://meldanor.dyndns.org:8080/PhoenixWebService/rest/";
    
    public static Result home() {
        return ok(home.render("Home"));
    }
    
    public static Result createTask() {
        return ok(createTask.render("Create Task"));
    }
    
    public static Result sendTask() {

        MultipartFormData form = request().body().asMultipartFormData();
        ArrayList<File> fileLst = new ArrayList<File>();
        for (FilePart name : form.getFiles())
            fileLst.add(name.getFile());

        Client c = Client.create();
        WebResource wr = c.resource(BASE_URI).path(PhoenixTask.WEB_RESOURCE_ROOT).path(PhoenixTask.WEB_RESOURCE_CREATE);
        PhoenixTask task = null;
        try {
            task = new PhoenixTask(Form.form().bindFromRequest().get("title"), Form.form().bindFromRequest().get("description"), fileLst, new ArrayList());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ClientResponse post = wr.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, task);
        System.out.println(post.getStatus());

        return ok();
    }

}
