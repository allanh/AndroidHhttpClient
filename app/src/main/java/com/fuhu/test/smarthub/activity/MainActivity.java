package com.fuhu.test.smarthub.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fuhu.middleware.MiddlewareConfig;
import com.fuhu.middleware.componet.DataPart;
import com.fuhu.middleware.componet.HTTPHeader;
import com.fuhu.middleware.componet.HttpCommand;
import com.fuhu.middleware.componet.HttpCommandBuilder;
import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.contract.MailBox;
import com.fuhu.middleware.contract.NabiHttpRequest;
import com.fuhu.test.smarthub.R;
import com.fuhu.test.smarthub.callback.TrackCallback;
import com.fuhu.test.smarthub.componet.TrackItem;
import com.fuhu.test.smarthub.manager.SpeechRecognizeManager;
import com.fuhu.test.smarthub.manager.TextToSpeechManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    private TextView tv_response;
    private Button bt_webrtc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_response = (TextView) findViewById(R.id.result);
        bt_webrtc= (Button) findViewById(R.id.bt_webrtc);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.RECORD_AUDIO)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

//            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        PERMISSIONS_REQUEST_RECORD_AUDIO);

                // PERMISSIONS_REQUEST_RECORD_AUDIO is an
                // app-defined int constant. The callback method gets the
                // result of the request.
//            }
        }

        bt_webrtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getParent(), WebRtcActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_RECORD_AUDIO: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (MiddlewareConfig.isRecognizingSpeech) {
            SpeechRecognizeManager.getInstance(this).startService();
            SpeechRecognizeManager.getInstance(this).registerReceiver(
                    TextToSpeechManager.getInstance(this), tv_response);
        }

//        if (MiddlewareConfig.enableGCM) {
//            GCMManager.getInstance(this).startService();
//            GCMManager.getInstance(this).registerReceiver();
//        }

//        if (MiddlewareConfig.enableWifiP2P) {
//            WifiP2PHandler.getInstance(this).registerReceiver();
//        }


        File file =new File(getExternalCacheDir(), "test.jpg");
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.pet802);
            OutputStream out=new FileOutputStream(file);
            byte buf[]=new byte[1024];
            int len;
            while((len=inputStream.read(buf))>0)
                out.write(buf,0,len);
            out.close();
            inputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        DataPart dataPart = null;
        if (file != null) {
            dataPart = new DataPart(file, "image/jpeg");
        }

        if (dataPart != null) {
            Log.d(TAG, "file: " + dataPart.getFileName());

            HttpCommand trackCommand = new HttpCommandBuilder()
                .setID("4")
//                .setURL("http://192.168.30.34:8080/IITService/tracking/upload/photo")
                .setURL(NabiHttpRequest.getAPI_UploadPhoto())
                .setMethod(HttpCommand.Method.POST)
                .setHeaders(HTTPHeader.getTrackingHeader(this))
                .addHeader("Content-Length", String.valueOf(dataPart.getFile().length()))
                .addHeader("Content-Type", "multipart/form-data; boundary=" + NabiHttpRequest.BOUNDARY)
                .setDataModel(TrackItem.class)
                .addDataPart("file", dataPart)
                .build();

            MailBox.getInstance().deliverMail(this, trackCommand, new TrackCallback() {
                @Override
                public void onResultReceived(TrackItem trackItem) {
                    Log.d(TAG, "trackItem");
                }

                @Override
                public void onFailed(String status, String message) {
                    Log.d(TAG, "Status: " + status);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (MiddlewareConfig.isRecognizingSpeech) {
            SpeechRecognizeManager.getInstance(this).stop();
        }

//        if (MiddlewareConfig.enableGCM) {
//            GCMManager.getInstance(this).stop();
//        }

//        if (MiddlewareConfig.enableWifiP2P) {
//            WifiP2PHandler.getInstance(this).stop();
//        }

    }

    @Override
    protected void onDestroy() {
        if (MiddlewareConfig.isRecognizingSpeech) {
            TextToSpeechManager.getInstance(this).shutdown();
        }

        super.onDestroy();
    }
}
