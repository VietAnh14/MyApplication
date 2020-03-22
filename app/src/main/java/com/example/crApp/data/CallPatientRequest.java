package com.example.crApp.data;

import com.example.crApp.utils.Utils;
import com.google.gson.annotations.SerializedName;

public class CallPatientRequest {
    @SerializedName("DeparmentCode")
    private String departmentCode;
    @SerializedName("Type")
    private String type;
    @SerializedName("Limit")
    private
    String limit = "1";
    @SerializedName("RequestedDate")
    private String requestDate = Utils.getDate();

    public CallPatientRequest(String departmentCode, String type, String limit, String requestDate) {
        this.departmentCode = departmentCode;
        this.type = type;
        this.limit = limit;
        this.requestDate = requestDate;
    }

    public CallPatientRequest() {
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
}
