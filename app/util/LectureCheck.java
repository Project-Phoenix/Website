package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import meta.Requester;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

import de.phoenix.date.Weekday;
import de.phoenix.rs.entity.PhoenixDetails;
import de.phoenix.rs.entity.PhoenixLecture;
import de.phoenix.rs.entity.PhoenixLectureGroup;

public class LectureCheck {

    private LectureCheck() {   
    }
    
    public static PhoenixDetails createPhoenixDetails(String room, String weekday, LocalTime startTime, LocalTime endTime, Period period, LocalDate startDate, LocalDate endDate){
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
        return LocalTime.parse(time, DateTimeFormat.forPattern("H:m")); 
        }
    
    public static LocalDate getDate(String date){
        return LocalDate.parse(date, DateTimeFormat.forPattern("YYYY-MM-dd")); 
    }
    
    public static void setNewDetails(List<PhoenixDetails> oldDetails, List<PhoenixDetails> newDetails, PhoenixLecture newLecture){
        int oldDetailsCount = oldDetails.size();
        int newDetailsCount = newDetails.size();
        
        if(oldDetailsCount <= newDetailsCount){
            updateDetails(oldDetails, newDetails, oldDetailsCount);
            if(oldDetailsCount < newDetailsCount){
                List<PhoenixDetails> newDetailList = new ArrayList<PhoenixDetails>();
                for(int i = oldDetailsCount; i < newDetailsCount; i++){
                    newDetailList.add(newDetails.get(i));
                }
                Requester.Lecture.addDetails(newLecture, newDetailList);
                if (Requester.Lecture.getStatus() != 200){
                    util.Err.displayError(Requester.Lecture.getStatus(),"Error adding some Details to this lecture!"); 
                }
            }
            
        }else{
            updateDetails(oldDetails, newDetails, newDetailsCount);
            for(int i = newDetailsCount; i < oldDetailsCount; i++){
                Requester.Lecture.deleteDetails(oldDetails.get(i));
                if (Requester.Lecture.getStatus() != 200){
                    util.Err.displayError(Requester.Lecture.getStatus(),"Error deleting this Detail!"); 
                    break;
                }
            }
        }
    }
    
    public static void setNewDetails(List<PhoenixDetails> oldDetails, List<PhoenixDetails> newDetails, PhoenixLectureGroup newGroup){
        int oldDetailsCount = oldDetails.size();
        int newDetailsCount = newDetails.size();
        
        if(oldDetailsCount <= newDetailsCount){
            updateDetails(oldDetails, newDetails, oldDetailsCount);
            if(oldDetailsCount < newDetailsCount){
                List<PhoenixDetails> newDetailList = new ArrayList<PhoenixDetails>();
                for(int i = oldDetailsCount; i < newDetailsCount; i++){
                    newDetailList.add(newDetails.get(i));
                }
                Requester.Group.addDetails(newGroup, newDetailList);
                if (Requester.Group.getStatus() != 200){
                    util.Err.displayError(Requester.Lecture.getStatus(),"Error adding some Details to this Group!"); 
                }
            }
            
        }else{
            updateDetails(oldDetails, newDetails, newDetailsCount);
            for(int i = newDetailsCount; i < oldDetailsCount; i++){
                Requester.Lecture.deleteDetails(oldDetails.get(i));
                if (Requester.Lecture.getStatus() != 200){
                    util.Err.displayError(Requester.Lecture.getStatus(),"Error deleting this Detail!"); 
                    break;
                }
            }
        }
    }

    private static void updateDetails(List<PhoenixDetails> oldDetails, List<PhoenixDetails> newDetails, int updateCount){
        for(int i = 0; i < updateCount; i++){
            Requester.Lecture.updateDetails(oldDetails.get(i), newDetails.get(i));
            if (Requester.Lecture.getStatus() != 200){
                util.Err.displayError(Requester.Lecture.getStatus(),"Error updating this Detail!"); 
                break;
            }
        } 
    }
    
    public static List<PhoenixDetails> createDetails(Map<String, String> data){
        List<PhoenixDetails> allDetails = new ArrayList<PhoenixDetails>();
        for(String item: data.keySet()){
            String number = "";
            if(item.startsWith("room_")){
                number = item.substring(5);
                System.out.println(data.get("startDate_"+number));
                allDetails.add(createPhoenixDetails( data.get("room_"+number), 
                                                               data.get("day_"+number),
                                                               getTime(data.get("startTime_"+number)),
                                                               getTime(data.get("endTime_"+number)),
                                                               getPeriod(Integer.parseInt(data.get("period_"+number)), data.get("periodDD_"+number)),
                                                               getDate(data.get("startDate_"+number)),
                                                               getDate(data.get("endDate_"+number))));
            }
        }
        return allDetails;
    }
    
}
