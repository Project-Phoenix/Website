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
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import meta.Requester;

import org.apache.commons.io.FileUtils;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import util.Err;
import views.html._Debug;
import views.html._Images;
import bootstrap.html.*;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import de.phoenix.rs.PhoenixClient;
import de.phoenix.rs.entity.PhoenixAttachment;
import de.phoenix.rs.entity.PhoenixSubmission;
import de.phoenix.rs.entity.PhoenixTask;
import de.phoenix.rs.entity.PhoenixText;
import de.phoenix.rs.entity.titleonly.LectureTitle;



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
    
    private final static String DEBUG_URI = "http://meldanor.dyndns.org:8080/PhoenixWebService/rest/debug";
    private final static Client CLIENT = PhoenixClient.create();
    
    public static Result untrail(String path) {
        return movedPermanently("/" + path);
     }
    
    public static Result home() {
        try {
            @SuppressWarnings("unused")
            String x = request().queryString().get("old")[0];
            return ok(views.html.home.render("Home"));
        } catch (Exception e) {}
        return ok(bootstrap.html.home.render("Home"));
    }
    
    public static Result test() {
        List<LectureTitle> lectureTitles = Requester.Lecture.getAllTitles();
        return ok(test.render("Test", lectureTitles));
    }
    
    public static Result menu() throws UnsupportedEncodingException {
        Map<String, String> data = Form.form().bindFromRequest().data();
        System.out.println(data.get("hiddenAllLectures"));
        if(data.get("hiddenAllLectures").equals("yes")){
            return redirect("/showLecture?option=all");
        }else{
            System.out.println(data.get("hiddenLecture"));
            System.out.println(data.get("hiddenGroup"));
            if(!(data.get("hiddenLecture").equals("")) && (data.get("hiddenGroup").equals(""))){
                return redirect("/showLecture?option=" + URLEncoder.encode(data.get("hiddenLecture"), "UTF-8"));
            //showGroup
            }else if((!data.get("hiddenLecture").equals("")) && (!data.get("hiddenGroup").equals("")) && (data.get("hiddenTaskSheet").equals(""))){
                System.out.println("I'm Right?!?");
                return redirect("/showGroup?option=" + URLEncoder.encode(data.get("hiddenLecture"), "UTF-8") + "&group=" + URLEncoder.encode(data.get("hiddenGroup"), "UTF-8"));
            //showTaskSheet
            }else if((!data.get("hiddenLecture").equals("")) && (!data.get("hiddenGroup").equals("")) && (!data.get("hiddenTaskSheet").equals(""))){
                
            }
        }
        return ok();
    }

    public static Result images() { 
        return ok(_Images.render());
    }
    
    public static Result uploadImg() {
        MultipartFormData body = request().body().asMultipartFormData();
        FilePart picture = body.getFile("picture");
        
        if (picture != null && !picture.getFilename().isEmpty()) {
          try {
              FileUtils.moveFile(picture.getFile(), new File("public/images", picture.getFilename()));
              flash("uploadSuccess", "true");
          }
          catch (IOException e) {
              flash("uploadSuccess", "ioerror"); 
          }
          flash("uploadSuccess", "success"); 
        } else { flash("uploadSuccess", "nofile");}
        
        return redirect(routes.Application.images());    
      }
    
    public static Result debug() {
        WebResource webresource = CLIENT.resource(DEBUG_URI); 
        ClientResponse response = webresource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        return ok(_Debug.render("DEBUG",response.getEntity(String.class).replace("\n", "<br>")));
    }
     
    public static Result deleteDebug() {
        WebResource webresource = CLIENT.resource(DEBUG_URI+"/delete"); 
        webresource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        flash("debug_delete","true");
        return redirect("/debug");
    }
    
    public static Result download(String clazz, String title, String filename, String type, String submNr) {
        try {
            
            if (clazz.equals("task")) {
                PhoenixTask task = Requester.Task.get(title);
                if (type.equals("attachment")) {
                    for(PhoenixAttachment a : task.getAttachments()) 
                        if ((a.getName()+"."+a.getType()).equals(filename))
                            return setDownloadResponse(a.getFullname(), a.getFile());
                } else if (type.equals("pattern"))
                    for(PhoenixText t : task.getPattern()) 
                        if ((t.getName()+"."+t.getType()).equals(filename))
                            return setDownloadResponse(t.getFullname(), t.getFile());
            }
            
            if (clazz.equals("submission")) {
                List<PhoenixSubmission> submissions = Requester.Submission.get(title);
                if (type.equals("attachment")) {
                    System.out.println(submissions.size());
                    for(PhoenixAttachment a : submissions.get(Integer.valueOf(submNr)).getAttachments()) 
                        if ((a.getName()+"."+a.getType()).equals(filename))
                            return setDownloadResponse(a.getFullname(), a.getFile());
                }
            }
            
            return util.Err.displayError(404,"File not found! (<i>"+filename+"</i>)");
        } catch (IOException e) {
            Err.displayError(500,"Error getting file (IOException): "+filename);
        } catch (NumberFormatException e) {
            Err.displayError(500,"Error getting file (NumberFormatException): "+filename);
        }
        
        return util.Err.displayError(500,"Unknown error! Check stack trance!");
    }
    
    private static Result setDownloadResponse(String filename, File file) {
        response().setContentType("application/x-download");
        response().setHeader("Content-disposition","attachment; filename="+URI.create(filename.replace(" ", "_"))); 
        response().setHeader("Content-Lenght", String.valueOf(file.length()));
        return ok(file);
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
            } catch (IOException e) { return util.Err.displayError(404,"File not found! (<i>"+filename+"</i>)"); }

            return util.Err.displayError(500,"Unknown error! Check stack trance!");
    }
}
