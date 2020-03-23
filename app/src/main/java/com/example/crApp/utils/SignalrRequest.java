package com.example.crApp.utils;

import java.util.UUID;

public class SignalrRequest {
    private UUID ID;
    private SignalrData Data;
    private int SentTime;
    private String Receiver;

    public SignalrRequest(SignalrData data, String receiver) {
        this.ID = UUID.randomUUID();
        this.Data = data;
        this.SentTime = 0;
        this.Receiver = receiver;
    }

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public SignalrData getData() {
        return Data;
    }

    public void setData(SignalrData data) {
        this.Data = data;
    }

    public int getSentTime() {
        return SentTime;
    }

    public void setSentTime(int sentTime) {
        this.SentTime = sentTime;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        this.Receiver = receiver;
    }

    public void increaseSent() {
        this.SentTime++;
    }
}
