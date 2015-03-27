package dk.aau.cs.psylog.sensor.accelerometer;

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
import android.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dk.aau.cs.psylog.module_lib.DBAccessContract;
import dk.aau.cs.psylog.module_lib.ISensor;
import dk.aau.cs.psylog.module_lib.SlidingWindow;

public class AccelerometerListener implements SensorEventListener, ISensor {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private int sensorDelay;
    ContentResolver cr;

    private final int MAXSIZE = 1000;
    private final float MAXDISTANCE = 0.5f;

    public AccelerometerListener(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        cr = context.getContentResolver();
    }

    SlidingWindow<Float> slidingWindowX = new SlidingWindow<>(MAXDISTANCE),
                         slidingWindowY = new SlidingWindow<>(MAXDISTANCE),
                         slidingWindowZ = new SlidingWindow<>(MAXDISTANCE);

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            boolean boolX = slidingWindowX.addPointAndCheck(new Pair<>(sensorEvent.values[0], new Date().getTime()));
            boolean boolY = slidingWindowY.addPointAndCheck(new Pair<>(sensorEvent.values[1], new Date().getTime()));
            boolean boolZ = slidingWindowZ.addPointAndCheck(new Pair<>(sensorEvent.values[2], new Date().getTime()));

            if(boolX || boolY || boolZ)
                addToDatabase(slidingWindowX.resetSlidingWindow().first,
                              slidingWindowY.resetSlidingWindow().first,
                              slidingWindowZ.resetSlidingWindow().first);
        }
    }

    private void addToDatabase(float x, float y, float z){
        Uri uri = Uri.parse(DBAccessContract.DBACCESS_CONTENTPROVIDER + "accelerometer_accelerations");
        ContentValues values = new ContentValues();
        values.put("accX", x);
        values.put("accY", y);
        values.put("accZ", z);
        cr.insert(uri, values);
        Log.d("ACC", "Der blev uploadet til DB");
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
