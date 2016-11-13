package notafk.great_uni_hack_2016;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alex on 11/13/2016.
 */

public class JSONParser extends AsyncTask<String, Void, String> {

    private Context CONTEXT;

    public JSONParser(Context c) {
        this.CONTEXT = c;
    }
    protected String doInBackground(String... urls) {
        HttpURLConnection c = null;
        try {
            Log.e("url: ", urls[0]);
            URL u = new URL(urls[0]);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(10000);
            c.setReadTimeout(10000);
            c.connect();
            int status = c.getResponseCode();
            Log.e("status: ", status + " ");
            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }
        } catch (MalformedURLException ex) {
            Log.e("JSONParser", ex.getMessage());
        } catch (IOException ex) {
            Log.e("JSONParser", ex.getMessage());
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Log.e("JSONParser", ex.getMessage());
                }
            }
        }
        return null;
    }

    protected void onPostExecute(String events) {
        if(events != null) {
            Log.e("Events: ", events.toString());

            String event_id = "0";
            String event_title = "";

            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            long[] vibrate = { 0, 100, 200, 300, 1000, 200, 100, 0 };


            try {
                JSONArray arr = new JSONArray(events);
                if(arr.length() == 0) {
                    SharedPreferences settings =
                            CONTEXT.getSharedPreferences(MainActivity.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("event_id", 0);
                    editor.putBoolean("upload_enabled", false);
                }
                for (int i = 0; i < arr.length(); i++) {
                    event_id = arr.getJSONObject(i).getString("id");
                    event_title = arr.getJSONObject(i).getString("title");

                    Intent yesIntent = new Intent(this.CONTEXT, EventDetectedPasswordActivity.class);
                    yesIntent.putExtra("eventTitle", event_title);
                    yesIntent.putExtra("eventId", Integer.parseInt(event_id));
                    yesIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pIntent = PendingIntent.getActivity(this.CONTEXT, (int) System.currentTimeMillis(),
                            yesIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this.CONTEXT)
                                    .setSmallIcon(R.drawable.ic_staticnotif)
                                    .setContentTitle("Event detected in your location!")
                                    .setContentText("Would you like to join " + event_title + "?")
                                    .setPriority(NotificationCompat.PRIORITY_MAX)
                                    .addAction(R.drawable.ic_justdone,"YES",pIntent)
                                    .setSound(alarmSound)
                                    .setVibrate(vibrate);
                    int mNotificationId = Integer.parseInt(event_id);
                    // Gets an instance of the NotificationManager service
                    NotificationManager mNotifyMgr =
                            (NotificationManager) CONTEXT.getSystemService(Context.NOTIFICATION_SERVICE);
                    // Builds the notification and issues it.
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());

                }
            } catch (Exception ex) {
                ///
            }




        } else {
            Log.e("Events: ", "null");
        }
    }
}