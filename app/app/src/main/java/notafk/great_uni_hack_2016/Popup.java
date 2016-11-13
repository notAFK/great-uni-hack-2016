package notafk.great_uni_hack_2016;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

/**
 * Created by Alex on 11/13/2016.
 */

public class Popup extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AlertDialog LDialog = new AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Message")
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if(!Popup.this.isFinishing()){
                            finish();
                        }
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(!Popup.this.isFinishing()){
                            //finish();
                        }
                    }
                })
                .setPositiveButton("ok", null).create();

        Log.e("MUIEEE", "asdasd");

        LDialog.show();
    }
}