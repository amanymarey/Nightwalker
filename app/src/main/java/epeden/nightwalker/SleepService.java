package epeden.nightwalker;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.SystemClock;

import java.util.Calendar;

public class SleepService extends Service implements SensorEventListener {

    Intent local;
    private SensorManager sm;
    private Sensor acc_sensor;
    BroadcastReceiver br;


    public void onCreate() {

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc_sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (acc_sensor != null) {
            sm.registerListener(this, acc_sensor, 1000000);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float f = (x*x) + (y*y) + (z*z);
        f = (float) Math.sqrt(f);


        Alarm a = Alarm.findById(Alarm.class, 1);
        Calendar c = Calendar.getInstance();
        WakeEvent wakeEvent = new WakeEvent(c.getTime(), c.getTime(), a);
        wakeEvent.save();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
