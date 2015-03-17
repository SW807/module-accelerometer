package dk.aau.cs.psylog.accelerometer;

import dk.aau.cs.psylog.module_lib.SensorService;

public class PsyLogService extends SensorService {
    @Override
    public void setSensor() {
        sensor = new AccelerometerListener(this);
    }
}
