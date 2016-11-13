package notafk.great_uni_hack_2016;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    public String givenPassword = "";
    public int notifCounter = 5;
    public static final String PREFS_NAME = "MyPrefsFile";

    protected void showSimplePopUp() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("PASSWORD PLEASE:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                givenPassword = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
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
                    // The tooooogle.
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



        final Button secondbutton = (Button) findViewById(R.id.button2);
        secondbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showSimplePopUp();
            }
        });

        // Start location service
        startService(new Intent(this, LocationService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}

