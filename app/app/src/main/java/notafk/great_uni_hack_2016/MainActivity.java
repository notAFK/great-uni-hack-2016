package notafk.great_uni_hack_2016;

import android.app.Activity;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.RemoteInput;
import android.widget.Button;
import android.widget.ToggleButton;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NotificationCompat;
import android.content.Intent;
import android.app.PendingIntent;
import android.support.v4.app.TaskStackBuilder;
import android.app.NotificationManager;
import android.content.Context;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton2);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });

        final NotificationCompat.Builder persNotf =
                new NotificationCompat.Builder(this)
                        .addAction(R.drawable.ic_justdone,"YES",null)
                        .addAction(R.drawable.ic_justdone,"YES",null)
                        .addAction(R.drawable.ic_justdone,"YES",null)
                        .setSmallIcon(R.drawable.ic_eventnotif)
                        .setContentTitle("AUTO UPLOAD IMAGES")
                        .setContentText("Toggle?")
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setOngoing(true);


        final NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        final NotificationManager persNotfMan =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        persNotfMan.notify(0, persNotf.build());


        final int notfCount = 1;

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] vibrate = { 0, 100, 200, 300, 1000, 200, 100, 0 };

        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .addAction(R.drawable.ic_justdone,"YES",null)
                        .addAction(R.drawable.ic_justdone,"YES",null)
                        .addAction(R.drawable.ic_justdone,"YES",null)
                        .setSmallIcon(R.drawable.ic_staticnotif)
                        .setContentTitle("Event detected in your location!")
                        .setContentText("Would you like to join?")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setSound(alarmSound)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setVibrate(vibrate);
//                        .setOngoing(true);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mNotificationManager.notify(notfCount, mBuilder.build());

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}

