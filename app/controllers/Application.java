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
    
    public static Result materialStudent() {
        return ok(materialStudent.render("Material Student"));
    }
    
    public static Result sheet_create(){
        return ok(sheet_create.render("Aufgabenblatt erstellen"));
    }
    
    public static Result AUDcourse(){
        return ok(AUDcourse.render("AuD VL"));
    }
    
    public static Result AUDgroup1(){
        return ok(AUDgroup1.render("AuD GR1"));
    }
    
    public static Result AUDgroup2(){
        return ok(AUDgroup2.render("AuD GR2"));
    }
    
    public static Result AUDgroup3(){
        return ok(AUDgroup3.render("AuD GR3"));
    }
    
    public static Result AUDgroup4(){
        return ok(AUDgroup4.render("AuD GR4"));
    }
    
    public static Result EIcourse(){
        return ok(EIcourse.render("Einf. Inf. VL"));
    }
    
    public static Result EIgroup1(){
        return ok(EIgroup1.render("Einf. Inf. GR1"));
    }
    
    public static Result EIgroup2(){
        return ok(EIgroup2.render("Einf. Inf. GR2"));
    }
    
    public static Result EIgroup3(){
        return ok(EIgroup3.render("Einf. Inf. GR3"));
    }
    
    public static Result EIgroup4(){
        return ok(EIgroup4.render("Einf. Inf. GR4"));
    }
    
    public static Result FPcourse(){
        return ok(FPcourse.render("Func. Prog. VL"));
    }
    
    public static Result FPgroup1(){
        return ok(FPgroup1.render("Func. Prog. GR1"));
    }
    
    public static Result FPgroup2(){
        return ok(FPgroup2.render("Func. Prog. GR2"));
    }
    
    public static Result FPgroup3(){
        return ok(FPgroup3.render("Func. Prog. GR3"));
    }
    
    public static Result FPgroup4(){
        return ok(FPgroup4.render("Func. Prog. GR4"));
    }
}
