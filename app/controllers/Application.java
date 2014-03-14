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

import meta.Requester;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import de.phoenix.rs.entity.PhoenixAttachment;
import de.phoenix.rs.entity.PhoenixTask;
import de.phoenix.rs.entity.PhoenixText;



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
    
    //protected final static String BASE_URI = "http://meldanor.dyndns.org:8080/PhoenixWebService/rest";
    //protected final static Client CLIENT = PhoenixClient.create();
    
    public static Result home() {
        return ok(home.render("Home"));
    }
    
    public static Result debug() {
        return ok(_Debug.render("DEBUG",""));
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
}
