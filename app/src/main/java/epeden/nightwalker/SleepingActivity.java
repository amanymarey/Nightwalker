package epeden.nightwalker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class SleepingActivity extends AppCompatActivity {

    private TextView phonePickupCountTextView;
    private TextView alarmTimeTextView;

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
        phonePickupCountTextView = (TextView) findViewById(R.id.phonePickupCountTextView);
        alarmTimeTextView = (TextView) findViewById(R.id.alarmTimeTextView);

        Alarm a = Alarm.findWithQuery(Alarm.class, "SELECT * FROM alarm ORDER BY original_start_time DESC LIMIT 1").get(0);
        SimpleDateFormat formattedDate = new SimpleDateFormat("EEE, dd MMM hh:mm:ss a");
        String alarmTimeText = "Alarm set for: " + formattedDate;
        alarmTimeTextView.setText(alarmTimeText);
        long alarm_id = a.getId();
        List<WakeEvent> wakeEvents = WakeEvent.find(WakeEvent.class, "alarm = ?", Long.toString(alarm_id));
        int count;
        if(wakeEvents.size() > 0){
            count = wakeEvents.size();
        }
        else {
            count = 0;
        }
        String pickupCountText = "You picked up your phone "+count+" times.";
        phonePickupCountTextView.setText(pickupCountText);
    }
}
