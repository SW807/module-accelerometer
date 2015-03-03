package dk.aau.cs.psylog.psylog_accelerometermodule;

import dk.aau.cs.psylog.module_lib.SuperService;

public class PsyLogService extends SuperService {
    @Override
    public void setSensor() {
        sensor = new AccelerometerListener(this);
    }
}
