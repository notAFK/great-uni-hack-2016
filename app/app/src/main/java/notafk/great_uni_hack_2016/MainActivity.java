package notafk.great_uni_hack_2016;

import android.app.Activity;
import android.content.DialogInterface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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


    private void showSimplePopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Pop Up");
        helpBuilder.setMessage("This is a Simple Pop Up");
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }


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
                    showSimplePopUp();
                } else {
                    // The toggle is disabled
                }
            }
        });

        final NotificationCompat.Builder persNotf =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_eventnotif)
                        .setContentTitle("AUTO UPLOAD IMAGES")
                        .setContentText("Toggle?")
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .addAction(R.drawable.ic_justdone,"TOGGLE",null)
                        .setOngoing(true);


        final NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        final NotificationManager persNotfMan =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        persNotfMan.notify(0, persNotf.build());


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] vibrate = { 0, 100, 200, 300, 1000, 200, 100, 0 };

        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_staticnotif)
                        .setContentTitle("Event detected in your location!")
                        .setContentText("Would you like to join?")
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .addAction(R.drawable.ic_justdone,"YES",null)
                        .addAction(R.drawable.ic_justdone,"NO",null)
                        .setSound(alarmSound)
                        .setVibrate(vibrate);
//                        .setOngoing(true);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mNotificationManager.notify(1, mBuilder.build());
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

