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

import play.mvc.*;
import play.data.Form;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import de.phoenix.security.*;


public class Login extends Controller {
    
    private static final String BASE_URL = "http://meldanor.dyndns.org:8080/PhoenixWebService/rest/";

    public static Result login() {
        String password =  Form.form().bindFromRequest().get("password");
        String username =  Form.form().bindFromRequest().get("username");
        
        Client client = Client.create();
        WebResource createAccountRes = client.resource(BASE_URL).path("account").path("create");
        createAccountRes.removeAllFilters();
        
        WebResource requestTokenRes = client.resource(BASE_URL).path("token").path("request");
        requestTokenRes.addFilter(new LoginFilter(username, password));
        
        ClientResponse response =  requestTokenRes.get(ClientResponse.class);
        if ( response.getClientResponseStatus().equals(ClientResponse.Status.OK) ) {
            Token token = response.getEntity(Token.class);
            WebResource validateTokenRes = client.resource(BASE_URL).path("token").path("validate");
            client.addFilter(new TokenFilter(token));
            response = validateTokenRes.get(ClientResponse.class);
            if ( response.getClientResponseStatus().equals(ClientResponse.Status.OK) ) {
                session("PhoenixUser",username);
                session(username, token.getID());
                return redirect(routes.Application.home()); 
            }
            else {
                return Controller.status(NOT_FOUND); //TODO: Token not valid
            }
        }
        return Controller.status(NOT_ACCEPTABLE); //TODO Login failed
    }
    
    public static Result register() {        
        if ( Form.form().bindFromRequest().get("password").equals( Form.form().bindFromRequest().get("rtpassword") ) ) {
            String password =  Form.form().bindFromRequest().get("password");
            String username =  Form.form().bindFromRequest().get("username");
            
            Client client = Client.create();
            WebResource createAccountRes = client.resource(BASE_URL).path("account").path("create");
            createAccountRes.addFilter(new CreateAccountFilter(username, password));
            
            ClientResponse response = createAccountRes.get(ClientResponse.class);
            if( response.getClientResponseStatus().equals(ClientResponse.Status.OK) ) {
                flash("Register","true");
                return redirect(routes.Application.home());
            }
            else
                    return Controller.status(NOT_ACCEPTABLE);
        }
        else
            flash("wrongReType","true");
        
        return redirect(routes.Application.register());
    }
    
    public static Result logout(){
        session().remove("PhoenixUser");
        return redirect(routes.Application.home());
    }

}
