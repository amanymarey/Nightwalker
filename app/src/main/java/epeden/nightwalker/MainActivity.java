package epeden.nightwalker;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.sql.Time;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Time alarm_time;
    private SensorManager sm;
    private Sensor acc_sensor;
    Intent dash_intent;
    Intent settings_intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window (for compatibilty with old android)
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.toolbarText);
        getSupportActionBar().setSubtitle(R.string.toolbarSubtitle);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc_sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (acc_sensor != null) {
            sm.registerListener(this, acc_sensor, 1000000);
        }

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
                startButtonPushed(v);
            }
        });

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void setAlarmTime(Time t) {
        alarm_time = t;
    }

    public void dashButtonPushed(View v) {
        dash_intent = new Intent (this,DashboardActivity.class);
        startActivity(dash_intent);
    }

    public void startButtonPushed(View v) {
//        start_intent = new Intent (this, StartActivity.class);
//        startActivity(start_intent);


    }



    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSettingsClicked(MenuItem item) {
        System.out.println("YEAH YOU GONNA LAUNCH SETTINGS!");
        settings_intent = new Intent (this,DashboardActivity.class);
        startActivity(settings_intent);
    }
}
