package com.example.crApp.utils;//package com.dolphinsolutions.kioskbooking.utils;
//
//import android.util.Log;
//
//import com.google.gson.Gson;
//import com.microsoft.signalr.HubConnection;
//import com.microsoft.signalr.HubConnectionBuilder;
//import com.microsoft.signalr.HubConnectionState;
//import com.microsoft.signalr.OnClosedCallback;
//
//import java.util.HashMap;
//import java.util.Objects;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class SignalrSingleton {
//    public static final String TAG = "SIGNALR";
//    private static final SignalrSingleton ourInstance = new SignalrSingleton();
//    private HashMap<String, SignalrRequest> requests;
//    public HubConnection hubConnection;
//    String deviceId;
//    Gson gson;
//
//    public static SignalrSingleton getInstance() {
//        return ourInstance;
//    }
//
//    private SignalrSingleton() {
//        deviceId = "";
//        this.requests = new HashMap<>();
//        this.gson = new Gson();
//    }
//
//    public void initialize(String url, String deviceId) {
//        hubConnection = HubConnectionBuilder.create(url).build();
//        this.deviceId = deviceId;
//        hubConnection.onClosed(new OnClosedCallback() {
//            @Override
//            public void invoke(Exception exception) {
//                if (exception != null) {
//                    Log.d(TAG, "onClosed: " + exception.getMessage());
//                    exception.printStackTrace();
//                    while (!startSignalrService()) {
//                        Log.d(TAG, "Reconnect Fail");
//                    }
//                } else {
//                    Log.d(TAG, "onClosed: Stop service" );
//                }
//            }
//        });
//    }
//
//    public boolean startSignalrService() {
//        hubConnection.start().blockingAwait();
//        if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
//            String _conId = hubConnection.getConnectionId();
//            Log.d(TAG, "Connected: " + _conId);
//            hubConnection.invoke("InitDevice", _conId, this.deviceId);
//            return true;
//        }
//        Log.d(TAG, "Connect fail");
//        return false;
//    }
//
//    public void stopSignalrService() {
//        hubConnection.stop();
//    }
//
//    public void sendMessage(final SignalrRequest signalrRequest) {
//        if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
//            hubConnection.invoke("PushDataBE", this.deviceId, signalrRequest.getReceiver(), gson.toJson(signalrRequest));
//        }
//        addRequest(signalrRequest);
//
//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                if (requests.containsKey(signalrRequest.getID().toString()))
//                {
//                    SignalrRequest retryRequest = requests.get(signalrRequest.getID().toString());
//                    if (Objects.requireNonNull(retryRequest).getSentTime() < 3)
//                    {
//                        sendMessage(retryRequest);
//                    }
//                }
//            }
//        };
//    }
//
//    public void messageReceiveAck(String deviceId, String message) {
//        if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
//            hubConnection.invoke("MessageRecive", deviceId, message);
//        }
//    }
//
//    public void addRequest(SignalrRequest signalrRequest) {
//        if (requests.containsKey(signalrRequest.getID().toString())) {
//            this.requests.put(signalrRequest.getID().toString(), signalrRequest);
//        } else {
//            Objects.requireNonNull(this.requests.get(signalrRequest.getID().toString())).increaseSent();
//        }
//    }
//
//    public void removeRequest(String signalrRequestId) {
//        if (requests.containsKey(signalrRequestId)) {
//            this.requests.remove(signalrRequestId);
//        }
//    }
//
//}
