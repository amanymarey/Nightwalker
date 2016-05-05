package epeden.nightwalker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity {

    private Ringtone ringtone;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        System.out.println("just created the notification activity, should start playing music...");;
        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(this, alarmUri);
        ringtone.play();


    }

    public void dismissPressed(View v) {
        AlarmManager.AlarmClockInfo alarmClockInfo = alarmMgr.getNextAlarmClock();
        PendingIntent i =  alarmClockInfo.getShowIntent();
        alarmMgr.cancel(i);

        Intent main_intent = new Intent(this,MainActivity.class);
        ringtone.stop();
        startActivity(main_intent);
    }
    public void snoozePressed(View v) {

        // Compare current snooze count with the snooze limit from settings
        Alarm a = Alarm.findById(Alarm.class, 1);
        int snoozeCount = a.getSnoozeCount();
        Settings foundSettings = Settings.findById(Settings.class, 1);
        int snoozeLimit = foundSettings.getSnoozeLimit();

        // Snooze if snooze-limit not reached
        if (snoozeCount >= snoozeLimit) {
            Toast.makeText(this, "Snooze Limit Reached", Toast.LENGTH_LONG);
        } else {
            ringtone.stop();

            // Get current snooze duration from settings
            int snoozeMinutes = foundSettings.getSnoozeDuration();

            // Set Calendar time and Date time
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, snoozeMinutes);
            Date date = new Date();
            date.setTime(c.getTimeInMillis());

            // Intents for alarm
            Intent intent = new Intent(this, NotificationActivity.class);
            alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            // Set new alarm and snooze
            alarmMgr.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), alarmIntent);
            a.snooze();
            a.setAlarmTime(date);

            // Start Sleep Service and Sleeping Activity
            Intent i = new Intent(this, SleepService.class);
            startService(i);
            Intent i2 = new Intent(this, SleepingActivity.class);
            startActivity(i2);
        }




    }

}
