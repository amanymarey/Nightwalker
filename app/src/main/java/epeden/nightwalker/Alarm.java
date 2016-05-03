package epeden.nightwalker;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Date;
import java.util.List;

public class Alarm extends SugarRecord {
    @Unique
    Date originalStartTime;
    Date alarmTime;
    int snoozeCount;

    public Alarm(){
    }

    public Alarm(Date startTime, int snoozeCount){
        this.originalStartTime = startTime;
        this.alarmTime = startTime;
        this.snoozeCount = snoozeCount;
    }

    // Get all books of this author
    List<WakeEvent> getBooks() {
        String id = getId().toString();
        return WakeEvent.find(WakeEvent.class, "alarm = ?", id);
    }

    public void setAlarmTime(Date newAlarmTime){

        this.alarmTime = newAlarmTime;
    }

    public int getSnoozeCount() {
        return snoozeCount;
    }

    public Date getAlarmTime() {
        return this.alarmTime;
    }


    public void snooze() {
        snoozeCount++;
    }

    public String getID() {
        return "";
    }

}