package controllers;

//import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result home() {
        return ok(home.render("Home"));
    }
  
}
