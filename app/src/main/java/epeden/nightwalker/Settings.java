package epeden.nightwalker;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Date;

public class Settings extends SugarRecord {
    @Unique
    int snoozeLimit;
    int snoozeDuration;

    public Settings(){
    }

    public Settings(int snoozeLimit, int snoozeDuration){
        this.snoozeLimit = snoozeLimit;
        this.snoozeDuration = snoozeDuration;
    }

    public void setSnoozeLimit(int snoozeLimit){
        this.snoozeLimit = snoozeLimit;
    }
    public void setSnoozeDuration(int snoozeDuration){
        this.snoozeDuration = snoozeDuration;
    }
}