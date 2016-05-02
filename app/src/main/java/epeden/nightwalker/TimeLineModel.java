package epeden.nightwalker;

import java.io.Serializable;

/**
 * Created by epeden on 5/2/16.
 */
public class TimeLineModel implements Serializable {
    private String event;

    public String getName() {
        return event;
    }

    public void setName(String name) {
        this.event = name;
    }

}