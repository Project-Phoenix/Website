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
    
    public static Result GROUPcreate() {
        return ok(GROUPcreate.render("Gruppe Erstellen"));
    }
    
    public static Result COURSEcreate() {
        return ok(COURSEcreate.render("Veranstaltung Erstellen"));
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
    
    public static Result AUDSTcourse(){
        return ok(AUDSTcourse.render("AuD ST VL"));
    }
    
    public static Result AUDmaterialStudent() {
        return ok(AUDmaterialStudent.render("AUD Material Student"));
    }
    
    public static Result AUDSTsolutions(){
        return ok(AUDSTsolutions.render("AuD ST Musterlösungen"));
    }
    
    public static Result AUDSTSEMsubmissions(){
        return ok(AUDSTSEMsubmissions.render("AuD ST Einreichungen"));
    }
    
    public static Result AUDSTGRsubmissions(){
        return ok(AUDSTGRsubmissions.render("AuD ST GR Einreichungen"));
    }
    
    public static Result AUDSTSHsubmissions(){
        return ok(AUDSTSHsubmissions.render("AuD ST Blatt Einreichungen"));
    }
    
    public static Result AUDSTSEMstatistics(){
        return ok(AUDSTSEMstatistics.render("AuD ST statistiken"));
    }
    
    public static Result AUDSTGRstatistics(){
        return ok(AUDSTGRstatistics.render("AuD ST GR statistiken"));
    }
    
    public static Result AUDSTSHstatistics(){
        return ok(AUDSTSHstatistics.render("AuD ST Blatt statistiken"));
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
    
    public static Result AUDSTgroup1(){
        return ok(AUDSTgroup1.render("AuD ST GR1"));
    }
    
    public static Result AUDSTgroup2(){
        return ok(AUDSTgroup2.render("AuD ST GR2"));
    }
    
    public static Result AUDSTgroup3(){
        return ok(AUDSTgroup3.render("AuD ST GR3"));
    }
    
    public static Result AUDSTgroup4(){
        return ok(AUDSTgroup4.render("AuD ST GR4"));
    }
    
    public static Result AUDgr1sh1(){
        return ok(AUDgr1sh1.render("AUD GR1 SH1"));
    }
    
    public static Result AUDgr1sh2(){
        return ok(AUDgr1sh2.render("AUD GR1 SH2"));
    }
    
    public static Result AUDgr1sh3(){
        return ok(AUDgr1sh3.render("AUD GR1 SH3"));
    }
    
    public static Result AUDSTgr1sh1(){
        return ok(AUDSTgr1sh1.render("AUD ST GR1 SH1"));
    }
    
    public static Result AUDSTgr1sh2(){
        return ok(AUDSTgr1sh2.render("AUD ST GR1 SH2"));
    }
    
    public static Result AUDSTgr1sh3(){
        return ok(AUDSTgr1sh3.render("AUD ST GR1 SH3"));
    }
    
    public static Result AUDgr2sh1(){
        return ok(AUDgr2sh1.render("AUD GR2 SH1"));
    }
    
    public static Result AUDgr2sh2(){
        return ok(AUDgr2sh2.render("AUD GR2 SH2"));
    }
    
    public static Result AUDgr2sh3(){
        return ok(AUDgr2sh3.render("AUD GR2 SH3"));
    }
    
    public static Result AUDgr3sh1(){
        return ok(AUDgr3sh1.render("AUD GR3 SH1"));
    }
    
    public static Result AUDgr3sh2(){
        return ok(AUDgr3sh2.render("AUD GR3 SH2"));
    }
    
    public static Result AUDgr3sh3(){
        return ok(AUDgr3sh3.render("AUD GR3 SH3"));
    }
    
    public static Result AUDgr4sh1(){
        return ok(AUDgr4sh1.render("AUD GR4 SH1"));
    }
    
    public static Result AUDgr4sh2(){
        return ok(AUDgr4sh2.render("AUD GR4 SH2"));
    }
    
    public static Result AUDgr4sh3(){
        return ok(AUDgr4sh3.render("AUD GR4 SH3"));
    }
    
    public static Result AUDgr1sh1a1(){
        return ok(AUDgr1sh1a1.render("AUD GR1 SH1 A1"));
    }
    
    public static Result AUDgr1sh1a2(){
        return ok(AUDgr1sh1a2.render("AUD GR1 SH1 A2"));
    }
    
    public static Result AUDgr1sh1a3(){
        return ok(AUDgr1sh1a3.render("AUD GR1 SH1 A3"));
    }
    
    public static Result AUDgr1sh2a1(){
        return ok(AUDgr1sh2a1.render("AUD GR1 SH2 A1"));
    }
    
    public static Result AUDgr1sh2a2(){
        return ok(AUDgr1sh2a2.render("AUD GR1 SH2 A2"));
    }
    
    public static Result AUDgr1sh2a3(){
        return ok(AUDgr1sh2a3.render("AUD GR1 SH2 A3"));
    }
    
    public static Result AUDgr1sh3a1(){
        return ok(AUDgr1sh3a1.render("AUD GR1 SH3 A1"));
    }
    
    public static Result AUDgr1sh3a2(){
        return ok(AUDgr1sh3a2.render("AUD GR1 SH3 A2"));
    }
    
    public static Result AUDgr1sh3a3(){
        return ok(AUDgr1sh3a3.render("AUD GR1 SH3 A3"));
    }
    
    public static Result AUDSTgr1sh1a1(){
        return ok(AUDSTgr1sh1a1.render("AUD ST GR1 SH1 A1"));
    }
    
    public static Result AUDSTgr1sh1a2(){
        return ok(AUDSTgr1sh1a2.render("AUD ST GR1 SH1 A2"));
    }
    
    public static Result AUDSTgr1sh1a3(){
        return ok(AUDSTgr1sh1a3.render("AUD ST GR1 SH1 A3"));
    }
    
    public static Result AUDSTgr1sh2a1(){
        return ok(AUDSTgr1sh2a1.render("AUD ST GR1 SH2 A1"));
    }
    
    public static Result AUDSTgr1sh2a2(){
        return ok(AUDSTgr1sh2a2.render("AUD ST GR1 SH2 A2"));
    }
    
    public static Result AUDSTgr1sh2a3(){
        return ok(AUDSTgr1sh2a3.render("AUD ST GR1 SH2 A3"));
    }
    
    public static Result AUDSTgr1sh3a1(){
        return ok(AUDSTgr1sh3a1.render("AUD ST GR1 SH3 A1"));
    }
    
    public static Result AUDSTgr1sh3a2(){
        return ok(AUDSTgr1sh3a2.render("AUD ST GR1 SH3 A2"));
    }
    
    public static Result AUDSTgr1sh3a3(){
        return ok(AUDSTgr1sh3a3.render("AUD ST GR1 SH3 A3"));
    }
    
    public static Result AUDsheet_create(){
        return ok(AUDsheet_create.render("AUD Blatt erstellen"));
    }
    
    public static Result AUDtaskpool(){
        return ok(AUDtaskpool.render("AUD Aufgabenpool"));
    }
    
    public static Result AUDTEsolutions(){
        return ok(AUDTEsolutions.render("AUD Musterlösungen"));
    }
    
    public static Result AUDTEmaterial(){
        return ok(AUDTEmaterial.render("AUD TEmaterial"));
    }
    
    public static Result AUDTESTmaterial(){
        return ok(AUDTESTmaterial.render("AUD STmaterial"));
    }
    
    public static Result AUDTESEMsubmissions(){
        return ok(AUDTESEMsubmissions.render("AUD SEM Einreichungen"));
    }   
    
    public static Result AUDTEGRsubmissions(){
        return ok(AUDTEGRsubmissions.render("AUD GR Einreichungen"));
    } 
    
    public static Result AUDTESHsubmissions(){
        return ok(AUDTESHsubmissions.render("AUD SH Einreichungen"));
    } 
    
    public static Result AUDTETAsubmissions(){
        return ok(AUDTETAsubmissions.render("AUD TA Einreichungen"));
    } 
    
    public static Result AUDTESEMstatistics(){
        return ok(AUDTESEMstatistics.render("AUD SEM Statistiken"));
    } 

    public static Result AUDTEGRstatistics(){
        return ok(AUDTEGRstatistics.render("AUD GR Statistiken"));
    } 
    
    public static Result AUDTESHstatistics(){
        return ok(AUDTESHstatistics.render("AUD SH Statistiken"));
    } 
    
    public static Result AUDTETAstatistics(){
        return ok(AUDTETAstatistics.render("AUD TA Statistiken"));
    } 
    
    public static Result EIcourse(){
        return ok(EIcourse.render("Einf. Inf. VL"));
    }
    
    public static Result EISTcourse(){
        return ok(EISTcourse.render("Einf. Inf. ST VL"));
    }
    
    public static Result EImaterialStudent() {
        return ok(EImaterialStudent.render("Einf. Inf. Material Student"));
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
    
    public static Result EISTgroup1(){
        return ok(EISTgroup1.render("Einf. Inf. ST GR1"));
    }
    
    public static Result EISTgroup2(){
        return ok(EISTgroup2.render("Einf. Inf. ST GR2"));
    }
    
    public static Result EISTgroup3(){
        return ok(EISTgroup3.render("Einf. Inf. ST GR3"));
    }
    
    public static Result EISTgroup4(){
        return ok(EISTgroup4.render("Einf. Inf. ST GR4"));
    }
    
    public static Result EIgr1sh1(){
        return ok(EIgr1sh1.render("Einf. Inf. GR1 SH1"));
    }
    
    public static Result EIgr1sh2(){
        return ok(EIgr1sh2.render("Einf. Inf. GR1 SH2"));
    }
    
    public static Result EIgr1sh3(){
        return ok(EIgr1sh3.render("Einf. Inf. GR1 SH3"));
    }
    
    public static Result EIgr2sh1(){
        return ok(EIgr2sh1.render("Einf. Inf. GR2 SH1"));
    }
    
    public static Result EIgr2sh2(){
        return ok(EIgr2sh2.render("Einf. Inf. GR2 SH2"));
    }
    
    public static Result EIgr2sh3(){
        return ok(EIgr2sh3.render("Einf. Inf. GR2 SH3"));
    }
    
    public static Result EIgr3sh1(){
        return ok(EIgr3sh1.render("Einf. Inf. GR3 SH1"));
    }
    
    public static Result EIgr3sh2(){
        return ok(EIgr3sh2.render("Einf. Inf. GR3 SH2"));
    }
    
    public static Result EIgr3sh3(){
        return ok(EIgr3sh3.render("Einf. Inf. GR3 SH3"));
    }
    
    public static Result EIgr4sh1(){
        return ok(EIgr4sh1.render("Einf. Inf. GR4 SH1"));
    }
    
    public static Result EIgr4sh2(){
        return ok(EIgr4sh2.render("Einf. Inf. GR4 SH2"));
    }
    
    public static Result EIgr4sh3(){
        return ok(EIgr4sh3.render("Einf. Inf. GR4 SH3"));
    }
    
    public static Result EIgr1sh1a1(){
        return ok(EIgr1sh1a1.render("EI GR1 SH1 A1"));
    }
    
    public static Result EIgr1sh1a2(){
        return ok(EIgr1sh1a2.render("EI GR1 SH1 A2"));
    }
    
    public static Result EIgr1sh1a3(){
        return ok(EIgr1sh1a3.render("EI GR1 SH1 A3"));
    }
    
    public static Result EIgr1sh2a1(){
        return ok(EIgr1sh2a1.render("EI GR1 SH2 A1"));
    }
    
    public static Result EIgr1sh2a2(){
        return ok(EIgr1sh2a2.render("EI GR1 SH2 A2"));
    }
    
    public static Result EIgr1sh2a3(){
        return ok(EIgr1sh2a3.render("EI GR1 SH2 A3"));
    }
    
    public static Result EIgr1sh3a1(){
        return ok(EIgr1sh3a1.render("EI GR1 SH3 A1"));
    }
    
    public static Result EIgr1sh3a2(){
        return ok(EIgr1sh3a2.render("EI GR1 SH3 A2"));
    }
    
    public static Result EIgr1sh3a3(){
        return ok(EIgr1sh3a3.render("EI GR1 SH3 A3"));
    }
    
    public static Result EIsheet_create(){
        return ok(EIsheet_create.render("Einf. Inf. Blatt erstellen"));
    }
    
    public static Result EItaskpool(){
        return ok(EItaskpool.render("Einf. Inf. Aufgabenpool"));
    }
    
    public static Result EITEsolutions(){
        return ok(EITEsolutions.render("Einf. Inf. Musterlösungen"));
    }
    
    public static Result EITEmaterial(){
        return ok(EITEmaterial.render("Einf. Inf. TEmaterial"));
    }
    
    public static Result EITESTmaterial(){
        return ok(EITESTmaterial.render("Einf. Inf. STmaterial"));
    }
    
    public static Result EITESEMsubmissions(){
        return ok(EITESEMsubmissions.render("EI SEM Einreichungen"));
    }   
    
    public static Result EITEGRsubmissions(){
        return ok(EITEGRsubmissions.render("EI GR Einreichungen"));
    } 
    
    public static Result EITESHsubmissions(){
        return ok(EITESHsubmissions.render("EI SH Einreichungen"));
    } 
    
    public static Result EITETAsubmissions(){
        return ok(EITETAsubmissions.render("EI TA Einreichungen"));
    } 
    
    public static Result EITESEMstatistics(){
        return ok(EITESEMstatistics.render("EI SEM Statistiken"));
    } 

    public static Result EITEGRstatistics(){
        return ok(EITEGRstatistics.render("EI GR Statistiken"));
    } 
    
    public static Result EITESHstatistics(){
        return ok(EITESHstatistics.render("EI SH Statistiken"));
    }
    
    public static Result EITETAstatistics(){
        return ok(EITETAstatistics.render("EI TA Statistiken"));
    } 
    
    public static Result FPcourse(){
        return ok(FPcourse.render("Func. Prog. VL"));
    }
    
    public static Result FPSTcourse(){
        return ok(FPSTcourse.render("Func. Prog. ST VL"));
    }
    
    public static Result FPmaterialStudent() {
        return ok(FPmaterialStudent.render("Func. Prog. Material Student"));
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
    
    public static Result FPSTgroup1(){
        return ok(FPSTgroup1.render("Func. Prog. ST GR1"));
    }
    
    public static Result FPSTgroup2(){
        return ok(FPSTgroup2.render("Func. Prog. ST GR2"));
    }
    
    public static Result FPSTgroup3(){
        return ok(FPSTgroup3.render("Func. Prog. ST GR3"));
    }
    
    public static Result FPSTgroup4(){
        return ok(FPSTgroup4.render("Func. Prog. ST GR4"));
    }
    
    public static Result FPgr1sh1(){
        return ok(FPgr1sh1.render("Func. Prog. GR1 SH1"));
    }
    
    public static Result FPgr1sh2(){
        return ok(FPgr1sh2.render("Func. Prog. GR1 SH2"));
    }
    
    public static Result FPgr1sh3(){
        return ok(FPgr1sh3.render("Func. Prog. GR1 SH3"));
    }
    
    public static Result FPgr2sh1(){
        return ok(FPgr2sh1.render("Func. Prog. GR2 SH1"));
    }
    
    public static Result FPgr2sh2(){
        return ok(FPgr2sh2.render("Func. Prog. GR2 SH2"));
    }
    
    public static Result FPgr2sh3(){
        return ok(FPgr2sh3.render("Func. Prog. GR2 SH3"));
    }
    
    public static Result FPgr3sh1(){
        return ok(FPgr3sh1.render("Func. Prog. GR3 SH1"));
    }
    
    public static Result FPgr3sh2(){
        return ok(FPgr3sh2.render("Func. Prog. GR3 SH2"));
    }
    
    public static Result FPgr3sh3(){
        return ok(FPgr3sh3.render("Func. Prog. GR3 SH3"));
    }
    
    public static Result FPgr4sh1(){
        return ok(FPgr4sh1.render("Func. Prog. GR4 SH1"));
    }
    
    public static Result FPgr4sh2(){
        return ok(FPgr4sh2.render("Func. Prog. GR4 SH2"));
    }
    
    public static Result FPgr4sh3(){
        return ok(FPgr4sh3.render("Func. Prog. GR4 SH3"));
    }
    
    public static Result FPgr1sh1a1(){
        return ok(FPgr1sh1a1.render("FP GR1 SH1 A1"));
    }
    
   public static Result FPgr1sh1a2(){
       return ok(FPgr1sh1a2.render("FP GR1 SH1 A2"));
    }
    
    public static Result FPgr1sh1a3(){
        return ok(FPgr1sh1a3.render("FP GR1 SH1 A3"));
    }
    
    public static Result FPgr1sh2a1(){
        return ok(FPgr1sh2a1.render("FP GR1 SH2 A1"));
    }
    
    public static Result FPgr1sh2a2(){
        return ok(FPgr1sh2a2.render("FP GR1 SH2 A2"));
    }
    
    public static Result FPgr1sh2a3(){
        return ok(FPgr1sh2a3.render("FP GR1 SH2 A3"));
    }
    
    public static Result FPgr1sh3a1(){
        return ok(FPgr1sh3a1.render("FP GR1 SH3 A1"));
    }
    
    public static Result FPgr1sh3a2(){
        return ok(FPgr1sh3a2.render("FP GR1 SH3 A2"));
    }
    
    public static Result FPgr1sh3a3(){
        return ok(FPgr1sh3a3.render("FP GR1 SH3 A3"));
    }
    
    public static Result FPsheet_create(){
        return ok(FPsheet_create.render("Func. Prog. Blatt erstellen"));
    }
    
    public static Result FPtaskpool(){
        return ok(FPtaskpool.render("Func. Prog. Aufgabenpool"));
    }
    
    public static Result FPTEsolutions(){
        return ok(FPTEsolutions.render("Func. Prog. Musterlösungen"));
    }
    
    public static Result FPTEmaterial(){
        return ok(FPTEmaterial.render("Func. Prog. TEmaterial"));
    }
    
    public static Result FPTESTmaterial(){
        return ok(FPTESTmaterial.render("Func. Prog. STmaterial"));
    }
    
    public static Result FPTESEMsubmissions(){
        return ok(FPTESEMsubmissions.render("FP SEM Einreichungen"));
    }   
    
    public static Result FPTEGRsubmissions(){
        return ok(FPTEGRsubmissions.render("FP GR Einreichungen"));
    } 
    
    public static Result FPTESHsubmissions(){
        return ok(FPTESHsubmissions.render("FP SH Einreichungen"));
    } 
    
    public static Result FPTETAsubmissions(){
        return ok(FPTETAsubmissions.render("FP TA Einreichungen"));
    } 
    
    public static Result FPTESEMstatistics(){
        return ok(FPTESEMstatistics.render("FP SEM Statistiken"));
    } 

    public static Result FPTEGRstatistics(){
        return ok(FPTEGRstatistics.render("FP GR Statistiken"));
    } 
    
    public static Result FPTESHstatistics(){
        return ok(FPTESHstatistics.render("FP SH Statistiken"));
    }
    
    public static Result ViewSubmissions() {
        SubmissionHandler subh = new SubmissionHandler();
        return ok(ViewSubmissions.render("Submissions", subh.getSubmissions()));
	}

    public static Result FPTETAstatistics(){
        return ok(FPTETAstatistics.render("FP TA Statistiken"));
    }
}
