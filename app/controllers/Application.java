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


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import util.Jenkins;
import views.html.*;
import de.phoenix.database.entity.User;
/**
 *  This class handles all the functionality used by the frontend.
 * @author Markus W.<br>Matthias E.
 * 
 */
public class Application extends Controller {
    
    /**
     * Displays the Homepage
     * @return play.mvc.Results.Status
     */
    public static Result home() {
        return ok(home.render("Home"));
    }
    
    public static Result register() {
        return ok(register.render("Registrieren"));
    }
    
    public static Result handleRegister() {
        Client client = Client.create();
        WebResource wr = client.resource(Jenkins.BASE_URL).path("account").path("register");
        User user = null;
        
        try {
            user = models.Parser.setUser(  Form.form().bindFromRequest() );
        }
        catch (Exception e) {
            
        }
        
        ClientResponse response = wr.post(ClientResponse.class, user);
        if (response.getStatus() == 200) {
            return ok(home.render("Home"));
        }
        
        return ok(); //TODO send connection error
        
    }
    
    public static Result login() {
        return ok(login.render());
    }
    
  
}
