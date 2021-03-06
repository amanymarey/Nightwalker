package epeden.nightwalker;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.channguyen.rsv.RangeSliderView;

import org.w3c.dom.Text;

import java.sql.SQLOutput;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sm;
    private Sensor acc_sensor;
    Intent dash_intent;
    Intent settings_intent;

    private AlarmManager alarmMgr;
    private TimePicker time_picker;
    private static MainActivity inst;
    private PendingIntent pendingIntent;

    private PendingIntent notificationIntent;
    public Settings settings;
    private TextView settingsTestTextViewLimit;
    private TextView settingsTestTextViewDuration;
    

    public static MainActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = findOrCreateSettings();
        settingsTestTextViewLimit = (TextView) findViewById(R.id.settingsTestTextViewLimit);
        settingsTestTextViewLimit.setText("Snooze Limit: " + settings.getSnoozeLimit());
        settingsTestTextViewDuration = (TextView) findViewById(R.id.settingsTestTextViewDuration);
        settingsTestTextViewDuration.setText("Snooze Duration: " + settings.getSnoozeDuration());

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window (for compatibility with old android)
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.toolbarText);
        getSupportActionBar().setSubtitle(R.string.toolbarSubtitle);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc_sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        if (acc_sensor != null) {
            sm.registerListener(this, acc_sensor, 1000000);
        }

        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        time_picker = (TimePicker) findViewById(R.id.timePicker);


        Button dash_button = (Button) findViewById(R.id.dash_button);
        Button start_button = (Button) findViewById(R.id.start_button);

        dash_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dashButtonPushed(v);
            }
        });
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarm(v);
            }
        });


    }

    public Settings findOrCreateSettings(){
        Settings foundSettings = Settings.findById(Settings.class, 1);
        if (foundSettings != null){
            return foundSettings;
        } else {
            Settings newSettings = new Settings(1, 5);
            newSettings.save();
            return newSettings;
        }
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    public void dashButtonPushed(View v) {
        dash_intent = new Intent (this,DashboardActivity.class);
        startActivity(dash_intent);
    }
    public void onSettingsClicked(MenuItem item) {
        settings_intent = new Intent (this,SettingsActivity.class);
        startActivity(settings_intent);
    }

    public void startAlarm(View view) {
        // Set Calendar time for Alarm Manager and set Date time for current Alarm object
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, time_picker.getHour());
        c.set(Calendar.MINUTE, time_picker.getMinute());
        Date date = new Date();
        date.setTime(c.getTimeInMillis());

        // Intents for alarm
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        notificationIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        // Set alarm
        alarmMgr.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), notificationIntent);

        // Create new Alarm object and save to database
        Alarm a = new Alarm(date, 0);
        a.save();

        // Start Sleep Service and Sleeping Activity
        Intent i = new Intent(this, SleepService.class);
        startService(i);
        Intent i2 = new Intent(this, SleepingActivity.class);
        startActivity(i2);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}

