package dk.aau.cs.psylog.accelerometer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.util.Log;
import dk.aau.cs.psylog.module_lib.ISensor;

public class AccelerometerListener implements SensorEventListener, ISensor {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private int sensorDelay;
    ContentResolver cr;

    public AccelerometerListener(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        cr = context.getContentResolver();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Uri uri = Uri.parse("content://dk.aau.cs.psylog.psylog" + "/accelerations");
            ContentValues values = new ContentValues();
            values.put("accX", sensorEvent.values[0]);
            values.put("accY", sensorEvent.values[1]);
            values.put("accZ", sensorEvent.values[2]);
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
        sensorDelay = intent.getIntExtra("sensorDelay", SensorManager.SENSOR_DELAY_NORMAL); //default set to the slowest update
    }
}
