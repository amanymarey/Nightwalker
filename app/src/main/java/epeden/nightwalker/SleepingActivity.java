package epeden.nightwalker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class SleepingActivity extends AppCompatActivity {

    private TextView snoozeCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeping);
        updatePhoneCount();
    }

    public void backButtonPushed(View v) {
        Intent main_intent = new Intent(this,MainActivity.class);
        startActivity(main_intent);
    }

    public void updatePhoneCount() {
        snoozeCountTextView = (TextView) findViewById(R.id.snoozeLimitTextView);
        Alarm a = Alarm.findWithQuery(Alarm.class, "SELECT * FROM alarm ORDERBY original_start_time DESC LIMIT 1").get(0);
        long alarm_id = a.getId();
        List<WakeEvent> wakeEvents = WakeEvent.find(WakeEvent.class, "alarm_id = ?", Long.toString(alarm_id));
        int count = wakeEvents.size();
        snoozeCountTextView.setText("You picked up your phone "+count+" times.");

    }
}
