package dk.aau.cs.psylog.accelerometer;

import dk.aau.cs.psylog.module_lib.SuperService;

public class PsyLogService extends SuperService {
    @Override
    public void setSensor() {
        sensor = new AccelerometerListener(this);
    }
}
