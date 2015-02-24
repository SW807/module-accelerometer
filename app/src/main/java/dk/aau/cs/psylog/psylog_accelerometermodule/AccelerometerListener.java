package dk.aau.cs.psylog.psylog_accelerometermodule;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class AccelerometerListener implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    public AccelerometerListener(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0],
                    y = sensorEvent.values[1],
                    z = sensorEvent.values[2];

            Log.i("AccReadings", "x:" + x + " y:" + y + " z:" + z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void stopSensor() {
        mSensorManager.unregisterListener(this);
    }
}
