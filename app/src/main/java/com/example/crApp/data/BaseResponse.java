package com.example.crApp.data;

public class BaseResponse<T> {
    private T data;
    private Meta metadata;

    public BaseResponse(T data, Meta metadata) {
        this.data = data;
        this.metadata = metadata;
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public Meta getMetadata() {
        return metadata;
    }

    public static class Meta {
        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
