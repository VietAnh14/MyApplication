package com.example.crApp.utils;//package com.dolphinsolutions.kioskbooking.utils;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.Binder;
//import android.os.IBinder;
//import android.util.Log;
//
//import com.dolphinsolutions.kioskbooking.MainActivity;
//import com.google.gson.Gson;
//import com.microsoft.signalr.Action1;
//import com.microsoft.signalr.Action2;
//
//public class SignalrService extends Service {
//    public static final String DEVICE_ID = "deviceId";
//    public static final String TAG = "SignalrService";
//
//    private final IBinder mBinder = new LocalBinder(); // Binder given to clients
//    public final static String URL = "url";
//    Gson gson;
//
//    public SignalrService() {
//        gson = new Gson();
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        int result = super.onStartCommand(intent, flags, startId);
//        String url = intent.getStringExtra(URL);
//        String deviceId = intent.getStringExtra(DEVICE_ID);
//        SignalrSingleton.getInstance().initialize(url, deviceId);
//        SignalrSingleton.getInstance().startSignalrService();
//
//        SignalrSingleton.getInstance().hubConnection.on("ClientReceiveFromBe", new Action2<String, String>() {
//
//            @Override
//            public void invoke(String from, String data) {
//                Log.d(TAG, "onReceived: " + from + " " + data);
//                SignalrRequest signalrRequest = gson.fromJson(data, SignalrRequest.class);
//                SignalrSingleton.getInstance().messageReceiveAck(from, signalrRequest.getID().toString());
//                Log.d(TAG, "Message data payload: " + signalrRequest.getData());
//                Intent intent = new Intent(MainActivity.ACTION_REFRESH);
//                intent.putExtra("body", signalrRequest.getData().getBody());
//                intent.putExtra("title", signalrRequest.getData().getTitle());
//                getApplication().sendBroadcast(intent);
//            }
//        }, String.class, String.class);
//
//        SignalrSingleton.getInstance().hubConnection.on("CheckLostClientReceive", new Action1<String>() {
//
//                @Override
//                public void invoke(String requestId) {
//                    Log.d(TAG, "onReceived: " + requestId);
//                    SignalrSingleton.getInstance().removeRequest(requestId);
//                }
//            }, String.class);
//
//        return result;
//    }
//
//    @Override
//    public void onDestroy() {
//        SignalrSingleton.getInstance().stopSignalrService();
//        super.onDestroy();
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // Return the communication channel to the service.
//
//        return mBinder;
//    }
//
//    /**
//     * Class used for the client Binder.  Because we know this service always
//     * runs in the same process as its clients, we don't need to deal with IPC.
//     */
//    public class LocalBinder extends Binder {
//        public SignalrService getService() {
//            // Return this instance of SignalRService so clients can call public methods
//            return SignalrService.this;
//        }
//    }
//}
