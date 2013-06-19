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
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import util.Jenkins;
import views.html.*;
import de.phoenix.database.entity.User;
import de.phoenix.security.LoginFilter;
import de.phoenix.security.Token;
import de.phoenix.security.TokenFilter;
import exceptions.EmptyFieldException;
import exceptions.PasswordMismatchException;
import exceptions.TooShortPasswordException;
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
    public static Result home() {
        return ok(home.render("Home"));
    }
    
    /**
     * Displays registration page
     * @return play.mvc.Results.Status
     */
    public static Result register() {
        return ok(register.render("Registrieren"));
    }
    
    /**
     * Handles registration process. Connects to jenkins server,<br>
     * defined in the BASE_URL constant, fills user <br>
     * entity with information binded from Request form and sends the user data
     * back to the jenkins server.
     * @return
     * @throws PasswordMismatchException 
     * @throws EmptyFieldException 
     * @throws TooShortPasswordException
     */
    public static Result handleRegister() throws EmptyFieldException, TooShortPasswordException, PasswordMismatchException {
        Client client = Client.create();
        WebResource wr = client.resource(Jenkins.BASE_URL).path("account").path("register");
        User user = null;
        
        try {
            user = models.Parser.setUser(  Form.form().bindFromRequest() );
        }
        catch (EmptyFieldException e){
            flash("empty_field","true");
            return ok(register.render("Registrieren"));            
        }        
        catch (TooShortPasswordException e) {
            flash("too_short_password","true");
            return ok(register.render("Registrieren"));
        }
        catch (PasswordMismatchException e) {
            flash("password_mismatch","true");
            return ok(register.render("Registrieren"));             
        }

        
        ClientResponse response = wr.post(ClientResponse.class, user);
        if (response.getStatus() == 200) {
            flash("registration_successful","true");
            return ok(home.render("Home"));
        }
        
        return ok(); //TODO send connection error  
    }
    
    /**
     * Displays login page
     * @return play.mvc.Results.Status
     */
    public static Result login() {
        return ok(login.render("Login"));
    }
    
    /**
     * Binds username and password from Request and sends it to the jenkins server.<br>
     * Also validates Token and generates a new session for the user (with TokenID).
     * @return play.mvc.Results.Status
     */
    public static Result handleLogin() {
        Client client = Client.create();
        WebResource requestTokenRes = client.resource(Jenkins.BASE_URL).path("token").path("request");
        requestTokenRes.addFilter(
                new LoginFilter( Form.form().bindFromRequest().get("username"), Form.form().bindFromRequest().get("password"))
                );
        
        ClientResponse response = requestTokenRes.get(ClientResponse.class);
        
        if ( response.getClientResponseStatus().equals(ClientResponse.Status.OK) ) {
            
            Token token = response.getEntity(Token.class);
            WebResource validateTokenRes = client.resource(Jenkins.BASE_URL).path("token").path("validate");
            client.addFilter(new TokenFilter(token));
            
            //Token Validation and Session creation from TokenID
            response = validateTokenRes.get(ClientResponse.class);
            if ( response.getClientResponseStatus().equals(ClientResponse.Status.OK) ) {
                session("PhoenixUser",Form.form().bindFromRequest().get("username") );
                session(Form.form().bindFromRequest().get("username"), token.getID());
                return ok(home.render("Home"));
            }
            else {
                return Controller.status(505); //TODO: Token not valid status
            }
        }
        return Controller.status(505); //TODO Login failed status
   

    }
    
  
}
