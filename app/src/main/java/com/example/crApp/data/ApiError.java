package com.example.crApp.data;

public class ApiError {
    int code;
    String message;
    boolean success;

    public ApiError() {
    }

    public ApiError(int code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
