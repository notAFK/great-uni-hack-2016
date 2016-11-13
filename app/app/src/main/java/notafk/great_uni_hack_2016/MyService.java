package notafk.great_uni_hack_2016;

/**
 * Created by Alex on 11/13/2016.
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service{

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("lol", "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }
}
