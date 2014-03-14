package util;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

import de.phoenix.date.Weekday;
import de.phoenix.rs.entity.PhoenixDetails;

public class LectureCheck {

    private LectureCheck() {   
    }
    
    public static PhoenixDetails getPhoenixDetails(String room, String weekday, LocalTime startTime, LocalTime endTime, Period period, LocalDate startDate, LocalDate endDate){
        PhoenixDetails details = new PhoenixDetails(room, Weekday.forName(weekday), startTime, endTime, period, startDate, endDate);
        return details;
    }
    
    public static Period getPeriod(int interval, String type){
        Period period = Period.ZERO;
        switch(type){
            case "days":
                return period.plusDays(interval);
            case "weeks":
                return period.plusWeeks(interval);
            case "months":
                return period.plusMonths(interval);
            default:
                return period;
        }
    }
    
    public static LocalTime getTime(String time){
        System.out.println(time);
        return LocalTime.parse(time, DateTimeFormat.forPattern("H:m")); 
        }
    
    public static LocalDate getDate(String date){
        System.out.println(date);
        return LocalDate.parse(date, DateTimeFormat.forPattern("Y-M-d")); 
    }
    
}
