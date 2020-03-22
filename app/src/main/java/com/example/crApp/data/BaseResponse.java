package com.example.crApp.data;

// 2:57 AM and this fucked me

public class BaseResponse<T> {
    private T result;
    private Boolean success;
    private Integer code;

    public BaseResponse(T result, Boolean success, Integer code) {
        this.result = result;
        this.success = success;
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BaseResponse() {
    }

}
