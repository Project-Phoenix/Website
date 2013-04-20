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
import views.html.*;

public class Login extends Controller {
    
    private static final String BASE_URL = "http://meldanor.dyndns.org:8080/PhoenixWebService/rest/";
    
    public static Result login() {
        return ok();
        // TODO: impl. login
    }
    
    public static Result register() {
        return ok();
        // TODO: impl. registration
    }
    

}
