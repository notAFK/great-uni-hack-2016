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
import android.support.v7.widget.ForwardingListener;
import android.text.InputType;
import android.util.Log;
import android.widget.*;
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

import static android.app.PendingIntent.getActivity;








//-------------------------------------------
//Imports for newly created pictures
import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
//-------------------------------------------




//--------------------------------------
//Andrei's imports

import java.io.ByteArrayOutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.*;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

//import cz.msebera.android.httpclient.Header;


//---------------------------------

public class MainActivity extends AppCompatActivity {

    public int notifCounter = 5;


    private PhotosObserver instUploadObserver = new PhotosObserver();
    public String absolutePathOfNewPhoto;

    public static boolean isSendingEverythingOn = true;
//
//    protected void showSimplePopUp() {
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("PASSWORD PLEASE:");
//
//        final EditText input = new EditText(this);
//        input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        builder.setView(input);
//
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                givenPassword = input.getText().toString();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        builder.show();
//    }

//    public Context toastContext = this.getApplicationContext();

//    ProgressDialog prgDialog;
    String encodedString;
    RequestParams params = new RequestParams();
//    String imgPath, fileName;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //---------------------------------------
        //The code which takes care of newly made pictures
        System.out.println("Create");
        this.getApplicationContext()
          .getContentResolver()
          .registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false,
            instUploadObserver);


        //---------------------------------------

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton2);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    System.out.println("ischecked");
                    isSendingEverythingOn = true;

                } else {
                    System.out.println("NONchecked");
                    isSendingEverythingOn = false;
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
                mNotificationManager.notify(notifCounter, mBuilder.build());
                notifCounter++;
            }
        });

        final Button secondbutton = (Button) findViewById(R.id.button2);
        secondbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                showSimplePopUp();
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

    @Override
    public void onResume() {
        System.out.println("Resume");
        super.onResume();
    }

    @Override
    public void onDestroy() {
        System.out.println("Destroy");
        super.onDestroy();
        this.getApplicationContext().getContentResolver()
          .unregisterContentObserver(instUploadObserver);
        Log.d("INSTANT", "unregistered content observer");
    }

    private class PhotosObserver extends ContentObserver {

        public PhotosObserver() {
            super(null);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            System.out.println("onChange, now reading from media");

            Media media = readFromMediaStore(getApplicationContext(),
              MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//			saved = "I detected " + media.file.getName();

            absolutePathOfNewPhoto = media.file.getAbsolutePath();


            if(isSendingEverythingOn) {
                uploadImage(absolutePathOfNewPhoto);
            }
            System.out.println("MERGEEEEEEEEE");


//            HttpClient httpclient = new DefaultHttpClient();
//HttpPost httppost = new HttpPost("LINK TO SERVER");



//            System.out.println("I detected picture "
////				+ MediaStore.Images.Media.EXTERNAL_CONTENT_URI
////				+ "$"
//              + media.file.getAbsolutePath());
////            Log.d("INSTANT", "detected picture");
        }
    }

    private Media readFromMediaStore(Context context, Uri uri) {
		System.out.println("read From Media Store");
        Cursor cursor = context.getContentResolver().query(uri, null, null,
          null, "date_added DESC");
        System.out.println("Now constructing the media");
        Media media = null;
        if (cursor.moveToNext()) {
            int dataColumn = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
            String filePath = cursor.getString(dataColumn);
            int mimeTypeColumn = cursor
              .getColumnIndexOrThrow(MediaColumns.MIME_TYPE);
            String mimeType = cursor.getString(mimeTypeColumn);
            System.out.println("Now filling the media");
            media = new Media(new File(filePath), mimeType);
        }
        cursor.close();
        return media;
    }

    private class Media {
        private File file;
        @SuppressWarnings("unused")
        private String type;

        public Media(File file, String type)
        {
            this.file = file;
            this.type = type;
        }
    }

//--------------Andrei's stuff

    // When Upload button is clicked
    public void uploadImage(String absolutePath) {
        System.out.println("UPLOADING IMAGE");
        // When Image is selected from Gallery
        if (absolutePath != null && !absolutePath.isEmpty()) {
//            prgDialog.setMessage("Converting Image to Binary Data");
//            prgDialog.show();
            // Convert image to String using Base64
            encodeImagetoString();

            // When Image is not selected from Gallery
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }
    }

    // AsyncTask - To convert Image to String
    public void encodeImagetoString() {
        System.out.println("Image encoding");
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            };

            @Override
            protected String doInBackground(Void... params) {
                System.out.println("Started doInBackground");
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(absolutePathOfNewPhoto,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
//                prgDialog.setMessage("Calling Upload");
//                // Put converted Image string into Async Http Post param
//                params.put("image", encodedString);
//                // Trigger Image upload
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }

    public void triggerImageUpload() {
        makeHTTPCall();
    }








    // Make Http call to upload Image to Php server
    public void makeHTTPCall() {
//        prgDialog.setMessage("Invoking Php");
        AsyncHttpClient client = new AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        System.out.println("A ajungs la pasul cel mare");
        client.post("http://test.lanfin.com/api/upload" ,
                params, new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        System.out.println("A trimis cu Success");
                        // Hide Progress Dialog
//                        prgDialog.hide();
                        Toast.makeText(getApplicationContext(), response,
                                Toast.LENGTH_LONG).show();
                    }

                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        System.out.println("A dat FAIL");
                        // Hide Progress Dialog
//                        prgDialog.hide();
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            System.out.println("------------404-----------");
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            System.out.println("------------500-----------");
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            System.out.println
                              ("------------ELSE!!!!-----------");
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured n Most Common Error: n1. Device not connected to Internetn2. Web App is not deployed in App servern3. App server is not runningn HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
        System.out.println("VICTORIE");
    }





//---------------Andrei - end




}

