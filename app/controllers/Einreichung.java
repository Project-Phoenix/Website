package controllers;

import java.util.ArrayList;

public class Einreichung {
    
    private String status;
    private String date;
    private ArrayList<String> files;
    
    public Einreichung(String status, String date) {
        this.status = status;
        this.date = date;
        this.files = new ArrayList<String>();
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<String> getFiles() {
        return files;
    }
    
    public void addFile(String name, String content){
        files.add(name+"\n"+content);
    }


}
