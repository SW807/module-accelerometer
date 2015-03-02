package dk.aau.cs.psylog.psylog_accelerometermodule;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import dk.aau.cs.psylog.module_lib.ISensor;

public class AccelerometerListener implements SensorEventListener, ISensor {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private int sensorDelay;

    public AccelerometerListener(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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

    public void startSensor()
    {
        mSensorManager.unregisterListener(this);
        mSensorManager.registerListener(this,mSensor,sensorDelay);
    }

    public void stopSensor() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void sensorParameters(Intent intent) {
        sensorDelay = intent.getIntExtra("sensorDelay",3); //default set to the slowest update
    }
}
