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
        Intent main_intent = new Intent(this,MainActivity.class);
        ringtone.stop();
        startActivity(main_intent);
    }
    public void snoozePressed(View v) {
        ringtone.stop();
        Intent intent = new Intent(this, NotificationActivity.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // set new alarm (9 minutes later) with alarmManager
//        int time = 60000*9;
//        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, time, alarmIntent);


        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 9);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), alarmIntent);
        // set time to current time of day + snooze time

        Date date = new Date();
        date.setTime(c.getTimeInMillis());

        // find current alarm, snooze, and update time
        Alarm a = Alarm.findById(Alarm.class, 1);
        a.snooze();
        a.setAlarmTime(date);

        // start sleepactivity service and sleeping activity
        Intent i = new Intent(this, SleepService.class);
        startService(i);
        Intent i2 = new Intent(this, SleepingActivity.class);
        startActivity(i2);
    }

}
