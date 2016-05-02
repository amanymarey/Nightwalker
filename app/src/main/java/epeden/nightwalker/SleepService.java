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
import android.util.FloatMath;

import java.util.Calendar;

public class SleepService extends Service implements SensorEventListener {

    Intent local;
    private SensorManager sm;
    private Sensor acc_sensor;
    BroadcastReceiver br;
    double mAccel;
    double mAccelLast;
    double mAccelCurrent;
    Calendar c;
    Alarm a;


    public void onCreate() {
        c = Calendar.getInstance();
        a = Alarm.findById(Alarm.class,1);
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
        try {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float[] mGravity = event.values.clone();
                // Shake detection
                float x = mGravity[0];
                float y = mGravity[1];
                float z = mGravity[2];

                float yAbs = Math.abs(mGravity[1]);

                mAccelLast = mAccelCurrent;
                mAccelCurrent = Math.sqrt(x * x + y * y + z * z);
                double delta = mAccelCurrent - mAccelLast;
                mAccel = mAccel * 0.9f + delta;

                if (yAbs > 2.0 && yAbs < 4.0) {
                    registerWakeEvent();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void registerWakeEvent(){
        Calendar c = Calendar.getInstance();
        WakeEvent wakeEvent = new WakeEvent(c.getTime(), c.getTime(), a);
        wakeEvent.save();
    }
    
}
