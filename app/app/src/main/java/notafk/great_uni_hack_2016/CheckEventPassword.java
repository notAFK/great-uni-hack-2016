package notafk.great_uni_hack_2016;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alex on 11/13/2016.
 */

public class CheckEventPassword extends AsyncTask<String, Void, String> {

    private Context CONTEXT;

    public CheckEventPassword(Context c) {
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

    protected void onPostExecute(String response) {
        if(response != null) {
            Log.e("Status: ", response.toString());

            try {
                JSONObject obj = new JSONObject(response);
                String success = obj.getString("status");

                if(success.equals("success")) {
                    int event_id = obj.getInt("id");
                    SharedPreferences settings =
                            CONTEXT.getSharedPreferences(MainActivity.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("event_id", event_id);
                    editor.putBoolean("upload_enabled", true);

                    // Commit the edits!
                    editor.apply();

                    Intent intent = new Intent(CONTEXT, MainActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("id", event_id); //Your id
                    b.putBoolean("enabled", true);
                    intent.putExtras(b);
                    CONTEXT.startActivity(intent);
                }
                else {

                }
            } catch (Exception ex) {
                ///
            }
        } else {
            Log.e("Events: ", "null");
        }
    }
}