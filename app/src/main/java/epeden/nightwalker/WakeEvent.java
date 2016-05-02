package epeden.nightwalker;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Date;

public class WakeEvent extends SugarRecord {
    @Unique
    Date startTime;
    Date endTime;

    Alarm alarm;

    public WakeEvent(){
    }

    public WakeEvent(Date startTime, Date endTime, Alarm alarm){
        this.startTime = startTime;
        this.endTime = startTime;
        this.alarm = alarm;
    }

    public void setAlarm(Alarm alarm){
        this.alarm = alarm;
    }
}