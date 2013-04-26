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
    
    public static Result register() {
        return ok(register.render("Registrieren"));
    }
   
    public static Result material() {
        return ok(material.render("Materialien"));
    }
    
    public static Result statistics() {
        return ok(statistics.render("Statistik"));
    }
    
    public static Result submissions() {
        return ok(submissions.render("Einreichungen"));
    }
    
    public static Result taskpool() {
        return ok(taskpool.render("Aufgabenpool"));
    }
    
    public static Result create() {
        return ok(create.render("Erstellen"));
    }
    
    public static Result doctrine() {
        return ok(doctrine.render("Lehre"));
    }
    
    public static Result solutions() {
        return ok(solutions.render("Musterlösungen"));
    }
    
    public static Result exercise() {
        return ok(exercise.render("Übungsblatt"));
    }
}
