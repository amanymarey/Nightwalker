package epeden.nightwalker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NotificationActivity extends AppCompatActivity {


    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

    }

    public void dismissPressed(View v) {
        Intent main_intent = new Intent(this,MainActivity.class);
        startActivity(main_intent);
    }
    public void snoozePressed(View v) {
        Intent intent = new Intent(this, NotificationActivity.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        int time = 60000*9;
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, time, alarmIntent);

        Intent i = new Intent(this, SleepService.class);
        startService(i);
    }

}
