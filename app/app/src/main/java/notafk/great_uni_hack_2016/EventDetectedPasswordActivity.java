package notafk.great_uni_hack_2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetectedPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detected_password2);

        onNewIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent){
        Bundle extras = intent.getExtras();
        if(extras != null){
            if(extras.containsKey("eventTitle"))
            {
                setContentView(R.layout.activity_event_detected_password2);
                // extract the extra-data in the Notification
                String msg = extras.getString("eventTitle");
                TextView txtView = (TextView) findViewById(R.id.eventPasswordTV);
                txtView.setText(msg);
            }
        }


    }
}
