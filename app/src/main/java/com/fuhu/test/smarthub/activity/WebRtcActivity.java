package com.fuhu.test.smarthub.activity;

import android.app.Activity;

public class WebRtcActivity extends Activity {
//    private static final String TAG = WebRtcActivity.class.getSimpleName();
//    private static final int PERMISSIONS_REQUEST_CAMERA = 1;
//
//    private SharedPreferences mSharedPreferences;
//    private TextView mUsernameTV;
//    private EditText mCallNumET;
//    private ListView mPeerList;
//    private Pubnub mPubNub;
//    private String username;
//
//    private ChannelItem mChannelItem;
//    private RTPPeerAdapter mPeerAdapter;
//    private BroadcastReceiver mWebRtcReceiver;
//    private Gson mGson = new Gson();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_webrtc_main);
//
//        this.mSharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
//        // Return to Log In screen if no user is logged in.
//        if (!this.mSharedPreferences.contains(Constants.USER_NAME)){
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//            return;
//        }
//
//        this.mWebRtcReceiver = new WebRtcReceiver(this);
//        this.username = this.mSharedPreferences.getString(Constants.USER_NAME, "");
//
//        this.mCallNumET  = (EditText) findViewById(R.id.call_num);
//        this.mUsernameTV = (TextView) findViewById(R.id.main_username);
//        // Set the username to the username text view
//        this.mUsernameTV.setText(this.username);
//
//        this.mPeerList = (ListView) findViewById(R.id.peerlist);
//        // Set up the List View for chatting
//        mPeerAdapter = new RTPPeerAdapter(this, new ArrayList<ChannelItem.RtpChannel>());
//        this.mPeerList.setAdapter(mPeerAdapter);
//        this.mPeerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, " click: " + position);
//
//                // dispatch call
//                if (mChannelItem != null && mChannelItem.getChannels() != null
//                        && mChannelItem.getChannels().size() > position
//                        && mChannelItem.getChannels().get(position) != null) {
//                    String callNum = mChannelItem.getChannels().get(position).getUuidsString();
//                    Log.d(TAG, "call: " + callNum);
//                    dispatchCall(callNum);
//                }
//            }
//        });
//
//        // Initializes WebRTC module
//        initWebRtc();
//
//        // Requests permissions to be granted to this application.
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.CAMERA},
//                    PERMISSIONS_REQUEST_CAMERA);
//        }
//
//        if (mPubNub != null) {
//            mPubNub.hereNow(true, true, new Callback() {
//                @Override
//                public void successCallback(String channel, Object message) {
//                    Log.d(TAG, "HERE NOW : " + message);
//
//                    mChannelItem = mGson.fromJson(message.toString(), ChannelItem.class);
//                    Log.d(TAG, "total: " + mChannelItem.getTotal_channels());
//
//                    if (mChannelItem.getTotal_channels() > 0) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(message.toString());
//
//                            if (jsonObject.has("channels")) {
//                                mChannelItem.setChannels(username, jsonObject.getJSONObject("channels"));
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        mPeerAdapter.setChannels(mChannelItem.getChannels());
//                                    }
//                                });
//                            }
//                        } catch (JSONException je) {
//                            je.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void errorCallback(String channel, PubnubError error) {
//                    Log.d(TAG, "HERE NOW : " + error);
//                }
//            });
//        }
//    }
//
//    /**
//     * This function Initializes WebRTC module and subscribes you to the username's standby channel.
//     */
//    public void initWebRtc() {
//        AppStatus appStatus = new AppStatus();
//        appStatus.setSilkMessageType(SilkMessageType.INITIALIZED);
//
//        WebRtcInitCallback.reqInitialize(this, new WebRtcInitCallback() {
//            @Override
//            public void onResultReceived(AMailItem mailItem) {
//                Log.d(TAG, "Silk SDK initialized");
//            }
//
//            @Override
//            public void onFailed(String status, String message) {
//                Log.d(TAG, "init fail");
//            }
//        }, appStatus);
//    }
//
//    public void makeCall(View view){
//        if (NabiHttpRequest.haveNetworkConnection(this)) {
//            String callNum = mCallNumET.getText().toString();
//            Log.d(TAG, "make call: " + callNum);
//            if (callNum.isEmpty() || callNum.equals(this.username)) {
//                Toast.makeText(this, "Enter a valid number.", Toast.LENGTH_SHORT).show();
//            }
//            dispatchCall(callNum);
//        } else {
//            Toast.makeText(this, getString(R.string.no_wifi), Toast.LENGTH_LONG).show();
//        }
//    }
//
//    public void dispatchCall(final String callNum) {
//        final String callNumStdBy = callNum + Constants.STDBY_SUFFIX;
//        JSONObject jsonCall = new JSONObject();
//        try {
//            jsonCall.put(Constants.JSON_CALL_USER, this.username);
//            mPubNub.publish(callNumStdBy, jsonCall, new Callback() {
//                @Override
//                public void successCallback(String channel, Object message) {
//                    Log.d("MA-dCall", "SUCCESS: " + message.toString());
//                    Intent intent = new Intent(WebRtcActivity.this, VideoChatActivity.class);
//                    intent.putExtra(Constants.USER_NAME, username);
//                    intent.putExtra(Constants.CALL_USER, callNum);
//                    startActivity(intent);
//                }
//            });
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.action_settings:
//                return true;
//            case R.id.action_sign_out:
//                signOut();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSIONS_REQUEST_CAMERA: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted.
//
//                } else {
//                    // permission denied, disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        registerReceiver(mWebRtcReceiver, WebRtcReceiver.getFilter());
//    }
//
//    @Override
//    protected void onStop() {
//        unregisterReceiver(mWebRtcReceiver);
//        super.onStop();
//    }
//
//    /**
//     * Log out, remove username from SharedPreferences, unsubscribe from PubNub, and send user back
//     *   to the LoginActivity
//     */
//    public void signOut(){
//        // TODO: Unsubscribe from all channels with PubNub object ( pn.unsubscribeAll() )
//        SharedPreferences.Editor edit = this.mSharedPreferences.edit();
//        edit.remove(Constants.USER_NAME);
//        edit.apply();
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.putExtra("oldUsername", this.username);
//        startActivity(intent);
//    }
}