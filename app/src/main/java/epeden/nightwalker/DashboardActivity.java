package epeden.nightwalker;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity implements SensorEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }


    public void onSensorChanged(SensorEvent event) {
        float f = event.values[0];

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void backButtonPushed(View v) {
        Intent main_intent = new Intent(this,MainActivity.class);
        startActivity(main_intent);
    }

}
