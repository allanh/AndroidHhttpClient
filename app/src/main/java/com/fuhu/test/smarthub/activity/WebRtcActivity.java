package com.fuhu.test.smarthub.activity;

import android.app.Activity;

/**
 * TODO: Uncomment mPubNub instance variable
 */
public class WebRtcActivity extends Activity {
//    private static final String TAG = WebRtcActivity.class.getSimpleName();
//    private static final int PERMISSIONS_REQUEST_CAMERA = 1;
//    private Gson mGson = new Gson();
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
//
//    /**
//     * TODO: "Login" by subscribing to PubNub channel + Constants.SUFFIX
//     * @param savedInstanceState
//     */
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
//        this.username = this.mSharedPreferences.getString(Constants.USER_NAME, "");
//
//        this.mCallNumET  = (EditText) findViewById(R.id.call_num);
//        this.mUsernameTV = (TextView) findViewById(R.id.main_username);
//        this.mUsernameTV.setText(this.username);  // Set the username to the username text view
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
//        //TODO: Create and instance of Pubnub and subscribe to standby channel
//        // In pubnub subscribe callback, send user to your VideoActivity
//        initPubNub();
//
//        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
////            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
////                    Manifest.permission.RECORD_AUDIO)) {
//
//            // Show an expanation to the user *asynchronously* -- don't block
//            // this thread waiting for the user's response! After the user
//            // sees the explanation, try again to request the permission.
//
////            } else {
//
//            // No explanation needed, we can request the permission.
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.CAMERA},
//                    PERMISSIONS_REQUEST_CAMERA);
//
//            // PERMISSIONS_REQUEST_RECORD_AUDIO is an
//            // app-defined int constant. The callback method gets the
//            // result of the request.
////            }
//        }
//
//        mPubNub.hereNow(true, true, new Callback() {
//            @Override
//            public void successCallback(String channel, Object message) {
//                Log.d(TAG, "HERE NOW : " + message);
//
//                mChannelItem = mGson.fromJson(message.toString(), ChannelItem.class);
//                Log.d(TAG, "total: " + mChannelItem.getTotal_channels());
//
//                if (mChannelItem.getTotal_channels() > 0) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(message.toString());
//
//                        if (jsonObject.has("channels")) {
//                            mChannelItem.setChannels(username, jsonObject.getJSONObject("channels"));
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mPeerAdapter.setChannels(mChannelItem.getChannels());
//                                }
//                            });
//                        }
//                    } catch (JSONException je) {
//                        je.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void errorCallback(String channel, PubnubError error) {
//                Log.d(TAG, "HERE NOW : " + error);
//            }
//        });
//
//    }
//
//    public void initPubNub() {
//        String stdbyChannel = this.username + Constants.STDBY_SUFFIX;
//        this.mPubNub = new Pubnub(Constants.PUB_KEY, Constants.SUB_KEY);
//        this.mPubNub.setUUID(this.username);
//
//        try {
//            this.mPubNub.subscribe(stdbyChannel, new Callback() {
//                @Override
//                public void successCallback(String channel, Object message) {
//                    Log.d("MA-success", "MESSAGE: " + message.toString());
//                    if (!(message instanceof JSONObject)) return; // Ignore if not JSONObject
//                    JSONObject jsonMsg = (JSONObject) message;
//                    try {
//                        if (!jsonMsg.has(Constants.JSON_CALL_USER)) return;
//                        String user = jsonMsg.getString(Constants.JSON_CALL_USER);
//                        // Consider Accept/Reject call here
//                        Intent intent = new Intent(WebRtcActivity.this, VideoChatActivity.class);
//                        intent.putExtra(Constants.USER_NAME, username);
//                        intent.putExtra(Constants.JSON_CALL_USER, user);
//                        startActivity(intent);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            //        this.mPubNub.whereNow(new Callback() {
////            @Override
////            public void successCallback(String channel, Object message) {
////                super.successCallback(channel, message);
////            }
////        });
//        } catch (PubnubException e) {
//            e.printStackTrace();
//        }
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
//
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
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