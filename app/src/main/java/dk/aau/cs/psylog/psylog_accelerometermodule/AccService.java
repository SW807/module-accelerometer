package dk.aau.cs.psylog.psylog_accelerometermodule;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccService extends Service {

    private AccelerometerListener accelerometerListener;

    @Override
    public int onStartCommand(Intent intent, int flag, int startid)
    {
        accelerometerListener.startSensor();

        //Skal være START_STICKY hvis servicen skal køre hele tiden, selv hvis den bliver dræbt. START_NOT_STICKY hjælper når man programmere.
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        accelerometerListener = new AccelerometerListener(this);
    }

    @Override
    public void onDestroy(){
        //Make sure to stop the sensors that have started
        accelerometerListener.stopSensor();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
