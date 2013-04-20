package controllers;

//import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result home() {
        return ok(home.render("Home"));
    }

    public static Result student1() {
        return ok(student1.render("Student"));
    }
    
    public static Result student2() {
        return ok(student2.render("Student"));
    }
    
    public static Result teacher() {
        return ok(teacher.render("Teacher"));
    }
    
    public static Result mygroup() {
        return ok(mygroup.render("MyGroup"));
    }
    
    public static Result group() {
        return ok(group.render("Group"));
    }
    
    public static Result course() {
        return ok(course.render("Course"));
    }
    
}
