package dk.aau.cs.psylog.psylog_accelerometermodule;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import dk.aau.cs.psylog.module_lib.SuperService;

public class AccService extends SuperService {
    @Override
    public void onCreate(){
        super.onCreate();
        sensor = new AccelerometerListener(this);
    }
}
