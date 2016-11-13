package notafk.great_uni_hack_2016;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EventDetectedPasswordActivity extends AppCompatActivity {
    private String API_URL = "http://10.0.2.2/projects/guh2016/api";
    private int event_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detected_password2);

        onNewIntent(getIntent());

    }

    @Override
    public void onNewIntent(Intent intent){
        String event_title = "";

        Bundle extras = intent.getExtras();
        if(extras != null){
            if(extras.containsKey("eventTitle"))
            {
                setContentView(R.layout.activity_event_detected_password2);
                // extract the extra-data in the Notification
                event_title = extras.getString("eventTitle");
                event_id = extras.getInt("eventId");
                TextView txtView = (TextView) findViewById(R.id.eventPasswordTV);
                txtView.setText(event_title);
            }
        }
        final Context c = this;
        final int evt_id = event_id;
        final Button secondbutton = (Button) findViewById(R.id.button3);
        secondbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView passTV = (TextView) findViewById(R.id.EditTextName);
                String pass = API_URL + "/pass/" + evt_id +
                        '/' + passTV.getText().toString();
                Log.e("pass: ", pass);
                new CheckEventPassword(c).execute(pass);
            }
        });



    }
}
