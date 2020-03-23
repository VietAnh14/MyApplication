package com.example.crApp.utils;

public interface EpsonReceiver {
    void onError(int errorCode, String error);
    void onPrintSuccess();
}
