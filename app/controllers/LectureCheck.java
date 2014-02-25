package controllers;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import de.phoenix.rs.entity.PhoenixDetails;

public class LectureCheck {
private String room;
private int day;
private LocalTime startTime;
private LocalTime endTime;
private Period period;
private LocalDate startDate; 
private LocalDate endDate;

    LectureCheck(String [] details) {
        this.room = details[0];
        this.day = checkWeekDay(details[1]);
        this.startTime = checkTime(details[2], details[3]);
        this.endTime = checkTime(details[4], details[5]);
        this.period = checkPeriod(details[6]);
        this.startDate = checkDate(details[7], details[8], details[9]);
        this.endDate = checkDate(details[10], details[11], details[12]);        
    }
    
    public PhoenixDetails getPhoenixDetails(){
        PhoenixDetails details = new PhoenixDetails(this.room, this.day, this.startTime, this.endTime, this.period, this.startDate, this.endDate);
        return details;
    }
    
    private int checkWeekDay(String day){
        int dayConstant = 0;
        switch(day){
            case "monday":
                dayConstant = DateTimeConstants.MONDAY;
                break;
            case "tuesday":
                dayConstant = DateTimeConstants.TUESDAY;
                break;
            case "wednesday":
                dayConstant = DateTimeConstants.WEDNESDAY;
                break;
            case "thursday":
                dayConstant = DateTimeConstants.THURSDAY;
                break;
            case "friday":
                dayConstant = DateTimeConstants.FRIDAY;
                break;
            case "saturday":
                dayConstant = DateTimeConstants.SATURDAY;
                break;
            case "sunday":
                dayConstant = DateTimeConstants.SUNDAY;
                break;
            default:
                //throw invalid input exception
                System.out.println("null, dafuq?!");
        }            
        return dayConstant;
    }
    
    private LocalTime checkTime(String hour, String minute){
        LocalTime time = new LocalTime(0,0); 
        if ((hour.matches("\\d+")) && (minute.matches("\\d+")))
            time = new LocalTime(Integer.parseInt(hour), Integer.parseInt(minute));
        //TODO: else exception        
        return time;
    }
    
    
    //should be better!
    private Period checkPeriod(String stringPeriod){
        Period period = Period.ZERO;
        switch(stringPeriod){
            case "daily":  
                period = Period.days(1);
                break;
            case "weekly":
                period = Period.weeks(1);
            case "monthly":
                period = Period.months(1);
            case "yearly":
                period = Period.years(1);
            //default:
                //no input exception
        } 
        return period;
    }
    
    private LocalDate checkDate(String year, String month, String day){
        LocalDate date = new LocalDate(1,1,1);
        if ((year.matches("\\d+")) && (month.matches("\\d+")) && (day.matches("\\d+")))
            date = new LocalDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        //TODO: else exception 
        return date;
    }
    
}
