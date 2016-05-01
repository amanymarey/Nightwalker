package epeden.nightwalker;

/**
 * Created by epeden on 5/1/16.
 */

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class NightRecord {


    List event_list;
    int pickUpCount;

    public NightRecord() {
        pickUpCount = 0;
        event_list = new ArrayList<SleepEvent>();
    }

    public void addPoint (long time, float value) {
        SleepEvent s = new SleepEvent(time, value);
        event_list.add(s);
    }



    public class SleepEvent  {
        long time;
        float value;
        public SleepEvent(long t, float v) {
            time = t;
            value = v;
        }

    }
}
