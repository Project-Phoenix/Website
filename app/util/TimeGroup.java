package util;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import de.phoenix.rs.entity.PhoenixLectureGroup;

public class TimeGroup {
    
    private PhoenixLectureGroup group;
    
    public TimeGroup(PhoenixLectureGroup group) {
        this.group = group;
    }

    public PhoenixLectureGroup getPhoenixGroup() {
        return group;
    }
    
    public DateTime getNextDeadlineDate() {
        return nextSameWeekday( this.group.getSubmissionDeadlineTime().toDateTimeToday(),
                this.group.getSubmissionDeadlineWeekday().getDateTimeConstant());
    }
    
    public String nextDeadlineDateString(String fmt) {
        return DateTimeFormat.forPattern(fmt).print( getNextDeadlineDate() );
    }
    
    private static DateTime nextSameWeekday(DateTime d, int weekday) {
        return d.isBefore(d.dayOfWeek().setCopy(weekday))?d.dayOfWeek().setCopy(weekday):d.plusWeeks(1).dayOfWeek().setCopy(weekday);
    }
    
    public static List<TimeGroup> toTimeGroup(List<PhoenixLectureGroup> groups) {
        ArrayList<TimeGroup> result = new ArrayList<TimeGroup>();
        for(PhoenixLectureGroup group : groups)
            result.add(new TimeGroup(group));
        return result;
    }
    
    public static String now(String fmt) {
        return DateTimeFormat.forPattern(fmt).print( DateTime.now() );
    }
    
    public static String getTurnus(org.joda.time.Period p) {
        if (p.getWeeks() > 0) {
            if (p.getWeeks() == 1)
                return "Wöchentlich";
            return "Jede "+p.getWeeks()+". Woche";
        }
        if (p.getDays() > 0) {
            if (p.getDays() == 1)
                return "Täglich";
            return "Alle "+p.getDays()+" Tage";
        }
        if (p.getMonths() > 0) 
            if (p.getMonths() == 1)
                return "Monatlich";
        return "Alle "+p.getMonths()+" Monate";
    }

}
